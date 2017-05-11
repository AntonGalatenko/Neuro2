import java.io.*;

public class NeuralNetwork {

    private NeuronHide[] neuronsHide;
    private NeuronOutput[] neuronsOutput;

    public NeuralNetwork(int numberOutputNeurons, int numberHideNeurons){
        neuronsHide = new NeuronHide[numberHideNeurons];
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
        double[] correctOutput = new double[neuronsOutput.length];
        correctOutput[correctAnswer] = 1.0;
        int n = 1;

        double[] outputHide = handleHide(input);
        double[] output = handleOutput(outputHide);



        while (! compareArrays(correctOutput, output)){
            for(int i = 0; i < neurons.length; i++){
//                double dif = correctOutput[i] - output[i];
                double dif = Math.pow((correctOutput[i] - output[i]), 2);
                neurons[i].changeWeights(input, dif);
            }
            output = handle(input);
            n++;
        }
        System.out.println("iteration " + n);
        System.out.println("___________________");
    }

    private boolean compareArrays(double[] correctOutput, double[] output) {
        if(correctOutput.length != output.length)
            return false;

        for(int i = 0; i < correctOutput.length; i++)
            if(correctOutput[i] != output[i])
                return false;

        return true;
    }

    public void printWeight(){
        for(NeuronHide n : neurons)
            System.out.println(n.printWeight());
    }

    public String getAnswer(int[][] input){
        double[] answer = handle(input);
        String result = "";
        for(double i : answer)
            result = result + " " + i + " ";
        return result;
    }

    public void saveWeightToFile(String file){
        PrintWriter pw = null;
        try {
            if(! new File(file).exists())
                new File(file).createNewFile();

            int j = 0;
            pw = new PrintWriter(file);

            for (int n = 0; n < neurons.length; n++){
                while (j < neurons[n].getHeight()){
                    for(int i = 0; i < neurons[n].getWidth(); i++)
                        pw.print(neurons[n].getWeight()[i][j] + " ");

                    pw.println();
                    j++;
                }
                if(n != neurons.length - 1)
                    pw.println("**************************");
                j = 0;
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            pw.close();
        }
    }

    public void loadWeightFromFile(String file){
        if(! new File(file).exists()){
            for(int i = 0; i < neurons.length; i++)
                neurons[i].randomizeWeight();

            return;
        }

        System.out.println("LOAD");

        BufferedReader br = null;
        String line;
        String[] sl;
        int h = 0;
        int n = 0;

        try {
            br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null){
                if(line.contains("*")){
                    line = br.readLine();
                    h = 0;
                    n++;
                }

                sl = line.split(" ");

                for(int i = 0; i < sl.length; i++)
                    neurons[n].setWeight(i,h, Integer.parseInt(sl[i]));

                h++;
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

}
