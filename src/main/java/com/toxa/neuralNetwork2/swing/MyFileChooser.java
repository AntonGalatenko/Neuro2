package com.toxa.neuralNetwork2.swing;

import javax.swing.*;
import java.io.File;

public class MyFileChooser extends JFrame {

    private String path;

    public MyFileChooser(){
        JFileChooser chooser = new JFileChooser();

        chooser.setCurrentDirectory(new File("D:\\MyJava\\workspace_Idea\\Neuro2\\NeuronImg\\img"));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.showOpenDialog(this);

        if(chooser.getSelectedFile().getPath() != null)
            path = chooser.getSelectedFile().getPath();
    }

    public String getPath(){
        return path;
    }
}
