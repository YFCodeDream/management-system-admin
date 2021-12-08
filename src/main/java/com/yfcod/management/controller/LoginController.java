package com.yfcod.management.controller;

import com.yfcod.management.Main;
import com.yfcod.management.dao.AdminDao;
import com.yfcod.management.dao.StudentDao;
import com.yfcod.management.dao.TeacherDao;
import com.yfcod.management.model.Admin;
import com.yfcod.management.model.Student;
import com.yfcod.management.model.Teacher;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Objects;

@SuppressWarnings("FieldCanBeLocal")
public class LoginController extends BaseController{
    @FXML
    private TextField adminIdField;
    @FXML
    private PasswordField adminPwdField;
    @FXML
    private HBox adminPhoneHBox;
    @FXML
    private TextField adminPhoneField;

    @FXML
    private Button leftBtn;
    @FXML
    private Button rightBtn;

    @FXML
    private ComboBox<String> identityBox;

    @FXML
    private Label registerInfoText;

    private Main main;
    private Stage dialogStage;

    private String currentIdentity;
    private String adminId;
    private String adminPwd;

    @FXML
    private void initialize() {
        leftBtn.setText("登录");
        rightBtn.setText("注册");
        registerInfoText.setVisible(false);
        adminPhoneHBox.setVisible(false);
        identityBox.getItems().addAll(
                "学生",
                "教师",
                "系统管理员"
        );
        identityBox.getSelectionModel().select(0);
        rightBtn.setDisable(true);

        identityBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                rightBtn.setDisable(!newValue.equals("系统管理员")));
    }

    @FXML
    private void handleLeft() {
        if (leftBtn.getText().equals("登录")) {
            if (identityBox.getValue().equals("系统管理员")) {
                if (loginToAdmin()) return;
            }

            if (identityBox.getValue().equals("教师")) {
                if (loginToTeacher()) return;
            }

            if (identityBox.getValue().equals("学生")) {
                loginToStudent();
            }
        } else if (leftBtn.getText().equals("清空")) {
            clearTextField();
        }
    }

    private boolean loginToAdmin() {
        if (checkAdminInvalid()) return true;
        showAlert("登录成功", "information");
        currentIdentity = "系统管理员";
        this.main.setCurrentIdentity(currentIdentity);

        this.main.showAdminOverview();
        this.dialogStage.setTitle("厦门大学考试管理系统 - 管理员号：" + adminId);
        this.dialogStage.centerOnScreen();
        return false;
    }

    private boolean loginToTeacher() {
        if (checkTeacherInvalid()) return true;
        showAlert("登录成功", "information");
        currentIdentity = "教师";

        this.main.setCurrentUserId(adminId);
        this.main.setCurrentIdentity(currentIdentity);

        this.main.showTeacherOverview();
        this.dialogStage.setTitle("厦门大学考试管理系统 - 教师号：" + adminId);
        this.dialogStage.centerOnScreen();
        return false;
    }

    private void loginToStudent() {
        if (checkStudentInvalid()) return;
        showAlert("登录成功", "information");
        currentIdentity = "学生";

        this.main.setCurrentUserId(adminId);
        this.main.setCurrentIdentity(currentIdentity);

        this.main.showStudentOverview();
        this.dialogStage.setTitle("厦门大学考试管理系统 - 学号：" + adminId);
        this.dialogStage.centerOnScreen();
    }

    @FXML
    private void handleRight() {
        if (rightBtn.getText().equals("注册")) {
            showAlert("需要验证管理员身份，请稍等", "information");

            if (checkAdminInvalid()) return;

            registerInfoText.setVisible(true);
            adminPhoneHBox.setVisible(true);

            leftBtn.setText("清空");
            rightBtn.setText("提交");

            this.main.getPrimaryStage().setTitle("厦门大学考试管理系统-注册");
        } else if (rightBtn.getText().equals("提交")) {
            this.adminId = adminIdField.getText();
            this.adminPwd = adminPwdField.getText();
            String adminPhone = adminPhoneField.getText();

            if (checkAdminInvalid(adminPhone)) {
                returnToLogin();
                clearTextField();
                return;
            }

            if (AdminDao.queryAdminById(this.adminId) != null) {
                showAlert("该管理员号已存在", "error");
                returnToLogin();
                clearRegisterInfo();
                return;
            }

            AdminDao.addAdmin(
                    new Admin(
                            this.adminId,
                            this.adminPwd,
                            adminPhone
                    )
            );
            showAlert("注册成功", "information");
            returnToLogin();
        }
    }

    private boolean checkAdminInvalid() {
        this.adminId = adminIdField.getText();
        this.adminPwd = adminPwdField.getText();
        Admin admin = AdminDao.queryAdminById(adminId);
        if (admin == null) {
            showAlert("未查找到该管理员", "warning");
            return true;
        }
        if (!Objects.equals(admin.getAdminPwd(), this.adminPwd)) {
            showAlert("密码错误，请重新输入", "warning");
            return true;
        }
        return false;
    }

    private boolean checkTeacherInvalid() {
        this.adminId = adminIdField.getText();
        this.adminPwd = adminPwdField.getText();
        Teacher teacher = TeacherDao.queryTeacherById(adminId);
        if (teacher == null) {
            showAlert("未查找到该教师", "warning");
            return true;
        }
        if (!Objects.equals(teacher.getPassword(), this.adminPwd)) {
            showAlert("密码错误，请重新输入", "warning");
            return true;
        }
        return false;
    }

    private boolean checkStudentInvalid() {
        this.adminId = adminIdField.getText();
        this.adminPwd = adminPwdField.getText();
        Student student = StudentDao.queryStudentById(adminId);
        if (student == null) {
            showAlert("未查找到该学生", "warning");
            return true;
        }
        if (!Objects.equals(student.getPassword(), this.adminPwd)) {
            showAlert("密码错误，请重新输入", "warning");
            return true;
        }
        return false;
    }

    private void clearTextField() {
        adminIdField.setText("");
        adminPwdField.setText("");
        adminPhoneField.setText("");
    }

    private void returnToLogin() {
        clearRegisterInfo();
        leftBtn.setText("登录");
        rightBtn.setText("注册");
    }

    private boolean checkAdminInvalid(String adminPhone) {
        if (adminId.length() != 11) {
            showAlert("管理员号为11位", "error");
            clearRegisterInfo();
            return true;
        }
        if (adminPwd == null || adminPwd.length() == 0) {
            showAlert("请输入密码", "warning");
            clearRegisterInfo();
            return true;
        }
        if (adminPhone == null || adminPhone.length() == 0) {
            showAlert("请输入电话号码", "warning");
            clearRegisterInfo();
            return true;
        }
        return false;
    }

    private void clearRegisterInfo() {
        registerInfoText.setVisible(false);
        adminPhoneHBox.setVisible(false);
    }

    @SuppressWarnings({"SameParameterValue", "DuplicatedCode"})
    private void showAlert(String message, String alertType) {
        Alert alert;
        if (Objects.equals(alertType, "error")) {
            alert = new Alert(Alert.AlertType.ERROR);
        } else if (Objects.equals(alertType, "information")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        } else if (Objects.equals(alertType, "configuration")) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
        } else if (Objects.equals(alertType, "warning")) {
            alert = new Alert(Alert.AlertType.WARNING);
        } else {
            return;
        }

        alert.initOwner(dialogStage);
        alert.setTitle("提示信息");
        alert.setHeaderText("厦门大学考试管理系统信息");
        alert.setContentText(message);

        alert.showAndWait();
    }

    public String getCurrentIdentity() {
        return currentIdentity;
    }

    @Override
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void setMainApp(Main main) {
        this.main = main;
    }
}
