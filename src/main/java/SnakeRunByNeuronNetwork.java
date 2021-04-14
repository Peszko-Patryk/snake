import javax.swing.*;

public class SnakeRunByNeuronNetwork {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        Graphic graphic = new Graphic();
//        FramePaint frame_paint = input_file.getFramePaint();
        frame.addKeyListener(new KeyOperations(graphic));
        frame.setSize(600,700);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Snake Run By Neuron Network");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(graphic);
    }
}
