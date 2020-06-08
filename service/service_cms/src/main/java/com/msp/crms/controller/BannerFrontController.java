package com.msp.crms.controller;


import com.msp.crms.entity.CrmBanner;
import com.msp.crms.service.CrmBannerService;
import com.msp.eduOrder.commonUtils.MResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author msp
 * @since 2020-05-27
 */
@RestController
@RequestMapping("/eduCrms/crm_banner")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("getBanners")
    public MResult getBanners(){

       List<CrmBanner> list = crmBannerService.getBanners();

        return MResult.ok().data("list",list);
    }
}

