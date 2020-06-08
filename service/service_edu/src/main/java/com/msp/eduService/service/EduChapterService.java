package com.msp.eduService.service;

import com.msp.eduService.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msp.eduService.entity.courseTree.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author msp
 * @since 2020-05-22
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> findChapter(String courseId);

    boolean deleteChapter(String chapterId);

    void deleteChapterByCourseId(String courseId);
}
