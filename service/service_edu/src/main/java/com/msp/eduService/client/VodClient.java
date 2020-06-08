package com.msp.eduService.client;

import com.msp.eduOrder.commonUtils.MResult;
import com.msp.eduService.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod" ,fallback = VodClientImpl.class)
@Component
public interface VodClient {
    @DeleteMapping("/eduvod/video/deleteVideo/{id}")
    MResult deleteVideoById(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/video/deleteVideos")
    MResult deleteMoreVideos(@RequestParam("ids") List<String> ids);
}
