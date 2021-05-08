import java.util.ArrayList;
import java.util.Random;

public class NeuronNetwork implements ListsHolder {
    private ArrayList<Layer> layers = new ArrayList<>();
    private int[] sizes = {13, 8, 3};
    private int input = 0;
    private Snake snake;
    private Apple apple;
    private Game game;
    public int lastMove;
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

    public void clearErrors() {
        for (Layer layer : layers) {
            for (Neuron neuron : layer.neurons) {
                neuron.clearError();
            }
        }
    }

    public void countErrors(int expected) {
        for (Neuron neuron : layers.get(layers.size() - 1).neurons) {
//            neuron.setError((float) Math.pow(neuron.getOutput() -0.5, 2));
            neuron.setError(0.5 - neuron.getOutput());
//            System.out.println(neuron.factors.get(0));
        }
        layers.get(layers.size() - 1).neurons.get(expected + 1).setError(1 - layers.get(layers.size() - 1).neurons.get(expected + 1).getOutput());
        for (int i = layers.size() - 1; i > 0; i--) {
            layers.get(i).passError();
        }
    }

    public void changeFactors() {
        for (int i = layers.size() - 1; i >= 1; i--) {
            for (Neuron neuron : layers.get(i).neurons) {
                for (int j = 0; j < neuron.factors.size(); j++) {
                    neuron.factors.set(j, neuron.factors.get(j) + (learningRate * neuron.getError() * neuron.delivers.get(j).getOutput()) / neuron.factors.get(j));
                }
            }
        }
        for (Neuron neuron : layers.get(0).neurons) {
            neuron.setFactor(neuron.getFactor() + learningRate * neuron.getError() * neuron.getInput() / neuron.getFactor());
        }
//        System.out.println(layers.get(0).neurons.get(0).getFactor());
    }

    private void changeSnakesDirection() {
//        System.out.println(layers.get(3).neurons.get(0).factors);
        lastMove = 0;
        double j = layers.get(layers.size() - 1).neurons.get(0).getOutput();
        int k = 1;
        for (int i = 0; i < layers.get(layers.size() - 1).neurons.size(); i++) {
            if (layers.get(layers.size() - 1).neurons.get(i).getOutput() >= j) {
                j = layers.get(layers.size() - 1).neurons.get(i).getOutput();
                k = i;
            }
        }
        if (k == 0) {
            lastMove = -1;
            snake.turnLeft();
        } else if (k == 2) {
            lastMove = 1;
            snake.turnRight();
        }
    }

    private void enterDataToFirstLayer() {
        input = 0;
        lookLeftBackAndRightFront();
        lookLeftFrontAndRightBack();
        lookLeftAndRight();
        lookFront();
    }

    private void lookFront() {
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
        xdir *= -1;
        ydir *= -1;
        putDataIntoNetwork(xdir, ydir);
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
        layers.get(0).neurons.get(input).setInput(checkIfAppleInLine(xdir, ydir));
        layers.get(0).neurons.get(input++).proccess(0);
        xdir *= -1;
        ydir *= -1;
        layers.get(0).neurons.get(input).setInput(checkIfAppleInLine(xdir, ydir));
        layers.get(0).neurons.get(input++).proccess(0);
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
        layers.get(0).neurons.get(input).setInput(checkIfAppleInLine(xdir, ydir));
        layers.get(0).neurons.get(input++).proccess(0);
        xdir *= -1;
        ydir *= -1;
        layers.get(0).neurons.get(input).setInput(checkIfAppleInLine(xdir, ydir));
        layers.get(0).neurons.get(input++).proccess(0);
    }

    public int checkIfAppleInLine(int xdir, int ydir) {
        for (int i = 1; i < 10; i++) {
            if (snake.getHeadPosY() + i * ydir == sizeOfField || snake.getHeadPosY() + i * ydir == -1 ||
                    snake.getHeadPosX() + i * xdir == sizeOfField || snake.getHeadPosX() + i * xdir == -1) {
                break;
            } else {
                if (snake.getHeadPosY() + i * ydir == game.getApple().getCell().getY() && snake.getHeadPosX() + i * xdir == game.getApple().getCell().getX()) {
                    return 1;
                }
            }
        }
        return 0;
    }

    private void putDataIntoNetwork(int xdir, int ydir) {
        layers.get(0).neurons.get(input).setInput(checkIfAppleInLine(xdir, ydir));
        layers.get(0).neurons.get(input++).proccess(0);
        layers.get(0).neurons.get(input).setInput(checkIfWallIsThere(xdir, ydir));
        layers.get(0).neurons.get(input++).proccess(0);
        layers.get(0).neurons.get(input).setInput(checkIfTaileIsThere(xdir, ydir));
        layers.get(0).neurons.get(input++).proccess(0);
    }

    private int checkIfTaileIsThere(int xdir, int ydir) {
        if (snake.getHeadPosY() + ydir == sizeOfField || snake.getHeadPosY() + ydir == -1 || snake.getHeadPosX() + xdir == sizeOfField || snake.getHeadPosX() + xdir == -1) {
            return 0;
        } else {
            if (snake.getCells()[snake.getHeadPosY() + ydir][snake.getHeadPosX() + xdir].isSnakeOn()) {
                return 1;
            }
        }
        return 0;
    }

    private int checkIfWallIsThere(int xdir, int ydir) {
        if (snake.getHeadPosX() + xdir == -1 || snake.getHeadPosX() + xdir == sizeOfField ||
                snake.getHeadPosY() + ydir == -1 || snake.getHeadPosY() + ydir == sizeOfField) {
            return 1;
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