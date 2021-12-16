package com.yfcod.management.controller;

import com.yfcod.management.Main;
import com.yfcod.management.dao.ArrangementDao;
import com.yfcod.management.dao.CourseDao;
import com.yfcod.management.dao.ScoreDao;
import com.yfcod.management.dao.TimetableDao;
import com.yfcod.management.model.*;
import com.yfcod.management.util.GenerateBarChartUtil;
import com.yfcod.management.util.GenerateStackedBarChartUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;

import static com.yfcod.management.util.ExportExcelUtil.exportExcel;
import static com.yfcod.management.util.GenerateAlertUtil.showAlert;

@SuppressWarnings("DuplicatedCode")
public class StudentController extends BaseController implements MenuItemOperation {
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
    private CheckBox showAllArrangementBox;
    @FXML
    private CheckBox showAllScoreBox;
    @FXML
    private CheckBox showAllCourseBox;

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
    private StackedBarChart<String, Number> rankRateChart;
    @FXML
    private CategoryAxis rankRateXAxis;
    @FXML
    private NumberAxis rankRateYAxis;
    @FXML
    private BarChart<String, Number> scoreChart;
    @FXML
    private CategoryAxis scoreXAxis;
    @FXML
    private NumberAxis scoreYAxis;
    @FXML
    private Label maxScoreLabel;
    @FXML
    private Label maxScoreCourseIdLabel;
    @FXML
    private Label minScoreLabel;
    @FXML
    private Label minScoreCourseIdLabel;

    @FXML
    private Pane navManageExamPane;
    @FXML
    private Pane navQueryScorePane;
    @FXML
    private Pane navQueryCoursesPane;

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

    private final Logger logger = Logger.getLogger(StudentController.class);

    @FXML
    private void initialize() {
        setCellValueFactory();
        setColumnsPrefWidth();
        setAllComboBox();
        setTableViewSelectedModel();
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

            if (showAllCourseBox.isSelected()) {
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

            if (showAllScoreBox.isSelected()) {
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
        showAndSendMail(true);
    }

    @FXML
    public void handleAllSendMail() {
        showAndSendMail(false);
    }

    @FXML
    public void handleUpdateInfo() {
        logger.info("student side -----");
        logger.info("updating personal information -----");
        updateInfo(primaryStage, "学生", currentStudentId);
        logger.info("update completed -----");
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

    public void showStudentScoreChart() {
        List<String> currentCourseNames = new ArrayList<>();
        List<Number> currentRankRate = new ArrayList<>();
        List<Number> currentStudentScores = new ArrayList<>();

        setStudentScoreChartData(currentCourseNames, currentRankRate, currentStudentScores);

        showRankRateChart(currentCourseNames, currentRankRate);
        showCurrentScoresChart(currentCourseNames, currentStudentScores);
        showCurrentScoresText(currentCourseNames, currentStudentScores);
    }

    private void showCurrentScoresText(List<String> currentCourseNames, List<Number> currentStudentScores) {
        int maxScore = 0;
        if (currentStudentScores.stream().mapToInt(Number::intValue).max().isPresent()) {
            maxScore = currentStudentScores.stream().mapToInt(Number::intValue)
                    .max().getAsInt();
        }
        int minScore = 0;
        if (currentStudentScores.stream().mapToInt(Number::intValue).min().isPresent()) {
            minScore = currentStudentScores.stream().mapToInt(Number::intValue)
                    .min().getAsInt();
        }
        for (int i = 0; i < currentCourseNames.size(); i++) {
            if (currentStudentScores.get(i).equals(maxScore)) {
                maxScoreCourseIdLabel.setText(currentCourseNames.get(i));
            }
            if (currentStudentScores.get(i).equals(minScore)) {
                minScoreCourseIdLabel.setText(currentCourseNames.get(i));
            }
        }
        maxScoreLabel.setText(String.valueOf(maxScore));
        minScoreLabel.setText(String.valueOf(minScore));
    }

    private void showCurrentScoresChart(List<String> currentCourseNames,
                                        List<Number> currentStudentScores) {
        GenerateBarChartUtil.setXYAxis(
                scoreXAxis,
                scoreYAxis,
                currentCourseNames,
                "课程名称",
                "已有成绩"
        );
        GenerateBarChartUtil.setBarChartData(
                scoreChart,
                currentCourseNames,
                currentStudentScores,
                currentStudentId
        );
    }

    private void showRankRateChart(List<String> currentCourseNames,
                                   List<Number> currentRankRate) {
        List<GenerateStackedBarChartUtil.BinaryTuple<String, Number>> binaryTuples = new ArrayList<>();
        List<GenerateStackedBarChartUtil.BinaryTuple<String, Number>> totalBinaryTuples = new ArrayList<>();
        assert currentCourseNames.size() == currentRankRate.size();
        for (int i = 0; i < currentCourseNames.size(); i++) {
            binaryTuples.add(new GenerateStackedBarChartUtil.BinaryTuple<>(
                    currentCourseNames.get(i), currentRankRate.get(i)
            ));
            totalBinaryTuples.add(new GenerateStackedBarChartUtil.BinaryTuple<>(
                    currentCourseNames.get(i), 1.0 - (double) currentRankRate.get(i)
            ));
        }
        HashMap<String, List<GenerateStackedBarChartUtil.BinaryTuple<String, Number>>> currentStudentSeries =
                new HashMap<>();
        currentStudentSeries.put(currentStudentId, binaryTuples);
        currentStudentSeries.put("总计", totalBinaryTuples);

        GenerateStackedBarChartUtil.setXYAxis(
                rankRateXAxis,
                rankRateYAxis,
                currentCourseNames,
                "课程名称",
                "排名占比"
        );
        GenerateStackedBarChartUtil.setStackedBarChartData(
                rankRateChart,
                Arrays.asList(currentStudentId, "总计"),
                currentStudentSeries
        );
    }

    private void setStudentScoreChartData(List<String> currentCourseNames,
                                          List<Number> currentRankRate,
                                          List<Number> currentStudentScores) {
        List<Timetable> timetables = TimetableDao.queryTimetableByConditions(
                new Timetable(currentStudentId)
        );
        for (Timetable timetable : timetables) {
            Integer currentCourseId = timetable.getCourseId();
            List<Course> currentCourses = CourseDao.queryCourseByConditions(
                    new Course(currentCourseId)
            );
            for (Course course : currentCourses) {
                currentCourseNames.add(course.getCourseName());
            }

            int rank = 0;
            List<Arrangement> currentArrangements = ArrangementDao.queryArrangementByConditions(
                    new Arrangement(currentCourseId)
            );
            for (Arrangement arrangement : currentArrangements) {
                Integer currentExamId = arrangement.getExamId();
                List<Score> currentScores = ScoreDao.queryScoreByConditions(
                        new Score(currentExamId)
                );
                int currentStudentScore = 0;
                for (Score score : currentScores) {
                    if (score.getStudentId().equals(currentStudentId)) {
                        currentStudentScore = score.getScore();
                    }
                }
                currentStudentScores.add(currentStudentScore);
                for (Score score : currentScores) {
                    if (score.getScore() < currentStudentScore) {
                        rank += 1;
                    }
                }
                currentRankRate.add(rank / ((double) currentScores.size() - 1));
            }
        }
    }

    private void showAndSendMail(boolean isCurrent) {
        if (!isCurrent) {
            setAllTableData();
        }
        switch (mainTablePane.getSelectionModel().getSelectedItem().getText()) {
            case "考试安排" :
                exportExcel(Arrangement.class, arrangements, "temp\\temp - arrangement.xls");
                inputMailAddressAndSend(
                        primaryStage,
                        "考试安排表",
                        "temp\\temp - arrangement.xls"
                );
                break;
            case "成绩查询":
                exportExcel(Course.class, courses, "temp\\temp - course.xls");
                inputMailAddressAndSend(
                        primaryStage,
                        "课程表",
                        "temp\\temp - course.xls"
                );
                break;
            case "课表查询":
                exportExcel(Score.class, scores, "temp\\temp - score.xls");
                inputMailAddressAndSend(
                        primaryStage,
                        "成绩表",
                        "temp\\temp - score.xls"
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
                    case "考试安排" :
                        exportExcel(Arrangement.class, arrangements, excelPath);
                        break;
                    case "成绩查询":
                        exportExcel(Course.class, courses, excelPath);
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
        showAllArrangementBox.setSelected(true);
        showAllCourseBox.setSelected(true);
        showAllScoreBox.setSelected(true);
    }

    private void setTableViewSelectedModel() {
        arrangementTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                fillArrangementSelectedData(newValue));
        scoreTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                fillScoreSelectedData(newValue));
        courseTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                fillCourseSelectedData(newValue));

        mainTablePane.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            int selectedIndex = mainTablePane.getSelectionModel().getSelectedIndex();
            if (selectedIndex == 0) {
                navManageExamPane.setOpacity(1);
                navQueryScorePane.setOpacity(0.5);
                navQueryCoursesPane.setOpacity(0.5);
            } else if (selectedIndex == 1) {
                navManageExamPane.setOpacity(0.5);
                navQueryScorePane.setOpacity(1);
                navQueryCoursesPane.setOpacity(0.5);
            } else if (selectedIndex == 2) {
                navManageExamPane.setOpacity(0.5);
                navQueryScorePane.setOpacity(0.5);
                navQueryCoursesPane.setOpacity(1);
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
                false);
    }

    @FXML
    private void setManageExamSelectedModel() {
        mainTablePane.getSelectionModel().select(0);
    }

    @FXML
    private void setQueryScoreSelectedModel() {
        mainTablePane.getSelectionModel().select(1);
    }

    @FXML
    private void setQueryCoursesSelectedModel() {
        mainTablePane.getSelectionModel().select(2);
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
