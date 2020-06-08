package com.msp.statistics.service;

import com.msp.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msp.statistics.entity.vo.SearchObject;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author msp
 * @since 2020-06-03
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void registerCount(String day);

    Map getShowData(SearchObject searchObject);
}
