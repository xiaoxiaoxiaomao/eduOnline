package com.msp.eduOrder.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.msp.eduOrder.commonUtils.MResult;
import com.msp.eduOrder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author msp
 * @since 2020-06-01
 */
@RestController
@RequestMapping("/eduOrder/payLog")
@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    //生成微信支付二维码接口
    //参数是订单号
    @GetMapping("createNative/{orderNo}")
    public MResult createNative(@PathVariable String orderNo) {
        //返回信息，包含二维码地址，还有其他需要的信息
        Map map = payLogService.createNatvie(orderNo);
        System.out.println("****返回二维码map集合:"+map);
        return MResult.ok().data(map);
    }

    //支付后查看支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public MResult queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map =  payLogService.queryPayStatus(orderNo);
        if (map == null){
            System.out.println("*******");
            return MResult.error().message("支付出错了");
        }

        //支付成功
        if ("SUCCESS".equals(map.get("trade_state"))){
            //将支付表中添加记录
            //更新订单表的支付状态
            System.out.println("==========");
            payLogService.updateOrdersStatus(map);
            System.out.println("=========2");
            return MResult.ok().message("支付成功");
        }
        return MResult.ok().code(25000).message("支付中");

    }

}

