import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;

public class GameField implements ListsHolder{
    private Random random = new Random();
    private Cell cell;

    public GameField(){
        setCells();
        putApple();
    }

    public void putApple() {
        while (true) {
            cell = cells[random.nextInt(sizeOfField)][random.nextInt(sizeOfField)];
            if (!cell.isSnakeOn()){
                apple.setCell(cell);
                break;
            }
        }
    }

    private void setCells() {
        for (int i = 0; i < sizeOfField; i++){
            for (int j = 0; j < sizeOfField; j++){
                cells[i][j] = new Cell(j, i, 43 + j*500/sizeOfField, 128 + i*500/sizeOfField, 92 + j*500/sizeOfField, 177 + i*500/sizeOfField);
            }
        }
    }

    public void paint(Graphics g, ImageObserver o){
        apple.paint(g,o);
        snake.paint(g);
    }
}
