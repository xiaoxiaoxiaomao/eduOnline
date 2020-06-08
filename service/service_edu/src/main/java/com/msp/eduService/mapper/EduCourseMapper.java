package com.msp.eduService.mapper;

import com.msp.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msp.eduService.entity.frontVo.CourseWebVo;
import com.msp.eduService.entity.vo.PublishCourseInfo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author msp
 * @since 2020-05-22
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    PublishCourseInfo getPublishCourseInfo(@Param("courseId") String courseId);

    CourseWebVo getBaseCourseInfo(@Param("courseId") String courseId);
}
