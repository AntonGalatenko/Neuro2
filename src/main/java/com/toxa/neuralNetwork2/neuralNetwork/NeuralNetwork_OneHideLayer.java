package com.toxa.neuralNetwork2.neuralNetwork;

import com.toxa.neuralNetwork2.Main;
import com.toxa.neuralNetwork2.neuralNetwork.neuron.NeuronHide;
import com.toxa.neuralNetwork2.neuralNetwork.neuron.NeuronOutput;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NeuralNetwork_OneHideLayer implements NeuralNetwork {

    private NeuronHide[] neuronsHide;
    private NeuronOutput[] neuronsOutput;

    double[] correctOutput = new double[10];
    double[] output = new double[10];
    double[] error = new double[10];

    ArrayList<HashMap> arrayHashMapList = new ArrayList<HashMap>();
    ArrayList<double[]> weightList = new ArrayList<double[]>();
    boolean t = true;

    public NeuralNetwork_OneHideLayer(int numberOutputNeurons){
        neuronsHide = new NeuronHide[numberOutputNeurons];
        neuronsOutput = new NeuronOutput[numberOutputNeurons];

        for(int i = 0; i < neuronsOutput.length; i++)
            neuronsOutput[i] = new NeuronOutput(neuronsHide.length);

        for(int i = 0; i < neuronsHide.length; i++)
            neuronsHide[i] = new NeuronHide();
    }

    public double[] handleOutput(double[] input){
        double[] output = new double[neuronsOutput.length];
        for(int i = 0; i < output.length; i++)
            output[i] = Math.round(neuronsOutput[i].transfer(input) * 100.0) / 100.0;
        return output;
    }

    public double[] handleHide(int[] input){
        double[] output = new double[neuronsHide.length];
        for(int i = 0; i < output.length; i++)
            output[i] = Math.round(neuronsHide[i].transfer(input) * 100.0) / 100.0;
        return output;
    }

    public boolean study(int[] input, int correctAnswer){
        correctOutput = new double[neuronsOutput.length];
        correctOutput[correctAnswer] = 1.0;
        int n = 0;

        double[] outputHide = handleHide(input);
        output = handleOutput(outputHide);

        error = new double[output.length];

        for(int i = 0; i < output.length; i++)
            error[i] = Math.round(Math.pow((correctOutput[i] - output[i]), 2) * 100.0 ) / 100.0;

        double deltaOutput[] = new double[output.length];
        HashMap<Double, Double> hashMap = new HashMap<Double, Double>();
        while (isError(error)){
            n++;

            for(int i = 0; i < output.length; i++)
                deltaOutput[i] = Math.round((correctOutput[i] - output[i]) * ((1 - output[i]) * output[i]) * 100.0) / 100.0;

            double gradSynapseFromOutputToHide = 0;
            double deltaWeightFromOutputToHide = 0;
            for(int i = 0; i < output.length; i++){
                for(int j = 0; j < neuronsOutput[0].getWeight().length; j++){
                    gradSynapseFromOutputToHide = outputHide[i] * deltaOutput[j];
                    deltaWeightFromOutputToHide = Main.E * gradSynapseFromOutputToHide + Main.A * neuronsOutput[j].getDeltaWeight()[i];
                    neuronsOutput[j].addWeight(i, deltaWeightFromOutputToHide);
                    neuronsOutput[j].setDeltaWeight(i, deltaWeightFromOutputToHide);
                }
            }

            double sum[] = new double[outputHide.length];
            for(int i = 0; i < outputHide.length; i++)
                for(int j = 0; j < outputHide.length; j++)
                    sum[i] += (deltaOutput[j] * neuronsOutput[j].getWeight()[i]);

            double deltaHide[] = new double[output.length];
            double gradSynapseFromHideToInput = 0;
            double deltaWeightFromHideToInput = 0;
            for(int i = 0; i < outputHide.length; i++){
                deltaHide[i] = ((1 - outputHide[i]) * outputHide[i]) * sum[i];
                for(int j = 0; j < neuronsHide[0].getWeight().length; j++){
                    gradSynapseFromHideToInput = input[j] * deltaHide[i];
                    deltaWeightFromHideToInput = Main.E * gradSynapseFromHideToInput + Main.A * neuronsHide[i].getDeltaWeight()[j];
                    neuronsHide[i].addWeight(j, deltaWeightFromHideToInput);
                    neuronsHide[i].setDeltaWeight(j, deltaWeightFromHideToInput);
                }
            }

            outputHide = handleHide(input);
            output = handleOutput(outputHide);

            for(int i = 0; i < output.length; i++)
                error[i] = Math.round(Math.pow((correctOutput[i] - output[i]), 2) * 100.0) / 100.0;

            if(n > 100){
                System.out.println();
                System.out.println();
                for(int i = 0; i < output.length; i++)
                    System.err.println("Error " + i + " - " + error[i] + " (" + output[i] + " | " + correctOutput[i] + ")" + " n=" + n);
                System.exit(0);
            }


/////////////////////

            double neuronWeightSum = 0;

            for(int i = 0; i < neuronsOutput[0].getWeight().length; i++)
                neuronWeightSum += neuronsOutput[0].getWeight()[i];

            double err = error[0] * 10;
            hashMap.put(neuronWeightSum, err);

            if(t){
                weightList.add(neuronsOutput[0].getWeight());
                System.out.println(Arrays.toString(neuronsOutput[0].getWeight()));
            }

////////////////////
        }

        t = false;

        arrayHashMapList.add(hashMap);

        System.out.println("iteration " + n + " " + Arrays.toString(error));

        if (n == 0)
            return true;
        return false;
    }

    public void printNeuronHideWeight(){
        for (int i = 0; i < neuronsHide.length; i++)
            System.out.print(neuronsHide[i].printWeight());

        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();
    }

    public void printError(){
        for(int i = 0; i < output.length; i++)
            System.out.println("Error " + i + " - " + error[i] + " (" + output[i] + " | " + correctOutput[i] + ")");
        System.out.println();
    }

    private boolean isError(double[] error){
        for (int i = 0; i < error.length; i++)
            if(error[i] > 0.03)
                return true;
        return false;
    }

    public void saveWeight(){
        StringBuilder weights = new StringBuilder();
        for(int i = 0; i < neuronsHide.length; i++)
            weights.append(neuronsHide[i].printWeight().toString());

        for(int i = 0; i < neuronsOutput.length; i++)
            weights.append(neuronsOutput[i].printWeight());

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("neuronWeight.txt"));

            bw.write(weights.toString());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void loadWeight() {
        BufferedReader br = null;
        String line;
        String[] sl;
        int h = 0;
        int k = 0;

        try {
            br = new BufferedReader(new FileReader("neuronWeight.txt"));

            while ((line = br.readLine()) != null){
                sl = line.split("   ");

                if(sl.length == 1){
                    k = 0;
                    h++;
                }
                else if(sl.length == 3)
                    for(int i = 0; i < sl.length; i++)
                        neuronsHide[h].setWeight(k++, Double.parseDouble(sl[i].replace(",",".")));
                else if(sl.length > 3){
                    int h1 = 0;
                    int k1 = 0;
                    for(int i = 1; i < sl.length + 1; i++){
                        neuronsOutput[h1].setWeight(k, Double.parseDouble(sl[i - 1].replace(",", ".")));
                        k++;

                        if(i % 10 == 0){
                            h1++;
                            k = 0;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getResult(int[] input) {
        double[] outputHide = handleHide(input);
        output = handleOutput(outputHide);

        int n = 0;
        double value = output[0];
        for(int i = 1; i < output.length; i++){
            if(output[i] > value){
                value = output[i];
                n = i;
            }
        }
        return n + "";
    }

    @Override
    public String getWeights() {
        StringBuilder weights = new StringBuilder();
        for(int i = 0; i < neuronsHide.length; i++)
            weights.append(neuronsHide[i].printWeight().toString());
        return weights.toString();
    }

    public ArrayList<HashMap> getArrayHashMapList() {
        return arrayHashMapList;
    }

    public ArrayList<double[]> getWeightList() {
        return weightList;
    }
}
