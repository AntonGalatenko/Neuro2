public class Neuron {

    private int[][] weight;
    private int limit = 200;
    private int w = 3;
    private int h = 5;

    public Neuron(){
        weight = new int[w][h];
//        randomizeWeight();
    }

    public void randomizeWeight() {
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                weight[i][j] = (int)((Math.random() * 9) + 1);
    }

    public int transferHard(int[][] input){
        int power = 0;

        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                power += weight[i][j] * input[i][j];
        return power >= limit ? 1 : 0;
    }

    public int transfer(int[][] input){
        int power = 0;

        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                power += weight[i][j] * input[i][j];
        return power;
    }

    public void changeWeights(int [][] input, int d){
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                weight[i][j] += d * input[i][j];
    }

    public StringBuilder printWeight(){
        StringBuilder result = new StringBuilder();
        result.append("****************************\n");

        int j = 0;
        while (j < h){
            for(int i = 0; i < w; i++)
                result.append(weight[i][j] + "  ");

            result.append("\n");
            j++;
        }
        result.setLength(result.length() - 1);

        return result;
    }

    public int[][] getWeight() {
        return weight;
    }

    public void setWeight(int i, int j, int weight) {
        this.weight[i][j] = weight;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }
}
