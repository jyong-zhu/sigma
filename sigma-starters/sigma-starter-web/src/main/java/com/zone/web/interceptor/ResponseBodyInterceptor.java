package com.zone.web.interceptor;

import com.zone.commons.entity.ResponseData;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/18 10:49 上午
 * @Description: 对 controller 的返回值进行拦截
 */
@ControllerAdvice
public class ResponseBodyInterceptor implements ResponseBodyAdvice<ResponseData> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        // supports 方法表示只对返回参数类型为 ResponseData 的数据进行拦截
        return methodParameter.getParameterType().isAssignableFrom(ResponseData.class);
    }

    @Override
    public ResponseData beforeBodyWrite(ResponseData data, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 拦截 controller 返回的 response data
        // 这里可以对返回体做一些通用逻辑的处理
        // 比如修改返回值、添加返回值等等
        // data.setMsg("这是被修改后的返回信息");
        return data;
    }
}
