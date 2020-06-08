package com.msp.eduService.controller;

import com.msp.eduOrder.commonUtils.MResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eduService/user")
@CrossOrigin
public class EduLoginController {
    @RequestMapping("login")
    public MResult login(){
        return MResult.ok().data("token","admin");
    }

    @RequestMapping("info")
    public MResult info(){
        return MResult.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
