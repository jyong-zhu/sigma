package com.zone.auth.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 5:01 下午
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum ResourceTypeEnum {

  MENU(1, "菜单栏资源点"),
  INTERFACE(2, "接口权限资源点"),
  DEFAULT(0, "未知类型"),
  ;

  private Integer code;
  private String desc;

  public static ResourceTypeEnum getByCode(Integer code) {
    for (ResourceTypeEnum tmp : ResourceTypeEnum.values()) {
      if (tmp.getCode().equals(code)) {
        return tmp;
      }
    }
    return DEFAULT;
  }
}
