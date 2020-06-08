package com.msp.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msp.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author msp
 * @since 2020-05-13
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> getPartTeacher();

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);
}
