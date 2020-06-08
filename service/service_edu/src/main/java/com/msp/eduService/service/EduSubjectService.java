package com.msp.eduService.service;

import com.msp.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msp.eduService.entity.subjectTree.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author msp
 * @since 2020-05-21
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file);

    List<OneSubject> getAllOneTwoSubject();
}

