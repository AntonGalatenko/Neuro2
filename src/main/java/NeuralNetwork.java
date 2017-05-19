public class NeuralNetwork {

    private NeuronHide[] neuronsHide;
    private NeuronOutput[] neuronsOutput;

    double[] correctOutput = new double[10];
    double[] output = new double[10];
    double[] error = new double[10];


    public NeuralNetwork(int numberOutputNeurons, int numberHideLayer){
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

    public double[] handleHide(int[][] input){
        double[] output = new double[neuronsHide.length];
        for(int i = 0; i < output.length; i++)
            output[i] = neuronsHide[i].transfer(input);
//            output[i] = Math.round(neuronsHide[i].transfer(input) * 100.0) / 100.0;
        return output;
    }

    public boolean study(int[][] input, int correctAnswer){
//        System.out.println("input neurons");
//        int jj = 0;
//        while (jj < input[0].length){
//            for(int ii = 0; ii < input.length; ii++)
//                System.out.print(input[ii][jj] + "  ");
//            System.out.println();
//            jj++;
//        }
//
//        System.out.println();
//        System.out.println("neurons input -> hide WEIGHT");
//        for (int i = 0; i < neuronsHide.length; i++)
//            System.out.print(neuronsHide[i].printWeightTrue());
//
        correctOutput = new double[neuronsOutput.length];
        correctOutput[correctAnswer] = 1.0;
        int n = 0;

        double[] outputHide = handleHide(input);
        output = handleOutput(outputHide);

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
//        for(int b = 1; b <= 100; b++){
//            System.out.println();
//            System.out.println("n= " + n++);
//            System.out.println();

            n++;

            for(int i = 0; i < output.length; i++){
                deltaOutput[i] = Math.round((correctOutput[i] - output[i]) * ((1 - output[i]) * output[i]) * 100.0) / 100.0;
//                System.out.println("deltaOutput[" + i + "] " + deltaOutput[i]);
            }

//            System.out.println();

            double sum[] = new double[outputHide.length];
            for(int i = 0; i < outputHide.length; i++)
                for(int j = 0; j < outputHide.length; j++)
                    sum[i] += (deltaOutput[j] * neuronsOutput[j].getWeight()[i]);

            double deltaHide[] = new double[output.length];
            double gradSynapseFromOutputToHide;
            double deltaWeightFromOutputToHide = 0;
            for(int i = 0; i < output.length; i++){
                deltaHide[i] = ((1 - outputHide[i]) * outputHide[i]) * sum[i];

                for(int j = 0; j < neuronsOutput[0].getWeight().length; j++){
                    gradSynapseFromOutputToHide = outputHide[i] * deltaOutput[j];
                    deltaWeightFromOutputToHide = Main.E * gradSynapseFromOutputToHide + Main.A * neuronsOutput[j].getDeltaWeight()[i];
                    neuronsOutput[j].addWeight(i, deltaWeightFromOutputToHide);
                    neuronsOutput[j].setDeltaWeight(i, deltaWeightFromOutputToHide);
                }
//                System.out.println("deltaHide[" + i + "] " + deltaHide[i]);
            }

            double gradSynapseFromHideToInput;
            double deltaWeightFromHideToInput = 0;
            for(int i = 0; i < outputHide.length; i++){
                for(int j = 0; j < neuronsHide[0].getWeight().length; j++){
                    gradSynapseFromHideToInput = neuronsHide[i].getWeightParsing()[j] * deltaHide[i];
                    deltaWeightFromHideToInput = Main.E * gradSynapseFromHideToInput + Main.A * neuronsHide[i].getDeltaWeight()[j];
                    neuronsHide[i].addWeightParsing(j, deltaWeightFromHideToInput);
                    neuronsHide[i].setDeltaWeightParsing(j, deltaWeightFromHideToInput);
                }
            }

//            System.out.println();
//            System.out.println("neurons_0 input -> hide WEIGHT");
//            System.out.println(neuronsHide[0].printWeight());

//            System.out.println();
//
//            System.out.println("neurons_0 hide -> output WEIGHT");
//            System.out.println(neuronsOutput[0].printWeight());

            outputHide = handleHide(input);
            output = handleOutput(outputHide);

            for(int i =0; i < output.length; i++){
                error[i] = Math.round(Math.pow((correctOutput[i] - output[i]), 2) * 100.0) / 100.0;
//                System.out.println("Error " + i + " - " + error[i] + " (" + output[i] + " | " + correctOutput[i] + ")");
            }

//            if(n > 1000){
//                for(int i =0; i < output.length; i++)
//                    System.err.println("Error " + i + " - " + error[i] + " (" + output[i] + " | " + correctOutput[i] + ")" + " n " + n);
//                break;
//            }
        }

        System.out.println("iteration " + n);

        if (n == 0)
            return true;
        return false;
    }

    public void printNeuronHideWeight(){
        for (int i = 0; i < neuronsHide.length; i++)
            System.out.print(neuronsHide[i].printWeightTrue());

        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();

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
            if(error[i] != 0.0)
                return true;
        return false;
    }

}
