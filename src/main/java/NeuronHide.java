public class NeuronHide {

    private double[][] weight;
//    private int limit = 200;
    private int w = 3;
    private int h = 5;
    private double[] weightParsing = new double[15];
    private double[] deltaWeightParsing = new double[15];

    public NeuronHide(){
        weight = new double[w][h];
        randomizeWeight();
        parsingWeight();
    }

    public void randomizeWeight() {
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                weight[i][j] = Math.round(Math.random() * 100.0) / 100.0;
    }

    public double transfer(int[][] input){
        double power = 0;

        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                power += weight[i][j] * input[i][j];

        if(power < -10)
            return 0;
        else if(power > 10)
            return 1;
        else
            return 1/(1 + Math.exp(-power));
    }

    public void changeWeights(int [][] input, int d){
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                weight[i][j] += d * input[i][j];
    }

    public StringBuilder printWeight(){
        StringBuilder result = new StringBuilder();
        result.append("****************************\n");

        for(int i = 0; i < weightParsing.length; i++){
            result.append(weightParsing[i] + "  ");

        }
        result.setLength(result.length() - 1);

        return result;
    }

    public StringBuilder printWeightTrue(){
        StringBuilder result = new StringBuilder();
//        result.append("****************************\n");

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

    private void parsingWeight(){
        int n = 0;
        for(int i = 0; i < weight.length; i++)
            for(int j = 0; j < weight.length; j++)
                weightParsing [n++] = weight[i][j];
    }

    public double[] getWeightParsing(){
        return weightParsing;
    }

    public double[][] getWeight() {
        return weight;
    }

    public double[] getDeltaWeight() {
        return deltaWeightParsing;
    }

    public void setDeltaWeight(double[] deltaWeightParsing) {
        this.deltaWeightParsing = deltaWeightParsing;
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
