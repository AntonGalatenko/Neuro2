import java.io.*;

public class NeuralNetwork {

    private Neuron[] neurons;

    public NeuralNetwork(){
        neurons = new Neuron[10];

        for(int i = 0; i < neurons.length; i++)
            neurons[i] = new Neuron();
    }

    public int[] handleHard(int[][] input){
        int[] output = new int[neurons.length];
        for(int i = 0; i < output.length; i++)
            output[i] = neurons[i].transferHard(input);

        return output;
    }

    public int[] handle(int[][] input){
        int[] output = new int[neurons.length];
        for(int i = 0; i < output.length; i++)
            output[i] = neurons[i].transfer(input);

        return output;
    }

    public void study(int[][] input, int correctAnswer){
        int[] correctOutput = new int[neurons.length];
        correctOutput[correctAnswer] = 1;
        int n = 1;

        int[] output = handleHard(input);
        while (! compareArrays(correctOutput, output)){
            for(int i = 0; i < neurons.length; i++){
                int dif = correctOutput[i] - output[i];
                neurons[i].changeWeights(input, dif);
            }
            output = handleHard(input);
            n++;
        }
        System.out.println("iteration " + n);
        System.out.println("___________________");

    }

    private boolean compareArrays(int[] correctOutput, int[] output) {
        if(correctOutput.length != output.length)
            return false;

        for(int i = 0; i < correctOutput.length; i++)
            if(correctOutput[i] != output[i])
                return false;

        return true;
    }

    public void printWeight(){
        for(Neuron n : neurons)
            System.out.println(n.printWeight());
    }

    public String getAnswer(int[][] input){
        int[] answer = handle(input);
        String result = "";
        for(int i : answer)
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
