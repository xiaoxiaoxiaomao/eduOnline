package com.msp.statistics.entity.vo;

import lombok.Data;

/**
 * 统计因子
 */
@Data
public class SearchObject {
    //统计因子
    private String type;
    //开始时间
    private String begin;
    //结束时间
    private String end;
}
