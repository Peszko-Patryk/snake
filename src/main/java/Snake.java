import java.awt.*;
import java.util.ArrayList;

public class Snake implements ListsHolder {
    private int length = 2;
    private int xDir = 0;
    private int yDir = -1;
    private int score = 0;
    private int movesLeft = 25;
    private int getMoves = 25;

    public ArrayList<Cell> body = new ArrayList();

    public void move() {
        if (checkIfHitWall() || checkIfAteTail()) {
            game.endGame();
        } else {
            body.add(cells[body.get(body.size() - 1).getY() + yDir][body.get(body.size() - 1).getX() + xDir]);
            cells[body.get(body.size() - 1).getY()][body.get(body.size() - 1).getX()].setSnakeOn(true);
            body.get(0).setSnakeOn(false);
            body.remove(0);
            if (movesLeft == 0) {
                game.endGame();
            } else {
                movesLeft -= 1;
            }
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

    public boolean checkIfHitWall() {
        if (body.get(body.size() - 1).getY() + yDir < 0 || body.get(body.size() - 1).getY() + yDir > 9 || body.get(body.size() - 1).getX() + xDir < 0 || body.get(body.size() - 1).getX() + xDir > 9) {
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
        body.add(cells[6][5]);
        cells[6][5].setSnakeOn(true);
        body.add(cells[5][5]);
        cells[5][5].setSnakeOn(true);
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i < body.size(); i++) {
            body.get(i).paint(g);
        }
    }

    public void setScore() {
        score++;
        movesLeft += getMoves;
        length++;
        body.add(apple.getCell());
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

    public int getyDir() {
        return yDir;
    }
}
