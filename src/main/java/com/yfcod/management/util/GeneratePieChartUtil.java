package com.yfcod.management.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;

public class GeneratePieChartUtil {
    public static void setPieChartData(PieChart pieChart,
                                       List<String> xData,
                                       List<Number> yData) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        if (xData.size() != yData.size()) return;
        for (int i = 0; i < xData.size(); i++) {
            pieChartData.add(new PieChart.Data(xData.get(i), yData.get(i).doubleValue()));
        }

        pieChart.getData().addAll(pieChartData);

        Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 18 arial;");

        for (PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    e -> {
                        caption.setTranslateX(e.getSceneX());
                        caption.setTranslateY(e.getSceneY());
                        caption.setText(String.valueOf(data.getPieValue()));
                    });
        }
    }
}
