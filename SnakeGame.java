

import javax.swing.*;
import java.awt.*;

public class SnakeGame {
    JFrame frame;
    SnakeGame(){
        frame = new JFrame("Snake game");
        frame.setBounds(10,10,905,780);
        GameBoard panel = new GameBoard();
        panel.setBackground(Color.gray);
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SnakeGame();
    }
}
