package com.toxa.neuralNetwork2.swing;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.util.ArrayList;

public class GraphicPanel extends JFrame{
    private JPanel mainPanel;

//    private int

    public GraphicPanel(){}

    public GraphicPanel(ArrayList value){
//        System.out.println("GRGR");

        add(mainPanel);
        setVisible(true);
        setLocation(300, 500);

        JFreeChart chart = new Diagram("Diagram").createChart(value);
        ChartPanel cp = new ChartPanel(chart);

        add(cp);


        pack();
//        repaint();
//        revalidate();
    }

   /* @Override
    public void paint(Graphics g) {
        super.paint(g);

        System.out.println("GRGR!@!@");

        g.drawLine(0, 0, 0, 600);
        g.drawLine(10, 200, 10, 400);

        g.drawRect(100, 20, 300, 400);
//        g.fillRect();

    }*/

}
