package com.zone.commons.entity;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/13 3:02 下午
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginUser {

  /**
   * 用户id
   */
  private Long accountId;

  /**
   * 用户名称
   */
  private String accountName;

  /**
   * 账号类型
   */
  private Integer accountType;

  /**
   * 角色列表
   */
  private List<Long> roleIdList;

  /**
   * 手机号
   */
  private String phone;

}
