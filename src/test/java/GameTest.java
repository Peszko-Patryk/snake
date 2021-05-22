import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void shouldPutApple_whereSnakeIsNot() {
        Game game = new Game(null);
        boolean result = game.getApple().getCell().isSnakeOn();
        assertFalse(result);
    }
}