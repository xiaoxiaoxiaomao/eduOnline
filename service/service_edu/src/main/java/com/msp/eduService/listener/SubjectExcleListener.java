package com.msp.eduService.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msp.eduService.entity.EduSubject;
import com.msp.eduService.entity.excle.ExcleSubjectData;
import com.msp.eduService.service.EduSubjectService;
import com.msp.serviceBase.exceptionHandler.EduOnlineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SubjectExcleListener extends AnalysisEventListener<ExcleSubjectData> {
    @Autowired
    private EduSubjectService eduSubjectService;

    @Override
    public void invoke(ExcleSubjectData excleSubjectData, AnalysisContext analysisContext) {
        if (excleSubjectData == null){
            throw new EduOnlineException(20001,"文件数据为空");
        }

        EduSubject oneSubject = this.existOneSubject(excleSubjectData.getOneSubjectName());
        if (oneSubject == null){
            oneSubject = new EduSubject();
            oneSubject.setTitle(excleSubjectData.getOneSubjectName());
            oneSubject.setParentId("0");
            eduSubjectService.save(oneSubject);
        }

        String pid = oneSubject.getId();

        EduSubject twoSubject = this.existTwoSubject(excleSubjectData.getTwoSubjectName(), pid);
        if (twoSubject == null){
            twoSubject = new EduSubject();
            twoSubject.setTitle(excleSubjectData.getTwoSubjectName());
            twoSubject.setParentId(pid);
            eduSubjectService.save(twoSubject);
        }


    }

    private EduSubject existOneSubject(String oneSubjectName){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",oneSubjectName);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }

    private EduSubject existTwoSubject(String oneSubjectName,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",oneSubjectName);
        wrapper.eq("parent_id",pid);
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
