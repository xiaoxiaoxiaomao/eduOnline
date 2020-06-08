package com.msp.eduService.entity.subjectTree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级标题
 */
@Data
public class OneSubject {

    private String id;
    private String title;

    private List<TwoSubject> twoSubjects = new ArrayList<>();
}
