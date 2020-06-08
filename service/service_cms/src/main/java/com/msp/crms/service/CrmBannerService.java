package com.msp.crms.service;

import com.msp.crms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author msp
 * @since 2020-05-27
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getBanners();
}
