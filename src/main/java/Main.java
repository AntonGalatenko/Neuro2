import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {
    static final File FILE = new File("NeuronImg\\img");
    static final String WEIGHT_FILE_PATH = "NeuronImg\\neuron2Weight.txt";
    static final double E = 0.9;        //Скорость обучения 0.7
    static final double A = 0.5;        //Момент 0.3

    public static void main(String[] args) {
        oneHideLayer();
//        noHideLayer();
//
//        Random random = new Random();
//        File[] files = FILE.listFiles();
//        int rnd = random.nextInt(files.length);

//        NeuralNetwork_NoHideLayer neuralNetwork = new NeuralNetwork_NoHideLayer(10);
//        NeuralNetwork_OneHideLayer neuralNetwork = new NeuralNetwork_OneHideLayer(10);
//        neuralNetwork.study(loadIMG(files[rnd]), rnd);

    }

    private static void noHideLayer() {
        NeuralNetwork_NoHideLayer neuralNetworkNoHideLayer = new NeuralNetwork_NoHideLayer(10);
        Random random = new Random();
        File[] files = FILE.listFiles();

        boolean isStudying;
        int n = 0;
        int k = 0;
        long start = System.currentTimeMillis();
        while (n < 200){
            k++;
            int rnd = random.nextInt(files.length);
            System.out.print(k + "   | " + rnd + " ");
            isStudying = neuralNetworkNoHideLayer.study(loadIMG(files[rnd]), rnd);
            if(isStudying)
                n++;
            else
                n = 0;
        }
        long time = (System.currentTimeMillis() - start) / 1000;

        System.out.println();

        neuralNetworkNoHideLayer.printNeuronHideWeight();

        neuralNetworkNoHideLayer.study(loadIMG(files[0]), 0);
        System.out.println("-- 0");
        neuralNetworkNoHideLayer.printError();
        neuralNetworkNoHideLayer.study(loadIMG(files[1]), 1);
        System.out.println("-- 1");
        neuralNetworkNoHideLayer.printError();
        neuralNetworkNoHideLayer.study(loadIMG(files[2]), 2);
        System.out.println("-- 2");
        neuralNetworkNoHideLayer.printError();
        neuralNetworkNoHideLayer.study(loadIMG(files[3]), 3);
        System.out.println("-- 3");
        neuralNetworkNoHideLayer.printError();
        neuralNetworkNoHideLayer.study(loadIMG(files[4]), 4);
        System.out.println("-- 4");
        neuralNetworkNoHideLayer.printError();
        neuralNetworkNoHideLayer.study(loadIMG(files[5]), 5);
        System.out.println("-- 5");
        neuralNetworkNoHideLayer.printError();
        neuralNetworkNoHideLayer.study(loadIMG(files[6]), 6);
        System.out.println("-- 6");
        neuralNetworkNoHideLayer.printError();
        neuralNetworkNoHideLayer.study(loadIMG(files[7]), 7);
        System.out.println("-- 7");
        neuralNetworkNoHideLayer.printError();
        neuralNetworkNoHideLayer.study(loadIMG(files[8]), 8);
        System.out.println("-- 8");
        neuralNetworkNoHideLayer.printError();
        neuralNetworkNoHideLayer.study(loadIMG(files[9]), 9);
        System.out.println("-- 9");
        neuralNetworkNoHideLayer.printError();

        System.out.println();
        System.out.println("k " + k + " time " + time);
        System.out.println();
    }

    private static void oneHideLayer(){
        NeuralNetwork_OneHideLayer neuralNetworkOneHideLayer = new NeuralNetwork_OneHideLayer(10);
        Random random = new Random();
        File[] files = FILE.listFiles();

        boolean isStudying;
        int n = 0;
        int k = 0;
        long start = System.currentTimeMillis();
        while (n < 200){
            k++;
            int rnd = random.nextInt(files.length);
            System.out.print(k + "   | " + rnd + " ");
            isStudying = neuralNetworkOneHideLayer.study(loadIMG(files[rnd]), rnd);
            if(isStudying)
                n++;
            else
                n = 0;
        }
        long time = (System.currentTimeMillis() - start) / 1000;

        System.out.println();

        neuralNetworkOneHideLayer.printNeuronHideWeight();

        neuralNetworkOneHideLayer.study(loadIMG(files[0]), 0);
        System.out.println("-- 0");
        neuralNetworkOneHideLayer.printError();
        neuralNetworkOneHideLayer.study(loadIMG(files[1]), 1);
        System.out.println("-- 1");
        neuralNetworkOneHideLayer.printError();
        neuralNetworkOneHideLayer.study(loadIMG(files[2]), 2);
        System.out.println("-- 2");
        neuralNetworkOneHideLayer.printError();
        neuralNetworkOneHideLayer.study(loadIMG(files[3]), 3);
        System.out.println("-- 3");
        neuralNetworkOneHideLayer.printError();
        neuralNetworkOneHideLayer.study(loadIMG(files[4]), 4);
        System.out.println("-- 4");
        neuralNetworkOneHideLayer.printError();
        neuralNetworkOneHideLayer.study(loadIMG(files[5]), 5);
        System.out.println("-- 5");
        neuralNetworkOneHideLayer.printError();
        neuralNetworkOneHideLayer.study(loadIMG(files[6]), 6);
        System.out.println("-- 6");
        neuralNetworkOneHideLayer.printError();
        neuralNetworkOneHideLayer.study(loadIMG(files[7]), 7);
        System.out.println("-- 7");
        neuralNetworkOneHideLayer.printError();
        neuralNetworkOneHideLayer.study(loadIMG(files[8]), 8);
        System.out.println("-- 8");
        neuralNetworkOneHideLayer.printError();
        neuralNetworkOneHideLayer.study(loadIMG(files[9]), 9);
        System.out.println("-- 9");
        neuralNetworkOneHideLayer.printError();

        System.out.println();
        System.out.println("k " + k + " time " + time);
        System.out.println();
    }

    private static int[] loadIMG(File file){
        int[] result = new int[15];

        try {
            BufferedImage img = ImageIO.read(file);

            int k = 0;
            int pixels;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 5; j++){
                    pixels = img.getRGB(i,j);

                    int n = 0;
                    if(pixels < - 100000)
                        n = 1;

                    result[k++] = n;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;
    }
}
