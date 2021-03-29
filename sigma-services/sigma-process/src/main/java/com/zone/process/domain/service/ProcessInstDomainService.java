package com.zone.process.domain.service;

import com.zone.commons.util.IdWorkerUtil;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 5:04 下午
 * @Description:
 */
@Service
public class ProcessInstDomainService {

    public static Long generateId() {
        return IdWorkerUtil.nextId();
    }
}
