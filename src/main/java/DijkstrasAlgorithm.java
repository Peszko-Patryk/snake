import java.util.ArrayList;

public class DijkstrasAlgorithm implements ListsHolder {
    private Cell head;
    private Cell apple;
    private Cell[][] cells;
    private Snake snake;
    private ArrayList<Cell> unvisited = new ArrayList<>();
    private int[][] tab;

    public DijkstrasAlgorithm(Snake snake, Cell[][] cells) {
        this.snake = snake;
        this.cells = cells;
        unvisited.clear();
        setUnvisited();
        searchRoutesToTheApple();
    }

    private void setUnvisited() {
        int xmin = 10, ymin = 10, xmax = 0, ymax = 0;
        for (Cell cell : snake.body) {
            if (cell.getX() > xmax) {
                xmax = cell.getX();
            } else if (cell.getX() < xmin) {
                xmin = cell.getX();
            }
            if (cell.getY() > ymax) {
                ymax = cell.getY();
            } else if (cell.getY() < ymin) {
                ymin = cell.getY();
            }
        }
        if (apple.getX() > xmax) {
            xmax = apple.getX();
        } else if (apple.getX() < xmin) {
            xmin = apple.getX();
        }
        if (apple.getY() > ymax) {
            ymax = apple.getY();
        } else if (apple.getY() < ymin) {
            ymin = apple.getY();
        }
        xmin = xmin > 0 ? xmin - 1 : xmin;
        xmax = xmax < 9 ? xmax + 1 : xmax;
        ymin = ymin > 0 ? ymin - 1 : ymin;
        ymax = ymax < 9 ? ymax + 1 : ymax;
        for (int i = xmin; i < xmax; i++) {
            for (int j = ymin; j < ymax; j++) {
                if (!cells[j][i].isSnakeOn()){
                    unvisited.add(cells[j][i]);
                }
            }
        }
        tab = new int[unvisited.size()][3];
    }

    private void searchRoutesToTheApple() {
        setIDsInTable();
    }

    private void setIDsInTable() {
        for (int i = 0; i< unvisited.size(); i++){
            tab[i][0] = unvisited.get(i).getX()*10 + unvisited.get(i).getY();
        }
    }
}