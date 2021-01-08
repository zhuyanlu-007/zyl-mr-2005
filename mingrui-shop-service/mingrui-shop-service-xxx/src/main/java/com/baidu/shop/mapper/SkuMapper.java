package com.baidu.shop.mapper;

import com.baidu.shop.dto.SkuDTO;
import com.baidu.shop.entity.SkuEntity;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

/**
 * @ClassName SkuMapper
 * @Description: TODO
 * @Author zhuyanlu
 * @Date 2021/1/7
 * @Version V1.0 7
 **/
public interface SkuMapper extends Mapper<SkuEntity>, InsertListMapper<SkuEntity> , DeleteByIdListMapper<SkuEntity,Long> {

    @Select(value = "select k.*,t.stock from tb_sku k, tb_stock t where k.id = t.sku_id and k.spu_id = #{spuId}")
    List<SkuDTO> getSkusAndStockBySpuId(Integer spuId);
}
