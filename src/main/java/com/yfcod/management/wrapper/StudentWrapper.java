package com.yfcod.management.wrapper;

import com.yfcod.management.model.Student;
import javafx.beans.property.*;
import lombok.Data;

@Data
public class StudentWrapper {
    private StringProperty studentIdProperty;
    private StringProperty studentNameProperty;
    private BooleanProperty genderProperty;
    private StringProperty birthdayProperty;
    private StringProperty nationProperty;
    private StringProperty majorProperty;
    private IntegerProperty gradeProperty;
    private StringProperty passwordProperty;

    public StudentWrapper(Student student) {
        this.studentIdProperty = new SimpleStringProperty(student.getStudentId());
        this.studentNameProperty = new SimpleStringProperty(student.getStudentName());
        this.genderProperty = new SimpleBooleanProperty(student.getGender());
        this.birthdayProperty = new SimpleStringProperty(student.getBirthday().toString());
        this.nationProperty = new SimpleStringProperty(student.getNation());
        this.majorProperty = new SimpleStringProperty(student.getMajor());
        this.gradeProperty = new SimpleIntegerProperty(student.getGrade());
        this.passwordProperty = new SimpleStringProperty(student.getPassword());
    }
}
