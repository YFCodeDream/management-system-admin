package com.yfcod.management.constant;

import java.util.HashMap;

public class TableColumnName {
    public static HashMap<String, String> tableColumnNames;

    static {
        tableColumnNames = new HashMap<>();
        tableColumnNames.put("examId", "考试编号");
        tableColumnNames.put("courseId", "课程编号");
        tableColumnNames.put("examDate", "考试日期");
        tableColumnNames.put("startTime", "起始时间");
        tableColumnNames.put("endTime", "结束时间");
        tableColumnNames.put("arrangementAddress", "考试地点");

        tableColumnNames.put("courseName", "课程名称");
        tableColumnNames.put("teacherId", "教师编号");
        tableColumnNames.put("courseAddress", "授课地点");
        tableColumnNames.put("courseDay", "授课日期");
        tableColumnNames.put("courseTimePeriod", "授课时段");

        tableColumnNames.put("studentId", "学生学号");
        tableColumnNames.put("score", "考试成绩");

        tableColumnNames.put("studentName", "姓名");
        tableColumnNames.put("gender", "性别");
        tableColumnNames.put("birthday", "出生日期");
        tableColumnNames.put("nation", "名族");
        tableColumnNames.put("major", "专业");
        tableColumnNames.put("grade", "年级");

        tableColumnNames.put("teacherName", "姓名");
    }
}
