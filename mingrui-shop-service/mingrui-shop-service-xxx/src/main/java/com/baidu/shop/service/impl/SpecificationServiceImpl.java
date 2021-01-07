package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpecGroupDTO;
import com.baidu.shop.dto.SpecParamDTO;
import com.baidu.shop.entity.SpecGroupEntity;
import com.baidu.shop.entity.SpecParamEntity;
import com.baidu.shop.mapper.SpecGroupMapper;
import com.baidu.shop.mapper.SpecParamMapper;
import com.baidu.shop.service.SpecificationService;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.baidu.shop.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


/**
 * @ClassName SpecificationServiceImpl
 * @Description: TODO
 * @Author zhuyanlu
 * @Date 2021/1/4
 * @Version V1.0 7
 **/
@RestController
public class SpecificationServiceImpl extends BaseApiService implements SpecificationService {

    @Resource
    private SpecGroupMapper specGroupMapper;


    @Resource
    private SpecParamMapper specParamMapper;

    //查询//品牌
    @Override
    public Result<List<SpecGroupEntity>> getSepcGroupInfo(SpecGroupDTO specGroupDTO) {

        Example example = new Example(SpecGroupEntity.class);
        example.createCriteria().andEqualTo("cid",
                BaiduBeanUtil.copyProperties(specGroupDTO, SpecGroupEntity.class).getCid());

        List<SpecGroupEntity> specGroupEntities = specGroupMapper.selectByExample(example);
        return this.setResultSuccess(specGroupEntities);
    }

    //新增
    @Override
    @Transactional
    public Result<JSONObject> saveSpecGroup(SpecGroupDTO specGroupDTO) {

        specGroupMapper.insertSelective(BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class));

        return this.setResultSuccess();
    }

    //修改
    @Override
    public Result<JSONObject> editSpecGroup(SpecGroupDTO specGroupDTO) {

        specGroupMapper.updateByPrimaryKeySelective(BaiduBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class));
        return this.setResultSuccess();
    }

    //删除
    @Override
    @Transactional
    public Result<JSONObject> deleteSpecGroupById(Integer id) {

        Example example = new Example(SpecParamEntity.class);
        example.createCriteria().andEqualTo("groupId",id);
        List<SpecParamEntity>specParamEntities=specParamMapper.selectByExample(example);
        if (specParamEntities.size()>0){
            return this.setResultError("该规格组有数据无法删除");
        }

        specGroupMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }


    //------------------------------------------------------------------------------------------规格参数
    //通过条件查询规格参数
    @Override
    @Transactional
    public Result<List<SpecParamEntity>> getSpecParamInfo(SpecParamDTO specParamDTO) {

        SpecParamEntity specParamEntity = BaiduBeanUtil.copyProperties(specParamDTO, SpecParamEntity.class);
        Example example = new Example(SpecParamEntity.class);
        Example.Criteria criteria = example.createCriteria();

        if(ObjectUtil.isNotNull(specParamEntity.getGroupId()))
            criteria.andEqualTo("groupId",specParamEntity.getGroupId());
        if(ObjectUtil.isNotNull(specParamEntity.getCid()))
            criteria.andEqualTo("cid",specParamEntity.getCid());

        List<SpecParamEntity> specParamEntities = specParamMapper.selectByExample(example);

        return this.setResultSuccess(specParamEntities);
    }

    //新增
    @Override
    @Transactional
    public Result<JSONObject> saveSpecParam(SpecParamDTO specParamDTO) {

        specParamMapper.insertSelective(BaiduBeanUtil.copyProperties(specParamDTO,SpecParamEntity.class));

        return this.setResultSuccess();
    }

    //修改
    @Override
    @Transactional
    public Result<JSONObject> editSpecParam(SpecParamDTO specParamDTO) {

        specParamMapper.updateByPrimaryKeySelective(BaiduBeanUtil.copyProperties(specParamDTO,SpecParamEntity.class));
        return this.setResultSuccess();
    }

    //删除
    @Override
    @Transactional
    public Result<JSONObject> deleteSpecParam(Integer id) {

        specParamMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }

}