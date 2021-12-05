package com.yfcod.management.controller;

import com.yfcod.management.Main;
import com.yfcod.management.dao.*;
import com.yfcod.management.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static com.yfcod.management.util.ExportExcelUtil.exportExcel;
import static com.yfcod.management.util.GenerateAlertUtil.showAlert;

@SuppressWarnings("DuplicatedCode")
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminController extends BaseController{
    /**
     * 左侧导航栏
     */
    @FXML
    private TableView<String> managementTableView;
    @FXML
    private TableColumn<String, String> managementTableColumn;

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

    /**
     * 学生表
     */
    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, String> studentIdStudentColumn;
    @FXML
    private TableColumn<Student, String> studentNameStudentColumn;
    @FXML
    private TableColumn<Student, String> genderStudentColumn;
    @FXML
    private TableColumn<Student, String> birthdayStudentColumn;
    @FXML
    private TableColumn<Student, String> nationStudentColumn;
    @FXML
    private TableColumn<Student, String> majorStudentColumn;
    @FXML
    private TableColumn<Student, String> gradeStudentColumn;

    @FXML
    private TextField studentIdStudentField;
    @FXML
    private TextField studentNameStudentField;
    @FXML
    private TextField genderStudentField;
    @FXML
    private TextField birthdayStudentField;
    @FXML
    private TextField nationStudentField;
    @FXML
    private TextField majorStudentField;
    @FXML
    private TextField gradeStudentField;

    /**
     * 教师表
     */
    @FXML
    private TableView<Teacher> teacherTableView;
    @FXML
    private TableColumn<Teacher, String> teacherIdTeacherColumn;
    @FXML
    private TableColumn<Teacher, String> teacherNameTeacherColumn;
    @FXML
    private TableColumn<Teacher, String> genderTeacherColumn;
    @FXML
    private TableColumn<Teacher, String> birthdayTeacherColumn;
    @FXML
    private TableColumn<Teacher, String> nationTeacherColumn;

    @FXML
    private TextField teacherIdTeacherField;
    @FXML
    private TextField teacherNameTeacherField;
    @FXML
    private TextField genderTeacherField;
    @FXML
    private TextField birthdayTeacherField;
    @FXML
    private TextField nationTeacherField;

    /**
     * 选课表
     */
    @FXML
    private TableView<Timetable> timetableTableView;
    @FXML
    private TableColumn<Timetable, String> studentIdTimetableColumn;
    @FXML
    private TableColumn<Timetable, String> courseIdTimetableColumn;

    @FXML
    private TextField studentIdTimetableField;
    @FXML
    private TextField courseIdTimetableField;

    @FXML
    private TabPane mainTablePane;

    /**
     * 日志板块
     */
    @FXML
    private TextArea logArea;
    @FXML
    private Button convertLogTypeBtn;
    private boolean isConcise = true;

    @FXML
    private Menu queryMenu;

    /**
     * 当前表
     */
    private String currentTable = "考试安排表";

    /**
     * 表中数据
     */
    private ObservableList<Arrangement> arrangements = FXCollections.observableArrayList();
    private ObservableList<Course> courses = FXCollections.observableArrayList();
    private ObservableList<Score> scores = FXCollections.observableArrayList();
    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<Teacher> teachers = FXCollections.observableArrayList();
    private ObservableList<Timetable> timetables = FXCollections.observableArrayList();

    /**
     * 导航栏数据
     */
    private static ObservableList<String> tableNames = FXCollections.observableArrayList();

    /**
     * 日志路径
     */
    private static String logPath = "log/debug.txt";

    private Stage primaryStage;
    private Main main;

    @FXML
    private void initialize() {
        tableNames.addAll(
                "考试安排表",
                "课程表",
                "成绩表",
                "学生表",
                "教师表",
                "选课表"
        );

        setManagementTableView();
        setCellValueFactory();
        setColumnsPrefWidth();
        setAllComboBox();
        setAllTableData();
        setNavigationSelectionModel();

        for (int i = 0; i < queryMenu.getItems().size(); i++) {
            int finalI = i;
            queryMenu.getItems().get(i).setOnAction(event -> {
                mainTablePane.getSelectionModel().select(finalI);
                currentTable = tableNames.get(finalI);
            });
        }

        logArea.setText(readLog(isConcise));
    }

    // 菜单栏操作

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
        showAlert(this.primaryStage,
                "关于\n厦门大学考试管理系统\nV1.0.0\n作者：YFCodeDream",
                "information");
    }

    // 查询栏操作

    @FXML
    private void handleQueryArrangement() {
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

            this.arrangements.clear();

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
                this.arrangements.addAll(arrangementList);
            } else {
                this.arrangements.addAll(arrangements);
            }

            arrangementTableView.setItems(this.arrangements);
        } catch (IllegalArgumentException e) {
            showAlert(primaryStage,
                    "请输入正确的数值",
                    "error");
        }
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

    @SuppressWarnings("DuplicatedCode")
    @FXML
    private void handleQueryStudent() {
        try {
            String studentId = studentIdStudentField.getText().equals("") ?
                    null : studentIdStudentField.getText();
            String studentName = studentNameStudentField.getText().equals("") ?
                    null : studentNameStudentField.getText();
            Boolean gender = null;
            if (!genderStudentField.getText().equals("")) {
                gender = !genderStudentField.getText().equals("男");
            }
            Date birthday = birthdayStudentField.getText().equals("") ?
                    null : Date.valueOf(birthdayStudentField.getText());
            String nation = nationStudentField.getText().equals("") ?
                    null : nationStudentField.getText();
            String major = majorStudentField.getText().equals("") ?
                    null : majorStudentField.getText();
            Integer grade = gradeStudentField.getText().equals("") ?
                    null : Integer.parseInt(gradeStudentField.getText());

            List<Student> students = StudentDao.queryStudentByConditions(
                    new Student(
                            studentId,
                            studentName,
                            gender,
                            birthday,
                            nation,
                            major,
                            grade
                    )
            );

            this.students.clear();
            this.students.addAll(students);
            studentTableView.setItems(this.students);
        } catch (IllegalArgumentException e) {
            showAlert(primaryStage,
                    "请输入正确的数值",
                    "error");
        }
    }

    @FXML
    private void handleQueryTeacher() {
        try {
            String teacherId = teacherIdTeacherField.getText().equals("") ?
                    null : teacherIdTeacherField.getText();
            String teacherName = teacherNameTeacherField.getText().equals("") ?
                    null : teacherNameTeacherField.getText();
            Boolean gender = null;
            if (!genderTeacherField.getText().equals("")) {
                gender = !genderTeacherField.getText().equals("男");
            }
            Date birthday = birthdayTeacherField.getText().equals("") ?
                    null : Date.valueOf(birthdayTeacherField.getText());
            String nation = nationTeacherField.getText().equals("") ?
                    null : nationTeacherField.getText();

            List<Teacher> teachers = TeacherDao.queryTeacherByConditions(
                    new Teacher(
                            teacherId,
                            teacherName,
                            gender,
                            birthday,
                            nation
                    )
            );

            this.teachers.clear();
            this.teachers.addAll(teachers);
            teacherTableView.setItems(this.teachers);
        } catch (IllegalArgumentException e) {
            showAlert(primaryStage,
                    "请输入正确的数值",
                    "error");
        }
    }

    @FXML
    private void handleQueryTimetable() {
        try {
            String studentId = studentIdTimetableField.getText().equals("") ?
                    null : studentIdTimetableField.getText();
            Integer courseId = courseIdTimetableField.getText().equals("") ?
                    null : Integer.parseInt(courseIdTimetableField.getText());

            List<Timetable> timetables = TimetableDao.queryTimetableByConditions(
                    new Timetable(
                            studentId,
                            courseId
                    )
            );

            this.timetables.clear();
            this.timetables.addAll(timetables);
            timetableTableView.setItems(this.timetables);
        } catch (IllegalArgumentException e) {
            showAlert(primaryStage,
                    "请输入正确的数值",
                    "error");
        }
    }

    // 日志操作

    @FXML
    private void handleUpdateLog() {
        logArea.setText(readLog(isConcise));
    }

    @FXML
    private void handleClearLog() {
        File logFile = new File(logPath);
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        logArea.setText(readLog(isConcise));
    }

    @FXML
    private void handleConvertLogType() {
        isConcise = !isConcise;
        logArea.setText(readLog(isConcise));
        if (isConcise) {
            convertLogTypeBtn.setText("显示完整日志");
        } else {
            convertLogTypeBtn.setText("显示简洁日志");
        }
    }

    // 辅助方法

    private void showExportDialogAndSave(boolean isCurrent) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "xls files (*.xls)", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);

        // 显示保存文件对话框
        File file = fileChooser.showSaveDialog(this.main.getPrimaryStage());

        if (file != null) {
            // 确保有正确的扩展名
            if (file.getPath().endsWith(".xls")) {
                if (!isCurrent) {
                    setAllTableData();
                }

                String excelPath = (file.getPath()).replace("\\", "\\\\");

                switch (currentTable) {
                    case "考试安排表" :
                        exportExcel(Arrangement.class, arrangements, excelPath);
                        break;
                    case "课程表":
                        exportExcel(Course.class, courses, excelPath);
                        System.out.println(1);
                        break;
                    case "成绩表":
                        exportExcel(Score.class, scores, excelPath);
                        break;
                    case "学生表":
                        exportExcel(Student.class, students, excelPath);
                        break;
                    case "教师表":
                        exportExcel(Teacher.class, teachers, excelPath);
                        break;
                    case "选课表":
                        exportExcel(Timetable.class, timetables, excelPath);
                        break;
                }
                showAlert(primaryStage,
                        "导出成功",
                        "information");
            }
        }
    }

    private String readLog(boolean isConcise) {
        File logFile = new File(logPath);
        BufferedReader reader = null;

        StringBuilder logStringBuilder = new StringBuilder();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(logFile), "GBK"
                    )
            );
            String temp;
            while ((temp = reader.readLine()) != null) {
                if (isConcise) {
                    if (temp.startsWith("DEBUG [JavaFX Application Thread] - ==>") ||
                            temp.startsWith("DEBUG [JavaFX Application Thread] - <==")) {
                        String replace = temp.replace(" [JavaFX Application Thread] -", "");
                        logStringBuilder.append(replace).append("\n");
                    }
                } else {
                    logStringBuilder.append(temp).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return logStringBuilder.toString();
    }

    private void setNavigationSelectionModel() {
        managementTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    currentTable = newValue;
                    mainTablePane.getSelectionModel().select(getTableIndex());
                }
        );

        mainTablePane.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> currentTable = tableNames.get(mainTablePane.getTabs().indexOf(newValue)))
        );
    }

    private Integer getTableIndex() {
        for (int i = 0; i < tableNames.size(); i++) {
            if (tableNames.get(i).equals(currentTable)) {
                return i;
            }
        }
        return -1;
    }

    private void setAllComboBox() {
        addressArrangementBox.getItems().addAll("一", "四");
        addressCourseBox.getItems().addAll("一", "四");
    }

    private void setAllTableData() {
        arrangements.clear();
        courses.clear();
        scores.clear();
        students.clear();
        teachers.clear();
        timetables.clear();

        arrangements.addAll(ArrangementDao.queryArrangementAll());
        courses.addAll(CourseDao.queryCourseAll());
        scores.addAll(ScoreDao.queryScoreAll());
        students.addAll(StudentDao.queryStudentAll());
        teachers.addAll(TeacherDao.queryTeacherAll());
        timetables.addAll(TimetableDao.queryTimetableAll());

        arrangementTableView.setItems(arrangements);
        courseTableView.setItems(courses);
        scoreTableView.setItems(scores);
        studentTableView.setItems(students);
        teacherTableView.setItems(teachers);
        timetableTableView.setItems(timetables);
    }

    private void setManagementTableView() {
        managementTableView.getColumns().get(0).prefWidthProperty()
                .bind(managementTableView.widthProperty().multiply(1));
        managementTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
        managementTableView.setItems(tableNames);
    }


    private void setColumnsPrefWidth() {
        setColumnPrefWidth(arrangementTableView);
        setColumnPrefWidth(courseTableView);
        setColumnPrefWidth(scoreTableView);
        setColumnPrefWidth(studentTableView);
        setColumnPrefWidth(teacherTableView);
        setColumnPrefWidth(timetableTableView);
    }

    private <T> void setColumnPrefWidth(TableView<T> tableView) {
        int columnSize = tableView.getColumns().size();
        for (int i = 0; i < columnSize; i++) {
            tableView.getColumns().get(i).prefWidthProperty()
                    .bind(tableView.widthProperty().multiply(1.0 / columnSize));
        }
    }

    private void setCellValueFactory() {
        setArrangementCellValueFactory();
        setCourseCellValueFactory();
        setScoreCellValueFactory();
        setStudentCellValueFactory();
        setTeacherCellValueFactory();
        setTimetableCellValueFactory();
    }

    private void setArrangementCellValueFactory() {
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

    private void setCourseCellValueFactory() {
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

    private void setScoreCellValueFactory() {
        examIdScoreColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getExamId().toString()));
        studentIdScoreColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStudentId()));
        scoreScoreColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getScore().toString()));
    }

    private void setStudentCellValueFactory() {
        studentIdStudentColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStudentId()));
        studentNameStudentColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStudentName()));
        genderStudentColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(!cellData.getValue().getGender() ? "男" : "女"));
        birthdayStudentColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBirthday().toString()));
        nationStudentColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNation()));
        majorStudentColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMajor()));
        gradeStudentColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGrade().toString()));
    }

    private void setTeacherCellValueFactory() {
        teacherIdTeacherColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTeacherId()));
        teacherNameTeacherColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTeacherName()));
        genderTeacherColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(!cellData.getValue().getGender() ? "男" : "女"));
        birthdayTeacherColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBirthday().toString()));
        nationTeacherColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNation()));
    }

    private void setTimetableCellValueFactory() {
        studentIdTimetableColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStudentId()));
        courseIdTimetableColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCourseId().toString()));
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
