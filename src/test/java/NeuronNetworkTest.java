import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NeuronNetworkTest {
    NeuronNetwork neuronNetwork;
    Game game;

    @BeforeEach
    void before() {
        game = new Game(null);
        neuronNetwork = game.getNeuronNetwork();
    }

    @Test
    void shouldReturnOne_whenAppleInVerticalLine() {
        do {
            game.putApple();
        } while (game.getApple().getCell().getY() < 9);
        game.getSnake().setHeadPosX(game.getApple().getCell().getX());
        game.getSnake().setHeadPosY(game.getApple().getCell().getY() + 1);
        boolean result = neuronNetwork.checkIfAppleInLine(0, -1) == 1 ? true : false;
        assertTrue(result);
    }

    @Test
    void shouldReturnOne_whenAppleInDiagonalLine() {
        do {
            game.putApple();
        } while (game.getApple().getCell().getY() < 9 && game.getApple().getCell().getX() < 9);
        game.getSnake().setHeadPosX(game.getApple().getCell().getX() + 1);
        game.getSnake().setHeadPosY(game.getApple().getCell().getY() + 1);
        boolean result = neuronNetwork.checkIfAppleInLine(-1, -1) == 1 ? true : false;
        assertTrue(result);
    }

    @Test
    void shouldReturnZero_whenAppleNotInLine() {
        do {
            game.putApple();
        } while (game.getApple().getCell().getY() < 9);
        game.getSnake().setHeadPosX(game.getApple().getCell().getX() + 1);
        game.getSnake().setHeadPosY(game.getApple().getCell().getY() + 1);
        boolean result = neuronNetwork.checkIfAppleInLine(0, -1) == 0 ? true : false;
        assertTrue(result);
    }

    @Test
    void shouldReturnZero_whenAppleNotInDiagonalLine() {
        do {
            game.putApple();
        } while (game.getApple().getCell().getY() < 9 && game.getApple().getCell().getX() < 9);
        game.getSnake().setHeadPosX(game.getApple().getCell().getX() + 1);
        game.getSnake().setHeadPosY(game.getApple().getCell().getY() + 1);
        boolean result = neuronNetwork.checkIfAppleInLine(1, 1) == 0 ? true : false;
        assertTrue(result);
    }
}