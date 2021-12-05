package com.yfcod.management.wrapper;

import com.yfcod.management.model.Arrangement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class ArrangementWrapper {
    private IntegerProperty examIdProperty;
    private IntegerProperty courseIdProperty;
    private StringProperty examDateProperty;
    private StringProperty startTimeProperty;
    private StringProperty endTimeProperty;
    private StringProperty addressProperty;

    public ArrangementWrapper(Arrangement arrangement) {
        this.examIdProperty = new SimpleIntegerProperty(arrangement.getExamId());
        this.courseIdProperty = new SimpleIntegerProperty(arrangement.getCourseId());
        this.examDateProperty = new SimpleStringProperty(arrangement.getExamDate().toString());
        this.startTimeProperty = new SimpleStringProperty(arrangement.getStartTime().toString());
        this.endTimeProperty = new SimpleStringProperty(arrangement.getEndTime().toString());
        this.addressProperty = new SimpleStringProperty(arrangement.getAddress());
    }
}
