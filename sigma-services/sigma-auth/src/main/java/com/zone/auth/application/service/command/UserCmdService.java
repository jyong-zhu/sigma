package com.zone.auth.application.service.command;

import com.zone.auth.application.service.command.cmd.UserLoginCommand;
import com.zone.auth.application.service.command.cmd.UserRegisterCommand;
import com.zone.auth.domain.agg.User;
import com.zone.auth.domain.repository.UserAggRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/10 11:15 下午
 * @Description:
 */
@Slf4j
@Service
public class UserCmdService {

    @Autowired
    private UserAggRepository userAggRepository;

    /**
     * 用户登陆校验并返回token
     */
    public String login(UserLoginCommand loginCommand) {
        return null;
    }

    /**
     * 注册用户信息
     */
    public Boolean register(UserRegisterCommand registerCommand, Long userId, String userName) {
        User user = User.create(registerCommand, userId, userName);
        userAggRepository.save(user);
        return true;
    }
}
