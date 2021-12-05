package com.yfcod.management.controller;

import com.yfcod.management.Main;
import javafx.stage.Stage;

public abstract class BaseController {
    public abstract void setMainApp(Main main);

    public abstract void setDialogStage(Stage primaryStage);
}
