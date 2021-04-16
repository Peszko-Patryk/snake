import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyOperations implements ListsHolder, KeyListener {
    private Graphic graphic;

    public KeyOperations(Graphic graphic) {
        this.graphic = graphic;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER) {
            if (!graphic.isWatch() && bestGames.size() > 0) {
                graphic.setWatch(true);
            }
        }else if (keyCode == KeyEvent.VK_UP) {
            if (graphic.speed > 5) {
                graphic.speed -= 1;
            }
        } else if (keyCode == KeyEvent.VK_DOWN) {
            if (graphic.speed < 20) {
                graphic.speed += 1;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
