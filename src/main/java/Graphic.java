import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graphic extends JPanel implements ListsHolder, ActionListener {
    private int numGen = 0;
    private int highestScore = 0;
    private int points = 0;
    public int speed = 10;
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
        for (int i = 0; i < 10; i++) {
            g.drawLine(92 + i * 500 / sizeOfField, 127, 92 + i * 500 / sizeOfField, 627);
            g.drawLine(42, 177 + i * 500 / sizeOfField, 542, 177 + i * 500 / sizeOfField);
        }
        game.paint(g, this);
        paintStrings(g);
    }

    private void paintStrings(Graphics g) {
        g.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        g.drawString("Generacja: " + numGen, 10, 20);
        g.drawString("Pozostało: " + numGen + " węży w następnej generacji.", 10, 60);
        g.drawString("Najwyższy wynik: " + highestScore, 10, 100);
        g.drawString("Punkty: " + snake.getScore(), 400, 20);
        g.drawString("Pozostało: " + snake.getMovesLeft() + " ruchów", 400, 60);
        g.drawString("Prędkość węża: " + (10 - (speed - 10)), 400, 100);

        if (!game.isState()) {
            g.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            g.setColor(Color.RED);
            g.drawString("Naciśnij ENTER aby rozpocząć grę", 70, 200);
            g.drawString("UP/DOWN zmienia prędkość węża", 70, 250);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
