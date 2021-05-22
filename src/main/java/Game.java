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
    private int sumOfScores = 0;
    private int highestScore = 0;
    private ArrayList<Cell> possibilities = new ArrayList<>();
    private ArrayList<Cell> shouldGo = new ArrayList<>();
    private ArrayList<Integer> bestDir = new ArrayList<>();
    private int a=0;
    private int w=0;
    private int t=0;
    public boolean ready = true;

    public Game(NeuronNetwork neuronNetwork) {
        setCells();
        snake = new Snake(cells);
        if (neuronNetwork == null) {
            this.neuronNetwork = new NeuronNetwork(this);
        } else {
            this.neuronNetwork = neuronNetwork;
        }
        this.neuronNetwork.changeConstants(0,1,2);
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
        if (bestDir.size() != 0) {
            if (neuronNetwork.lastMove != bestDir.get(0)) {
//                System.out.println("zmieniam wspolczynniki bo powienien " + bestDir.get(0));
                backPropagation();
            }
        }
        ready = true;
    }

    private boolean wentWhereShouldNot() {
        if (shouldGo.contains(cells[snake.getHeadPosY()][snake.getHeadPosX()])) {
            if (possibilities.contains(cells[snake.getHeadPosY()][snake.getHeadPosX()])) {
                return false;
            }
        }
        return true;
    }

    private void backPropagation() {
        neuronNetwork.clearErrors();
        neuronNetwork.countErrors(bestDir.get(0));
        neuronNetwork.changeFactors();
    }

    private void findTheRightMove() {
        possibilities.clear();
        shouldGo.clear();
        bestDir.clear();
        checkHowToStayAlive();
        checkIfSnakeHasClearRouteToApple();
    }

    private void checkHowToStayAlive() {
        int xdir = snake.getxDir();
        int ydir = snake.getyDir();
        if (snake.getHeadPosX() + xdir >= 0 && snake.getHeadPosX() + xdir < sizeOfField && snake.getHeadPosY() + ydir >= 0 &&
                snake.getHeadPosY() + ydir < sizeOfField && !cells[snake.getHeadPosY() + ydir][snake.getHeadPosX() + xdir].isSnakeOn()) {
            possibilities.add(cells[snake.getHeadPosY() + ydir][snake.getHeadPosX() + xdir]);
            bestDir.add(0);
        }
        int temp = xdir;
        xdir = ydir;
        ydir = temp;
        if (snake.getHeadPosX() + xdir >= 0 && snake.getHeadPosX() + xdir < sizeOfField && snake.getHeadPosY() + ydir >= 0 &&
                snake.getHeadPosY() + ydir < sizeOfField && !cells[snake.getHeadPosY() + ydir][snake.getHeadPosX() + xdir].isSnakeOn()) {
            possibilities.add(cells[snake.getHeadPosY() + ydir][snake.getHeadPosX() + xdir]);
            bestDir.add(ydir == 0 ? -1 : 1);
        }
        xdir *= -1;
        ydir *= -1;
        if (snake.getHeadPosX() + xdir >= 0 && snake.getHeadPosX() + xdir < sizeOfField && snake.getHeadPosY() + ydir >= 0 &&
                snake.getHeadPosY() + ydir < sizeOfField && !cells[snake.getHeadPosY() + ydir][snake.getHeadPosX() + xdir].isSnakeOn()) {
            possibilities.add(cells[snake.getHeadPosY() + ydir][snake.getHeadPosX() + xdir]);
            bestDir.add(ydir == 0 ? 1 : -1);
        }
    }

    private boolean checkIfSnakeHasClearRouteToApple() {
        if (apple.getCell().getX() == snake.getHeadPosX()) {
//            System.out.println("to samo x");
            if (snake.getxDir() == 0 && ((snake.getyDir() > apple.getCell().getY() && snake.getyDir() == 1) || (snake.getyDir() < apple.getCell().getY() && snake.getyDir() == -1))) {
                return true;
            }
            shouldGo.add(cells[snake.getHeadPosY() + (snake.getHeadPosY() > apple.getCell().getY() ? -1 : 1)][snake.getHeadPosX()]);
            findWay(cells[snake.getHeadPosY() + (snake.getHeadPosY() > apple.getCell().getY() ? -1 : 1)][snake.getHeadPosX()]);
            return true;
        } else if (apple.getCell().getY() == snake.getHeadPosY()) {
//            System.out.println("to samo y");
            if (snake.getyDir() == 0 && ((snake.getxDir() > apple.getCell().getX() && snake.getxDir() == 1) || (snake.getxDir() < apple.getCell().getX() && snake.getxDir() == -1))) {
                return true;
            }
            shouldGo.add(cells[snake.getHeadPosY()][snake.getHeadPosX() + (snake.getHeadPosX() > apple.getCell().getX() ? -1 : 1)]);
            findWay(cells[snake.getHeadPosY()][snake.getHeadPosX() + (snake.getHeadPosX() > apple.getCell().getX() ? -1 : 1)]);
            return true;
        } else if (abs(apple.getCell().getX() - snake.getHeadPosX()) == abs(apple.getCell().getY() - snake.getHeadPosY())) {
            //jezeli z gory to tam sprobuj idz do gory, jezeli nie mozesz to rozejrzyj sie na boki
            // else idz do dolu lub rozejrzyj sie na boki
            if (checkLeftBackAndRightFrontDiagonals()) {
                return true;
            }
            return checkRightBackAndLeftFrontDiagonals();
        }
        return false;
    }

    private boolean checkRightBackAndLeftFrontDiagonals() {
        int xdir = 0, ydir = 0;
        if (snake.getxDir() == 0) {
            if (snake.getyDir() == -1) {
                xdir = 1;
                ydir = 1;
            } else {
                xdir = -1;
                ydir = -1;
            }
        } else if (snake.getxDir() == -1) {
            xdir = 1;
            ydir = -1;
        } else if (snake.getxDir() == 1) {
            xdir = -1;
            ydir = 1;
        }
        if (neuronNetwork.checkIfAppleInLine(xdir, ydir) == 1) {
            if (bestDir.contains(1)) {
                bestDir.clear();
                bestDir.add(1);
            }
            return true;
        }
        xdir *= -1;
        ydir *= -1;
        if (neuronNetwork.checkIfAppleInLine(xdir, ydir) == 1) {
            if (bestDir.contains(0)) {
                bestDir.clear();
                bestDir.add(0);
            }
            return true;
        }
        return false;
    }

    private boolean checkLeftBackAndRightFrontDiagonals() {
        int xdir = 0, ydir = 0;
        if (snake.getxDir() == 0) {
            if (snake.getyDir() == -1) {
                xdir = -1;
                ydir = 1;
            } else {
                xdir = 1;
                ydir = -1;
            }
        } else if (snake.getxDir() == -1) {
            xdir = 1;
            ydir = 1;
        } else if (snake.getxDir() == 1) {
            xdir = -1;
            ydir = -1;
        }
        if (neuronNetwork.checkIfAppleInLine(xdir, ydir) == 1) {
            if (bestDir.contains(-1)) {
                bestDir.clear();
                bestDir.add(-1);
            }
            return true;
        }
        xdir *= -1;
        ydir *= -1;
        if (neuronNetwork.checkIfAppleInLine(xdir, ydir) == 1) {
            if (bestDir.contains(0)) {
                bestDir.clear();
                bestDir.add(0);
            }
            return true;
        }
        return false;
    }

    private void findWay(Cell cell) {
//        System.out.println("glowa : " + snake.getHeadPosY() + " , " + snake.getHeadPosX() + " cel : " + cell.getY() + " , " + cell.getX());
        if (snake.getHeadPosY() + snake.getyDir() >= 0 && snake.getHeadPosY() + snake.getyDir() < sizeOfField &&
                snake.getHeadPosX() + snake.getxDir() >= 0 && snake.getHeadPosX() + snake.getxDir() < sizeOfField) {
//            System.out.println(cells[snake.getHeadPosY() + snake.getyDir()][snake.getHeadPosX() + snake.getxDir()] == cell);
            if (cells[snake.getHeadPosY() + snake.getyDir()][snake.getHeadPosX() + snake.getxDir()] == cell) {
                if (bestDir.contains(0)) {
                    bestDir.clear();
                    bestDir.add(0);
                }
                return;
            }
        }
        snake.turnLeft();
        if (snake.getHeadPosY() + snake.getyDir() >= 0 && snake.getHeadPosY() + snake.getyDir() < sizeOfField &&
                snake.getHeadPosX() + snake.getxDir() >= 0 && snake.getHeadPosX() + snake.getxDir() < sizeOfField) {
//            System.out.println(cells[snake.getHeadPosY() + snake.getyDir()][snake.getHeadPosX() + snake.getxDir()] == cell);
            if (cells[snake.getHeadPosY() + snake.getyDir()][snake.getHeadPosX() + snake.getxDir()] == cell) {
                snake.turnRight();
                if (bestDir.contains(-1)) {
                    bestDir.clear();
                    bestDir.add(-1);
                }
                return;
            }
        }
        snake.turnRight();
        snake.turnRight();
        if (snake.getHeadPosY() + snake.getyDir() >= 0 && snake.getHeadPosY() + snake.getyDir() < sizeOfField &&
                snake.getHeadPosX() + snake.getxDir() >= 0 && snake.getHeadPosX() + snake.getxDir() < sizeOfField) {
//            System.out.println(cells[snake.getHeadPosY() + snake.getyDir()][snake.getHeadPosX() + snake.getxDir()] == cell);
            if (cells[snake.getHeadPosY() + snake.getyDir()][snake.getHeadPosX() + snake.getxDir()] == cell) {
                snake.turnLeft();
                if (bestDir.contains(1)) {
                    bestDir.clear();
                    bestDir.add(1);
                }
                return;
            }
        }
        snake.turnLeft();
    }

    private boolean checkDiagonals(int dir) {
//        System.out.println("checkDiagonals");
        for (int i = snake.getHeadPosY() - dir; i * dir >= dir * apple.getCell().getY(); i -= dir) {
            if (cells[i][snake.getHeadPosX()].isSnakeOn()) {
//                System.out.println("zajete: " + snake.getHeadPosY() + " , " + i);
                if (snake.getHeadPosX() > apple.getCell().getX()) {
                    for (int j = snake.getHeadPosX() - 1; j >= apple.getCell().getX(); j--) {
                        if (cells[snake.getHeadPosY()][j].isSnakeOn()) {
//                            System.out.println("zajete: " + j + " , " + i);
                            if (dir * (snake.getHeadPosY() - i) >= snake.getHeadPosX() - j && dir * (snake.getHeadPosY() - i) > 1) {
                                //idz do gory
                                shouldGo.add(cells[snake.getHeadPosY() - dir][snake.getHeadPosX()]);
                                findWay(cells[snake.getHeadPosY() - dir][snake.getHeadPosX()]);
                                return true;
                            } else if (snake.getHeadPosX() - j > 1) {
                                //idz w lewo
                                shouldGo.add(cells[snake.getHeadPosY()][snake.getHeadPosX() - 1]);
                                findWay(cells[snake.getHeadPosY()][snake.getHeadPosX() - 1]);
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                    shouldGo.add(cells[snake.getHeadPosY()][snake.getHeadPosX() - 1]);
                    findWay(cells[snake.getHeadPosY()][snake.getHeadPosX() - 1]);
                    return true;
                } else {
                    for (int j = snake.getHeadPosX() + 1; j <= apple.getCell().getX(); j++) {
                        if (cells[snake.getHeadPosY()][j].isSnakeOn()) {
//                            System.out.println("zajete: " + j + " , " + i);
                            if (dir * (snake.getHeadPosY() - i) >= j - snake.getHeadPosX() && dir * (snake.getHeadPosY() - i) > 1) {
                                //idz do gory
                                shouldGo.add(cells[snake.getHeadPosY() - dir][snake.getHeadPosX()]);
                                findWay(cells[snake.getHeadPosY() - dir][snake.getHeadPosX()]);
                                return true;
                            } else if (j - snake.getHeadPosX() > 1) {
                                //idz w prawo
                                shouldGo.add(cells[snake.getHeadPosY()][snake.getHeadPosX() + 1]);
                                findWay(cells[snake.getHeadPosY()][snake.getHeadPosX() + 1]);
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                    shouldGo.add(cells[snake.getHeadPosY()][snake.getHeadPosX() + 1]);
                    findWay(cells[snake.getHeadPosY()][snake.getHeadPosX() + 1]);
                    return true;
                }
            }
        }
        shouldGo.add(cells[snake.getHeadPosY() - dir][snake.getHeadPosX()]);
        findWay(cells[snake.getHeadPosY() - dir][snake.getHeadPosX()]);
        return true;
    }

    private void startNewGame() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j].setSnakeOn(false);
            }
        }
        sumOfScores += snake.getScore();
        snake = new Snake(cells);
//        if (numGen == 800){
//            changeConstants();
//            numGen = 0;
//            highestScore = 0;
//            sumOfScores = 0;
//        }
        neuronNetwork.setSnake(snake);
        putApple();
    }

    private void changeConstants(){
        System.out.println("Dla a = "+ a + " , w = " + w +" , t = " + t + " najwyzszy wynik wynosi " + highestScore + " a suma " + sumOfScores);
        if (a == 2){
            a = 0;
            if (w == 2){
                w = 0;
                if (t == 2){
                    System.out.println("KONIEC!!!!!!!!");
                    return;
                } else{
                    t++;
                }
            } else{
                w++;
            }
        } else{
            a++;
        }
        neuronNetwork = new NeuronNetwork(this);
        neuronNetwork.changeConstants(a,t,w);
    }

    public void putApple() {
        while (true) {
            cell = snake.getCells()[random.nextInt(sizeOfField)][random.nextInt(sizeOfField)];
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

    public int getSumOfScores() {
        return sumOfScores;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }
}