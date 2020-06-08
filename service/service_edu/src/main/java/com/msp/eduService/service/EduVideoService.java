package com.msp.eduService.service;

import com.msp.eduService.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author msp
 * @since 2020-05-22
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteVideo(String courseId);
}
