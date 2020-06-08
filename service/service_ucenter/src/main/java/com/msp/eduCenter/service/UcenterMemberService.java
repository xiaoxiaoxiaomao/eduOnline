package com.msp.eduCenter.service;

import com.msp.eduCenter.entity.UcenterMember;
import com.msp.eduCenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author msp
 * @since 2020-05-29
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    boolean register(RegisterVo registerVo);

    Integer countRegisterDay(String day);
}
