package com.toxa.neuralNetwork2.swing;

import com.toxa.neuralNetwork2.neuralNetwork.NeuralNetwork;
import com.toxa.neuralNetwork2.neuralNetwork.NeuralNetwork_OneHideLayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends  JFrame{

    private NeuralNetwork neuralNetwork;
    private ImageIcon imageIcon;
    private MyFileChooser myFileChooser;
    private JPanel mainPanel;
    private JLabel LeftLabel;
    private JButton button1;
    private JPanel leftPanel;
    private JLabel imageLabel;
    private JPanel leftBottomPanel;
    private JLabel resultLabel;

    public MainFrame() {
        setTitle("Neural Network 2");

        neuralNetwork = new NeuralNetwork_OneHideLayer(10);
        neuralNetwork.loadWeight();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFileChooser = new MyFileChooser();
                Image image = null;
                try {
                    image = ImageIO.read(new File(myFileChooser.getPath()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                image = image.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
                imageIcon = new ImageIcon(image);
                imageLabel.setIcon(imageIcon);
                pack();
                revalidate();

                int[] img = loadIMG(myFileChooser.getPath());
                String result = neuralNetwork.getResult(img);
                resultLabel.setText("Результат: " + result);

            }
        });



        add(mainPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private int[] loadIMG(String path){
        File file = new File(path);
        int[] result = new int[15];

        try {
            BufferedImage img = ImageIO.read(file);

            int k = 0;
            int pixels;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 5; j++){
                    pixels = img.getRGB(i,j);

                    int n = 0;
                    if(pixels < - 100000)
                        n = 1;

                    result[k++] = n;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;
    }
}
