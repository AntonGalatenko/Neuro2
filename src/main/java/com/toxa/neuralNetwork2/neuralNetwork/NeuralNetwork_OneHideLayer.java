package com.toxa.neuralNetwork2.neuralNetwork;

import com.toxa.neuralNetwork2.Main;

import java.io.*;

public class NeuralNetwork_OneHideLayer implements NeuralNetwork {

    private NeuronHide[] neuronsHide;
    private NeuronOutput[] neuronsOutput;

    double[] correctOutput = new double[10];
    double[] output = new double[10];
    double[] error = new double[10];


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
//            output[i] = neuronsHide[i].transfer(input);
            output[i] = Math.round(neuronsHide[i].transfer(input) * 100.0) / 100.0;
        return output;
    }

    public boolean study(int[] input, int correctAnswer){
//        System.out.println("input neurons " + correctAnswer);

//        System.out.println();
//        System.out.println("neurons input -> hide WEIGHT");
//        for (int i = 0; i < neuronsHide.length; i++)
//            System.out.print(neuronsHide[i].printWeight());

        correctOutput = new double[neuronsOutput.length];
        correctOutput[correctAnswer] = 1.0;
        int n = 0;

        double[] outputHide = handleHide(input);
        output = handleOutput(outputHide);

//        System.out.println("neuron hide -> output WEIGHT");
//        for(int i = 0; i < output.length; i++)
//            System.out.println(neuronsOutput[i].printWeight());
//
//        System.out.println();
        error = new double[output.length];
        for(int i = 0; i < output.length; i++){
            error[i] = Math.round(Math.pow((correctOutput[i] - output[i]), 2) * 100.0 ) / 100.0;
//            System.out.println("Error " + i + " - " + error[i] + " (" + output[i] + " | " + correctOutput[i] + ")");
        }

//        System.out.println();
//        System.out.println("hide neurons");
//        for(int i = 0; i < outputHide.length; i++)
//            System.out.print(outputHide[i] + " ");
//        System.out.println();
//        System.out.println();

//        System.out.println("out neurons");
//        for(int i = 0; i < output.length; i++)
//            System.out.print(output[i] + " ");
//        System.out.println();
//        System.out.println();

        double deltaOutput[] = new double[output.length];
        while (isError(error)){
//        for(int b = 1; b <= 100; b++){
//            System.out.println();
//            System.out.println("n= " + n++);
//            System.out.println();

            n++;

            for(int i = 0; i < output.length; i++){
//                deltaOutput[i] = Math.round((correctOutput[i] - output[i]) * (1 - (output[i] * output[i])) * 100.0) / 100.0;
                deltaOutput[i] = Math.round((correctOutput[i] - output[i]) * ((1 - output[i]) * output[i]) * 100.0) / 100.0;
//                System.out.println("deltaOutput[" + i + "] " + deltaOutput[i]);
            }

//            System.out.println();
//

            double gradSynapseFromOutputToHide = 0;
            double deltaWeightFromOutputToHide = 0;
            for(int i = 0; i < output.length; i++){
                for(int j = 0; j < neuronsOutput[0].getWeight().length; j++){
                    gradSynapseFromOutputToHide = outputHide[i] * deltaOutput[j];
                    deltaWeightFromOutputToHide = Main.E * gradSynapseFromOutputToHide + Main.A * neuronsOutput[j].getDeltaWeight()[i];
                    neuronsOutput[j].addWeight(i, deltaWeightFromOutputToHide);
                    neuronsOutput[j].setDeltaWeight(i, deltaWeightFromOutputToHide);
                }
//                System.out.println("gradSynapseFromOutputToHide " + gradSynapseFromOutputToHide + "  | deltaWeightFromOutputToHide " + deltaWeightFromOutputToHide);
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
//                System.out.println("deltaHide[" + i + "] " + deltaHide[i]);
                for(int j = 0; j < neuronsHide[0].getWeight().length; j++){
                    gradSynapseFromHideToInput = input[j] * deltaHide[i];
                    deltaWeightFromHideToInput = Main.E * gradSynapseFromHideToInput + Main.A * neuronsHide[i].getDeltaWeight()[j];
                    neuronsHide[i].addWeight(j, deltaWeightFromHideToInput);
                    neuronsHide[i].setDeltaWeight(j, deltaWeightFromHideToInput);

//                    System.out.println("input " + input[j]);
//                    System.out.println("gradSynapseFromHideToInput " + gradSynapseFromHideToInput + "  | deltaWeightFromHideToInput " + deltaWeightFromHideToInput);

                }

            }
//            System.out.println("deltaHide[0] " + deltaHide[0]);


//            System.out.println();
//            System.out.println("neurons_0 input -> hide WEIGHT");
//            System.out.println(neuronsHide[0].printWeight());

//            System.out.println();

//            System.out.println("neurons_0 hide -> output WEIGHT");
//            System.out.println(neuronsOutput[0].printWeight());

            outputHide = handleHide(input);
            output = handleOutput(outputHide);

            for(int i = 0; i < output.length; i++){
                error[i] = Math.round(Math.pow((correctOutput[i] - output[i]), 2) * 100.0) / 100.0;
//                System.out.println("Error " + i + " - " + error[i] + " (" + output[i] + " | " + correctOutput[i] + ")");
            }

            if(n > 100){
                System.out.println();
                System.out.println();
                for(int i =0; i < output.length; i++)
                    System.err.println("Error " + i + " - " + error[i] + " (" + output[i] + " | " + correctOutput[i] + ")" + " n=" + n);
                System.exit(0);
            }
        }

        System.out.println("iteration " + n);

        if (n == 0)
            return true;
        return false;
    }

    public void printNeuronHideWeight(){
//        for (int i = 0; i < neuronsHide.length; i++)
//            System.out.print(neuronsHide[i].printWeight());
//
//        System.out.println();
//        System.out.println("---------------------------------------");
//        System.out.println();

        for (int i = 0; i < neuronsHide.length; i++)
            System.out.print(neuronsHide[i].printWeight());

        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();

//        for(int i = 0; i < output.length; i++)
//                System.out.println("Error " + i + " - " + error[i] + " (" + output[i] + " | " + correctOutput[i] + ")");
    }

    public void printError(){
        for(int i = 0; i < output.length; i++)
            System.out.println("Error " + i + " - " + error[i] + " (" + output[i] + " | " + correctOutput[i] + ")");
        System.out.println();
    }

    private boolean isError(double[] error){
        for (int i = 0; i < error.length; i++)
            if(error[i] > 0.01)
                return true;
        return false;
    }

    public void saveWeight(){
        StringBuilder weights = new StringBuilder();
        for(int i = 0; i < neuronsHide.length; i++)
            weights.append(neuronsHide[i].printWeight().toString());

//        StringBuilder weightsOut = new StringBuilder();
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

//                System.out.println("length " + sl.length);
//                System.out.println("value " + sl[0] + " " + sl[1] + " " + sl[2]);

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
//                        System.out.println("|" + sl[i - 1] + "|");

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

        for (int i = 0; i < neuronsHide.length; i++)
            System.out.print(neuronsHide[i].printWeight());

        for (int i = 0; i < neuronsOutput.length; i++)
            System.out.print(neuronsOutput[i].printWeight());

        System.out.println();
        System.out.println();

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

}
