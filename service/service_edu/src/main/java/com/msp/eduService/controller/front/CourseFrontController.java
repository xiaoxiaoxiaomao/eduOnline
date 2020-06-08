package com.msp.eduService.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msp.eduOrder.commonUtils.JwtUtils;
import com.msp.eduOrder.commonUtils.MResult;
import com.msp.eduService.client.OrderClient;
import com.msp.eduService.entity.EduCourse;
import com.msp.eduService.entity.courseTree.ChapterVo;
import com.msp.eduService.entity.frontVo.CourseFrontVo;
import com.msp.eduService.entity.frontVo.CourseWebVo;
import com.msp.eduService.service.EduChapterService;
import com.msp.eduService.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduService/courseFront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrderClient orderClient;

    /**
     * 分页查询前端课程
     * @param page
     * @param limit
     * @param courseFrontVo
     * @return
     */
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public MResult getFrontCourseList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo){

        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String,Object> map = courseService.getFrontCourseList(coursePage,courseFrontVo);
        return MResult.ok().data(map);
    }

    //2 课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public MResult getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //查询课程是否已购买
        //用户id查询

        boolean isBuy = orderClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.findChapter(courseId);

        return MResult.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",isBuy);
    }
}

