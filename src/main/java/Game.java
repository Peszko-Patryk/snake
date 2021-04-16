import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Game implements ListsHolder {
    private Random random = new Random();
    private Cell[][] cells = new Cell[sizeOfField][sizeOfField];
    private Apple apple;
    private Cell cell;
    private Snake snake;

    public Game(NeuronNetwork neuronNetwork) {
        setCells();
        snake = new Snake(cells);
        if (neuronNetwork == null) {
            neuronNetwork = new NeuronNetwork(this);
        } else {
            neuronNetwork.changeSlightlyFactors();
        }
        snake.setNeuronNetwork(neuronNetwork);
        neuronNetwork.setSnake(snake);
        apple = new Apple();
        putApple();
        snake.setBody();
    }

    public int play() {
        while (snake.isState()) {
            snake.decide();
            if (checkIfSnakeAteApple()) {
                putApple();
            }
        }
        return 50 * snake.getScore() + snake.getMovesDone();
    }

    public void paint(Graphics g, ImageObserver o) {
        if (snake.isState()) {
            snake.decide();
            snake.paint(g);
            if (checkIfSnakeAteApple()) {
                putApple();
            }
            apple.paint(g, o);
        }
    }

    public void putApple() {
        while (true) {
            cell = cells[random.nextInt(sizeOfField)][random.nextInt(sizeOfField)];
            if (!cell.isSnakeOn()) {
                apple.setCell(cell);
                break;
            }
        }
    }

    private boolean checkIfSnakeAteApple() {
        if (snake.body.get(snake.body.size() - 1).getX() == apple.getCell().getX()) {
            if (snake.body.get(snake.body.size() - 1).getY() == apple.getCell().getY()) {
                snake.setScore();
                return true;
            }
        }
        return false;
    }

    private void setCells() {
        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                cells[i][j] = new Cell(j, i, 43 + j * 500 / sizeOfField, 128 + i * 500 / sizeOfField, (43 + 500/sizeOfField - 1) + j * 500 / sizeOfField, (128 + 500 / sizeOfField -1) + i * 500 / sizeOfField);
            }
        }
    }

    public Apple getApple() {
        return apple;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }
}