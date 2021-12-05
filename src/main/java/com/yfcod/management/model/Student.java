package com.yfcod.management.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Student {
    private String studentId;
    private String studentName;
    private Boolean gender;
    private Date birthday;
    private String nation;
    private String major;
    private Integer grade;
    private String password;

    public Student() {
    }

    public Student(String studentId, String studentName, Boolean gender, Date birthday, String nation, String major, Integer grade) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.gender = gender;
        this.birthday = birthday;
        this.nation = nation;
        this.major = major;
        this.grade = grade;
    }

    public Student(String studentId, String studentName, Boolean gender, Date birthday, String nation, String major, Integer grade, String password) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.gender = gender;
        this.birthday = birthday;
        this.nation = nation;
        this.major = major;
        this.grade = grade;
        this.password = password;
    }
}
