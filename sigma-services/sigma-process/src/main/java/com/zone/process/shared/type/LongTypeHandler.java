package com.zone.process.shared.type;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/2 1:37 下午
 * @Description:
 */
@Slf4j
public class LongTypeHandler implements VariableTypeHandler<Long> {

    @Override
    public Long transform(String value) {
        Preconditions.checkState(StrUtil.isNotBlank(value), "参数值不能为空");
        try {
            Long res = Long.valueOf(value);
            return res;
        } catch (Exception e) {
            log.error("参数类型: Long 转换值: [{}] 转换出错: [{}]", value, e.getMessage());
            Preconditions.checkState(false, "参数值类型错误");
            return null;
        }
    }
}
