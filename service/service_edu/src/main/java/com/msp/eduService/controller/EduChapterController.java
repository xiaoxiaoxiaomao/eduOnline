package com.msp.eduService.controller;


import com.msp.eduOrder.commonUtils.MResult;
import com.msp.eduService.entity.EduChapter;
import com.msp.eduService.entity.courseTree.ChapterVo;
import com.msp.eduService.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/eduService/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("getAllChapter/{courseId}")
    public MResult getAllChapter(@PathVariable String courseId) {
        List<ChapterVo> list = eduChapterService.findChapter(courseId);
        return MResult.ok().data("list", list);
    }

    @PostMapping("addChapter")
    public MResult addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return MResult.ok();
    }

    @GetMapping("getChapterInfo/{chapterId}")
    public MResult getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return MResult.ok().data("eduChapter", eduChapter);
    }

    @PostMapping("updateChapter")
    public MResult updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return MResult.ok();
    }

    @DeleteMapping("deleteChapter/{chapterId}")
    public MResult deleteChapter(@PathVariable String chapterId) {
        boolean result = eduChapterService.deleteChapter(chapterId);
        if (result) {
            return MResult.ok();
        } else {
            return MResult.error();
        }
    }
}

