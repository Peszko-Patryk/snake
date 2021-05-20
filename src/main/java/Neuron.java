import java.util.ArrayList;
import java.util.Random;

public class Neuron {
    private double factor;
    private int constant = 0;
    private double output;
    private double input;
    private Random random = new Random();
    public ArrayList<Neuron> delivers;
    public ArrayList<Double> factors;
    private double error = 0;


    public Neuron() {
        factor = random.nextFloat();
    }

    public void proccess(int input) {
        output = constant;
        if (input != -1) {
            output = factor * this.input;
        } else {
            for (int i = 0; i < delivers.size(); i++) {
                output += delivers.get(i).getOutput() * factors.get(i);
            }
//            ReLuFunction();
            sigmoidFunction();
        }
    }

    private void sigmoidFunction() {
        output = (float) (1 / (1 + Math.exp(-output)));
    }

    private void ReLuFunction() {
        if (output <= 0) {
            output = 0;
        }
    }

    public void setDelivers(Layer layer) {
        delivers = layer.neurons;
        factors = new ArrayList<>();
        for (int i = 0; i < layer.neurons.size(); i++) {
            factors.add(random.nextDouble());
        }
    }

    public void clearError() {
        error = 0;
    }

    public void setConstant(int constant) {
        this.constant = constant;
    }

    public double getInput() {
        return input;
    }

    public void setInput(float input) {
        this.input = input;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error += error;
    }

    public double getOutput() {
        return output;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }
}
