package com.yfcod.management.wrapper;

import com.yfcod.management.model.Teacher;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class TeacherWrapper {
    private StringProperty teacherIdProperty;
    private StringProperty teacherNameProperty;
    private BooleanProperty genderProperty;
    private StringProperty birthdayProperty;
    private StringProperty nationProperty;
    private StringProperty passwordProperty;

    public TeacherWrapper(Teacher teacher) {
        this.teacherIdProperty = new SimpleStringProperty(teacher.getTeacherId());
        this.teacherNameProperty = new SimpleStringProperty(teacher.getTeacherName());
        this.genderProperty = new SimpleBooleanProperty(teacher.getGender());
        this.birthdayProperty = new SimpleStringProperty(teacher.getBirthday().toString());
        this.nationProperty = new SimpleStringProperty(teacher.getNation());
        this.passwordProperty = new SimpleStringProperty(teacher.getPassword());
    }
}
