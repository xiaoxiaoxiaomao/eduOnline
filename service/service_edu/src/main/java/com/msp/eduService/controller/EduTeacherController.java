package com.msp.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msp.eduOrder.commonUtils.MResult;
import com.msp.eduService.entity.EduTeacher;
import com.msp.eduService.entity.vo.TeacherQuery;
import com.msp.eduService.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author msp
 * @since 2020-05-13
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduService/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询讲师表所有数据
     *
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public MResult findAllTeacher() {

        List<EduTeacher> teachers = teacherService.list(null);
        return MResult.ok().data("teachers", teachers);
    }

    /**
     * 逻辑删除讲师
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public MResult removeTeacher(@ApiParam(name = "id", value = "讲师ID") @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return MResult.ok();
        }
        return MResult.error();
    }

    /**
     * 分页查询讲师
     *
     * @param current
     * @param limit
     * @return
     */
    @GetMapping("pageTeacher/{current}/{limit}")
    public MResult pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher, null);
        List<EduTeacher> records = pageTeacher.getRecords();
        long total = pageTeacher.getTotal();
        return MResult.ok().data("total", total).data("records", records);
    }

    /**
     * 条件分页查询
     *
     * @param current
     * @param limit
     * @param teacherQuery
     * @return
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public MResult pageTeacherCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.gt("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        wrapper.orderByDesc("gmt_create");
        teacherService.page(pageTeacher, wrapper);
        List<EduTeacher> records = pageTeacher.getRecords();
        long total = pageTeacher.getTotal();
        return MResult.ok().data("total", total).data("records", records);
    }

    /**
     * 添加讲师
     *
     * @param eduTeacher
     * @return
     */
    @PostMapping("addTeacher")
    public MResult addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.save(eduTeacher);
        if (flag) {
            return MResult.ok();
        }
        return MResult.error();
    }

    /**
     * 根据讲师id查询
     *
     * @param id
     * @return
     */
    @GetMapping("getTeacher/{id}")
    public MResult getTeacher(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return MResult.ok().data("teacher", teacher);
    }

    /**
     * 修改讲师信息
     * @param teacher
     * @return
     */
    @PostMapping("updateTeacher")
    public MResult updateTeacher(@RequestBody EduTeacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        if (flag) {
            return MResult.ok();
        }
        return MResult.error();
    }

}

