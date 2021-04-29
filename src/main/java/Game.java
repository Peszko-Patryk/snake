import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Game implements ListsHolder {
    private Random random = new Random();
    private Cell[][] cells = new Cell[sizeOfField][sizeOfField];
    private NeuronNetwork neuronNetwork;
    private Apple apple;
    private Cell cell;
    private Snake snake;
    private int numGen = 1;

    public Game(NeuronNetwork neuronNetwork) {
        setCells();
        snake = new Snake(cells);
        if (neuronNetwork == null) {
            this.neuronNetwork = new NeuronNetwork(this);
        } else {
            this.neuronNetwork = neuronNetwork;
        }
        apple = new Apple();
        putApple();
        this.neuronNetwork.setSnake(snake);
    }

    public void paint(Graphics g, ImageObserver o) {
        if (snake.isState()) {
            snake.paint(g);
            if (checkIfSnakeAteApple()) {
                putApple();
            }
            apple.paint(g, o);
        }
    }

    public void move() {
        neuronNetwork.decide();
        if (!snake.move()) {
            numGen++;
            startNewGame();
        }
//        propagacja()
    }

    private void startNewGame() {
        snake = new Snake(cells);
        neuronNetwork.setSnake(snake);
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
                cells[i][j] = new Cell(j, i, 43 + j * 500 / sizeOfField, 128 + i * 500 / sizeOfField, (43 + 500 / sizeOfField - 1) + j * 500 / sizeOfField, (128 + 500 / sizeOfField - 1) + i * 500 / sizeOfField);
            }
        }
    }

    public Apple getApple() {
        return apple;
    }

    public int getNumGen() {
        return numGen;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }
}