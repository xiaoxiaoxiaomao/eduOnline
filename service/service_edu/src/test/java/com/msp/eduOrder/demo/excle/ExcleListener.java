package com.msp.eduOrder.demo.excle;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcleListener extends AnalysisEventListener<DemoData> {
    //一行一行的读取表的内容
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
    System.out.println("*****"+demoData);
    }
    //读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
       System.out.println("****"+headMap);
    }
    //读取完之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
