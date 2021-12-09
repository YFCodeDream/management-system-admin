package com.yfcod.management.util;

import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import java.util.List;

public abstract class BaseBarChartUtil {
    public static void setXYAxis(CategoryAxis xAxis,
                                 NumberAxis yAxis,
                                 List<String> xValues,
                                 String xLabel,
                                 String yLabel) {
        xAxis.setCategories(FXCollections.observableArrayList(xValues));
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
    }
}
