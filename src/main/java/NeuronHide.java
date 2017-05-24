public class NeuronHide {
    private double[] weight;
    private double[] deltaWeight;

    public NeuronHide(){
        weight = new double[15];
        deltaWeight = new double[15];
        randomizeWeight();
    }

    public void randomizeWeight() {
        for(int i = 0; i < weight.length; i++)
            weight[i] = Math.round(((Math.random() * 2) - 1) * 100.0) / 100.0;
    }

    public double transfer(int[] input){
        double power = 0;

        for(int i = 0; i < input.length; i++)
            power += weight[i] * input[i];

        if(power < -10)
            return 0;
        else if(power > 10)
            return 1;
        else
//            return (Math.exp(2 * power) - 1) / (Math.exp(2 * power) + 1);
            return 1 / (1 + Math.exp(-power));
    }

    public double transfer(double[] input){
        double power = 0;

        for(int i = 0; i < input.length; i++)
            power += weight[i] * input[i];

        if(power < -10)
            return 0;
        else if(power > 10)
            return 1;
        else
//            return (Math.exp(2 * power) - 1) / (Math.exp(2 * power) + 1);
            return 1 / (1 + Math.exp(-power));
    }

    public double transfer1(int[] input){
        double power = 0;

        for(int i = 0; i < input.length; i++)
            power += weight[i] * input[i];

        if(power < -10)
            return 0;
        else if(power > 10)
            return 1;
        else
//            return (Math.exp(2 * power) - 1) / (Math.exp(2 * power) + 1);
            return 1 / (1 + Math.exp(-power));
    }

    public StringBuilder printWeight(){
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < 5; i++)
            result.append(String.format("%.2f", weight[i]) + "   " + String.format("%.2f", weight[i + 5]) + "   " + String.format("%.2f", weight[i + 10]) + "\n");

        result.append("\n");

        return result;
    }

    public void addWeight(int i, double value) {
        weight[i] += value;
    }

    public double[] getWeight(){
        return weight;
    }

    public void setDeltaWeight(int i, double value) {
        deltaWeight[i] = value;
    }

    public double[] getDeltaWeight() {
        return deltaWeight;
    }

}
