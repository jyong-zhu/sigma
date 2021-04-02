package com.zone.process.shared.type;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/2 1:25 下午
 * @Description:
 */
public class StringTypeHandler implements VariableTypeHandler<String> {

    @Override
    public String transform(String value) {
        Preconditions.checkState(StrUtil.isNotBlank(value), "参数值不能为空");
        return value;
    }
}
