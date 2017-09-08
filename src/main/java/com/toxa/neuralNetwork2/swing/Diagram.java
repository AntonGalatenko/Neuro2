package com.toxa.neuralNetwork2.swing;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Diagram extends ApplicationFrame {

    public Diagram(String title) {
        super(title);
    }

    public JFreeChart createChart(ArrayList value){
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "График",
                "Вес",
                "Ошибка",
                null,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = chart.getXYPlot();
//        plot.setDomainTickBandPaint(new Color(232, 232, 232));

        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint(Color.gray);

        plot.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));

        XYSplineRenderer renderer = new XYSplineRenderer();
        renderer.setPrecision(4);
        renderer.setSeriesShapesVisible(0, false);

        XYDataset dataSet = createDataSet(value);

        plot.setDataset(0, dataSet);

        plot.setRenderer(0, renderer);

        return chart;
    }

    private XYDataset createDataSet(ArrayList<HashMap> value){

        HashMap<Double, Double> hashMap = value.get(0);

        XYSeriesCollection dataSet = new XYSeriesCollection();

        XYSeries series = new XYSeries("Neuron 1");

        for(Map.Entry<Double, Double> entry : hashMap.entrySet()){
            series.add(entry.getKey(), entry.getValue());
            System.out.println("w " + entry.getKey() + " err " + entry.getValue());
        }

        System.out.println("size " + value.size());

//        series.add(1.0, 1.0);
//        series.add(2.0, 4.0);
//        series.add(3.0, 2.0);
//        series.add(12.0, 11.0);
//        series.add(9.0, 6.0);
//        series.add(5.0, 7.0);

        dataSet.addSeries(series);

        return dataSet;
    }

}
