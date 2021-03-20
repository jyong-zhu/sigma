package com.zone.web.interceptor;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.zone.commons.entity.Page;
import com.zone.commons.entity.ResponseData;
import com.zone.web.annotation.SetValue;
import com.zone.web.annotation.SetValueTag;
import com.zone.web.enums.CollectionTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/18 10:49 上午
 * @Description: 对 controller 的返回值进行拦截
 */
@Slf4j
@ControllerAdvice
public class ResponseBodyInterceptor implements ResponseBodyAdvice<ResponseData> {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        // supports 方法用于过滤拦截的方法
        // 以下表示只对返回参数类型为 ResponseData 且有被 @SetValueTag 注解注释的方法进行拦截
        Annotation setValueTag = methodParameter.getMethodAnnotation(SetValueTag.class);
        return methodParameter.getParameterType().isAssignableFrom(ResponseData.class)
                && setValueTag != null;
    }

    @Override
    public ResponseData beforeBodyWrite(ResponseData data, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 拦截 controller 返回的 response data
        // 这里可以对返回体做一些通用逻辑的处理
        // 比如修改返回值、添加返回值、对返回数据加密、根据id去其他微服务中查值等等
        if (data != null) {
            recursiveSetValue(data.getData());
        }
        return data;
    }

    /**
     * 递归设置用户姓名
     */
    private void recursiveSetValue(Object curObj) {
        if (curObj == null) {
            return;
        }

        CollectionTypeEnum type = CollectionTypeEnum.getByName(curObj.getClass().getName());
        // 集合或者Page则往遍历元素并递归
        if (type != null) {
            switch (type) {
                case LIST:
                    List list = (List) curObj;
                    list.forEach(tmp -> recursiveSetValue(tmp));
                    break;
                case SET:
                    Set set = (Set) curObj;
                    set.forEach(tmp -> recursiveSetValue(tmp));
                    break;
                case MAP:
                    Map map = (Map) curObj;
                    map.forEach((key, value) -> {
                        recursiveSetValue(key);
                        recursiveSetValue(value);
                    });
                    break;
                case PAGE:
                    Page page = (Page) curObj;
                    recursiveSetValue(page.getData());
                    break;
            }
        }

        // 以下为非集合和Page的普通类型
        // 判断当前类是否有 @SetValueTag 注解
        Class clazz = curObj.getClass();
        SetValueTag setValueTag = (SetValueTag) clazz.getAnnotation(SetValueTag.class);
        if (setValueTag != null) {
            ReflectionUtils.doWithLocalFields(curObj.getClass(), field -> {
                ReflectionUtils.makeAccessible(field);
                Object fieldObj = ReflectionUtils.getField(field, curObj);

                SetValue setValue = field.getAnnotation(SetValue.class);

                // 请求接口，对需要赋值的字段赋值
                setFieldValue(setValue, field, curObj);

                recursiveSetValue(fieldObj);
            });
        }
    }

    /**
     * 设置指定字段的值
     */
    private void setFieldValue(SetValue setValue, Field targetField, Object curObj) {
        if (setValue != null) {
            try {
                // 获取接口的请求参数, 仅支持单个参数
                Map<String, Object> paramMap = Maps.newHashMap();
                for (int i = 0; i < setValue.fields().length; i++) {
                    Field field = ReflectionUtils.findField(curObj.getClass(), setValue.fields()[i]);
                    if (field != null) {
                        ReflectionUtils.makeAccessible(field);
                        Object value = ReflectionUtils.getField(field, curObj);
                        paramMap.put(setValue.params()[i], value);
                    }
                }

                // 请求获取值
                Object value = queryRpc(setValue, paramMap, targetField.getType());

                // 设值
                ReflectionUtils.makeAccessible(targetField);
                ReflectionUtils.setField(targetField, curObj, value);

            } catch (Exception e) {
                log.error("拦截器设置responseData失败: [{}]", e.getMessage());
            }
        }
    }

    /**
     * 通过 restTemplate 访问接口，请求数据
     */
    private Object queryRpc(SetValue setValue, Map<String, Object> paramMap, Class targetClass) {

        ResponseData responseData;
        if ("get".equals(setValue.type())) {
            // get方法需要改写url路径
            String url = setValue.url() + "?";
            String paramUrl = paramMap.keySet().stream().map(key -> key + "={" + key + "}").collect(Collectors.joining("&"));
            responseData = restTemplate.getForObject(url + paramUrl, ResponseData.class, paramMap);
        } else {
            responseData = restTemplate.postForObject(setValue.url(), paramMap, ResponseData.class);
        }

        // 返回查询的数据
        if (responseData != null && responseData.getSuccess() && responseData.getData() != null) {
            String json = JSONUtil.toJsonStr(responseData.getData());
            // 如果是对象
            if (JSONUtil.isJsonObj(json)) {
                return JSONUtil.toBean(json, targetClass);
            }
            // 如果是数组
            if (JSONUtil.isJsonArray(json)) {
                JSONArray jsonArray = JSONUtil.parseArray(json);
                return jsonArray.toList(setValue.itemClass());
            }
            // 不是json直接返回
            return responseData.getData();
        }
        return null;
    }
}
