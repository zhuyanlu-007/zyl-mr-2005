package com.baidu.shop.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @ClassName Result
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/12/22
 * @Version V1.0
 **/
@Data // 生成set 和get函数
@NoArgsConstructor //生成无参构造函数
public class Result<T> {

    private Integer code;//返回码

    private String message;//返回消息

    private T data;//返回数据

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = (T) data;
    }
}
