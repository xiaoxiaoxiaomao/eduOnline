package com.msp.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msp.eduOrder.commonUtils.MResult;
import com.msp.statistics.client.UcenterClient;
import com.msp.statistics.entity.StatisticsDaily;
import com.msp.statistics.entity.vo.SearchObject;
import com.msp.statistics.mapper.StatisticsDailyMapper;
import com.msp.statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author msp
 * @since 2020-06-03
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        //先删除之前插入的数据，避免同一天由多个统计记录
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        //查询某日注册用户个数等信息
        MResult mResult = ucenterClient.countRegister(day);
        Integer registerCount = (Integer) mResult.getData().get("registerNum");


        //将统计数据插入到统计表中
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(registerCount);
        //统计日期
        sta.setDateCalculated(day);

        sta.setVideoViewNum(RandomUtils.nextInt(100, 200));
        sta.setLoginNum(RandomUtils.nextInt(100, 200));
        sta.setCourseNum(RandomUtils.nextInt(100, 200));

        baseMapper.insert(sta);
    }

    /**
     * 生成统计图
     *
     * @param searchObject
     * @return
     */
    @Override
    public Map getShowData(SearchObject searchObject) {

        String begin = searchObject.getBegin();
        String end = searchObject.getEnd();
        String type = searchObject.getType();
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);
        wrapper.orderByAsc("date_calculated");

        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> dataList = new ArrayList<>();

        for (int i = 0; i < staList.size(); i++) {
            //封装记录的时间
            StatisticsDaily daily = staList.get(i);
            dateList.add(daily.getDateCalculated());
            //封装数量
            switch (type) {
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }

        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("dateList",dateList);
        map.put("dataList",dataList);
        return map;
    }
}
