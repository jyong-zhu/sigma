package com.zone.commons.entity;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/15 10:58 下午
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData<T> implements Serializable {

    private Boolean success;

    private Integer code;

    private String msg;

    private T data;

    public static <T> ResponseData<T> ok(T data) {
        return new ResponseData<>(true, HttpStatus.HTTP_OK, "success", data);
    }

    public static <T> ResponseData<T> ok(T data, String msg) {
        return new ResponseData<>(true, HttpStatus.HTTP_OK, msg, data);
    }

    public static <T> ResponseData<T> ok(T data, Integer code, String msg) {
        return new ResponseData<>(true, code, msg, data);
    }

    public static <T> ResponseData<T> error(String msg) {
        return new ResponseData<>(false, HttpStatus.HTTP_BAD_REQUEST, msg, null);
    }

    public static <T> ResponseData<T> error(T data, String msg) {
        return new ResponseData<>(false, HttpStatus.HTTP_BAD_REQUEST, msg, data);
    }

    public static <T> ResponseData<T> error(Integer code, String msg) {
        return new ResponseData<>(false, code, msg, null);
    }

    public static <T> ResponseData<T> error(T data, Integer code, String msg) {
        return new ResponseData<>(false, code, msg, data);
    }
}
