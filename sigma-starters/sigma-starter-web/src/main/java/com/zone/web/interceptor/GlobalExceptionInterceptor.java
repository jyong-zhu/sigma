package com.zone.web.interceptor;

import com.zone.commons.entity.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/17 10:26 下午
 * @Description:
 */
@Slf4j
// 拦截器，对 controller 进行增强处理
@RestControllerAdvice
public class GlobalExceptionInterceptor {

    @ExceptionHandler(NullPointerException.class)
    public ResponseData<Object> nullPointerException(Exception e) {
        log.error("空指针异常：[{}]", e.getMessage());
        return ResponseData.error(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseData<Object> illegalStateException(Exception e) {
        log.error("illegalState：[{}]", e.getMessage());
        return ResponseData.error(e.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseData<Object> uniqueKeyException(Exception e) {
        log.error("数据库出错：[{}]", e.getMessage());
        return ResponseData.error("数据出错");
    }
}
