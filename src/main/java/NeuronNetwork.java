import java.util.ArrayList;
import java.util.Random;

public class NeuronNetwork implements ListsHolder {
    private ArrayList<Layer> layers = new ArrayList<>();
    private int[] sizes = {23, 18, 18, 3};
    private int input = 0;
    private Snake snake;
    private Apple apple;
    private Game game;
    private Random rand = new Random();

    public NeuronNetwork(Game game) {
        this.game = game;
        createLayers();
    }

    private void createLayers() {
        layers.add(new Layer(sizes[0]));
        for (int i = 1; i < sizes.length; i++) {
            layers.add(new Layer(sizes[i], layers.get(i - 1)));
        }
    }

    public void decide() {
        enterDataToFirstLayer();
        for (int i = 1; i < layers.size(); i++) {
            layers.get(i).proccess();
        }
        changeSnakesDirection();
    }

    private void changeSnakesDirection() {
        float j = layers.get(layers.size() - 1).neurons.get(0).getOutput();
        int k = 1;
        for (int i = 0; i < layers.get(layers.size() - 1).neurons.size(); i++) {
            if (layers.get(layers.size() - 1).neurons.get(i).getOutput() >= j) {
                j = layers.get(layers.size() - 1).neurons.get(i).getOutput();
                k = i;
            }
        }
        if (k == 0) {
            snake.turnLeft();
        } else if (k == 2) {
            snake.turnRight();
        }
    }

    private void enterDataToFirstLayer() {
        input = 0;
        lookLeftBackAndRightFront();
        input = 6;
        lookLeftFrontAndRightBack();
        input = 12;
        lookLeftAndRight();
        input = 18;
        lookFrontAndBack();
        input = 0;
    }

    private void lookFrontAndBack() {
        int xdir = snake.getxDir();
        int ydir = snake.getyDir();
        putDataIntoNetwork(xdir, ydir);
    }

    private void lookLeftAndRight() {
        int xdir = 0, ydir = 0;
        if (snake.getxDir() == 0) {
            if (snake.getyDir() == -1) {
                xdir = -1;
                ydir = 0;
            } else {
                xdir = 1;
                ydir = 0;
            }
        } else if (snake.getxDir() == -1) {
            xdir = 0;
            ydir = 1;
        } else if (snake.getxDir() == 1) {
            xdir = 0;
            ydir = -1;
        }
        putDataIntoNetwork(xdir, ydir);
        layers.get(0).neurons.get(input++).proccess(checkHowFarToTail(-xdir, -ydir));
    }

    private void lookLeftFrontAndRightBack() {
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
        putDataIntoNetwork(xdir, ydir);
        layers.get(0).neurons.get(input++).proccess(checkHowFarToTail(-xdir, -ydir));
    }

    private void lookLeftBackAndRightFront() {
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
        putDataIntoNetwork(xdir, ydir);
        layers.get(0).neurons.get(input++).proccess(checkHowFarToTail(-xdir, -ydir));
    }

    private void putDataIntoNetwork(int xdir, int ydir) {
        layers.get(0).neurons.get(input++).proccess(checkHowFarToWall(xdir, ydir));
        layers.get(0).neurons.get(input++).proccess(checkHowFarToApple(xdir, ydir));
        layers.get(0).neurons.get(input++).proccess(checkHowFarToTail(xdir, ydir));
        xdir *= -1;
        ydir *= -1;
        layers.get(0).neurons.get(input++).proccess(checkHowFarToWall(xdir, ydir));
        layers.get(0).neurons.get(input++).proccess(checkHowFarToApple(xdir, ydir));
    }

    private int checkHowFarToApple(int xdir, int ydir) {
        for (int i = 1; i < 10; i++) {
            if (snake.getHeadPosY() + i * ydir == sizeOfField || snake.getHeadPosY() + i * ydir == -1 ||
                    snake.getHeadPosX() + i * xdir == sizeOfField || snake.getHeadPosX() + i * xdir == -1) {
                break;
            } else {
                if (snake.getHeadPosY() + i * ydir == game.getApple().getCell().getY() && snake.getHeadPosX() + i * xdir == game.getApple().getCell().getX()) {
                    return (10 - i);
                }
            }
        }
        return 0;
    }

    private int checkHowFarToTail(int xdir, int ydir) {
        for (int i = 1; i < 10; i++) {
            if (snake.getHeadPosY() + i * ydir == sizeOfField || snake.getHeadPosY() + i * ydir == -1 || snake.getHeadPosX() + i * xdir == sizeOfField || snake.getHeadPosX() + i * xdir == -1) {
                break;
            } else {
                if (snake.getCells()[snake.getHeadPosY() + i * ydir][snake.getHeadPosX() + i * xdir].isSnakeOn()) {
                    return (10 - i);
                }
            }
        }
        return 0;
    }

    private int checkHowFarToWall(int xdir, int ydir) {
        for (int i = 1; i < 10; i++) {
            if (snake.getHeadPosX() + i * xdir == 0 || snake.getHeadPosX() + i * xdir == 9 ||
                    snake.getHeadPosY() + i * ydir == 0 || snake.getHeadPosY() + i * ydir == 9) {
                return (10 - i);
            }
        }
        return 0;
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