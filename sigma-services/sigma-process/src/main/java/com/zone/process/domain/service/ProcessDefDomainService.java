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

    // 针对领域服务，以下这些方法需要放到领域服务中：
    // 1. 不是属于单个聚合根的业务或者需要多个聚合根配合的业务，放在领域服务中，注意是业务，如果没有业务，协调工作应该放到应用服务中
    // 2. 静态方法放在领域服务中
    // 3. 需要通过rpc等其它外部服务处理业务的，放在领域服务中

    /**
     * 生成聚合根的id是有领域的概念的，但它不适合放在实体与值对象上，所以放到领域服务中
     * IdWorkerUtil是 starter 中的类，为了简洁，不对这些依赖再抽一层
     */
    public static Long generateId() {
        return IdWorkerUtil.nextId();
    }
}
