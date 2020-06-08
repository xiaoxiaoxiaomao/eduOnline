package com.msp.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msp.eduService.entity.EduTeacher;
import com.msp.eduService.mapper.EduTeacherMapper;
import com.msp.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author msp
 * @since 2020-05-13
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    @Cacheable(key = "'teacherList'",value = "teacher")
    @Override
    public List<EduTeacher> getPartTeacher() {
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = baseMapper.selectList(teacherQueryWrapper);
        return teacherList;
    }

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage) {

        baseMapper.selectPage(teacherPage, null);
        List<EduTeacher> records = teacherPage.getRecords();
        long current = teacherPage.getCurrent();
        long total = teacherPage.getTotal();
        long size = teacherPage.getSize();
        long pages = teacherPage.getPages();
        boolean hasNext = teacherPage.hasNext();
        boolean hasPrevious = teacherPage.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }


}
