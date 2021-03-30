package com.zone.process.application.service.command;

import com.zone.commons.entity.LoginUser;
import com.zone.process.application.service.command.cmd.TaskOperateCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/27 10:43 上午
 * @Description:
 */
@Slf4j
@Service
public class ProcessTaskCmdService {


    public Boolean operate(TaskOperateCommand operateCommand, LoginUser loginUser) {
        return null;
    }
}
