package com.zone.process.domain.service;

import com.zone.commons.util.IdWorkerUtil;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/29 4:34 下午
 * @Description:
 */
@Service
public class ProcessDefDomainService {

    /**
     * 生成聚合根的id是有领域的概念的，但它不适合放在实体与值对象上，所以放到领域服务中
     * IdWorkerUtil是 starter 中的类，为了简洁，不对这些依赖再抽一层
     */
    public static Long generateId() {
        return IdWorkerUtil.nextId();
    }
}
