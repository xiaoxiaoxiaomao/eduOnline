package com.msp.eduCenter.controller;


import com.msp.eduOrder.commonUtils.JwtUtils;
import com.msp.eduOrder.commonUtils.MResult;
import com.msp.eduCenter.entity.UcenterMember;
import com.msp.eduCenter.entity.vo.RegisterVo;
import com.msp.eduCenter.service.UcenterMemberService;
import com.msp.eduOrder.commonUtils.orderVo.UcenterMemberOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author msp
 * @since 2020-05-29
 */
@RestController
@RequestMapping("/eduCenter/ucenter_member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    //登录
    @PostMapping("login")
    public MResult login(@RequestBody UcenterMember member) {
        //service
        //返回token值，使用jwt生成
        String token = ucenterMemberService.login(member);
        return MResult.ok().data("token", token);
    }

    //注册
    @PostMapping("register")
    public MResult register(@RequestBody RegisterVo registerVo) {
        boolean result = ucenterMemberService.register(registerVo);

            return MResult.ok();


    }

    //获取token的用户信息
    @GetMapping("getTokenInfo")
    public MResult getTokenInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(memberId);
        return MResult.ok().data("memberInfo", member);
    }

    //根据id获取用户信息给订单
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember member = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //查询某一天的注册人数
    @GetMapping("countRegister/{day}")
    public MResult countRegister(@PathVariable String day){
        Integer registerNum = ucenterMemberService.countRegisterDay(day);
        return MResult.ok().data("registerNum",registerNum);
    }

}

