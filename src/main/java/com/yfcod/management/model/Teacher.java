package com.yfcod.management.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Teacher {
    private String teacherId;
    private String teacherName;
    private Boolean gender;
    private Date birthday;
    private String nation;
    private String password;

    public Teacher() {
    }

    public Teacher(String teacherId, String password) {
        this.teacherId = teacherId;
        this.password = password;
    }

    public Teacher(String teacherId, String teacherName, Boolean gender, Date birthday, String nation) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.gender = gender;
        this.birthday = birthday;
        this.nation = nation;
    }

    public Teacher(String teacherId, String teacherName, Boolean gender, Date birthday, String nation, String password) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.gender = gender;
        this.birthday = birthday;
        this.nation = nation;
        this.password = password;
    }
}
