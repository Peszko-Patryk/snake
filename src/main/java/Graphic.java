import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graphic extends JPanel implements ListsHolder, ActionListener {
    private int numGen = 1;
    private int highestScore = 0;
    public int speed = 10;
    private boolean watch = false;
    private Timer timer = new Timer(20 * speed, this);
    Image snakeImage = Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\snake.png");

    public Graphic() {
        timer.start();
    }

    public void paint(Graphics g) {
        timer.setDelay(20 * speed);
        g.fillRect(1, 1, 582, 659);
        g.drawImage(snakeImage, 0, 0, 584, 661, this);
        g.setColor(Color.WHITE);
        g.drawRect(42, 127, 500, 500);
        for (int i = 0; i < sizeOfField; i++) {
            g.drawLine(42 + i * 500 / sizeOfField, 127, 42 + i * 500 / sizeOfField, 627);
            g.drawLine(42, 127 + i * 500 / sizeOfField, 542, 127 + i * 500 / sizeOfField);
        }
        if (bestGames.size() > 0 && watch) {
            if (bestGames.get(0).getSnake().isState()) {
                bestGames.get(0).paint(g, null);
            } else {
//                watch = false;
                if (bestGames.get(0).getSnake().getScore() > highestScore) {
                    highestScore = bestGames.get(0).getSnake().getScore();
                }
                bestGames.remove(0);
                numGen++;
            }
        }
        paintStrings(g);
    }

    private void paintStrings(Graphics g) {
        g.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        g.drawString("Generacja: " + numGen, 10, 20);
        g.drawString("Pozostało: " + numGen + " węży w następnej generacji.", 10, 60);
        g.drawString("Najwyższy wynik: " + highestScore, 10, 100);
        g.drawString("Punkty: " + (bestGames.size() > 0 ? bestGames.get(0).getSnake().getScore() : 0), 400, 20);
        g.drawString("Pozostało: " + (bestGames.size() > 0 ? bestGames.get(0).getSnake().getMovesLeft() : 0) + " ruchów", 400, 60);
        g.drawString("Prędkość węża: " + (10 - (speed - 10)), 400, 100);

        if (!watch && bestGames.size() > 0) {
            g.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            g.setColor(Color.RED);
            g.drawString("Naciśnij ENTER aby rozpocząć oglądanie", 20, 200);
            g.drawString("UP/DOWN zmienia prędkość węża", 70, 250);
        }
    }

    public boolean isWatch() {
        return watch;
    }

    public void setWatch(boolean watch) {
        this.watch = watch;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
