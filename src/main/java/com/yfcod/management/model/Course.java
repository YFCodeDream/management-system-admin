package com.yfcod.management.model;

import lombok.Data;

@Data
public class Course {
    private Integer courseId;
    private String courseName;
    private String teacherId;
    private String address;
    private Integer courseDay;
    private Integer courseTimePeriod;

    public Course(String teacherId) {
        this.teacherId = teacherId;
    }

    public Course(Integer courseId,
                  String courseName,
                  String teacherId,
                  String address,
                  Integer courseDay,
                  Integer courseTimePeriod) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherId = teacherId;
        this.address = address;
        this.courseDay = courseDay;
        this.courseTimePeriod = courseTimePeriod;
    }
}
