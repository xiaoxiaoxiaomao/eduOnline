package com.msp.eduCenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msp.eduOrder.commonUtils.JwtUtils;
import com.msp.eduOrder.commonUtils.MD5;
import com.msp.eduCenter.entity.UcenterMember;
import com.msp.eduCenter.entity.vo.RegisterVo;
import com.msp.eduCenter.mapper.UcenterMemberMapper;
import com.msp.eduCenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msp.serviceBase.exceptionHandler.EduOnlineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author msp
 * @since 2020-05-29
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public String login(UcenterMember member) {
        //验证
        //获取手机号和密码

        String password = member.getPassword();
        String mobile = member.getMobile();
        //非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new EduOnlineException(20001, "登录失败");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        //查询登录用户是否存在
        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
        if (ucenterMember == null) {
            throw new EduOnlineException(20001, "登录失败");
        }

        //判断密码
        //先将输入密码加密，再比较
        if (!ucenterMember.getPassword().equals(MD5.encrypt(password))) {
            throw new EduOnlineException(20001, "登录失败");
        }

        //判断是否禁用
        if (ucenterMember.getIsDisabled()) {
            throw new EduOnlineException(20001, "登录失败");
        }

        //登录成功,生成token
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return token;
    }

    @Override
    public boolean register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        //非空判断
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password))
        {
            throw new EduOnlineException(20001, "注册失败");
        }

        //判断手机号是否重复
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer result = baseMapper.selectCount(queryWrapper);
        if (result > 0){
            throw new EduOnlineException(20001, "用户已存在");
        }

        //判断验证码
        //获取验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode) ){
            throw new EduOnlineException(20001, "验证码错误");
        }

        //注册
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickname);
        ucenterMember.setPassword(MD5.encrypt(password));//密码需要加密的
        ucenterMember.setIsDisabled(false);//用户不禁用
        ucenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        int i = baseMapper.insert(ucenterMember);
        return i > 0;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
