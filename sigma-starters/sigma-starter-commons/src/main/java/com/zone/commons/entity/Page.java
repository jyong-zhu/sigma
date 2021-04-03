package com.zone.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/13 11:58 上午
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {

    private List<T> data;

    private Long pageNo;

    private Long pageSize;

    private Long totalSize;

    public <R> Page<R> convert(Function<? super T, ? extends R> mapper) {
        List<R> collect = this.getData().stream().map(mapper).collect(Collectors.toList());
        Page<R> page = new Page<>();
        page.setData(collect);
        page.setTotalSize(this.totalSize);
        page.setPageNo(this.pageNo);
        page.setPageSize(this.pageSize);
        return page;
    }

}
