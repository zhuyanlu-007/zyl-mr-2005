package com.baidu.shop.mapper;

import com.baidu.shop.entity.StockEntity;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @ClassName StockMapper
 * @Description: TODO
 * @Author zhuyanlu
 * @Date 2021/1/7
 * @Version V1.0 7
 **/
public interface StockMapper extends Mapper<StockEntity>, DeleteByIdListMapper<StockEntity,Long> {
}
