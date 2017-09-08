package com.toxa.neuralNetwork2.swing;

import com.toxa.neuralNetwork2.neuralNetwork.NeuralNetwork;
import com.toxa.neuralNetwork2.neuralNetwork.NeuralNetwork_OneHideLayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame{

    private final String PATH = "D:\\MyJava\\workspace_Idea\\Neuro2\\NeuronImg\\img";
    private NeuralNetwork neuralNetwork;
    private MyFileChooser myFileChooser;
    private JPanel mainPanel;
    private JLabel LeftLabel;
    private JButton button1;
    private JPanel leftPanel;
    private JLabel imageLabel;
    private JPanel leftBottomPanel;
    private JLabel resultLabel;
    private JPanel listPanel;
    private JList filesList;
    private GraphicPanel grPanel;

    public MainFrame() {
        setTitle("Neural Network 2");

        neuralNetwork = new NeuralNetwork_OneHideLayer(10);
        neuralNetwork.loadWeight();
        grPanel = new GraphicPanel();

        filesList.setModel(getListModel(PATH));

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                myFileChooser = new MyFileChooser(path);
//                path = myFileChooser.getPath();
//                filesList.setModel(getListModel(path));
                grPanel = new GraphicPanel();

            }
        });

        filesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if((! e.getValueIsAdjusting()) && (filesList.getSelectedValue() != null)){
                        String p = PATH + "\\" + filesList.getSelectedValue();
                        imageLabel.setIcon(getImgIcon(p));
                        int[] img = loadIMG(p);
                        String result = neuralNetwork.getResult(img);
                        resultLabel.setText("Результат: " + result);


                }
            }
        });

        add(mainPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        revalidate();

    }

    private ImageIcon getImgIcon(String path){
        Image image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
            System.out.println("path " + path);
            ex.printStackTrace();
        }
        image = image.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        return new ImageIcon(image);
    }

    private DefaultListModel getListModel(String path){
        File file = new File(path);
        if(file.isFile())
            return null;

        DefaultListModel result = new DefaultListModel();
        File[] fileList = file.listFiles();

        for(File f : fileList)
            result.addElement(f.getName());

        return result;
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
