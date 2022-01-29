package com.zone.auth.domain.agg;

import com.google.common.base.Preconditions;
import com.zone.auth.application.service.command.cmd.AccountChangeCommand;
import com.zone.auth.application.service.command.cmd.AccountUpdateCommand;
import com.zone.auth.shared.enums.AccountTypeEnum;
import com.zone.commons.entity.LoginUser;
import com.zone.commons.util.SecurityUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: jianyong.zhu
 * @Date: 2022/1/21 11:47 上午
 * @Description: 账号聚合根
 */
@Data
@Accessors(chain = true)
public class AccountAgg {

  @ApiModelProperty(value = "主键")
  private Long id;

  @ApiModelProperty(value = "姓名")
  private String name;

  @ApiModelProperty(value = "手机号")
  private String phone;

  @ApiModelProperty(value = "密码，采用哈希算法进行单向加密")
  private String password;

  @ApiModelProperty(value = "1-超管 2-非超管")
  private AccountTypeEnum accountType;

  @ApiModelProperty(value = "邮箱")
  private String email;

  @ApiModelProperty(value = "角色id列表")
  private List<Long> roleIdList;

  @ApiModelProperty(value = "0-停用 1-正常")
  private Boolean status;

  @ApiModelProperty(value = "user_id")
  private Long createBy;

  @ApiModelProperty(value = "user_name")
  private String createName;

  @ApiModelProperty(value = "user_id")
  private Long updateBy;

  @ApiModelProperty(value = "user_name")
  private String updateName;


  /**
   * 新建账号 初始化数据
   */
  public void init(LoginUser loginUser) {
    // 默认密码：123456
    this.setPassword(SecurityUtil.digestSha1("123456"));
    this.setAccountType(AccountTypeEnum.NORMAL_USER);
    this.setStatus(true);
  }

  /**
   * 更新账号
   */
  public void update(AccountUpdateCommand updateCommand) {
    this.setEmail(updateCommand.getEmail());
    this.setName(updateCommand.getName());
    this.setStatus(updateCommand.getStatus());
  }

  /**
   * 修改个人信息
   */
  public void change(AccountChangeCommand changeCommand) {
    String oldPwd = SecurityUtil.digestSha1(SecurityUtil.rsaDecrypt(changeCommand.getOldPwd()));
    String newPwd = SecurityUtil.digestSha1(SecurityUtil.rsaDecrypt(changeCommand.getNewPwd()));

    Preconditions.checkState(this.getPassword().equals(oldPwd), "旧密码不正确");
    this.setPassword(newPwd);
    this.setName(changeCommand.getName());
    this.setEmail(changeCommand.getEmail());
  }
}
