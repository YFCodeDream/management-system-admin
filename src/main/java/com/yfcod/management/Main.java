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
import org.apache.log4j.Logger;

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
    private final KeyCodeCombination currentSendMailCombination =
            new KeyCodeCombination(KeyCode.M,
                    KeyCombination.CONTROL_DOWN);
    private final KeyCodeCombination allSendMailCombination =
            new KeyCodeCombination(KeyCode.M,
                    KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN);
    private final KeyCodeCombination showAllTableDataCombination =
            new KeyCodeCombination(KeyCode.F5);

    private final Logger logger = Logger.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("厦门大学考试管理系统 - 登录");

        this.primaryStage.setOnCloseRequest((event) -> BaseController.clearTempFile(logger));

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
                    setKeyCodeCombination(rootScene, (MenuItemOperation) controller);

                    if (currentIdentity.equals("系统管理员")) {
                        logger.info("system admin logged in -----");
                        logger.info("current admin id: " + currentUserId);
                        ((AdminController) controller).setCurrentAdminId(currentUserId);
                    }
                    if (currentIdentity.equals("教师")) {
                        logger.info("teacher side user logged in -----");
                        logger.info("current teacher id: " + currentUserId);
                        ((TeacherController) controller).setCurrentTeacherId(currentUserId);
                        ((TeacherController) controller).showAllAverageChart();
                        ((TeacherController) controller).setAllTableData();
                    }
                    if (currentIdentity.equals("学生")) {
                        logger.info("student side user logged in -----");
                        logger.info("current student id: " + currentUserId);
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

    private void setKeyCodeCombination(Scene rootScene, MenuItemOperation controller) {
        rootScene.getAccelerators().put(exportCurrentDataCombination,
                controller::handleExportCurrentData);
        rootScene.getAccelerators().put(exportAllDataCombination,
                controller::handleExportAllData);
        rootScene.getAccelerators().put(updateInfoCombination,
                controller::handleUpdateInfo);
        rootScene.getAccelerators().put(logOutCombination,
                controller::handleLogout);
        rootScene.getAccelerators().put(quitCombination,
                controller::handleQuit);
        rootScene.getAccelerators().put(currentSendMailCombination,
                controller::handleCurrentSendMail);
        rootScene.getAccelerators().put(allSendMailCombination,
                controller::handleAllSendMail);
        rootScene.getAccelerators().put(showAllTableDataCombination,
                controller::handleShowAllTableData);
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
