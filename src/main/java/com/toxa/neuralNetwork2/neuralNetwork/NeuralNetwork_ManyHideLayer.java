package com.toxa.neuralNetwork2.neuralNetwork;

import com.toxa.neuralNetwork2.Main;
import com.toxa.neuralNetwork2.neuralNetwork.neuron.NeuronHide;
import com.toxa.neuralNetwork2.neuralNetwork.neuron.NeuronOutput;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork_ManyHideLayer {

    private List<NeuronHide[]> neuronHideList;
    private List<double[]> outputHideList;
    private List<double[]> deltaList;
    private List<double[]> sumList;
    private int numberHideLayers;

    private NeuronHide[] neuronsHide;
    private NeuronOutput[] neuronsOutput;

    double[] correctOutput = new double[10];
    double[] output = new double[10];
    double[] error = new double[10];


    public NeuralNetwork_ManyHideLayer(int numberOutputNeurons, int numberHideLayers){
        this.numberHideLayers = numberHideLayers;
        neuronHideList = new ArrayList<NeuronHide[]>();
        outputHideList = new ArrayList<double[]>();
        deltaList = new ArrayList<double[]>();
        sumList = new ArrayList<double[]>();

        neuronsHide = new NeuronHide[numberOutputNeurons];
        neuronsOutput = new NeuronOutput[numberOutputNeurons];

        for(int i = 0; i < neuronsOutput.length; i++)
            neuronsOutput[i] = new NeuronOutput(neuronsHide.length);

        for(int i = 0; i < numberHideLayers; i++){
            neuronsHide = new NeuronHide[numberOutputNeurons];
            for(int j = 0; j < neuronsHide.length; j++)
                neuronsHide[j] = new NeuronHide();
            neuronHideList.add(neuronsHide);
        }
    }

    public double[] handleOutput(double[] input){
        double[] output = new double[neuronsOutput.length];
        for(int i = 0; i < output.length; i++)
            output[i] = Math.round(neuronsOutput[i].transfer(input) * 100.0) / 100.0;

        return output;
    }

    public double[] handleHide(int[] input, NeuronHide[] neuronHide){
        double[] output = new double[neuronHide.length];
        for(int i = 0; i < output.length; i++)
//            output[i] = neuronsHide[i].transfer(input);
            output[i] = Math.round(neuronHide[i].transfer(input) * 100.0) / 100.0;
        return output;
    }

    public double[] handleHide(double[] input, NeuronHide[] neuronHide){
        double[] output = new double[neuronHide.length];
        for(int i = 0; i < output.length; i++)
//            output[i] = neuronsHide[i].transfer(input);
            output[i] = Math.round(neuronHide[i].transfer(input) * 100.0) / 100.0;
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

        double[] outputHide = new double[0];
//        double[] outputHide = new double[neuronHideList.get(0)[0].getWeight().length];
        for(int i = 0; i < numberHideLayers; i++){
            if(i == 0)
                outputHide = handleHide(input, neuronHideList.get(i));
            else
                outputHide = handleHide(outputHideList.get(i - 1), neuronHideList.get(i));

            outputHideList.add(outputHide);
        }
        output = handleOutput(outputHideList.get(numberHideLayers - 1));

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

        double[] deltaNext = new double[output.length];
        while (isError(error)){
//        for(int b = 1; b <= 100; b++){
//            System.out.println();
//            System.out.println("n= " + n++);
//            System.out.println();

            n++;

            for(int i = 0; i < output.length; i++){
//                deltaOutput[i] = Math.round((correctOutput[i] - output[i]) * (1 - (output[i] * output[i])) * 100.0) / 100.0;
                deltaNext[i] = Math.round((correctOutput[i] - output[i]) * ((1 - output[i]) * output[i]) * 100.0) / 100.0;
//                System.out.println("deltaOutput[" + i + "] " + deltaOutput[i]);
            }
            deltaList.add(deltaNext);

//            System.out.println();

            double[] deltaHide = new double[output.length];
            double[] sum;
            for(int f = 0; f < numberHideLayers; f++) {
                for(int i = 0; i < outputHide.length; i++){
                    sum = new double[outputHide.length];
                    for(int j = 0; j < outputHide.length; j++){
                        if(f == 0)
                            sum[i] += (deltaList.get(f)[j] * neuronsOutput[j].getWeight()[i]);
                        else
                            sum[i] += (deltaList.get(f)[j] * neuronHideList.get(numberHideLayers - f - 1)[j].getWeight()[i]);
                    }
                    sumList.add(sum);
                    deltaHide[i] = ((1 - outputHideList.get(numberHideLayers - f - 1)[i]) * outputHideList.get(numberHideLayers - f - 1)[i]) * sumList.get(f)[i];
                    deltaList.add(deltaHide);
                }
            }

            double gradSynapseFromOutputToHide = 0;
            double deltaWeightFromOutputToHide = 0;
            for(int f = 0; f < numberHideLayers; f++){
                for(int i = 0; i < output.length; i++){
                    for(int j = 0; j < neuronsOutput[0].getWeight().length; j++){
                        gradSynapseFromOutputToHide = outputHideList.get(numberHideLayers - f - 1)[i] * deltaList.get(f)[j];
                        if(f == 0){
                            deltaWeightFromOutputToHide = Main.E * gradSynapseFromOutputToHide + Main.A * neuronsOutput[j].getDeltaWeight()[i];
                            neuronsOutput[j].addWeight(i, deltaWeightFromOutputToHide);
                            neuronsOutput[j].setDeltaWeight(i, deltaWeightFromOutputToHide);
                        }
                        else{
                            deltaWeightFromOutputToHide = Main.E * gradSynapseFromOutputToHide + Main.A * neuronHideList.get(numberHideLayers - f - 1)[j].getDeltaWeight()[i];
                            neuronHideList.get(numberHideLayers - f - 1)[j].addWeight(i, deltaWeightFromOutputToHide);
                            neuronHideList.get(numberHideLayers - f - 1)[j].setDeltaWeight(i, deltaWeightFromOutputToHide);
                        }

                    }
//                System.out.println("gradSynapseFromOutputToHide " + gradSynapseFromOutputToHide + "  | deltaWeightFromOutputToHide " + deltaWeightFromOutputToHide);
                }
            }

            double gradSynapseFromHideToInput = 0;
            double deltaWeightFromHideToInput = 0;
            for(int i = 0; i < outputHide.length; i++){
//                System.out.println("deltaHide[" + i + "] " + deltaHide[i]);
                for(int j = 0; j < neuronsHide[0].getWeight().length; j++){
                    gradSynapseFromHideToInput = input[j] * deltaList.get(deltaList.size() - 1)[i];
                    deltaWeightFromHideToInput = Main.E * gradSynapseFromHideToInput + Main.A * neuronHideList.get(0)[i].getDeltaWeight()[j];
                    neuronHideList.get(0)[i].addWeight(j, deltaWeightFromHideToInput);
                    neuronHideList.get(0)[i].setDeltaWeight(j, deltaWeightFromHideToInput);

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

            outputHideList.clear();
            deltaList.clear();
            sumList.clear();
            for(int i = 0; i < numberHideLayers; i++){
                if(i == 0)
                    outputHide = handleHide(input, neuronHideList.get(i));
                else
                    outputHide = handleHide(outputHideList.get(i - 1), neuronHideList.get(i));

                outputHideList.add(outputHide);
            }
            output = handleOutput(outputHideList.get(numberHideLayers - 1));

            for(int i = 0; i < output.length; i++){
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
            if(error[i] > 0.3)
                return true;
        return false;
    }

}
