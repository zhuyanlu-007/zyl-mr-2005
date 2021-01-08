package com.baidu.shop.dto;

import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
/**
 * @ClassName SkuDTO
 * @Description: TODO
 * @Author zhuyanlu
 * @Date 2021/1/7
 * @Version V1.0 7
 **/
@ApiModel(value = "SKU属性数据传输类")
@Data
public class SkuDTO {

    @ApiModelProperty(value = "主键", example = "1")
    @NotNull(message = "主键不能为空",groups = {MingruiOperation.Update.class})
    private Long id;

    @ApiModelProperty(value = "spu主键", example = "1")
    @NotNull(message = "Spu主键不能为空",groups = {MingruiOperation.Update.class})
    private Integer spuId;

    @ApiModelProperty(value = "商品标题")
    @NotEmpty(message = "商品标题不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String title;

    @ApiModelProperty(value = "商品的图片，多个图片以‘,’分割")
    private String images;

    @ApiModelProperty(value = "销售价格，单位为分", example = "1")
    @NotNull(message = "销售价格不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Integer price;

    @ApiModelProperty(value = "特有规格属性在spu属性模板中的对应下标组合")
    private String indexes;

    @ApiModelProperty(value = "sku的特有规格参数键值对，json格式，反序列化时请使用linkedHashMap，保证有序")
    private String ownSpec;

    //注意此处使用boolean值来接,在service中处理一下就可以了
    @ApiModelProperty(value = "是否有效，0无效，1有效", example = "1")
    @NotNull(message = "是否有效不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Boolean enable;

    @ApiModelProperty(value = "添加时间")
    @NotNull(message = "创建时间不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Date createTime;

    @ApiModelProperty(value = "最后修改时间")
    @NotNull(message = "最后修改时间不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Date lastUpdateTime;

    //方便接受页面传递过来的参数
    @ApiModelProperty(value = "库存")
    @NotNull(message = "库存不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Integer stock;


}
