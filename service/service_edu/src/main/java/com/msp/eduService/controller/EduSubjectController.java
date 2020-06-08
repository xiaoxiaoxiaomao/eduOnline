package com.msp.eduService.controller;


import com.msp.eduOrder.commonUtils.MResult;
import com.msp.eduService.entity.subjectTree.OneSubject;
import com.msp.eduService.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author msp
 * @since 2020-05-21
 */
@RestController
@RequestMapping("/eduService/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类

    //获取上传的excle文件 读取
    @PostMapping("addSubject")
    public MResult addSubject(MultipartFile file){
        //获取上传文件
        eduSubjectService.saveSubject(file);
        return MResult.ok();
    }

    @GetMapping("getAllSubject")
    public MResult getAllSubject(){
       List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return MResult.ok().data("list",list);
    }
}

