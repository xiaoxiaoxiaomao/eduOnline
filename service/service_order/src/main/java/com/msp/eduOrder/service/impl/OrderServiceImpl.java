package com.msp.eduOrder.service.impl;

import com.msp.eduOrder.client.EduClient;
import com.msp.eduOrder.client.UcenterClient;
import com.msp.eduOrder.commonUtils.orderVo.CourseWebVoOrder;
import com.msp.eduOrder.commonUtils.orderVo.UcenterMemberOrder;
import com.msp.eduOrder.entity.Order;
import com.msp.eduOrder.mapper.OrderMapper;
import com.msp.eduOrder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msp.eduOrder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author msp
 * @since 2020-06-01
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private EduClient eduClient;

    @Override
    public String createOrder(String courseId, String memberId) {
        //查询课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //查询用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);

        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1

        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
