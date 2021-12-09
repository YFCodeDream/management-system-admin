package com.yfcod.management.mapper;

import com.yfcod.management.model.Student;

import java.util.List;

public interface StudentMapper {
    List<Student> queryStudentAll();

    List<Student> queryStudentByConditions(Student student);

    Student queryStudentById(String studentId);

    void addStudent(Student student);

    void updateStudent(Student student);

    void updateStudentPwd(Student student);

    void deleteStudentById(String studentId);
}
