package com.yfcod.management.model;

import lombok.Data;

@Data
public class Timetable {
    private String studentId;
    private Integer courseId;

    public Timetable(String studentId, Integer courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }
}
