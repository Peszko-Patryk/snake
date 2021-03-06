import java.util.ArrayList;

public class Layer {
    public ArrayList<Neuron> neurons = new ArrayList<>();

    public Layer(int size, Layer layer) {
        createNeurons(size, layer);
    }

    public Layer(int size) {
        for (int i = 0; i < size; i++) {
            neurons.add(new Neuron());
        }
    }

    private void createNeurons(int size, Layer layer) {
        for (int i = 0; i < size; i++) {
            neurons.add(new Neuron());
            neurons.get(i).setDelivers(layer);
        }
    }

    public void passError() {
        for (Neuron neuron : neurons) {
            for (int i = 0; i < neuron.delivers.size(); i++) {
                neuron.delivers.get(i).setError(neuron.getError() * neuron.factors.get(i));
            }
        }
    }

    public void proccess() {
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).proccess(-1);
        }
    }
}
