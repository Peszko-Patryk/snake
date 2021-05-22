import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NeuronTest {

    @Test
    void shouldProccess() {
        Neuron neuron = new Neuron();
        double factor = neuron.getFactor();
        neuron.setInput(1);
        neuron.proccess(1);
        boolean result = neuron.getOutput() == (1 / (1 + Math.exp(-factor))) ? true : false;
        assertTrue(result);
    }
}