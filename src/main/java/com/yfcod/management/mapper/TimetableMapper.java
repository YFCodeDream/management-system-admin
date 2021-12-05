package com.yfcod.management.mapper;

import com.yfcod.management.model.Timetable;

import java.util.List;

public interface TimetableMapper {
    List<Timetable> queryTimetableAll();

    List<Timetable> queryTimetableByConditions(Timetable timetable);

    List<Timetable> queryTimetableByStudentId(String studentId);
}
