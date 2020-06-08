package com.msp.eduService.controller.front;

import com.msp.eduService.entity.EduCourse;
import com.msp.eduService.entity.EduTeacher;
import com.msp.eduService.service.EduCourseService;
import com.msp.eduService.service.EduTeacherService;
import com.msp.eduOrder.commonUtils.MResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("eduService/indexFront")
@CrossOrigin
public class IndexFrontController {
    
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("index")
    public MResult index(){
        List<EduTeacher> teacherList = eduTeacherService.getPartTeacher();
        //获取前八课程
        List<EduCourse> courseList = eduCourseService.getPartCourse();
        //获取前四名讲师


        return MResult.ok().data("teacherList",teacherList).data("courseList",courseList);
    }
}
