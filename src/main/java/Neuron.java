import java.util.ArrayList;
import java.util.Random;

public class Neuron {
    private float factor;
    private float output;
    private Random random = new Random();
    public ArrayList<Neuron> delivers;
    public ArrayList<Float> factors;


    public Neuron() {
        factor = random.nextFloat();
    }

    public void proccess(int input) {
        if (input != -1) {
            output = factor * input;
        } else {
            for (int i = 0; i < delivers.size(); i++) {
                output += delivers.get(i).getOutput() * factors.get(i);
            }
        }
        ReLuFunction();
    }

    private void ReLuFunction() {
        if (output <= 0) {
            output = 0;
        }
    }

    public void setDelivers(Layer layer) {
        delivers = layer.neurons;
        factors = new ArrayList<>();
        for (int i = 0; i<layer.neurons.size(); i++){
            factors.add(random.nextFloat());
        }
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
}
