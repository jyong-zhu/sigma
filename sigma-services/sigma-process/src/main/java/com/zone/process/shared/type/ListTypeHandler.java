package com.zone.process.shared.type;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/4/2 1:34 下午
 * @Description:
 */
@Slf4j
public class ListTypeHandler implements VariableTypeHandler<List> {

    @Override
    public List transform(String value) {
        Preconditions.checkState(StrUtil.isNotBlank(value), "参数值不能为空");
        try {
            // 约定列表类型的参数值用,隔开
            List list = Arrays.asList(value.split(","));
            return list;
        } catch (Exception e) {
            log.error("参数类型: List 转换值: [{}] 转换出错: [{}]", value, e.getMessage());
            Preconditions.checkState(false, "参数值类型错误");
            return null;
        }
    }
}
