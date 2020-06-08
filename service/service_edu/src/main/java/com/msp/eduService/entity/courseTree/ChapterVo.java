package com.msp.eduService.entity.courseTree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {
    private String id;
    private String title;
    private List<VideoVo> videoVos = new ArrayList<>();
}
