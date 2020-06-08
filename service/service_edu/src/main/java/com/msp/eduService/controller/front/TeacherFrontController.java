package com.msp.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msp.eduOrder.commonUtils.MResult;
import com.msp.eduService.entity.EduCourse;
import com.msp.eduService.entity.EduTeacher;
import com.msp.eduService.service.EduCourseService;
import com.msp.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduService/teacherFront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    //前台讲师
    @PostMapping("/getTeacherFrontList/{current}/{limit}")
    public MResult getTeacherFrontList(@PathVariable long current,@PathVariable long limit){
        Page<EduTeacher> teacherPage = new Page<>(current,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(teacherPage);
        return MResult.ok().data(map);
    }

    //讲师详情 以及所讲课程
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public MResult getTeacherFrontInfo(@PathVariable String teacherId){

        EduTeacher eduTeacher = teacherService.getById(teacherId);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return MResult.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }
}
