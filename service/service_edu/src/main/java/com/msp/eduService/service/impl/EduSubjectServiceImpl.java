package com.msp.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msp.eduService.entity.EduSubject;
import com.msp.eduService.entity.excle.ExcleSubjectData;
import com.msp.eduService.entity.subjectTree.OneSubject;
import com.msp.eduService.entity.subjectTree.TwoSubject;
import com.msp.eduService.listener.SubjectExcleListener;
import com.msp.eduService.mapper.EduSubjectMapper;
import com.msp.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author msp
 * @since 2020-05-21
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Autowired
    private SubjectExcleListener subjectExcleListener;

    @Override
    public void saveSubject(MultipartFile file) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, ExcleSubjectData.class, subjectExcleListener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", 0);
        List<EduSubject> oneEduSubjects = baseMapper.selectList(wrapperOne);

        //二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", 0);
        List<EduSubject> twoEduSubjects = baseMapper.selectList(wrapperTwo);



        //封装最终的数据
        ArrayList<OneSubject> finalSubjects = new ArrayList<>();
        for (EduSubject oneEduSubject : oneEduSubjects) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(oneEduSubject,oneSubject);


            ArrayList<TwoSubject> twoSubjects = new ArrayList<>();
            for (EduSubject twoEduSubject : twoEduSubjects) {
                if (twoEduSubject.getParentId().equals(oneSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoEduSubject,twoSubject);
                    twoSubjects.add(twoSubject);
                }
            }

            oneSubject.setTwoSubjects(twoSubjects);
            finalSubjects.add(oneSubject);
        }


        return finalSubjects;
    }
}
