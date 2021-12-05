package com.yfcod.management.mapper;

import com.yfcod.management.model.Course;

import java.util.List;

public interface CourseMapper {
    List<Course> queryCourseAll();

    List<Course> queryCourseByConditions(Course course);

    Course queryCourseById(Integer courseId);

    void addCourse(Course course);

    void updateCourse(Course course);

    void deleteCourseById(Integer courseId);
}
