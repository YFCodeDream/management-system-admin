package com.yfcod.management.mapper;

import com.yfcod.management.model.Teacher;

import java.util.List;

public interface TeacherMapper {
    List<Teacher> queryTeacherAll();

    List<Teacher> queryTeacherByConditions(Teacher teacher);

    Teacher queryTeacherById(String teacherId);

    void addTeacher(Teacher teacher);

    void updateTeacher(Teacher teacher);

    void updateTeacherPwd(Teacher teacher);

    void deleteTeacherById(String teacherId);
}
