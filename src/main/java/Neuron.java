import java.util.ArrayList;
import java.util.Random;

public class Neuron {
    private float factor;
    private float constant;
    private float output;
    private Random random = new Random();
    public ArrayList<Neuron> delivers;


    public Neuron() {
        factor = random.nextFloat() / 100;
        constant = random.nextFloat() / 100;
    }

    public void proccess(int input) {
        int data = 0;
        if (input != -1) {
            data = input;
        } else {
            for (int i = 0; i < delivers.size(); i++) {
                data += delivers.get(i).getOutput();
            }
        }
        output = constant + factor * data;
        ReLuFunction();
    }

    private void ReLuFunction() {
        if (output <= 0) {
            output = 0;
        }
    }

    public void setDelivers(Layer layer) {
        delivers = layer.neurons;
    }

    public float getOutput() {
        return output;
    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public float getConstant() {
        return constant;
    }

    public void setConstant(float constant) {
        this.constant = constant;
    }
}
