package com.yfcod.management.model;

import lombok.Data;

@Data
public class Score {
    private Integer examId;
    private String studentId;
    private Integer score;

    public Score(Integer examId) {
        this.examId = examId;
    }

    public Score(String studentId) {
        this.studentId = studentId;
    }

    public Score(Integer examId, String studentId) {
        this.examId = examId;
        this.studentId = studentId;
    }

    public Score(Integer examId, String studentId, Integer score) {
        this.examId = examId;
        this.studentId = studentId;
        this.score = score;
    }
}
