public class NeuronHide {

    private double[][] weight;
    private int w = 3;
    private int h = 5;
    private double[] weightParsing;
    private double[] deltaWeightParsing;

    public NeuronHide(){
        weight = new double[w][h];
        weightParsing = new double[15];
        deltaWeightParsing = new double[15];
        randomizeWeight();
        parsingWeight();
    }

    public void randomizeWeight() {
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                weight[i][j] = Math.round((Math.random() - 0.5) * 100.0) / 100.0;
    }

    public double transfer(int[][] input){
        double power = 0;
        int[] inputParsing = parsingInput(input);

        for(int i = 0; i < inputParsing.length; i++)
            power += weightParsing[i] * inputParsing[i];

        if(power < -10)
            return 0;
        else if(power > 10)
            return 1;
        else
            return 1 / (1 + Math.exp(-power));
    }

    public void changeWeights(int [][] input, int d){
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                weight[i][j] += d * input[i][j];
    }

    public StringBuilder printWeight(){
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < 5; i++)
            result.append(weightParsing[i] + "  " + weightParsing[i + 5] + "  " + weightParsing[i + 10] + "\n");

        result.append("\n");
//        result.setLength(result.length() - 1);

        return result;
    }

    public StringBuilder printWeightTrue(){
        StringBuilder result = new StringBuilder();

        int j = 0;
        while (j < h){
            for(int i = 0; i < w; i++)
                result.append(weight[i][j] + "  ");

            result.append("\n");
            j++;
        }
        result.append("\n");
//        result.setLength(result.length() - 1);

        return result;
    }

    private void parsingWeight(){
        int n = 0;
        for(int i = 0; i < w; i++){
            for(int j = 0; j < h; j++){
                weightParsing[n] = weight[i][j];
                n++;
            }
        }
    }

    private int[] parsingInput(int[][] value){
        int n = 0;
        int[] result = new int[15];
        for(int i = 0; i < value.length; i++){
            for(int j = 0; j < value[0].length; j++){
                result [n] = value[i][j];
                n++;
            }
        }
        return result;
    }

    public void addWeightParsing(int i, double value) {
        weightParsing[i] += value;
    }

    public double[] getWeightParsing(){
        return weightParsing;
    }

    public double[][] getWeight() {
        return weight;
    }

    public void setDeltaWeightParsing(int i, double value) {
        deltaWeightParsing[i] = value;
    }

    public double[] getDeltaWeight() {
        return deltaWeightParsing;
    }

}
