package com.zone.commons.consts;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/15 10:33 下午
 * @Description:
 */
public class GatewayConstants {

    public static final List<String> whiteList = Lists.newArrayList(
            "/user/login",
            "/swagger/api-docs",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/doc.html",
            "/v2/api-docs/**");

    public static final String Authorization = "Authorization";

    public static final String jsonContentType = "application/json;charset=UTF-8";


}