import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {
    static final File FILE = new File("NeuronImg\\img");
    static final String WEIGHT_FILE_PATH = "NeuronImg\\neuron2Weight.txt";
    static final double E = 0.7;        //Скорость обучения
    static final double A = 0.3;        //Момент

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(10, 1);
        Random random = new Random();

//        neuralNetwork.loadWeightFromFile(WEIGHT_FILE_PATH);

        File[] files = FILE.listFiles();

//        for(int i = 1; i <= 400; i++){
//            System.out.println("i = " + i);

//            System.out.println("rnd = " + rnd);
        boolean isStudying = false;


        int n = 0;
        int k = 0;
        long start = System.currentTimeMillis();
        while (n < 200){
            k++;
            int rnd = random.nextInt(files.length);
            System.out.print(k + "   | " + rnd + " ");
            isStudying = neuralNetwork.study(loadIMG(files[rnd]), rnd);
            if(isStudying)
                n++;
            else
                n = 0;
        }
        long time = (System.currentTimeMillis() - start) / 1000;

        System.out.println();
        System.out.println("k " + k + " time " + time);
        System.out.println();

        neuralNetwork.printNeuronHideWeight();

        neuralNetwork.study(loadIMG(files[0]), 0);
        System.out.println("-- 0");
        neuralNetwork.printError();
        neuralNetwork.study(loadIMG(files[1]), 1);
        System.out.println("-- 1");
        neuralNetwork.printError();
        neuralNetwork.study(loadIMG(files[2]), 2);
        System.out.println("-- 2");
        neuralNetwork.printError();
        neuralNetwork.study(loadIMG(files[3]), 3);
        System.out.println("-- 3");
        neuralNetwork.printError();
        neuralNetwork.study(loadIMG(files[4]), 4);
        System.out.println("-- 4");
        neuralNetwork.printError();
        neuralNetwork.study(loadIMG(files[5]), 5);
        System.out.println("-- 5");
        neuralNetwork.printError();
        neuralNetwork.study(loadIMG(files[6]), 6);
        System.out.println("-- 6");
        neuralNetwork.printError();
        neuralNetwork.study(loadIMG(files[7]), 7);
        System.out.println("-- 7");
        neuralNetwork.printError();
        neuralNetwork.study(loadIMG(files[8]), 8);
        System.out.println("-- 8");
        neuralNetwork.printError();
        neuralNetwork.study(loadIMG(files[9]), 9);
        System.out.println("-- 9");
        neuralNetwork.printError();


    }

    private static int[][] loadIMG(File file){
        int[][] result = new int[3][5];

        try {
            BufferedImage img = ImageIO.read(file);

            int [][] pixels = new int[3][5];

            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 5; j++){
                    pixels[i][j] = img.getRGB(i,j);

                    int n = 0;
                    if(pixels[i][j] < - 100000)
                        n = 1;

                    result[i][j] = n;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;
    }
}
