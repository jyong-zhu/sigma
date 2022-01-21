package com.zone.auth.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 1:25 下午
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum AccountTypeEnum {
  ADMIN_USER(1, "超管"),
  NORMAL_USER(2, "普通用户"),
  DEFAULT(0, "未知用户");

  private Integer code;
  private String desc;

  public static AccountTypeEnum getByCode(Integer code) {
    for (AccountTypeEnum tmp : AccountTypeEnum.values()) {
      if (tmp.getCode().equals(code)) {
        return tmp;
      }
    }
    return DEFAULT;
  }

  /**
   * 判断是否为管理员
   */
  public static boolean isAdmin(Integer code) {
    return ADMIN_USER.getCode().equals(code);
  }
}
