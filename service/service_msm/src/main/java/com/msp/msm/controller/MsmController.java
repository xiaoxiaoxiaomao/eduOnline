package com.msp.msm.controller;

import com.msp.eduOrder.commonUtils.MResult;
import com.msp.msm.service.MsmService;
import com.msp.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("eduMsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("send/{phone}")
    public MResult sendMessage(@PathVariable String phone){
        System.out.println("++++++++++++++++");
        //先从redis中获取验证码
        String code = (String) redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return MResult.ok();
        }

        //获取不到，发送阿里云短信服务
        code = RandomUtil.getFourBitRandom();
        HashMap<String, Object> param = new HashMap<>();
        param.put("code",code);

        boolean isSend = msmService.sendMsg(param,phone);
        if (isSend){
            //发送成功，将验证码存入redis中，并设置有效时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return MResult.ok();
        }else {
            return MResult.error().message("发送短信失败");
        }

    }
}
