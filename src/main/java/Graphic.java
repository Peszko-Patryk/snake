import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graphic extends JPanel implements ListsHolder, ActionListener {
    private int numGen = 0;
    private int highestScore = 0;
    private int points = 0;
    private Timer timer = new Timer(200,this);
    Image snakeImage = Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\snake.png");


    public Graphic(){
        timer.start();
    }

    public void paint(Graphics g){
        g.fillRect(1,1,582,659);
        g.drawImage(snakeImage,0, 0, 584, 661, this);
        g.setColor(Color.WHITE);
        g.drawRect(42, 127, 500, 500);
        for (int i = 0; i < 10; i++){
            g.drawLine(92 + i*500/sizeOfField, 127, 92 + i*500/sizeOfField, 627);
            g.drawLine(42, 177 + i*500/sizeOfField, 542, 177 + i*500/sizeOfField);
        }
        paintStrings(g);
        game.paint(g,this);
    }

    private void paintStrings(Graphics g){
        g.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        g.drawString("Generacja: " + numGen, 10, 20);
        g.drawString("Pozostało: " + numGen + " węży w następnej generacji.", 10, 60);
        g.drawString("Najwyższy wynik: " + highestScore, 10, 100);
        g.drawString("Punkty: " + snake.getScore(), 400, 20);
        g.drawString("Pozostało: 100" + points + " ruchów", 400, 60);
        g.drawString("Prędkość węża: " + points, 400, 100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
