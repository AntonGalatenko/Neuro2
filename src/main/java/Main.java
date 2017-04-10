import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    static File file = new File("D:\\NeuronImg\\img");
    static final String WEIGHT_FILE_PATH = "D:\\NeuronImg\\neuron2Weight.txt";

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork();

        neuralNetwork.loadWeightFromFile(WEIGHT_FILE_PATH);
        neuralNetwork.printWeight();

//        File[] files = file.listFiles();
//        Random random = new Random();
//
//        for(int i = 1; i < 400; i++){
//            System.out.println("i = " + i);
//            int rnd = random.nextInt(files.length - 1);
//            System.out.println("rnd = " + rnd);
//
//            neuralNetwork.study(loadIMG(files[rnd]), rnd);
//        }
//

        System.out.println();
        System.out.println(neuralNetwork.getAnswer(loadIMG(new File("D:\\NeuronImg\\img\\1.bmp"))));
        System.out.println(neuralNetwork.getAnswer(loadIMG(new File("D:\\NeuronImg\\img\\2.bmp"))));
        System.out.println(neuralNetwork.getAnswer(loadIMG(new File("D:\\NeuronImg\\img\\3.bmp"))));
        System.out.println(neuralNetwork.getAnswer(loadIMG(new File("D:\\NeuronImg\\img\\4.bmp"))));
        System.out.println(neuralNetwork.getAnswer(loadIMG(new File("D:\\NeuronImg\\img\\5.bmp"))));
        System.out.println(neuralNetwork.getAnswer(loadIMG(new File("D:\\NeuronImg\\img\\6.bmp"))));
        System.out.println(neuralNetwork.getAnswer(loadIMG(new File("D:\\NeuronImg\\img\\7.bmp"))));
        System.out.println(neuralNetwork.getAnswer(loadIMG(new File("D:\\NeuronImg\\img\\8.bmp"))));
        System.out.println(neuralNetwork.getAnswer(loadIMG(new File("D:\\NeuronImg\\img\\9.bmp"))));
        System.out.println(neuralNetwork.getAnswer(loadIMG(new File("D:\\NeuronImg\\img\\0.bmp"))));

//        neuralNetwork.saveWeightToFile(WEIGHT_FILE_PATH);
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
