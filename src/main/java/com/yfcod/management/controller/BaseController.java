package com.yfcod.management.controller;

import com.yfcod.management.Main;
import com.yfcod.management.dao.*;
import com.yfcod.management.model.*;
import com.yfcod.management.util.SendMailUtil;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    protected void baseArrangementSelectedData(Arrangement selectedArrangement, TextField examIdArrangementField, TextField courseIdArrangementField, TextField examDateArrangementField, TextField startTimeArrangementField, TextField endTimeArrangementField, TextField addressArrangementField, ComboBox<String> addressArrangementBox) {
        if (selectedArrangement == null) {
            examIdArrangementField.setText("");
            courseIdArrangementField.setText("");
            examDateArrangementField.setText("");
            startTimeArrangementField.setText("");
            endTimeArrangementField.setText("");
            addressArrangementField.setText("");
            return;
        }
        examIdArrangementField.setText(String.valueOf(selectedArrangement.getExamId()));
        courseIdArrangementField.setText(String.valueOf(selectedArrangement.getCourseId()));
        examDateArrangementField.setText(selectedArrangement.getExamDate().toString());
        startTimeArrangementField.setText(selectedArrangement.getStartTime().toString());
        endTimeArrangementField.setText(selectedArrangement.getEndTime().toString());

        String[] split = selectedArrangement.getAddress().split("号楼");

        addressArrangementField.setText(split[1]);
        addressArrangementBox.getSelectionModel().select(split[0]);
    }

    protected void baseCourseSelectedData(Course selectedCourse, TextField courseIdCourseField, TextField courseNameCourseField, TextField addressCourseField, ComboBox<String> addressCourseBox, TextField courseDayCourseField, TextField courseTimePeriodCourseField) {
        courseIdCourseField.setText(selectedCourse == null ? "" : String.valueOf(selectedCourse.getCourseId()));
        courseNameCourseField.setText(selectedCourse == null ? "" : selectedCourse.getCourseName());

        if (selectedCourse == null) {
            addressCourseField.setText("");
        } else {
            String[] split = selectedCourse.getAddress().split("号楼");
            addressCourseField.setText(split[1]);
            addressCourseBox.getSelectionModel().select(split[0]);
        }

        courseDayCourseField.setText(selectedCourse == null ? "" : String.valueOf(selectedCourse.getCourseDay()));
        courseTimePeriodCourseField.setText(
                selectedCourse == null ? "" : String.valueOf(selectedCourse.getCourseTimePeriod())
        );
    }

    protected void updateInfo(Stage primaryStage, String currentIdentity, String currentUserId) {
        Dialog<HashMap<String, String>> dialog = new Dialog<>();
        dialog.setTitle("修改个人信息");
        dialog.setHeaderText("修改个人信息");

        ButtonType submitButtonType = new ButtonType("提交", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 20, 20));

        PasswordField firstPwd = new PasswordField();
        firstPwd.setPromptText("密码");
        PasswordField secondPwd = new PasswordField();
        secondPwd.setPromptText("确认密码");

        grid.add(new Label("密码："), 0, 0);
        grid.add(firstPwd, 1, 0);
        grid.add(new Label("确认密码："), 0, 1);
        grid.add(secondPwd, 1, 1);

        TextField adminPhoneField = null;
        if (currentIdentity.equals("系统管理员")) {
            adminPhoneField = new TextField();
            adminPhoneField.setPromptText("电话号码");
            grid.add(new Label("电话号码："), 0, 2);
            grid.add(adminPhoneField, 1, 2);
        }

        Node submitButton = dialog.getDialogPane().lookupButton(submitButtonType);
        submitButton.setDisable(true);

        firstPwd.textProperty().addListener((observable, oldValue, newValue) ->
                submitButton.setDisable(newValue.trim().isEmpty()));

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(firstPwd::requestFocus);

        TextField finalAdminPhoneField = adminPhoneField;
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                HashMap<String, String> updateInfoMap = new HashMap<>();
                updateInfoMap.put("firstPwd", firstPwd.getText());
                updateInfoMap.put("secondPwd", secondPwd.getText());
                if (finalAdminPhoneField != null) {
                    updateInfoMap.put("adminPhone", finalAdminPhoneField.getText());
                }
                return updateInfoMap;
            }
            return null;
        });

        Optional<HashMap<String, String>> result = dialog.showAndWait();
        result.ifPresent(updateInfoMap -> {
            if (firstPwd.getText().isEmpty() || firstPwd.getText() == null) {
                showAlert(primaryStage,
                        "请输入新密码",
                        "warning");
                return;
            }
            if (!firstPwd.getText().equals(secondPwd.getText())) {
                showAlert(primaryStage,
                        "两次输入密码不一致",
                        "warning");
                return;
            }
            switch (currentIdentity) {
                case "系统管理员":
                    String adminPhone = updateInfoMap.get("adminPhone");
                    if (adminPhone == null || adminPhone.isEmpty()) {
                        AdminDao.updateAdmin(
                                new Admin(currentUserId, updateInfoMap.get("firstPwd"), null)
                        );
                    } else {
                        if (adminPhone.length() != 11) {
                            showAlert(primaryStage,
                                    "手机号不足11位",
                                    "warning");
                            return;
                        }
                        AdminDao.updateAdmin(
                                new Admin(currentUserId, updateInfoMap.get("firstPwd"), adminPhone)
                        );
                    }
                    break;
                case "教师":
                    TeacherDao.updateTeacherPwd(
                            new Teacher(currentUserId, updateInfoMap.get("firstPwd"))
                    );
                    break;
                case "学生":
                    StudentDao.updateStudentPwd(
                            new Student(currentUserId, updateInfoMap.get("firstPwd"))
                    );
                    break;
            }
            showAlert(primaryStage,
                    "修改密码成功",
                    "information");
        });
    }

    protected void inputMailAddressAndSend(Stage primaryStage,
                                    String subTitle,
                                    String excelTempFilePath) {
        TextInputDialog mailAddressDialog = new TextInputDialog();
        mailAddressDialog.setTitle("输入发送至邮箱地址");
        mailAddressDialog.setHeaderText("输入发送至邮箱地址");
        mailAddressDialog.setContentText("请输入邮箱");

        Optional<String> result = mailAddressDialog.showAndWait();
        if (result.isPresent()) {
            try {
                SendMailUtil.sendMail(
                        result.get(),
                        subTitle,
                        excelTempFilePath
                );
                showAlert(primaryStage,
                        "邮件发送成功，请查收",
                        "information");
            } catch (IllegalArgumentException e) {
                showAlert(primaryStage,
                        "邮箱输入格式不正确，请重新输入",
                        "warning");
            }
        }
    }

    protected void showAbout(Stage primaryStage) {
        showAlert(primaryStage,
                "关于\n厦门大学考试管理系统\nV1.0.0\n作者：YFCodeDream",
                "information");
    }
}
