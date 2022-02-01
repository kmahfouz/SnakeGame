package SnakeGame;

import javax.swing.*;
import java.io.IOException;

public class GameWindow extends JFrame {
    GameWindow() throws IOException {
            this.add(new BoardPanel());
            this.setTitle("Snake");
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setResizable(false);
            this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }

}