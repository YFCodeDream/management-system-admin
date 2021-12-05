package com.yfcod.management.util;

import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.List;

public class GenerateBarChartUtil {
    public static void setXYAxis(CategoryAxis xAxis,
                                 NumberAxis yAxis,
                                 List<String> xValues,
                                 String xLabel,
                                 String yLabel) {
        xAxis.setCategories(FXCollections.observableArrayList(xValues));
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
    }

    public static void setBarChartData(BarChart<String, Number> barChart,
                                       List<String> xData,
                                       List<Number> yData,
                                       String seriesName) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(seriesName);
        if (xData.size() != yData.size()) return;

        for (int i = 0; i < xData.size(); i++) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(xData.get(i), yData.get(i));
            data.setNode(new Label(yData.get(i).toString()));
            series.getData().add(data);
        }

        barChart.getData().add(series);
    }
}
