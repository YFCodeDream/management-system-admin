package com.yfcod.management.controller;

import com.yfcod.management.Main;
import com.yfcod.management.dao.ArrangementDao;
import com.yfcod.management.dao.TimetableDao;
import com.yfcod.management.model.Arrangement;
import com.yfcod.management.model.Course;
import com.yfcod.management.model.Score;
import com.yfcod.management.model.Timetable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static com.yfcod.management.util.GenerateAlertUtil.showAlert;

public abstract class BaseController {
    public abstract void setMainApp(Main main);

    public abstract void setDialogStage(Stage primaryStage);

    @SuppressWarnings("DuplicatedCode")
    protected void queryArrangement(TextField examIdArrangementField,
                                    TextField courseIdArrangementField,
                                    TextField examDateArrangementField,
                                    TextField startTimeArrangementField,
                                    TextField endTimeArrangementField,
                                    TextField addressArrangementField,
                                    ComboBox<String> addressArrangementBox,
                                    ObservableList<Arrangement> arrangements2,
                                    CheckBox isOverdueBox,
                                    TableView<Arrangement> arrangementTableView,
                                    Stage primaryStage,
                                    String studentId) {
        try {
            Integer examId = examIdArrangementField.getText().equals("") ?
                    null : Integer.parseInt(examIdArrangementField.getText());
            Integer courseId = courseIdArrangementField.getText().equals("") ?
                    null: Integer.parseInt(courseIdArrangementField.getText());
            Date examDate = examDateArrangementField.getText().equals("") ?
                    null: Date.valueOf(examDateArrangementField.getText());
            Time startTime = startTimeArrangementField.getText().equals("") ?
                    null : Time.valueOf(startTimeArrangementField.getText());
            Time endTime = endTimeArrangementField.getText().equals("") ?
                    null: Time.valueOf(endTimeArrangementField.getText());
            String addressClassNum = addressArrangementField.getText().equals("") ?
                    null: addressArrangementField.getText();
            String addressBuildingNum = addressArrangementBox.getValue();

            String address = null;
            if (addressClassNum != null && !addressBuildingNum.equals("请选择")) {
                address = (addressBuildingNum + "号楼" + addressClassNum);
            }

            List<Arrangement> arrangements = ArrangementDao.queryArrangementByConditions(
                    new Arrangement(
                            examId,
                            courseId,
                            examDate,
                            startTime,
                            endTime,
                            address
                    )
            );

            arrangements2.clear();

            ArrayList<Arrangement> arrangementList = new ArrayList<>();
            if (isOverdueBox.isSelected()) {
                for (Arrangement arrangement : arrangements) {
                    if (arrangement.getExamDate().before(
                            Date.valueOf(String.valueOf(new Date(System.currentTimeMillis()))))
                    ) {
                        arrangementList.add(arrangement);
                    }
                    if (arrangement.getExamDate().equals(
                            Date.valueOf(String.valueOf(new Date(System.currentTimeMillis()))))
                    ) {
                        if (arrangement.getEndTime().before(
                                Time.valueOf(String.valueOf(new Time(System.currentTimeMillis())))
                        ))
                            arrangementList.add(arrangement);
                    }
                }
                if (studentId != null) {
                    List<Timetable> timetables = TimetableDao.queryTimetableByConditions(
                            new Timetable(studentId)
                    );
                    for (Timetable timetable : timetables) {
                        Integer currentCourseId = timetable.getCourseId();
                        for (Arrangement arrangement : arrangementList) {
                            if (arrangement.getCourseId().equals(currentCourseId)) {
                                arrangements2.add(arrangement);
                            }
                        }
                    }
                } else {
                    arrangements2.addAll(arrangementList);
                }
            } else {
                if (studentId != null) {
                    List<Timetable> timetables = TimetableDao.queryTimetableByConditions(
                            new Timetable(studentId)
                    );
                    for (Timetable timetable : timetables) {
                        Integer currentCourseId = timetable.getCourseId();
                        for (Arrangement arrangement : arrangements) {
                            if (arrangement.getCourseId().equals(currentCourseId)) {
                                arrangements2.add(arrangement);
                            }
                        }
                    }
                } else {
                    arrangements2.addAll(arrangements);
                }
            }

            arrangementTableView.setItems(arrangements2);
        } catch (IllegalArgumentException e) {
            showAlert(primaryStage,
                    "请输入正确的数值",
                    "error");
        }
    }

    protected void arrangementCellValueFactory(TableColumn<Arrangement, String> examIdArrangementColumn,
                                                      TableColumn<Arrangement, String> courseIdArrangementColumn,
                                                      TableColumn<Arrangement, String> examDateArrangementColumn,
                                                      TableColumn<Arrangement, String> startTimeArrangementColumn,
                                                      TableColumn<Arrangement, String> endTimeArrangementColumn,
                                                      TableColumn<Arrangement, String> addressArrangementColumn) {
        examIdArrangementColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getExamId().toString()));
        courseIdArrangementColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCourseId().toString()));
        examDateArrangementColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getExamDate().toString()));
        startTimeArrangementColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStartTime().toString()));
        endTimeArrangementColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEndTime().toString()));
        addressArrangementColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAddress()));
    }

    protected void courseCellValueFactory(TableColumn<Course, String> courseIdCourseColumn,
                                                 TableColumn<Course, String> courseNameCourseColumn,
                                                 TableColumn<Course, String> teacherIdCourseColumn,
                                                 TableColumn<Course, String> addressCourseColumn,
                                                 TableColumn<Course, String> courseDayCourseColumn,
                                                 TableColumn<Course, String> courseTimePeriodCourseColumn) {
        courseIdCourseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCourseId().toString()));
        courseNameCourseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCourseName()));
        teacherIdCourseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTeacherId()));
        addressCourseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAddress()));
        courseDayCourseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCourseDay().toString()));
        courseTimePeriodCourseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCourseTimePeriod().toString()));
    }

    protected static void scoreCellValueFactory(TableColumn<Score, String> examIdScoreColumn,
                                                TableColumn<Score, String> studentIdScoreColumn,
                                                TableColumn<Score, String> scoreScoreColumn) {
        examIdScoreColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getExamId().toString()));
        studentIdScoreColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStudentId()));
        scoreScoreColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getScore().toString()));
    }

    protected <T> void setColumnPrefWidth(TableView<T> tableView) {
        int columnSize = tableView.getColumns().size();
        for (int i = 0; i < columnSize; i++) {
            tableView.getColumns().get(i).prefWidthProperty()
                    .bind(tableView.widthProperty().multiply(1.0 / columnSize));
        }
    }

    protected void showAbout(Stage primaryStage) {
        showAlert(primaryStage,
                "关于\n厦门大学考试管理系统\nV1.0.0\n作者：YFCodeDream",
                "information");
    }
}
