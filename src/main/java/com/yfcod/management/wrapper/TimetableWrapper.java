package com.yfcod.management.wrapper;

import com.yfcod.management.model.Timetable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class TimetableWrapper {
    private StringProperty studentIdProperty;
    private IntegerProperty courseIdProperty;

    public TimetableWrapper(Timetable timetable) {
        this.studentIdProperty = new SimpleStringProperty(timetable.getStudentId());
        this.courseIdProperty = new SimpleIntegerProperty(timetable.getCourseId());
    }
}
