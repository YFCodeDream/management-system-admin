package com.yfcod.management;

import com.yfcod.management.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private String currentIdentity;
    private String currentUserId;
    private Stage primaryStage;

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
                }
                if (currentIdentity != null) {
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

            primaryStage.setScene(new Scene(rootPane));
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

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
