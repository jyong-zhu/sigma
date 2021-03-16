package com.zone.mybatisplus.util;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/13 12:00 下午
 * @Description:
 */
public final class PlusPageConverter {

    public static <T> Page<T> convert(IPage<T> iPage) {
        Page<T> page = new Page<>();

        page.setData(iPage.getRecords());
        page.setPageNo(iPage.getCurrent());
        page.setPageSize(iPage.getSize());
        page.setTotalSize(iPage.getTotal());

        return page;
    }
}