package com.yfcod.management.wrapper;

import com.yfcod.management.model.Score;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class ScoreWrapper {
    private IntegerProperty examIdProperty;
    private StringProperty studentIdProperty;
    private IntegerProperty scoreProperty;

    public ScoreWrapper(Score score) {
        this.examIdProperty = new SimpleIntegerProperty(score.getScore());
        this.studentIdProperty = new SimpleStringProperty(score.getStudentId());
        this.scoreProperty = new SimpleIntegerProperty(score.getScore());
    }
}
