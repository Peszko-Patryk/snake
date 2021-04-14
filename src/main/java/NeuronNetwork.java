import java.util.ArrayList;

public class NeuronNetwork implements ListsHolder {
    private ArrayList<Layer> layers = new ArrayList<>();
    private int[] sizes = {23, 18, 18, 3};

    public NeuronNetwork() {
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
        float j = -1000000000;
        int k = 1;
        for (int i = 0; i < layers.get(layers.size() - 1).neurons.size(); i++) {
            if (layers.get(layers.size() - 1).neurons.get(i).getOutput() > j) {
                j = layers.get(layers.size() - 1).neurons.get(i).getOutput();
                k = i;
            }
        }
        if (j == 0) {
            snake.turnLeft();
        } else if (j == 2) {
            snake.turnRight();
        }
        snake.move();
    }

    private void enterDataToFirstLayer() {
        lookLeftBackAndRightFront();
        lookLeftFrontAndRightBack();
        lookLeftAndRight();
        lookFrontAndBack();
    }

    private void lookFrontAndBack() {
        int xdir = snake.getxDir();
        int ydir = snake.getyDir();
        layers.get(0).neurons.get(18).proccess(checkHowFarToWall(xdir, ydir));
        layers.get(0).neurons.get(19).proccess(checkHowFarToTail(xdir, ydir));
        layers.get(0).neurons.get(20).proccess(checkHowFarToApple(xdir, ydir));
        xdir *= -1;
        ydir *= -1;
        layers.get(0).neurons.get(21).proccess(checkHowFarToWall(xdir, ydir));
        layers.get(0).neurons.get(22).proccess(checkHowFarToApple(xdir, ydir));
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
        layers.get(0).neurons.get(12).proccess(checkHowFarToWall(xdir, ydir));
        layers.get(0).neurons.get(13).proccess(checkHowFarToTail(xdir, ydir));
        layers.get(0).neurons.get(14).proccess(checkHowFarToApple(xdir, ydir));
        xdir *= -1;
        ydir *= -1;
        layers.get(0).neurons.get(15).proccess(checkHowFarToWall(xdir, ydir));
        layers.get(0).neurons.get(16).proccess(checkHowFarToTail(xdir, ydir));
        layers.get(0).neurons.get(17).proccess(checkHowFarToApple(xdir, ydir));
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
        layers.get(0).neurons.get(6).proccess(checkHowFarToWall(xdir, ydir));
        layers.get(0).neurons.get(7).proccess(checkHowFarToTail(xdir, ydir));
        layers.get(0).neurons.get(8).proccess(checkHowFarToApple(xdir, ydir));
        xdir *= -1;
        ydir *= -1;
        layers.get(0).neurons.get(9).proccess(checkHowFarToWall(xdir, ydir));
        layers.get(0).neurons.get(10).proccess(checkHowFarToTail(xdir, ydir));
        layers.get(0).neurons.get(11).proccess(checkHowFarToApple(xdir, ydir));
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
        layers.get(0).neurons.get(0).proccess(checkHowFarToWall(xdir, ydir));
        layers.get(0).neurons.get(1).proccess(checkHowFarToTail(xdir, ydir));
        layers.get(0).neurons.get(2).proccess(checkHowFarToApple(xdir, ydir));
        xdir *= -1;
        ydir *= -1;
        layers.get(0).neurons.get(3).proccess(checkHowFarToWall(xdir, ydir));
        layers.get(0).neurons.get(4).proccess(checkHowFarToTail(xdir, ydir));
        layers.get(0).neurons.get(5).proccess(checkHowFarToApple(xdir, ydir));
    }

    private int checkHowFarToApple(int xdir, int ydir) {
        for (int i = 1; i < 10; i++) {
            if (snake.getHeadPosY() + i * ydir == 10 || snake.getHeadPosY() + i * ydir == -1 ||
                    snake.getHeadPosX() + i * xdir == 10 || snake.getHeadPosX() + i * xdir == -1) {
                break;
            } else {
                if (snake.getHeadPosY() + i * ydir == apple.getCell().getY() && snake.getHeadPosX() + i * xdir == apple.getCell().getX()) {
                    return (10 - i);
                }
            }
        }
        return 0;
    }

    private int checkHowFarToTail(int xdir, int ydir) {
        for (int i = 1; i < 10; i++) {
            if (snake.getHeadPosY() + i * ydir == 10 || snake.getHeadPosY() + i * ydir == -1 ||
                    snake.getHeadPosX() + i * xdir == 10 || snake.getHeadPosX() + i * xdir == -1) {
                break;
            } else {
                if (cells[snake.getHeadPosY() + i * ydir][snake.getHeadPosX() + i * xdir].isSnakeOn()) {
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
}