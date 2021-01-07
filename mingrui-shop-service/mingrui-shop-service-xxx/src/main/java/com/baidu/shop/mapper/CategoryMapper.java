package com.baidu.shop.mapper;

import com.baidu.shop.entity.CategoryEntity;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @ClassName CategoryMapper
 * @Description: TODO
 * @Author zhuyanlu
 * @Date 2020/12/22
 * @Version V1.0
 **/
public interface CategoryMapper extends Mapper<CategoryEntity> , SelectByIdListMapper<CategoryEntity,Integer> {

    //接口 + 实现类 + xml
    //接口 + xml
    //接口 + 注解(@Select + @Insert .....)
    @Select(value = "select id,name from tb_category where id in (select category_id from tb_category_brand where brand_id=#{brandId})")
    List<CategoryEntity> getCategoryByBrandId(Integer brandId);
}
