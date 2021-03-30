package com.zone.process.domain.service;

import com.zone.commons.util.IdWorkerUtil;
import org.springframework.stereotype.Service;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/31 7:28 上午
 * @Description: 生成聚合根的id的领域服务
 */
@Service
public class AggIdentityDomainService {

    /**
     * 生成聚合根的id是有领域的概念的，但它不适合放在实体与值对象上
     * 因为生成id不是实体和值对象的一种行为，所以放到领域服务中
     * IdWorkerUtil 是 starter 中的工具类，为了简洁，不对这些依赖再抽一层
     */
    public static Long generateInstAggId() {
        return IdWorkerUtil.nextId();
    }

    public static Long generateDefAggId() {
        return IdWorkerUtil.nextId();
    }

    public static Long generateCategoryAggId() {
        return IdWorkerUtil.nextId();
    }

    public static Long generateFormAggId() {
        return IdWorkerUtil.nextId();
    }
}
