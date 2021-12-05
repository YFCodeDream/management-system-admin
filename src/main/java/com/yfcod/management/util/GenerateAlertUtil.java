package com.yfcod.management.util;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.Objects;

public class GenerateAlertUtil {
    public static void showAlert(Stage primaryStage, String message, String alertType) {
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

        alert.initOwner(primaryStage);
        alert.setTitle("提示信息");
        alert.setHeaderText("厦门大学考试管理系统信息");
        alert.setContentText(message);

        alert.showAndWait();
    }
}
