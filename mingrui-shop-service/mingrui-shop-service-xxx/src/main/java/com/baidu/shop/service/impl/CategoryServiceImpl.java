package com.baidu.shop.service.impl;

import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryBrandEntity;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.mapper.CategoryBrandMapper;
import com.baidu.shop.mapper.CategoryMapper;
import com.baidu.shop.service.CategoryService;
import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.ObjectUtil;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CategoryServiceImpl
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/12/22
 * @Version V1.0
 **/
@RestController
public class CategoryServiceImpl extends BaseApiService implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    @Override//查询
    public Result<List<CategoryEntity>> getCategoryByBrandId(Integer brandId) {

        List<CategoryEntity> list = categoryMapper.getCategoryByBrandId(brandId);

        return this.setResultSuccess(list);
    }

    //新增
    @Transactional
    @Override
    public Result<JsonObject> addCategoryById(CategoryEntity categoryEntity) {

        CategoryEntity parentCategoryEntity = new CategoryEntity();
        parentCategoryEntity.setId(categoryEntity.getParentId());
        parentCategoryEntity.setIsParent(1);
        categoryMapper.updateByPrimaryKeySelective(parentCategoryEntity);

        categoryMapper.insertSelective(categoryEntity);

        return this.setResultSuccess();
    }

    //修改
    @Transactional
    //@Transient
    @Override
    public Result<JsonObject> editCategoryById(CategoryEntity categoryEntity) {
        categoryMapper.updateByPrimaryKeySelective(categoryEntity);
        return this.setResultSuccess();
    }

    //查询
    @Override
    public Result<List<CategoryEntity>> getCategoryByPid(Integer pid) {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setParentId(pid);

        List<CategoryEntity> list = categoryMapper.select(categoryEntity);
        return this.setResultSuccess(list);
    }

    //删除
    @Transactional //以后增删改方法必须加这个注解
    @Override
    public Result<JsonObject> deleteCategoryById(Integer id) {

        //校验id是否合法
        if(ObjectUtil.isNull(id) || id <= 0) return this.setResultError(HTTPStatus.OPERATION_ERROR,"id不合法");
        CategoryEntity categoryEntity = categoryMapper.selectByPrimaryKey(id);
        if(ObjectUtil.isNull(categoryEntity)) return this.setResultError(HTTPStatus.OPERATION_ERROR,"数据不存在");
        if(categoryEntity.getIsParent() == 1) return this.setResultError(HTTPStatus.OPERATION_ERROR,"当前节点为父节点");


        Example example1 = new Example(CategoryEntity.class);
        example1.createCriteria().andEqualTo("parentId", categoryEntity.getParentId());

        List<CategoryBrandEntity> categoryBrandEntities = categoryBrandMapper.selectByExample(example1);
        if(categoryBrandEntities.size() != 0){
            return this.setResultError("该属性已被品牌绑定不能删除");
        }

        //通过当前节点的父节点id查询当前节点的父节点下是否还有其他子节点;
        Example example = new Example(CategoryEntity.class);
        //前边格式固定 拼接sql语句   where 1 = 1; select form 表明 where 1=1 and parentId=?
        example.createCriteria().andEqualTo("parentId", categoryEntity.getParentId());
        //将拼接后的sql返回到list集合内
        List<CategoryEntity> categoryEntities = categoryMapper.selectByExample(example);



        //如果size <= 1 --> 如果当前节点被删除的话 当前节点的父节点下没有节点了 --> 将当前节点的父节点状态改为叶子节点
        if(categoryEntities.size() <= 1){
            CategoryEntity updateCategoryEntity = new CategoryEntity();
            updateCategoryEntity.setIsParent(0);
            updateCategoryEntity.setId(categoryEntity.getParentId());

            categoryMapper.updateByPrimaryKeySelective(updateCategoryEntity);
        }
        //通过id删除节点
        categoryMapper.deleteByPrimaryKey(id);

        return this.setResultSuccess();
    }
}
