import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SnakeTest implements ListsHolder {
    Snake snake;
    Game game = new Game(null);

    @BeforeEach
    void before() {
        snake = new Snake(game.getCells());
    }

    @Test
    void shouldNotMove_whenTailIsOnItsWay() {
        Game game = new Game(null);
        snake = game.getSnake();
        snake.body.clear();
        snake.body.add(snake.getCells()[1][1]);
        snake.getCells()[1][1].setSnakeOn(true);
        snake.body.add(snake.getCells()[1][2]);
        snake.getCells()[1][1].setSnakeOn(true);
        snake.body.add(snake.getCells()[2][2]);
        snake.getCells()[1][1].setSnakeOn(true);
        snake.body.add(snake.getCells()[2][1]);
        snake.getCells()[1][1].setSnakeOn(true);
        snake.setLength(4);
        boolean result = snake.move();
        assertFalse(result);
    }

    @Test
    void shouldMove_whenTailIsNotOnItsWay() {
        Game game = new Game(null);
        snake = game.getSnake();
        snake.body.clear();
        snake.body.add(snake.getCells()[1][1]);
        snake.getCells()[1][1].setSnakeOn(true);
        snake.body.add(snake.getCells()[1][2]);
        snake.getCells()[1][1].setSnakeOn(true);
        snake.body.add(snake.getCells()[2][2]);
        snake.getCells()[1][1].setSnakeOn(true);
        snake.body.add(snake.getCells()[2][1]);
        snake.getCells()[1][1].setSnakeOn(true);
        snake.setLength(4);
        snake.turnLeft();
        boolean result = snake.move();
        assertTrue(result);
    }

    @Test
    void shouldNotMove_whenWallIsOnItsWay() {
        Game game = new Game(null);
        snake = game.getSnake();
        snake.body.clear();
        snake.body.add(snake.getCells()[1][1]);
        snake.getCells()[1][1].setSnakeOn(true);
        snake.body.add(snake.getCells()[0][1]);
        snake.getCells()[1][1].setSnakeOn(true);
        snake.setLength(2);
        boolean result = snake.move();
        assertFalse(result);
    }

    @Test
    void shouldMove_whenWallIsNotOnItsWay() {
        Game game = new Game(null);
        snake = game.getSnake();
        snake.body.clear();
        snake.body.add(snake.getCells()[1][1]);
        snake.getCells()[1][1].setSnakeOn(true);
        snake.body.add(snake.getCells()[0][1]);
        snake.getCells()[1][1].setSnakeOn(true);
        snake.setLength(2);
        snake.turnRight();
        boolean result = snake.move();
        assertTrue(result);
    }

    @Test
    void shouldMove_whenFarFromWallAndTail() {
        snake.move();
        boolean result = snake.move();
        assertTrue(result);
    }

    @Test
    void shouldTurnLeft() {
        snake.turnLeft();
        boolean result = snake.getxDir() == -1 && snake.getyDir() == 0 ? true : false;
        assertTrue(result);
    }

    @Test
    void shouldTurnRight() {
        snake.turnRight();
        boolean result = snake.getxDir() == 1 && snake.getyDir() == 0 ? true : false;
        assertTrue(result);
    }

    @Test
    void shouldSetBody() {
        boolean result = false;
        if (snake.body.get(0).getX() == 5 && snake.body.get(0).getY() == 7) {
            if (snake.body.get(1).getX() == 5 && snake.body.get(1).getY() == 6) {
                if (snake.body.get(2).getX() == 5 && snake.body.get(2).getY() == 5) {
                    if (snake.body.get(3).getX() == 5 && snake.body.get(3).getY() == 4) {
                        result = true;
                    }
                }
            }
        }
        assertTrue(result);
    }

    @Test
    void shouldSetScore() {
        snake.setScore();
        boolean result = snake.getScore() == 1 && snake.getLength() == 5 ? true : false;
        assertTrue(result);
    }
}