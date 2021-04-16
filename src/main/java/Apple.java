import java.awt.*;
import java.awt.image.ImageObserver;

public class Apple implements ListsHolder {
    Image apple = Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\apple.png");
    private Cell cell = null;

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void paint(Graphics g, ImageObserver o) {
        if (cell != null) {
            g.drawImage(apple, cell.getStartX() - 10, cell.getStartY() - 10, (int) (500 / sizeOfField * 1.3), (int) (500 / sizeOfField * 1.3), o);
        }
    }
}
