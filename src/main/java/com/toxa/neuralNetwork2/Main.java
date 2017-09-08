package com.toxa.neuralNetwork2;

import com.toxa.neuralNetwork2.neuralNetwork.NeuralNetwork_ManyHideLayer;
import com.toxa.neuralNetwork2.neuralNetwork.NeuralNetwork_NoHideLayer;
import com.toxa.neuralNetwork2.neuralNetwork.NeuralNetwork_OneHideLayer;
import com.toxa.neuralNetwork2.swing.GraphicPanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Main {
    static final File FILE = new File("NeuronImg\\img");
    static final String WEIGHT_FILE_PATH = "NeuronImg\\neuron2Weight.txt";
    static public final double E = 0.7;        //Скорость обучения 0.7
    static public final double A = 0.3;        //Момент 0.3

    public static void main(String[] args) {

//        MainFrame mainFrame = new MainFrame();
//
//        oneHideLayerFinal();
        oneHideLayer();
//        noHideLayer();
//        manyHideLayer(2);
//
//        Random random = new Random();
//        File[] files = FILE.listFiles();
//        int rnd = random.nextInt(files.length);

//        com.toxa.neuralNetwork2.NeuralNetwork_NoHideLayereuralNetwork_NoHideLayer com.toxa.neuralNetwork2.neuralNetwork = new com.toxa.neuralNetwork2.neuralNetworkalNetwork2.NeuralNetwork_NoHideLayer(10);
//        com.toxa.neuralNetwork2.neuralNetworkalNetwork2.NeuralNetwork_OneHideLayer com.toxa.neuralNetwork2.neuralNetwork = new com.toxa.neuralNetwork2.neuralNetworkalNetwork2.NeuralNetwork_OneHideLayer(10);
//        com.toxa.neuralNetwork2.neuralNetwork.study(loadIMG(files[rnd]), rnd);

    }

    private static void noHideLayer() {
        NeuralNetwork_NoHideLayer neuralNetworkNoHideLayer = new NeuralNetwork_NoHideLayer(10);
        Random random = new Random();
        File[] files = FILE.listFiles();

        boolean isStudying;
        int n = 0;
        int k = 0;
        long start = System.currentTimeMillis();
        while (n < 200){
            k++;
            int rnd = random.nextInt(files.length);
            System.out.print(k + "   | " + rnd + " ");
            isStudying = neuralNetworkNoHideLayer.study(loadIMG(files[rnd]), rnd);
            if(isStudying)
                n++;
            else
                n = 0;
        }
        long time = (System.currentTimeMillis() - start) / 1000;

        System.out.println();

        neuralNetworkNoHideLayer.printNeuronHideWeight();

        for(int i = 0; i < files.length; i++){
            neuralNetworkNoHideLayer.study(loadIMG(files[i]), i);
            System.out.println("-- i");
            neuralNetworkNoHideLayer.printError();
        }

        System.out.println();
        System.out.println("k " + (k - 200) + " time " + time);
        System.out.println();
    }

    private static void oneHideLayer(){
        NeuralNetwork_OneHideLayer neuralNetworkOneHideLayer = new NeuralNetwork_OneHideLayer(10);
        Random random = new Random();
        File[] files = FILE.listFiles();

        boolean isStudying;
        int n = 0;
        int k = 0;
        long start = System.currentTimeMillis();
        while (n < 200){
            k++;
            int rnd = random.nextInt(files.length);
            char fileName = files[rnd].getName().charAt(0);
            System.out.print(k + "   | " + fileName + " ");
//            System.out.println("file name " + fileName);
            isStudying = neuralNetworkOneHideLayer.study(loadIMG(files[rnd]), Character.getNumericValue(fileName));
            if(isStudying)
                n++;
            else
                n = 0;
        }
        long time = (System.currentTimeMillis() - start) / 1000;

        System.out.println();

        neuralNetworkOneHideLayer.printNeuronHideWeight();

        for(int i = 0; i < files.length; i++){
            neuralNetworkOneHideLayer.study(loadIMG(files[i]), Character.getNumericValue(files[i].getName().charAt(0)));
            System.out.println("-- " + files[i].getName());
            neuralNetworkOneHideLayer.printError();
        }

        System.out.println();
        System.out.println("k " + (k - 200) + " time " + time);
        System.out.println();

        neuralNetworkOneHideLayer.saveWeight();


        ArrayList<HashMap> arrayList = neuralNetworkOneHideLayer.getArrayHashMapList();
        new GraphicPanel(arrayList);

        ArrayList<double[]> ar = neuralNetworkOneHideLayer.getWeightList();
        System.out.println();
        System.out.println("weight");
        for(int i = 0; i < ar.size(); i++)
            System.out.println(Arrays.toString(ar.get(i)));


    }

    private static void oneHideLayerFinal(){
        NeuralNetwork_OneHideLayer neuralNetworkOneHideLayer = new NeuralNetwork_OneHideLayer(10);
        File[] files = FILE.listFiles();

        neuralNetworkOneHideLayer.loadWeight();

        for(int i = 0; i < files.length; i++){
            System.out.print("<> " + files[i].getName() + "; result - ");
            System.out.println(neuralNetworkOneHideLayer.getResult(loadIMG(files[i])));
//            neuralNetworkOneHideLayer.getResult(loadIMG(files[i]));

//            neuralNetworkOneHideLayer.printError();
        }
    }

    private static void manyHideLayer(int numberHideLayer){
        NeuralNetwork_ManyHideLayer neuralNetworkManyHideLayer = new NeuralNetwork_ManyHideLayer(10, numberHideLayer);
        Random random = new Random();
        File[] files = FILE.listFiles();

        boolean isStudying;
        int n = 0;
        int k = 0;
        long start = System.currentTimeMillis();
        while (n < 200){
            k++;
            int rnd = random.nextInt(files.length);
            char fileName = files[rnd].getName().charAt(0);
            System.out.print(k + "   | " + fileName + " ");
//            System.out.println("file name " + fileName);
            isStudying = neuralNetworkManyHideLayer.study(loadIMG(files[rnd]), Character.getNumericValue(fileName));
            if(isStudying)
                n++;
            else
                n = 0;
        }
        long time = (System.currentTimeMillis() - start) / 1000;

        System.out.println();

        neuralNetworkManyHideLayer.printNeuronHideWeight();

        for(int i = 0; i < files.length; i++){
            neuralNetworkManyHideLayer.study(loadIMG(files[i]), Character.getNumericValue(files[i].getName().charAt(0)));
            System.out.println("-- " + files[i].getName());
            neuralNetworkManyHideLayer.printError();
        }

        System.out.println();
        System.out.println("k " + (k - 200) + " time " + time);
        System.out.println();
    }

    private static int[] loadIMG(File file){
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
