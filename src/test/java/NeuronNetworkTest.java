import org.junit.jupiter.api.Test;

class NeuronNetworkTest {

    @Test
    void decide() {
        NeuronNetwork neuronNetwork = new NeuronNetwork();
        Snake snake = new Snake();
        snake.setBody();
        neuronNetwork.decide();
        assert true;
    }
}