package com.msp.eduOrder.demo.excle;

import com.alibaba.excel.EasyExcel;

public class ExcleTest {
    public static void main(String[] args) {
        //excle写操作

        /*String fileName = "C:\\Users\\71464\\Desktop\\核酸检测人员信息统计.xlsx";
        ArrayList<DemoData> list = new ArrayList<>();
        DemoData demoData = new DemoData();
        demoData.setSname("沪杭段");
        demoData.setSno(3);
        list.add(demoData);
        EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(list);
    */

    String fileName = "C:\\Users\\71464\\Desktop\\课程分类.xlsx";

    EasyExcel.read(fileName,DemoData.class,new ExcleListener()).sheet().doRead();



    }
}
