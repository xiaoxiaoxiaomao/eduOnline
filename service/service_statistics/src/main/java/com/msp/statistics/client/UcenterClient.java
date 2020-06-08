package com.msp.statistics.client;

import com.msp.eduOrder.commonUtils.MResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {
    @GetMapping("/eduCenter/ucenter_member/countRegister/{day}")
    MResult countRegister(@PathVariable("day") String day);
}
