package com.yfcod.management.wrapper;

import com.yfcod.management.model.Course;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class CourseWrapper {
    private IntegerProperty courseIdProperty;
    private StringProperty courseNameProperty;
    private StringProperty teacherIdProperty;
    private StringProperty addressProperty;
    private IntegerProperty courseDayProperty;
    private IntegerProperty courseTimePeriodProperty;

    public CourseWrapper(Course course) {
        this.courseIdProperty = new SimpleIntegerProperty(course.getCourseId());
        this.courseNameProperty = new SimpleStringProperty(course.getCourseName());
        this.teacherIdProperty = new SimpleStringProperty(course.getTeacherId());
        this.addressProperty = new SimpleStringProperty(course.getAddress());
        this.courseDayProperty = new SimpleIntegerProperty(course.getCourseDay());
        this.courseTimePeriodProperty = new SimpleIntegerProperty(course.getCourseTimePeriod());
    }
}
