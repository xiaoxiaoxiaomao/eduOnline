package com.msp.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msp.eduService.entity.EduChapter;
import com.msp.eduService.entity.EduVideo;
import com.msp.eduService.entity.courseTree.ChapterVo;
import com.msp.eduService.entity.courseTree.VideoVo;
import com.msp.eduService.mapper.EduChapterMapper;
import com.msp.eduService.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msp.eduService.service.EduVideoService;
import com.msp.serviceBase.exceptionHandler.EduOnlineException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author msp
 * @since 2020-05-22
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> findChapter(String courseId) {
        //查询章节
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(chapterWrapper);

        //查询小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideos = eduVideoService.list(videoWrapper);


        ArrayList<ChapterVo> chapterVos = new ArrayList<>();

        for (int i = 0; i < eduChapters.size(); i++) {
            EduChapter eduChapter = eduChapters.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);

            ArrayList<VideoVo> videoVos = new ArrayList<>();
            for (int m = 0; m < eduVideos.size(); m++) {
                EduVideo eduVideo = eduVideos.get(m);
                if (eduVideo.getChapterId().equals(chapterVo.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setVideoVos(videoVos);
            chapterVos.add(chapterVo);
        }
        return chapterVos;
    }

    /**
     * 章节删除
     * @param chapterId
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        //查询章节中的小结
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(videoQueryWrapper);
        //是否存在小节
        if (count > 0){
            //yes,不能删除
            throw new EduOnlineException(20001,"无法删除，请先删除小节");
        }else {
            //no,不查询，直接删除
            int result = baseMapper.deleteById(chapterId);
            return result >0;
        }
    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_Id",courseId);
        baseMapper.delete(wrapper);
    }
}
