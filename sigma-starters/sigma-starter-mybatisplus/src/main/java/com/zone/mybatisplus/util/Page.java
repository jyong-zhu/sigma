package com.zone.mybatisplus.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

}
