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
import com.yfcod.management.util.GenerateBarChartUtil;
import com.yfcod.management.util.GeneratePieChartUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.yfcod.management.util.ExportExcelUtil.exportExcel;
import static com.yfcod.management.util.GenerateAlertUtil.showAlert;

@SuppressWarnings("DuplicatedCode")
public class TeacherController extends BaseController implements MenuItemOperation {
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
    private CheckBox showAllScoreBox;
    @FXML
    private CheckBox showAllCourseBox;
    @FXML
    private CheckBox showAllArrangementBox;

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

    /**
     * 图表区
     */
    @FXML
    private BarChart<String, Number> allAverageChart;
    @FXML
    private CategoryAxis allAverageXAxis;
    @FXML
    private NumberAxis allAverageYAxis;
    @FXML
    private Label maxAverageScoreLabel;
    @FXML
    private Label maxScoreCourseIdLabel;
    @FXML
    private Label minAverageScoreLabel;
    @FXML
    private Label minScoreCourseIdLabel;

    @FXML
    private PieChart subsectionChart;
    @FXML
    private Label maxScoreLabel;
    @FXML
    private Label maxScoreStudentIdLabel;
    @FXML
    private Label minScoreLabel;
    @FXML
    private Label minScoreStudentIdLabel;
    @FXML
    private Label currentStatisticCourseName;

    @FXML
    private TextField statisticExamIdField;

    /**
     * TabPane管理
     */
    @FXML
    private TabPane manageExamTabPane;
    @FXML
    private TabPane mainTablePane;

    /**
     * 左侧导航栏
     */
    @FXML
    private Pane statisticsExamPane;
    @FXML
    private Pane manageExamPane;

    /**
     * 当前教师号
     */
    private String currentTeacherId;

    /**
     * 表中数据
     */
    private final ObservableList<Arrangement> arrangements = FXCollections.observableArrayList();
    private final ObservableList<Course> courses = FXCollections.observableArrayList();
    private final ObservableList<Score> scores = FXCollections.observableArrayList();

    private Stage primaryStage;
    private Main main;

    @FXML
    private void initialize() {
        setCellValueFactory();
        setColumnsPrefWidth();
        setAllComboBox();
        setTableViewSelectedModel();
        clearStatisticExamText();
    }

    @FXML
    private void handleStatisticExam() {
        String examId = statisticExamIdField.getText();
        try {
            subsectionChart.getData().clear();
            showSubsectionChart(Integer.parseInt(examId));
        } catch (IllegalArgumentException e) {
            showAlert(primaryStage,
                    "请输入正确的考试代码",
                    "warning");
        }
    }

    @FXML
    private void handleQueryArrangement() {
        if (showAllArrangementBox.isSelected()) {
            setAllTableData();
            return;
        }
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
                null);
    }

    @FXML
    private void handleAddArrangement() {
        try {
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

            if (checkArrangementInvalid(courseId, examDate, startTime, endTime, address)) return;

            ArrangementDao.addArrangement(
                    new Arrangement(
                            null,
                            courseId,
                            examDate,
                            startTime,
                            endTime,
                            address
                    )
            );

            showAlert(primaryStage,
                    "预约成功，考试安排已添加",
                    "information");

            setAllTableData();
        } catch (IllegalArgumentException e) {
            showAlert(primaryStage,
                    "请输入正确的数值",
                    "error");
        }
    }

    private boolean checkArrangementInvalid(Integer courseId, Date examDate, Time startTime, Time endTime, String address) {
        if (courseId == null ||
                examDate == null ||
                startTime == null ||
                endTime == null ||
                address == null
        ) {
            showAlert(primaryStage,
                    "请输入完整信息",
                    "error");
            return true;
        }

        if (checkCourseIdBelongToCurrentTeacher(courseId)) {
            showAlert(primaryStage,
                    "您只能预约您的授课，请重新输入授课代码",
                    "error");
            return true;
        }

        if (checkArrangementAvailable(examDate, startTime, endTime, address)) {
            showAlert(primaryStage,
                    "您预约的考试与其余考试冲突，请重新选择",
                    "error");
            return true;
        }
        return false;
    }

    private boolean checkArrangementAvailable(Date examDate, Time startTime, Time endTime, String address) {
        List<Arrangement> arrangements = ArrangementDao.queryArrangementAll();
        for (Arrangement arrangement : arrangements) {
            if (arrangement.getExamDate().equals(examDate)) {
                if (arrangement.getAddress().equals(address)) {
                    assert startTime != null;
                    if ((startTime.before(arrangement.getEndTime()) && startTime.after(arrangement.getStartTime())) ||
                            (Objects.requireNonNull(endTime).before(arrangement.getEndTime()) && endTime.after(arrangement.getStartTime()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkCourseIdBelongToCurrentTeacher(Integer courseId) {
        List<Course> courses = CourseDao.queryCourseAll();
        boolean isFound = false;
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                if (course.getTeacherId().equals(currentTeacherId)) {
                    isFound = true;
                }
            }
        }
        return isFound;
    }

    @FXML
    private void handleUpdateArrangement() {
        try {
            Arrangement selectedArrangement =
                    arrangementTableView.getSelectionModel().getSelectedItem();

            if (selectedArrangement == null) {
                showAlert(primaryStage,
                        "请选择您要修改的考试安排",
                        "warning");
                return;
            }

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

            if (checkArrangementInvalid(courseId, examDate, startTime, endTime, address)) return;

            ArrangementDao.updateArrangement(
                    new Arrangement(
                            examId,
                            courseId,
                            examDate,
                            startTime,
                            endTime,
                            address
                    )
            );

            showAlert(primaryStage,
                    "考试安排已修改",
                    "information");

            setAllTableData();
        } catch (IllegalArgumentException e) {
            showAlert(primaryStage,
                    "请输入正确的数值",
                    "error");
        }
    }

    @FXML
    private void handleDeleteArrangement() {
        Arrangement selectedArrangement =
                arrangementTableView.getSelectionModel().getSelectedItem();

        if (selectedArrangement == null) {
            showAlert(primaryStage,
                    "请选择您要删除的考试安排",
                    "warning");
            return;
        }

        ArrangementDao.deleteArrangement(selectedArrangement);

        showAlert(primaryStage,
                "考试安排已删除",
                "information");

        setAllTableData();
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

            if (showAllCourseBox.isSelected()) {
                setAllTableData();
                return;
            }

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

            if (showAllScoreBox.isSelected()) {
                setAllTableData();
                return;
            }

            if (!checkExamIdBelongToCurrentTeacher(examId)) {
                showAlert(primaryStage,
                        "该考试代码不为您所有，请重新输入",
                        "error");
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

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkExamIdBelongToCurrentTeacher(Integer examId) {
        List<Arrangement> arrangements = ArrangementDao.queryArrangementAll();
        boolean isFound = false;
        for (Arrangement arrangement : arrangements) {
            if (arrangement.getExamId().equals(examId)) {
                if (checkCourseIdBelongToCurrentTeacher(arrangement.getCourseId())) {
                    isFound = true;
                }
            }
        }
        return isFound;
    }

    @FXML
    private void handleAddScore() {
        try {
            Integer examId = examIdScoreField.getText().equals("") ?
                    null : Integer.parseInt(examIdScoreField.getText());
            String studentId = studentIdScoreField.getText().equals("") ?
                    null : studentIdScoreField.getText();
            Integer score = scoreScoreField.getText().equals("") ?
                    null : Integer.parseInt(scoreScoreField.getText());

            if (checkScoreInvalid(examId, studentId, score)) return;

            if (ScoreDao.queryScoreByConditions(
                    new Score(
                            examId,
                            studentId,
                            null
                    )
            ) != null) {
                showAlert(primaryStage,
                        "此学生成绩信息已存在",
                        "error");
                return;
            }

            ScoreDao.addScore(
                    new Score(
                            examId,
                            studentId,
                            score
                    )
            );

            showAlert(primaryStage,
                    "成绩信息已添加",
                    "information");

            setAllTableData();
        } catch (IllegalArgumentException e) {
            showAlert(primaryStage,
                    "请输入正确的数值",
                    "error");
        }
    }

    private boolean checkScoreInvalid(Integer examId, String studentId, Integer score) {
        if (examId == null || studentId == null || score == null) {
            showAlert(primaryStage,
                    "请输入完整信息",
                    "error");
            return true;
        }

        if (!checkExamIdBelongToCurrentTeacher(examId)) {
            showAlert(primaryStage,
                    "该考试代码不为您所有，请重新输入",
                    "error");
            return true;
        }

        if (!checkStudentIdBelongToCurrentTeacher(studentId)) {
            showAlert(primaryStage,
                    "此学生未选择您的授课",
                    "error");
            return true;
        }
        return false;
    }

    private boolean checkStudentIdBelongToCurrentTeacher(String studentId) {
        List<Timetable> timetables = TimetableDao.queryTimetableAll();
        boolean isFound = false;
        for (Timetable timetable : timetables) {
            if (studentId.equals(timetable.getStudentId())) {
                if (checkCourseIdBelongToCurrentTeacher(timetable.getCourseId())) {
                    isFound = true;
                }
            }
        }
        return isFound;
    }

    @FXML
    private void handleUpdateScore() {
        try {
            Score selectedScore = scoreTableView.getSelectionModel().getSelectedItem();
            if (selectedScore == null) {
                showAlert(primaryStage,
                        "请选择您要修改的成绩信息",
                        "warning");
                return;
            }
            Integer examId = examIdScoreField.getText().equals("") ?
                    null : Integer.parseInt(examIdScoreField.getText());
            String studentId = studentIdScoreField.getText().equals("") ?
                    null : studentIdScoreField.getText();
            Integer score = scoreScoreField.getText().equals("") ?
                    null : Integer.parseInt(scoreScoreField.getText());

            if (checkScoreInvalid(examId, studentId, score)) return;

            ScoreDao.updateScore(
                    new Score(
                            examId,
                            studentId,
                            score
                    )
            );

            showAlert(primaryStage,
                    "成绩信息已修改",
                    "information");

            setAllTableData();
        } catch (IllegalArgumentException e) {
            showAlert(primaryStage,
                    "请输入正确的数值",
                    "error");
        }
    }

    @FXML
    private void handleDeleteScore() {
        try {
            Score selectedScore = scoreTableView.getSelectionModel().getSelectedItem();
            if (selectedScore == null) {
                showAlert(primaryStage,
                        "请选择您要删除的成绩信息",
                        "warning");
                return;
            }
            ScoreDao.deleteScore(selectedScore);

            showAlert(primaryStage,
                    "成绩信息已删除",
                    "information");

            setAllTableData();
        } catch (IllegalArgumentException e) {
            showAlert(primaryStage,
                    "请输入正确的数值",
                    "error");
        }
    }

    @FXML
    public void handleShowAllTableData() {
        setAllTableData();
    }

    @FXML
    public void handleExportCurrentData() {
        showExportDialogAndSave(true);
    }

    @FXML
    public void handleExportAllData() {
        showExportDialogAndSave(false);
    }

    @FXML
    public void handleCurrentSendMail() {
        showAndSendMail(false);
    }

    @FXML
    public void handleAllSendMail() {
        showAndSendMail(true);
    }

    @FXML
    public void handleUpdateInfo() {
        updateInfo(primaryStage, "教师", currentTeacherId);
    }

    @FXML
    public void handleLogout() {
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
    public void handleQuit() {
        System.exit(0);
    }

    @FXML
    private void handleAbout() {
        showAbout(this.primaryStage);
    }

    private void showAndSendMail(boolean isCurrent) {
        if (!isCurrent) {
            setAllTableData();
        }
        switch (mainTablePane.getSelectionModel().getSelectedItem().getText()) {
            case "考试安排表" :
                exportExcel(Arrangement.class, arrangements, "temp\\temp - arrangement.xls");
                inputMailAddressAndSend(
                        primaryStage,
                        "考试安排表",
                        "temp\\temp - arrangement.xls",
                        ".xls"
                );
                break;
            case "课程表":
                exportExcel(Course.class, courses, "temp\\temp - course.xls");
                inputMailAddressAndSend(
                        primaryStage,
                        "课程表",
                        "temp\\temp - course.xls",
                        ".xls"
                );
                break;
            case "成绩表":
                exportExcel(Score.class, scores, "temp\\temp - score.xls");
                inputMailAddressAndSend(
                        primaryStage,
                        "成绩表",
                        "temp\\temp - score.xls",
                        ".xls"
                );
                break;
        }
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
                    case "考试安排表" :
                        exportExcel(Arrangement.class, arrangements, excelPath);
                        break;
                    case "课程表":
                        exportExcel(Course.class, courses, excelPath);
                        break;
                    case "成绩表":
                        exportExcel(Score.class, scores, excelPath);
                        break;
                }
                showAlert(primaryStage,
                        "导出成功",
                        "information");
            }
        }
    }

    public void showAllAverageChart() {
        List<Number> averageScore = new ArrayList<>();
        List<String> courseNames = new ArrayList<>();

        List<Course> courses = CourseDao.queryCourseByConditions(
                new Course(currentTeacherId)
        );

        for (Course course: courses) {
            List<Arrangement> arrangements = ArrangementDao.queryArrangementByConditions(
                    new Arrangement(course.getCourseId())
            );
            boolean hasScore = false;
            if (arrangements.size() != 0) {
                for (Arrangement arrangement : arrangements) {
                    List<Score> scores = ScoreDao.queryScoreByConditions(
                            new Score(arrangement.getExamId())
                    );
                    if (scores.size() != 0) {
                        hasScore = true;
                    }
                }
                if (hasScore) {
                    courseNames.add(course.getCourseName());
                }
            }
            if (hasScore) {
                for (Arrangement arrangement : arrangements) {
                    List<Score> scores = ScoreDao.queryScoreByConditions(
                            new Score(arrangement.getExamId())
                    );

                    if (scores.stream().mapToDouble(Score::getScore).average().isPresent()) {
                        averageScore.add(
                                scores.stream().mapToDouble(Score::getScore).average().getAsDouble()
                        );
                    }
                }
            }
        }

        showAllAverageText(averageScore, courseNames);

        GenerateBarChartUtil.setXYAxis(
                allAverageXAxis,
                allAverageYAxis,
                courseNames,
                "课程名称",
                "平均分"
        );
        GenerateBarChartUtil.setBarChartData(
                allAverageChart,
                courseNames,
                averageScore,
                currentTeacherId
        );
    }

    private void showAllAverageText(List<Number> averageScore, List<String> courseNames) {
        double maxAverageScore = 0;
        if (averageScore.stream().mapToDouble(Number::doubleValue).max().isPresent()) {
            maxAverageScore = averageScore.stream().mapToDouble(Number::doubleValue).
                    max().getAsDouble();
        }
        double minAverageScore = 0;
        if (averageScore.stream().mapToDouble(Number::doubleValue).min().isPresent()) {
            minAverageScore = averageScore.stream().mapToDouble(Number::doubleValue).
                    min().getAsDouble();
        }

        for (int i = 0; i < courseNames.size(); i++) {
            if (String.valueOf(averageScore.get(i)).equals(String.valueOf(maxAverageScore))) {
                maxScoreCourseIdLabel.setText(courseNames.get(i));
            }
            if (String.valueOf(averageScore.get(i)).equals(String.valueOf(minAverageScore))) {
                minScoreCourseIdLabel.setText(courseNames.get(i));
            }
        }

        maxAverageScoreLabel.setText(String.valueOf(maxAverageScore));
        minAverageScoreLabel.setText(String.valueOf(minAverageScore));
    }

    public void showSubsectionChart(Integer examId) {
        List<String> xData = Arrays.asList(
                "90（包括）-100",
                "80（包括）-90",
                "70（包括）-80",
                "60（包括）-70",
                "60以下"
        );
        List<Score> scores = ScoreDao.queryScoreByConditions(
                new Score(examId)
        );

        List<Arrangement> arrangements = ArrangementDao.queryArrangementAll();
        boolean hasGotCurrentTeacherExam = false;
        for (Arrangement arrangement : arrangements) {
            if (arrangement.getExamId().equals(examId)) {
                List<Course> courses = CourseDao.queryCourseByConditions(
                        new Course(currentTeacherId)
                );
                for (Course course : courses) {
                    if (Objects.equals(course.getCourseId(), arrangement.getCourseId())) {
                        hasGotCurrentTeacherExam = true;
                        currentStatisticCourseName.setText(course.getCourseName());
                        break;
                    }
                }
                if (hasGotCurrentTeacherExam) break;
            }
        }

        if (!hasGotCurrentTeacherExam || scores.size() == 0) {
            showAlert(primaryStage,
                    "未查找到该考试数据",
                    "warning");
            clearStatisticExamText();
            return;
        }
        List<Number> subsectionStudentNum = Arrays.asList(0, 0, 0, 0, 0);
        for (Score score : scores) {
            if (score.getScore() >= 90 && score.getScore() <= 100) {
                subsectionStudentNum.set(0, (Integer) subsectionStudentNum.get(0) + 1);
            } else if (score.getScore() >= 80) {
                subsectionStudentNum.set(1, (Integer) subsectionStudentNum.get(1) + 1);
            } else if (score.getScore() >= 70) {
                subsectionStudentNum.set(2, (Integer) subsectionStudentNum.get(2) + 1);
            } else if (score.getScore() >= 60) {
                subsectionStudentNum.set(3, (Integer) subsectionStudentNum.get(3) + 1);
            } else {
                subsectionStudentNum.set(4, (Integer) subsectionStudentNum.get(4) + 1);
            }
        }
        GeneratePieChartUtil.setPieChartData(
                subsectionChart,
                xData,
                subsectionStudentNum);
        showSubsectionText(scores);
    }

    private void showSubsectionText(List<Score> scores) {
        if (scores.stream().mapToDouble(Score::getScore).max().isPresent()) {
            maxScoreLabel.setText(
                    String.valueOf((int) scores.stream().mapToDouble(Score::getScore).max().getAsDouble())
            );
        }
        if (scores.stream().mapToDouble(Score::getScore).min().isPresent()) {
            minScoreLabel.setText(
                    String.valueOf((int) scores.stream().mapToDouble(Score::getScore).min().getAsDouble())
            );
        }

        for (Score score : scores) {
            if (String.valueOf(score.getScore()).equals(maxScoreLabel.getText())) {
                maxScoreStudentIdLabel.setText(score.getStudentId());
            }
            if (String.valueOf(score.getScore()).equals(minScoreLabel.getText())) {
                minScoreStudentIdLabel.setText(score.getStudentId());
            }
        }
    }

    private void clearStatisticExamText() {
        maxScoreLabel.setText("");
        maxScoreStudentIdLabel.setText("");
        minScoreLabel.setText("");
        minScoreStudentIdLabel.setText("");
        currentStatisticCourseName.setText("");
    }

    private void setAllComboBox() {
        addressArrangementBox.getItems().addAll("一", "四");
        addressCourseBox.getItems().addAll("一", "四");
        showAllArrangementBox.setSelected(true);
        showAllCourseBox.setSelected(true);
        showAllScoreBox.setSelected(true);
    }

    public void setAllTableData() {
        arrangements.clear();
        courses.clear();
        scores.clear();

        arrangements.addAll(ArrangementDao.queryArrangementAll());

        List<Course> courses = CourseDao.queryCourseAll();
        for (Course course : courses) {
            if (course.getTeacherId().equals(currentTeacherId)) {
                this.courses.add(course);
            }
        }

        for (Course course : this.courses) {
            List<Arrangement> arrangements = ArrangementDao.queryArrangementByConditions(
                    new Arrangement(
                            course.getCourseId()
                    )
            );
            for (Arrangement arrangement : arrangements) {
                scores.addAll(ScoreDao.queryScoreByConditions(
                        new Score(arrangement.getExamId())
                ));
            }
        }

        arrangementTableView.setItems(arrangements);
        courseTableView.setItems(this.courses);
        scoreTableView.setItems(scores);

        teacherIdCourseField.setText(currentTeacherId);
        teacherIdCourseField.setEditable(false);
    }

    private void setColumnsPrefWidth() {
        setColumnPrefWidth(arrangementTableView);
        setColumnPrefWidth(courseTableView);
        setColumnPrefWidth(scoreTableView);
    }

    @FXML
    private void setStatisticsExamSelectedModel() {
        manageExamTabPane.getSelectionModel().select(0);
    }

    @FXML
    private void setManageExamSelectedModel() {
        manageExamTabPane.getSelectionModel().select(1);
    }

    private void setTableViewSelectedModel() {
        arrangementTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                fillArrangementSelectedData(newValue));
        scoreTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                fillScoreSelectedData(newValue));
        courseTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                fillCourseSelectedData(newValue));

        manageExamTabPane.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            int selectedIndex = manageExamTabPane.getSelectionModel().getSelectedIndex();
            if (selectedIndex == 0) {
                statisticsExamPane.setOpacity(1);
                manageExamPane.setOpacity(0.5);
            } else if (selectedIndex == 1) {
                statisticsExamPane.setOpacity(0.5);
                manageExamPane.setOpacity(1);
            }
        }));
    }

    private void fillArrangementSelectedData(Arrangement selectedArrangement) {
        baseArrangementSelectedData(selectedArrangement,
                examIdArrangementField,
                courseIdArrangementField,
                examDateArrangementField,
                startTimeArrangementField,
                endTimeArrangementField,
                addressArrangementField,
                addressArrangementBox);
    }

    private void fillScoreSelectedData(Score selectedScore) {
        examIdScoreField.setText(selectedScore == null ? "" : String.valueOf(selectedScore.getExamId()));
        studentIdScoreField.setText(selectedScore == null ? "" : selectedScore.getStudentId());
        scoreScoreField.setText(selectedScore == null ? "" : String.valueOf(selectedScore.getScore()));
    }

    private void fillCourseSelectedData(Course selectedCourse) {
        baseCourseSelectedData(selectedCourse,
                courseIdCourseField,
                courseNameCourseField,
                teacherIdCourseField,
                addressCourseField,
                addressCourseBox,
                courseDayCourseField,
                courseTimePeriodCourseField,
                true);
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

    public void setCurrentTeacherId(String currentTeacherId) {
        this.currentTeacherId = currentTeacherId;
    }

    @Override
    public void setMainApp(Main main) {
        this.main = main;
    }

    @Override
    public void setDialogStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
