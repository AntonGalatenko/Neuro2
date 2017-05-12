public class NeuronOutput {

    private double[] weight;
    private double[] deltaWeight;
//    private int numberWeight;

    public NeuronOutput(int numberWeight){
//        this.numberWeight = numberWeight;
        weight = new double[numberWeight];
        randomizeWeight();
    }

    public void randomizeWeight() {
        for(int i = 0; i < weight.length; i++)
            weight[i] = (int)((Math.random() * 9) + 1);
    }

    public double transfer(double[] input){
        int power = 0;

        for(int i = 0; i < weight.length; i++)
            power += weight[i] * input[i];

        if(power < -10)
            return 0;
        else if(power > 10)
            return 1;
        else
            return 1/(1 + Math.exp(-power));
    }



//    public int transfer(int[][] input){
//        int power = 0;
//
//        for(int i = 0; i < w; i++)
//            for(int j = 0; j < h; j++)
//                power += weight[i][j] * input[i][j];
//        return power;
//    }

//    public double transfer(int[][] input){
//        int power = 0;
//
//        for(int i = 0; i < w; i++)
//            for(int j = 0; j < h; j++)
//                power += weight[i][j] * input[i][j];
//
//        if(power < -10)
//            return 0;
//        else if(power > 10)
//            return 1;
//        else
//            return (int)Math.round(1/(1 + Math.exp(-power)));
//    }

//    public void changeWeights(int [][] input, int d){
//        for(int i = 0; i < w; i++)
//            for(int j = 0; j < h; j++)
//                weight[i][j] += d * input[i][j];
//    }

    public StringBuilder printWeight(){
        StringBuilder result = new StringBuilder();
        result.append("****************************\n");

        for(int i = 0; i < weight.length; i++){
            result.append(weight[i] + "  ");

        }
        result.setLength(result.length() - 1);

        return result;
    }
//
//    public int[][] getWeight() {
//        return weight;
//    }
//
//    public void setWeight(int i, int j, int weight) {
//        this.weight[i][j] = weight;
//    }

    public double[] getWidth() {
        return weight;
    }

//    public void setDeltaWeight(double[] deltaWeight) {
//        this.deltaWeight = deltaWeight;
//    }

    public double[] getDeltaWidth() {
        return weight;
    }

//    public int getHeight() {
//        return h;
//    }
}
