package com.msp.eduOrder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msp.eduOrder.commonUtils.JwtUtils;
import com.msp.eduOrder.commonUtils.MResult;
import com.msp.eduOrder.entity.Order;
import com.msp.eduOrder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author msp
 * @since 2020-06-01
 */
@RestController
@RequestMapping("/eduOrder/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    //添加订单
    @PostMapping("createOrder/{courseId}")
    public MResult saveOrder(@PathVariable String courseId, HttpServletRequest request){
        //订单信息需要课程信息和用户信息
        //用户id通过token查询
        //返回一个订单号供查询使用
        String orderNo = orderService.createOrder(courseId,JwtUtils.getMemberIdByJwtToken(request));
        return MResult.ok().data("orderId",orderNo);
    }
    //根据订单号查询订单
    @GetMapping("getOrderInfo/{orderNo}")
    public MResult getOrderInfo(@PathVariable String orderNo){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        return MResult.ok().data("item",order);
    }

    //根据课程id和用户id查询课程是否被购买
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if (count > 0){
            return true;
        }
        return false;
    }

}

