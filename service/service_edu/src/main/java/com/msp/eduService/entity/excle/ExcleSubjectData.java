package com.msp.eduService.entity.excle;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcleSubjectData {
    @ExcelProperty(index = 0)
    private String oneSubjectName;
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
