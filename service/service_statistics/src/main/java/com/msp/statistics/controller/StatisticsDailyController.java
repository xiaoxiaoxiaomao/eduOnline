package com.msp.statistics.controller;


import com.msp.eduOrder.commonUtils.MResult;
import com.msp.statistics.entity.vo.SearchObject;
import com.msp.statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author msp
 * @since 2020-06-03
 */
@RestController
@RequestMapping("/statistics/daily")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService dailyService;

    //按天统计
    @PostMapping("registerCount/{day}")
    public MResult registerCount(@PathVariable String day){
        dailyService.registerCount(day);
        return MResult.ok();
    }

    @PostMapping("showData")
    public MResult showData(@RequestBody SearchObject searchObject){
        Map map = dailyService.getShowData(searchObject);
        return MResult.ok().data(map);
    }

}

