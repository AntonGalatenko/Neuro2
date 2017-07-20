package com.toxa.neuralNetwork2.swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainFrame extends  JFrame{

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
            }
        });




        add(mainPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
}
