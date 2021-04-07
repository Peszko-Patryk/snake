import java.awt.*;
import java.awt.image.ImageObserver;

public class Game implements ListsHolder {

    private boolean state = false;

    public Game(){
        snake.setBody();
    }

    public void paint(Graphics g, ImageObserver o){
        if (state) {
            snake.move();
        }
        snake.paint(g);
        if(checkIfSnakeAteApple()){
            relocateApple();
        }
        apple.paint(g, o);
    }

    private void relocateApple() {
        gamefield.putApple();
    }

    private boolean checkIfSnakeAteApple() {
        if (snake.body.get(snake.body.size() - 1).getX() == apple.getCell().getX()){
            if (snake.body.get(snake.body.size() - 1).getY() == apple.getCell().getY()){
                snake.setScore();
                return true;
            }
        }
        return false;
    }

    public void endGame(){
        setState(false);
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state){
        this.state = state;
    }
}