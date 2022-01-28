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
      "/sigma-auth/rpc/auth/check",
      "/sigma-auth/account/login",
      "/sigma-auth/account/public-key",
      "/swagger-ui",
      "/swagger-ui/**",
      "/swagger-resources",
      "/swagger-resources/**",
      "/v3/**",
      "/actuator",
      "/actuator/**");

  public static final String AUTHORIZATION = "Authorization";

  public static final String JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

  public static final String UTF_8 = "UTF-8";

  public static final String ACCOUNT_ID = "accountId";

  public static final String ACCOUNT_NAME = "accountName";

  public static final String ACCOUNT_TYPE = "accountType";

  public static final String ROLE_ID_LIST = "roleIdList";

  public static final String PHONE = "phone";

}
