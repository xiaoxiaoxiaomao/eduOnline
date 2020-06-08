package com.msp.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msp.eduOrder.commonUtils.MResult;
import com.msp.eduOrder.commonUtils.orderVo.CourseWebVoOrder;
import com.msp.eduService.entity.EduCourse;
import com.msp.eduService.entity.frontVo.CourseWebVo;
import com.msp.eduService.entity.vo.CourseInfoVo;
import com.msp.eduService.entity.vo.CourseQuery;
import com.msp.eduService.entity.vo.PublishCourseInfo;
import com.msp.eduService.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author msp
 * @since 2020-05-22
 */
@RestController
@RequestMapping("/eduService/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    //分页条件查询
    @PostMapping("getAllCourseCondition/{current}/{limit}")
    public MResult getAllCourse(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> coursePage = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        wrapper.orderByDesc("gmt_create");
        eduCourseService.page(coursePage, wrapper);
        List<EduCourse> courseList = coursePage.getRecords();
        long total = coursePage.getTotal();
        return  MResult.ok().data("courseList",courseList).data("total",total);

    }

    @PostMapping("addCourse")
    public MResult addCourse(@RequestBody CourseInfoVo courseInfoVo){
       String courseId = eduCourseService.addCourse(courseInfoVo);
        return MResult.ok().data("courseId",courseId);
    }

   @GetMapping("getCourse/{courseId}")
    public MResult getCourseInfo(@PathVariable String courseId ){
       CourseInfoVo courseInfoVo = eduCourseService.getCourseInfoById(courseId);
        return MResult.ok().data("courseInfoVo",courseInfoVo);
   }

   @PostMapping("updateCourse")
    public MResult updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return MResult.ok();
   }


   @GetMapping("getPublishCourseInfo/{courseId}")
    public MResult getPublishCourseInfo(@PathVariable String courseId){
       PublishCourseInfo publishCourseInfo = eduCourseService.getPublishCourseInfo(courseId);
       return MResult.ok().data("publishCourseInfo",publishCourseInfo);

   }
    //发布课程，修改状态
   @PostMapping("publishCourse/{courseId}")
    public MResult publishCourse(@PathVariable String courseId){
       EduCourse eduCourse = new EduCourse();
       eduCourse.setId(courseId);
       eduCourse.setStatus("Normal");
       eduCourseService.updateById(eduCourse);
       return MResult.ok();
   }


   //删除课程
    @DeleteMapping("deleteCourse/{courseId}")
    public MResult deleteCourse(@PathVariable String courseId){
        eduCourseService.deleteCourse(courseId);
        return MResult.ok();
    }

    //查询课程信息给订单
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseInfo = eduCourseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }






}

