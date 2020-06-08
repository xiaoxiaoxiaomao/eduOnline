package com.msp.eduService.client.impl;

import com.msp.eduOrder.commonUtils.MResult;
import com.msp.eduService.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 熔断机制定义
 */
@Component
public class VodClientImpl implements VodClient {
    @Override
    public MResult deleteVideoById(String id) {
        return MResult.error().message("删除视频出错");
    }

    @Override
    public MResult deleteMoreVideos(List<String> ids) {
        return MResult.error().message("删除多个视频出错");
    }
}
