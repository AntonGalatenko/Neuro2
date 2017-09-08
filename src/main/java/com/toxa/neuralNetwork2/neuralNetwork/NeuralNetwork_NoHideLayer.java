package com.toxa.neuralNetwork2.neuralNetwork;

import com.toxa.neuralNetwork2.Main;
import com.toxa.neuralNetwork2.neuralNetwork.neuron.NeuronHide;

public class NeuralNetwork_NoHideLayer {

    private NeuronHide[] neuronsOutput;
    double[] correctOutput = new double[10];
    double[] output = new double[10];
    double[] error = new double[10];


    public NeuralNetwork_NoHideLayer(int numberOutputNeurons){
        neuronsOutput = new NeuronHide[numberOutputNeurons];

        for(int i = 0; i < neuronsOutput.length; i++)
            neuronsOutput[i] = new NeuronHide();

    }

    public double[] handleOutput(int[] input){
        double[] output = new double[neuronsOutput.length];
        for(int i = 0; i < output.length; i++)
            output[i] = Math.round(neuronsOutput[i].transfer(input) * 100.0) / 100.0;

        return output;
    }

//    public double[] handleOutput1(int[] input){
//        double[] output = new double[neuronsOutput.length];
//        for(int i = 0; i < output.length; i++)
//            output[i] = Math.round(neuronsOutput[i].transfer1(input) * 100.0) / 100.0;
//
//        return output;
//    }

//    private int[] parsingInput(int[][] value){
//        int n = 0;
//        int[] result = new int[15];
//        for(int i = 0; i < 3; i++){
//            for(int j = 0; j < 5; j++){
//                result [n] = value[i][j];
//                n++;
//            }
//        }
//        return result;
//    }

    public boolean study(int[] input, int correctAnswer){
//        System.out.println("correctAnswer " + correctAnswer);
//        int jj = 0;
//        while (jj < 5){
//            for(int ii = 0; ii < 3; ii++)
//                System.out.print(input[ii][jj] + "  ");
//            System.out.println();
//            jj++;
//        }
//
//        System.out.println();
//        System.out.println("neurons WEIGHT");
//        for (int i = 0; i < neuronsOutput.length; i++)
//            System.out.print(neuronsOutput[i].printWeight());
//
        correctOutput = new double[neuronsOutput.length];
        correctOutput[correctAnswer] = 1.0;
        int n = 0;

        output = handleOutput(input);

//        System.out.println("neuron hide -> output WEIGHT");
//        for(int i = 0; i < output.length; i++)
//            System.out.println(neuronsOutput[i].printWeight());

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
//
//        System.out.println("out neurons");
//        for(int i = 0; i < output.length; i++)
//            System.out.print(output[i] + " ");
//        System.out.println();
//        System.out.println();

        double deltaOutput[] = new double[output.length];
        while (isError(error)){
//        for(int b = 1; b <= 10; b++){
//            System.out.println();
//            System.out.println("n= " + n++);
//            System.out.println();

            n++;

            for(int i = 0; i < output.length; i++){
                deltaOutput[i] = Math.round((correctOutput[i] - output[i]) * ((1 - output[i]) * output[i]) * 100.0) / 100.0;
//                System.out.println("deltaOutput[" + i + "] " + deltaOutput[i]);
            }

//            System.out.println();


            double gradSynapseFromHideToInput;
            double deltaWeightFromHideToInput;
            for(int i = 0; i < output.length; i++){
                for(int j = 0; j < neuronsOutput[0].getWeight().length; j++){
                    gradSynapseFromHideToInput = input[j] * deltaOutput[i];
                    deltaWeightFromHideToInput = Main.E * gradSynapseFromHideToInput + Main.A * neuronsOutput[i].getDeltaWeight()[j];
//                    System.out.println("gradSynapseFromHideToInput " + gradSynapseFromHideToInput + " | deltaWeightFromHideToInput " + deltaWeightFromHideToInput);
                    neuronsOutput[i].addWeight(j, deltaWeightFromHideToInput);
                    neuronsOutput[i].setDeltaWeight(j, deltaWeightFromHideToInput);
                }
            }

//            System.out.println();
//            System.out.println("neurons_0 input -> hide WEIGHT");
//            System.out.println(neuronsHide[0].printWeight());

//            System.out.println();

//            System.out.println("neurons_2 WEIGHT");
//            System.out.println(neuronsOutput[2].printWeight());

            output = handleOutput(input);

            for(int i =0; i < output.length; i++){
                error[i] = Math.round(Math.pow((correctOutput[i] - output[i]), 2) * 100.0) / 100.0;
//                System.out.println("Error " + i + " - " + error[i] + " (" + output[i] + " | " + correctOutput[i] + ")");
            }

            if(n > 1000){
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
        for (int i = 0; i < neuronsOutput.length; i++)
            System.out.print(neuronsOutput[i].printWeight());

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
            if(error[i] > 0.2)
                return true;
        return false;
    }

}
