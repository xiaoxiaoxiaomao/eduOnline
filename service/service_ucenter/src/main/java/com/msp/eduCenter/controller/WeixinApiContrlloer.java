package com.msp.eduCenter.controller;

import com.msp.eduCenter.utils.WeixinProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;

@Controller
@RequestMapping("api/ucenter/wx")
@CrossOrigin
public class WeixinApiContrlloer {
    //生成微信二维码
    @GetMapping("login")
    public String getWxCode(){
        //通过官方链接生成二维码
        //https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
      // String url = "https://open.weixin.qq.com/connect/qrconnect?appid="+ WeixinProperties.WX_OPEN_APP_ID+"&redirect_uri="+;
       //拼接字符串
        //固定地址，后面拼接参数
//        String url = "https://open.weixin.qq.com/" +
//                "connect/qrconnect?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";

        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "#wechat_redirect";

        System.out.println(baseUrl);
        //对redirect_url进行URLEncoder编码
        String redirectUrl = WeixinProperties.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        }catch(Exception e) {
        }

        //设置%s里面值
        String url = String.format(baseUrl, WeixinProperties.WX_OPEN_APP_ID, redirectUrl);

        System.out.println(url);

        //重定向到请求微信地址里面
        return "redirect:"+url;
    }
}
