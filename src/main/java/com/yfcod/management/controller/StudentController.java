package com.yfcod.management.controller;

import com.yfcod.management.Main;
import com.yfcod.management.dao.ArrangementDao;
import com.yfcod.management.dao.CourseDao;
import com.yfcod.management.dao.ScoreDao;
import com.yfcod.management.dao.TimetableDao;
import com.yfcod.management.model.Arrangement;
import com.yfcod.management.model.Course;
import com.yfcod.management.model.Score;
import com.yfcod.management.model.Timetable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.yfcod.management.util.ExportExcelUtil.exportExcel;
import static com.yfcod.management.util.GenerateAlertUtil.showAlert;

public class StudentController extends BaseController {
    /**
     * 考试安排表
     */
    @FXML
    private TableView<Arrangement> arrangementTableView;
    @FXML
    private TableColumn<Arrangement, String> examIdArrangementColumn;
    @FXML
    private TableColumn<Arrangement, String> courseIdArrangementColumn;
    @FXML
    private TableColumn<Arrangement, String> examDateArrangementColumn;
    @FXML
    private TableColumn<Arrangement, String> startTimeArrangementColumn;
    @FXML
    private TableColumn<Arrangement, String> endTimeArrangementColumn;
    @FXML
    private TableColumn<Arrangement, String> addressArrangementColumn;

    @FXML
    private TextField examIdArrangementField;
    @FXML
    private TextField courseIdArrangementField;
    @FXML
    private TextField examDateArrangementField;
    @FXML
    private TextField startTimeArrangementField;
    @FXML
    private TextField endTimeArrangementField;
    @FXML
    private TextField addressArrangementField;

    @FXML
    private ComboBox<String> addressArrangementBox;
    @FXML
    private CheckBox isOverdueBox;

    /**
     * 成绩表
     */
    @FXML
    private TableView<Score> scoreTableView;
    @FXML
    private TableColumn<Score, String> examIdScoreColumn;
    @FXML
    private TableColumn<Score, String> studentIdScoreColumn;
    @FXML
    private TableColumn<Score, String> scoreScoreColumn;

    @FXML
    private TextField examIdScoreField;
    @FXML
    private TextField studentIdScoreField;
    @FXML
    private TextField scoreScoreField;

    @FXML
    private CheckBox showAllDataBox;

    /**
     * 课程安排表
     */
    @FXML
    private TableView<Course> courseTableView;
    @FXML
    private TableColumn<Course, String> courseIdCourseColumn;
    @FXML
    private TableColumn<Course, String> courseNameCourseColumn;
    @FXML
    private TableColumn<Course, String> teacherIdCourseColumn;
    @FXML
    private TableColumn<Course, String> addressCourseColumn;
    @FXML
    private TableColumn<Course, String> courseDayCourseColumn;
    @FXML
    private TableColumn<Course, String> courseTimePeriodCourseColumn;

    @FXML
    private TextField courseIdCourseField;
    @FXML
    private TextField courseNameCourseField;
    @FXML
    private TextField teacherIdCourseField;
    @FXML
    private TextField addressCourseField;
    @FXML
    private TextField courseDayCourseField;
    @FXML
    private TextField courseTimePeriodCourseField;

    @FXML
    private ComboBox<String> addressCourseBox;

    @FXML
    private TabPane mainTablePane;

    /**
     * 表中数据
     */
    private final ObservableList<Arrangement> arrangements = FXCollections.observableArrayList();
    private final ObservableList<Course> courses = FXCollections.observableArrayList();
    private final ObservableList<Score> scores = FXCollections.observableArrayList();

    private String currentStudentId;

    private Stage primaryStage;
    private Main main;

    @FXML
    private void initialize() {
        setCellValueFactory();
        setColumnsPrefWidth();
        setAllComboBox();
    }

    @FXML
    private void handleQueryArrangement() {
        queryArrangement(examIdArrangementField,
                courseIdArrangementField,
                examDateArrangementField,
                startTimeArrangementField,
                endTimeArrangementField,
                addressArrangementField,
                addressArrangementBox,
                this.arrangements,
                isOverdueBox,
                arrangementTableView,
                primaryStage,
                currentStudentId);
    }

    @FXML
    private void handleQueryCourse() {
        try {
            Integer courseId = courseIdCourseField.getText().equals("") ?
                    null : Integer.parseInt(courseIdCourseField.getText());
            String courseName = courseNameCourseField.getText().equals("") ?
                    null : courseNameCourseField.getText();
            String teacherId = teacherIdCourseField.getText().equals("") ?
                    null : teacherIdCourseField.getText();
            String addressClassNum = addressCourseField.getText().equals("") ?
                    null : addressCourseField.getText();
            String addressBuildingNum = addressCourseBox.getValue();
            Integer courseDay = courseDayCourseField.getText().equals("") ?
                    null : Integer.parseInt(courseDayCourseField.getText());
            Integer courseTimePeriod = courseTimePeriodCourseField.getText().equals("") ?
                    null : Integer.parseInt(courseTimePeriodCourseField.getText());

            String address = null;
            if (addressClassNum != null && !addressBuildingNum.equals("请选择")) {
                address = (addressBuildingNum + "号楼" + addressClassNum);
            }

            if (showAllDataBox.isSelected()) {
                setAllTableData();
                return;
            }

            List<Timetable> timetables = TimetableDao.queryTimetableByConditions(
                    new Timetable(currentStudentId)
            );
            List<Integer> currentCourseIds = new ArrayList<>();
            for (Timetable timetable : timetables) {
                Integer currentCourseId = timetable.getCourseId();
                currentCourseIds.add(currentCourseId);
            }
            if (!currentCourseIds.contains(courseId)) {
                showAlert(primaryStage,
                        "未选择此门课",
                        "warning");
                return;
            } else {
                List<Course> courses = CourseDao.queryCourseByConditions(
                        new Course(
                                courseId,
                                courseName,
                                teacherId,
                                address,
                                courseDay,
                                courseTimePeriod
                        )
                );
                this.courses.clear();
                this.courses.addAll(courses);
            }

            courseTableView.setItems(this.courses);
        } catch (IllegalArgumentException e) {
            showAlert(primaryStage,
                    "请输入正确的数值",
                    "error");
        }
    }

    @FXML
    private void handleQueryScore() {
        try {
            Integer examId = examIdScoreField.getText().equals("") ?
                    null : Integer.parseInt(examIdScoreField.getText());
            String studentId = studentIdScoreField.getText().equals("") ?
                    null : studentIdScoreField.getText();
            Integer score = scoreScoreField.getText().equals("") ?
                    null : Integer.parseInt(scoreScoreField.getText());

            if (showAllDataBox.isSelected()) {
                setAllTableData();
                return;
            }

            List<Score> scores = ScoreDao.queryScoreByConditions(
                    new Score(
                            examId,
                            studentId,
                            score
                    )
            );

            this.scores.clear();
            this.scores.addAll(scores);
            scoreTableView.setItems(this.scores);
        } catch (IllegalArgumentException e) {
            showAlert(primaryStage,
                    "请输入正确的数值",
                    "error");
        }
    }

    @FXML
    private void handleExportCurrentData() {
        showExportDialogAndSave(true);
    }

    @FXML
    private void handleExportAllData() {
        showExportDialogAndSave(false);
    }

    @FXML
    private void handleLogout() {
        this.main.getPrimaryStage().centerOnScreen();
        this.main.getPrimaryStage().setMaximized(false);
        this.main.getPrimaryStage().setTitle("厦门大学考试管理系统 - 登录");

        showAlert(primaryStage,
                "当前用户已登出",
                "information");

        this.main.showLoginOverview();
        primaryStage.centerOnScreen();
    }

    @FXML
    private void handleQuit() {
        System.exit(0);
    }

    @FXML
    private void handleAbout() {
        showAbout(this.primaryStage);
    }

    private void showExportDialogAndSave(boolean isCurrent) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "xls files (*.xls)", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(this.main.getPrimaryStage());

        if (file != null) {
            if (file.getPath().endsWith(".xls")) {
                if (!isCurrent) {
                    setAllTableData();
                }

                String excelPath = (file.getPath()).replace("\\", "\\\\");

                switch (mainTablePane.getSelectionModel().getSelectedItem().getText()) {
                    case "考试安排" :
                        exportExcel(Arrangement.class, arrangements, excelPath);
                        break;
                    case "成绩查询":
                        exportExcel(Course.class, courses, excelPath);
                        System.out.println(1);
                        break;
                    case "课表查询":
                        exportExcel(Score.class, scores, excelPath);
                        break;
                }
                showAlert(primaryStage,
                        "导出成功",
                        "information");
            }
        }
    }

    public void setAllTableData() {
        arrangements.clear();
        courses.clear();
        scores.clear();

        List<Timetable> timetables = TimetableDao.queryTimetableByConditions(
                new Timetable(currentStudentId)
        );

        for (Timetable timetable : timetables) {
            Integer courseId = timetable.getCourseId();
            List<Arrangement> arrangements = ArrangementDao.queryArrangementByConditions(
                    new Arrangement(courseId)
            );
            this.arrangements.addAll(
                    arrangements
            );
            this.courses.addAll(
                    CourseDao.queryCourseByConditions(
                            new Course(courseId)
                    )
            );
            for (Arrangement arrangement : arrangements) {
                Integer examId = arrangement.getExamId();
                scores.addAll(ScoreDao.queryScoreByConditions(
                        new Score(examId, currentStudentId)
                ));
            }
        }

        arrangementTableView.setItems(this.arrangements);
        courseTableView.setItems(this.courses);
        scoreTableView.setItems(this.scores);

        studentIdScoreField.setText(currentStudentId);
        studentIdScoreField.setEditable(false);
    }

    private void setCellValueFactory() {
        setArrangementCellValueFactory();
        setCourseCellValueFactory();
        setScoreCellValueFactory();
    }

    private void setArrangementCellValueFactory() {
        arrangementCellValueFactory(examIdArrangementColumn,
                courseIdArrangementColumn,
                examDateArrangementColumn,
                startTimeArrangementColumn,
                endTimeArrangementColumn,
                addressArrangementColumn);
    }

    private void setCourseCellValueFactory() {
        courseCellValueFactory(courseIdCourseColumn,
                courseNameCourseColumn,
                teacherIdCourseColumn,
                addressCourseColumn,
                courseDayCourseColumn,
                courseTimePeriodCourseColumn);
    }

    private void setScoreCellValueFactory() {
        scoreCellValueFactory(examIdScoreColumn,
                studentIdScoreColumn,
                scoreScoreColumn);
    }

    private void setColumnsPrefWidth() {
        setColumnPrefWidth(arrangementTableView);
        setColumnPrefWidth(courseTableView);
        setColumnPrefWidth(scoreTableView);
    }

    private void setAllComboBox() {
        addressArrangementBox.getItems().addAll("一", "四");
        addressCourseBox.getItems().addAll("一", "四");
        showAllDataBox.setSelected(true);
    }

    public void setCurrentStudentId(String currentStudentId) {
        this.currentStudentId = currentStudentId;
    }

    @Override
    public void setDialogStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void setMainApp(Main main) {
        this.main = main;
    }
}
