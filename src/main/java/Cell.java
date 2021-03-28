import java.awt.*;

public class Cell {
    private final int x;
    private final int y;
    private final int startX;
    private final int startY;
    private final int stopX;
    private final int stopY;
    private boolean snakeOn = false;

    public Cell(int x, int y,int startX, int startY, int stopX, int stopY) {
        this.x = x;
        this.y = y;
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isSnakeOn() {
        return snakeOn;
    }

    public void setSnakeOn(boolean snakeOn) {
        this.snakeOn = snakeOn;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getStopX() {
        return stopX;
    }

    public int getStopY() {
        return stopY;
    }

    public void paint(Graphics g) throws NullPointerException{
        g.fillRect(startX, startY, stopX-startX,stopY-startY);
    }
}
