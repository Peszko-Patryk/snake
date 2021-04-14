import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SnakeTest implements ListsHolder {

    @Test
    void shouldMove_whenFarFromWallAndTail() {
        Snake snake = new Snake();
        GameField gameField = new GameField();
        snake.setBody();
        snake.move();
        boolean result = false;
        if (snake.body.get(0).getX() == 5 && snake.body.get(0).getY() == 5) {
            if (snake.body.get(1).getX() == 5 && snake.body.get(1).getY() == 4) {
                result = true;
            }
        }
        assertTrue(result);
    }

    @Test
    void shouldEndGame_whenSnakeHitsTheWall() {
        Snake snake = new Snake();
        GameField gameField = new GameField();
        snake.body.add(cells[0][5]);
        game.setState(true);
        snake.move();
        assertFalse(game.isState());
    }

    @Test
    void shouldTurnLeft() {
        Snake snake = new Snake();
        snake.turnLeft();
        boolean result = false;
        if (snake.getxDir() == -1 && snake.getyDir() == 0) {
            result = true;
        }
        assertTrue(result);
    }

    @Test
    void ShouldTurnRight() {
        Snake snake = new Snake();
        snake.turnRight();
        boolean result = false;
        if (snake.getxDir() == 1 && snake.getyDir() == 0) {
            result = true;
        }
        assertTrue(result);
    }

    @Test
    void ShouldSetBody() {
        Snake snake = new Snake();
        GameField gameField = new GameField();
        snake.setBody();
        boolean result = false;
        if (snake.body.get(0).getX() == 5 && snake.body.get(0).getY() == 6) {
            if (snake.body.get(1).getX() == 5 && snake.body.get(1).getY() == 5) {
                result = true;
            }
        }
        assertTrue(result);
    }

    @Test
    void ShouldSetScore() {
        Snake snake = new Snake();
        snake.setScore();
        boolean result = false;
        if (snake.getScore() == 1 && snake.getMovesLeft() == 50 && snake.getLength() == 3) {
            result = true;
        }
        assertTrue(result);
    }
}