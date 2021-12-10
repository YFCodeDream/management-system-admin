package com.yfcod.management;

import com.yfcod.management.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private String currentIdentity;
    private String currentUserId;
    private Stage primaryStage;

    private final KeyCodeCombination loginCombination =
            new KeyCodeCombination(KeyCode.ENTER);
    private final KeyCodeCombination updateInfoCombination =
            new KeyCodeCombination(KeyCode.U,
                    KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
    private final KeyCodeCombination logOutCombination =
            new KeyCodeCombination(KeyCode.L,
                    KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
    private final KeyCodeCombination quitCombination =
            new KeyCodeCombination(KeyCode.Q,
                    KeyCombination.CONTROL_DOWN);
    private final KeyCodeCombination exportCurrentDataCombination =
            new KeyCodeCombination(KeyCode.E,
                    KeyCombination.CONTROL_DOWN);
    private final KeyCodeCombination exportAllDataCombination =
            new KeyCodeCombination(KeyCode.E,
                    KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN);

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("厦门大学考试管理系统 - 登录");
        showLoginOverview();
    }

    public void showLoginOverview() {
        loadOverview("layout/login/login.fxml", true);
    }

    public void showAdminOverview() {
        loadOverview("layout/admin/admin.fxml", false);
        primaryStage.setResizable(true);
    }

    public void showTeacherOverview() {
        loadOverview("layout/admin/teacher.fxml", false);
        primaryStage.setResizable(false);
    }

    public void showStudentOverview() {
        loadOverview("layout/admin/student.fxml", false);
        primaryStage.setResizable(false);
    }

    private void loadOverview(String fxmlPath, boolean hasNotLogin) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(fxmlPath));
            AnchorPane rootPane = loader.load();
            Scene rootScene = new Scene(rootPane);
            primaryStage.setScene(rootScene);

            if (hasNotLogin) {
                rootPane.setBackground(new Background(new BackgroundImage(
                        new Image("file:src/main/resources/com/yfcod/management/img/login-bg-autumn-cp.jpg"),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT
                )));

                primaryStage.setResizable(false);
            }

            BaseController controller = loader.getController();

            if (controller != null) {
                controller.setMainApp(this);
                controller.setDialogStage(this.primaryStage);
                if (hasNotLogin) {
                    LoginController loginController = loader.getController();
                    currentIdentity = loginController.getCurrentIdentity();

                    if (loginController.getLeftBtn().getText().equals("登录")) {
                        rootScene.getAccelerators().put(loginCombination, loginController::handleLeft);
                    } else {
                        rootScene.getAccelerators().remove(loginCombination);
                    }
                }
                if (currentIdentity != null) {
                    rootScene.getAccelerators().put(exportCurrentDataCombination, () ->
                            ((MenuItemOperation) controller).handleExportCurrentData());
                    rootScene.getAccelerators().put(exportAllDataCombination, () ->
                            ((MenuItemOperation) controller).handleExportAllData());
                    rootScene.getAccelerators().put(updateInfoCombination, () ->
                            ((MenuItemOperation) controller).handleUpdateInfo());
                    rootScene.getAccelerators().put(logOutCombination, () ->
                            ((MenuItemOperation) controller).handleLogout());
                    rootScene.getAccelerators().put(quitCombination, () ->
                            ((MenuItemOperation) controller).handleQuit());
                    if (currentIdentity.equals("系统管理员")) {
                        ((AdminController) controller).setCurrentAdminId(currentUserId);
                    }
                    if (currentIdentity.equals("教师")) {
                        ((TeacherController) controller).setCurrentTeacherId(currentUserId);
                        ((TeacherController) controller).showAllAverageChart();
                        ((TeacherController) controller).setAllTableData();
                    }
                    if (currentIdentity.equals("学生")) {
                        ((StudentController) controller).setCurrentStudentId(currentUserId);
                        ((StudentController) controller).setAllTableData();
                        ((StudentController) controller).showStudentScoreChart();
                    }
                }
            }

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public void setCurrentIdentity(String currentIdentity) {
        this.currentIdentity = currentIdentity;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public KeyCodeCombination getLoginCombination() {
        return loginCombination;
    }

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
