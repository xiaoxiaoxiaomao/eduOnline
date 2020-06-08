package com.msp.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msp.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msp.eduService.entity.frontVo.CourseFrontVo;
import com.msp.eduService.entity.frontVo.CourseWebVo;
import com.msp.eduService.entity.vo.CourseInfoVo;
import com.msp.eduService.entity.vo.PublishCourseInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author msp
 * @since 2020-05-22
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourse(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoById(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    PublishCourseInfo getPublishCourseInfo(String courseId);

    void deleteCourse(String courseId);

    List<EduCourse> getPartCourse();

    Map<String, Object> getFrontCourseList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}

