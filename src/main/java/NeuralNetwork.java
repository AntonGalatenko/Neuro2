public class NeuralNetwork {

    private NeuronHide[] neuronsHide;
    private NeuronOutput[] neuronsOutput;


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
            output[i] = neuronsOutput[i].transfer(input);

        return output;
    }

    public double[] handleHide(int[][] input){
        double[] output = new double[neuronsHide.length];
        for(int i = 0; i < output.length; i++)
            output[i] = neuronsHide[i].transfer(input);
        return output;
    }

    public void study(int[][] input, int correctAnswer){

//        System.out.println("neuronsOutput weight");
//        for (int i = 0; i < neuronsOutput.length; i++)
//            System.out.println(neuronsOutput[i].printWeight());

//        System.out.println("input neurons");
//        int jj = 0;
//        while (jj < input[0].length){
//            for(int ii = 0; ii < input.length; ii++)
//                System.out.print(input[ii][jj] + "  ");
//
//            System.out.println();
//            jj++;
//        }
//
//        System.out.println();
//
        System.out.println("neuronsHide 0 weight");
        System.out.println(neuronsHide[0].printWeightTrue());
        System.out.println();
//        for (int i = 0; i < neuronsHide.length; i++)
//            System.out.println(neuronsHide[i].printWeightTrue());

        double[] correctOutput = new double[neuronsOutput.length];
        correctOutput[correctAnswer] = 1.0;
        int n = 0;

        double[] outputHide = handleHide(input);
        double[] output = handleOutput(outputHide);

//        System.out.println();
//        System.out.println("neuron hide out " + outputHide[0]);
//
//        System.out.println();
//        System.out.println("neuron output " + output[0]);

        for(int i = 0; i < output.length; i++){
            double error = Math.pow((correctOutput[i] - output[i]), 2);
            System.out.println("Error " + i + " - " + String.format("%.2f", error) + " (" + output[i] + " | " + correctOutput[i] + ")");
        }

        double deltaOutput[] = new double[output.length];
//        while (! compareArrays(correctOutput, output)){
        for(int b = 1; b <= 1000; b++){

            for(int i = 0; i < output.length; i++){
//                double error = Math.pow((correctOutput[i] - output[i]), 2);
//                    System.out.println("error " + error);
                deltaOutput[i] = (correctOutput[i] - output[i]) * ((1 - output[i]) * output[i]);
//                    System.out.println("deltaOutput[" + i + "] " + deltaOutput[i]);

//                neurons[i].changeWeights(input, dif);
            }
//            output = handle(input);
            n++;
//        }

        double sum[] = new double[outputHide.length];
        for(int i = 0; i < outputHide.length; i++){
            for(int j = 0; j < outputHide.length; j++){
                sum[i] += (deltaOutput[j] * neuronsOutput[j].getWidth()[i]);
            }
//                System.out.println("sum[" + i + "] " + sum[i]);

        }


        double deltaHide[] = new double[output.length];
        double[] gradSynapseFromOutputToHide = new double[output.length];
        double deltaWeightFromOutputToHide = 0;
//        while (! compareArrays(correctOutput, outputHide)){
//        for(int b = 1; b < 50; b++){
            for(int i = 0; i < output.length; i++){
//                double error = Math.pow((correctOutput[i] - output[i]), 2);
//                System.out.println("error " + error);
                deltaHide[i] = ((1 - outputHide[i]) * outputHide[i]) * sum[i];
//                    System.out.println("delta " + deltaHide[i]);

                for(int j = 0; j < neuronsOutput[0].getWidth().length; j++){
                    gradSynapseFromOutputToHide[j] = outputHide[i] * deltaOutput[j];
                    deltaWeightFromOutputToHide = Main.E * gradSynapseFromOutputToHide[j] + Main.A * neuronsOutput[j].getDeltaWidth()[j];
//                        System.out.println("deltaWeightFromOutputToHide " + deltaWeightFromOutputToHide);
                    neuronsOutput[j].getWidth()[j] += deltaWeightFromOutputToHide;
                    neuronsOutput[j].getDeltaWidth()[j] = deltaWeightFromOutputToHide;
                }
            }

            double[] gradSynapseFromHideToInput = new double[outputHide.length];
            double deltaWeightFromHideToInput = 0;
            for(int i = 0; i < outputHide.length; i++){
                for(int j = 0; j < neuronsHide[0].getWeight().length; j++){
                    gradSynapseFromHideToInput[j] = neuronsHide[i].getWeightParsing()[j] * deltaHide[j];
                    deltaWeightFromHideToInput = Main.E * gradSynapseFromHideToInput[j] + Main.A * neuronsHide[j].getDeltaWeight()[j];
                    neuronsHide[j].getWeightParsing()[j] += deltaWeightFromHideToInput;
                    neuronsHide[j].getDeltaWeight()[j] = deltaWeightFromHideToInput;
                }
            }

            outputHide = handleHide(input);
            output = handleOutput(outputHide);

            String o = "";
            String co = "";

            for(int iq = 0; iq < output.length; iq++){
                o += output[iq] + " ";
                co += correctOutput[iq] + " ";
            }
//            System.out.println(n + " [" + o + "] [" + co + "]");

            System.out.println(neuronsHide[0].printWeightTrue());
            System.out.println("---------------------------------------------");

        }

        System.out.println("iteration " + n);
        System.out.println("___________________");

        outputHide = handleHide(input);
        output = handleOutput(outputHide);

        System.out.println();

        for(int i = 0; i < output.length; i++){
            double error = Math.pow((correctOutput[i] - output[i]), 2);
            System.out.println("Error " + i + " - " + String.format("%.2f", error) + " (" + output[i] + " | " + correctOutput[i] + ")");
        }

//                System.out.println("changed neuronsOutput weight");
//        for (int i = 0; i < neuronsOutput.length; i++)
//            System.out.println(neuronsOutput[i].printWeight());

//        System.out.println("changed neuronsHide weight");
//        for (int i = 0; i < neuronsHide.length; i++)
//            System.out.println(neuronsHide[i].printWeight());
    }

    private boolean compareArrays(double[] correctOutput, double[] output) {
        if(correctOutput.length != output.length)
            return false;

        for(int i = 0; i < correctOutput.length; i++)
            if(correctOutput[i] != output[i])
                return false;

        return true;
    }

//    public void printWeight(){
//        for(NeuronHide n : neurons)
//            System.out.println(n.printWeight());
//    }
//
//    public String getAnswer(int[][] input){
//        double[] answer = handle(input);
//        String result = "";
//        for(double i : answer)
//            result = result + " " + i + " ";
//        return result;
//    }

//    public void saveWeightToFile(String file){
//        PrintWriter pw = null;
//        try {
//            if(! new File(file).exists())
//                new File(file).createNewFile();
//
//            int j = 0;
//            pw = new PrintWriter(file);
//
//            for (int n = 0; n < neurons.length; n++){
//                while (j < neurons[n].getHeight()){
//                    for(int i = 0; i < neurons[n].getWidth(); i++)
//                        pw.print(neurons[n].getWeight()[i][j] + " ");
//
//                    pw.println();
//                    j++;
//                }
//                if(n != neurons.length - 1)
//                    pw.println("**************************");
//                j = 0;
//            }
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            pw.close();
//        }
//    }
//
//    public void loadWeightFromFile(String file){
//        if(! new File(file).exists()){
//            for(int i = 0; i < neurons.length; i++)
//                neurons[i].randomizeWeight();
//
//            return;
//        }
//
//        System.out.println("LOAD");
//
//        BufferedReader br = null;
//        String line;
//        String[] sl;
//        int h = 0;
//        int n = 0;
//
//        try {
//            br = new BufferedReader(new FileReader(file));
//
//            while ((line = br.readLine()) != null){
//                if(line.contains("*")){
//                    line = br.readLine();
//                    h = 0;
//                    n++;
//                }
//
//                sl = line.split(" ");
//
//                for(int i = 0; i < sl.length; i++)
//                    neurons[n].setWeight(i,h, Integer.parseInt(sl[i]));
//
//                h++;
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (br != null)
//                    br.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
