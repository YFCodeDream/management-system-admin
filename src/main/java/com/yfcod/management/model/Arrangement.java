package com.yfcod.management.model;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class Arrangement {
    private Integer examId;
    private Integer courseId;
    private Date examDate;
    private Time startTime;
    private Time endTime;
    private String address;

    public Arrangement(Integer courseId) {
        this.courseId = courseId;
    }

    public Arrangement(Integer examId,
                       Integer courseId,
                       Date examDate,
                       Time startTime,
                       Time endTime,
                       String address) {
        this.examId = examId;
        this.courseId = courseId;
        this.examDate = examDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
    }
}
