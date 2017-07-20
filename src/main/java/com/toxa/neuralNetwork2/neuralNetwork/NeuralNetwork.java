package com.toxa.neuralNetwork2.neuralNetwork;

public interface NeuralNetwork {

    public void saveWeight();
    public void loadWeight();
    public String getResult(int[] input);
    public String getWeights();
}
