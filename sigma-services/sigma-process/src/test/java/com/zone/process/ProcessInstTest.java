package com.zone.process;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.zone.process.application.service.command.cmd.InstStartCommand;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/1 5:03 下午
 * @Description:
 */
public class ProcessInstTest {

    @Test
    public void start() {

        Map<Long, Map<String, Object>> paramMap = Maps.newHashMap();
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("user", "2,3,4");
        paramMap.put(1L, dataMap);

        InstStartCommand instStartCommand = new InstStartCommand()
                .setDefId(1406311885439008L)
                .setDescription("发起流程实例")
                .setComment("这是发起流程实例的评论")
                .setName("维修机器工单")
                .setDueTime(LocalDateTimeUtil.toEpochMilli(LocalDateTime.now().plusDays(10)))
                .setFormDataMap(paramMap);

        System.out.println(JSONUtil.toJsonStr(instStartCommand));
    }
}