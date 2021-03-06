import java.awt.*;
import java.util.ArrayList;

public class Snake implements ListsHolder {
    private int length = 5;
    private int xDir = 0;
    private int yDir = -1;
    private int score = 0;
    private int movesLeft = 200;
    private int movesDone = 0;
    private int getMoves = 50;
    private int headPosX = 5;
    private int headPosY = 5;
    private boolean state = true;
    private Cell[][] cells;

    public ArrayList<Cell> body = new ArrayList();

    public Snake(Cell[][] cells) {
        this.cells = cells;
        setBody();
    }

    public boolean move() {
        if (checkIfHitWall() || checkIfAteTail()) {
            state = false;
            return false;
        } else {
            headPosX = body.get(body.size() - 1).getX() + xDir;
            headPosY = body.get(body.size() - 1).getY() + yDir;
            body.add(cells[headPosY][headPosX]);
            cells[body.get(body.size() - 1).getY()][body.get(body.size() - 1).getX()].setSnakeOn(true);
            body.get(0).setSnakeOn(false);
            body.remove(0);
            if (movesLeft == 0) {
                return false;
            } else {
                movesLeft--;
                movesDone++;
            }
            return true;
        }
    }

    private boolean checkIfAteTail() {
        Cell cell;
        for (int i = 0; i < length - 3; i++) {
            cell = body.get(i);
            if ((body.get(length - 1).getY() + yDir) == cell.getY() && (body.get(length - 1).getX() + xDir) == cell.getX()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfHitWall() {
        if (body.get(body.size() - 1).getY() + yDir < 0 || body.get(body.size() - 1).getY() + yDir >= sizeOfField ||
                body.get(body.size() - 1).getX() + xDir < 0 || body.get(body.size() - 1).getX() + xDir >= sizeOfField) {
            return true;
        }
        return false;
    }

    public void turnLeft() {
        if (yDir == -1) {
            yDir = 0;
            xDir = -1;
        } else if (yDir == 1) {
            yDir = 0;
            xDir = 1;
        } else if (xDir == -1) {
            yDir = 1;
            xDir = 0;
        } else if (xDir == 1) {
            yDir = -1;
            xDir = 0;
        }
    }

    public void turnRight() {
        if (yDir == -1) {
            yDir = 0;
            xDir = 1;
        } else if (yDir == 1) {
            yDir = 0;
            xDir = -1;
        } else if (xDir == -1) {
            yDir = -1;
            xDir = 0;
        } else if (xDir == 1) {
            yDir = 1;
            xDir = 0;
        }
    }

    public void setBody() {
        body.add(cells[7][5]);
        cells[7][5].setSnakeOn(true);
        body.add(cells[6][5]);
        cells[6][5].setSnakeOn(true);
        body.add(cells[5][5]);
        cells[5][5].setSnakeOn(true);
        body.add(cells[4][5]);
        cells[4][5].setSnakeOn(true);
        length = body.size();
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i < body.size(); i++) {
            body.get(i).paint(g);
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setScore() {
        score++;
        movesLeft += getMoves;
        length++;
        body.add(body.get(body.size() - 1));
    }

    public boolean isState() {
        return state;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public int getScore() {
        return score;
    }

    public int getxDir() {
        return xDir;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getyDir() {
        return yDir;
    }

    public int getHeadPosX() {
        return headPosX;
    }

    public void setHeadPosX(int headPosX) {
        this.headPosX = headPosX;
    }

    public void setHeadPosY(int headPosY) {
        this.headPosY = headPosY;
    }

    public int getHeadPosY() {
        return headPosY;
    }
}