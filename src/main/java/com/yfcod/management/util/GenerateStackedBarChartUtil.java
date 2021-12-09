package com.yfcod.management.util;

import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

public class GenerateStackedBarChartUtil extends BaseBarChartUtil{
    public static void setStackedBarChartData(StackedBarChart<String, Number> stackedBarChart,
                                              List<String> seriesNames,
                                              HashMap<String, List<BinaryTuple<String, Number>>> seriesData) {
        for (String seriesName : seriesNames) {
            XYChart.Series<String, Number> stringNumberSeries = new XYChart.Series<>();
            stringNumberSeries.setName(seriesName);
            List<BinaryTuple<String, Number>> currentSeriesData = seriesData.get(seriesName);
            for (BinaryTuple<String, Number> eachData : currentSeriesData) {
                stringNumberSeries.getData().add(new XYChart.Data<>(eachData.getFirst(), eachData.getSecond()));
            }
            stackedBarChart.getData().add(stringNumberSeries);
        }
    }

    @Data
    public static class BinaryTuple <A, B> {
        private A first;
        private B second;

        public BinaryTuple(A first, B second) {
            this.first = first;
            this.second = second;
        }
    }
}
