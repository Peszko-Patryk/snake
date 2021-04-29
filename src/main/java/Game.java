import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class Game implements ListsHolder {
    private Random random = new Random();
    private Cell[][] cells = new Cell[sizeOfField][sizeOfField];
    private NeuronNetwork neuronNetwork;
    private Apple apple;
    private Cell cell;
    private Snake snake;
    private int numGen = 1;
    private int highestScore = 0;
    private ArrayList<Cell> possibilities = new ArrayList<>();

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
                highestScore = snake.getScore() > highestScore ? snake.getScore() : highestScore;
            }
            apple.paint(g, o);
        }
    }

    public void move() {
        findTheRightMove();
        neuronNetwork.decide();
        if (!snake.move()) {
            numGen++;
            startNewGame();
        }
//        backPropagation();
    }

    private void findTheRightMove() {
        if (!checkIfSnakeSeesApple()){
            checkHowToStayAlive();
        } else {
            findShortestRoute();
        }
        System.out.println(possibilities);
    }

    private void findShortestRoute() {

    }

    private void checkHowToStayAlive() {
        possibilities.clear();
        int xdir = snake.getxDir();
        int ydir = snake.getyDir();
        if (snake.getHeadPosX() + xdir >= 0 && snake.getHeadPosX() + xdir < 10 && snake.getHeadPosY() + ydir >= 0 &&
                snake.getHeadPosY() + ydir < 10 && !cells[snake.getHeadPosY() + ydir][snake.getHeadPosX() + xdir].isSnakeOn()){
            possibilities.add(cells[snake.getHeadPosY() + ydir][snake.getHeadPosX() + xdir]);
            return;
        }
        int temp = xdir;
        xdir = ydir;
        ydir = temp;
        if (snake.getHeadPosX() + xdir >= 0 && snake.getHeadPosX() + xdir < 10 && snake.getHeadPosY() + ydir >= 0 &&
                snake.getHeadPosY() + ydir < 10 && !cells[snake.getHeadPosY() + ydir][snake.getHeadPosX() + xdir].isSnakeOn()) {
            possibilities.add(cells[snake.getHeadPosY() + ydir][snake.getHeadPosX() + xdir]);
            return;
        }
        xdir *= -1;
        ydir *= -1;
        if (snake.getHeadPosX() + xdir >= 0 && snake.getHeadPosX() + xdir < 10 && snake.getHeadPosY() + ydir >= 0 &&
                snake.getHeadPosY() + ydir < 10 && !cells[snake.getHeadPosY() + ydir][snake.getHeadPosX() + xdir].isSnakeOn()) {
            possibilities.add(cells[snake.getHeadPosY() + ydir][snake.getHeadPosX() + xdir]);
        }
    }

    private boolean checkIfSnakeSeesApple() {
        if (apple.getCell().getX() == snake.getHeadPosX()){
            return true;
        } else if (apple.getCell().getY() == snake.getHeadPosY()){
            return true;
        } else if (abs(apple.getCell().getX() - snake.getHeadPosX()) == abs(apple.getCell().getY() - snake.getHeadPosY())){
            return true;
        } else {
            return false;
        }
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

    public int getHighestScore() {
        return highestScore;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }
}