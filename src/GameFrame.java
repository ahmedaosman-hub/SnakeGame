/*
 * Name:       Ahmed Osman
 *
 * Course:     Summer 2021
 *
 * Date:       08/10/2021
 *
 * Filename:   GameFrame.java
 *
 * Purpose:    Setting the frame of our snake game.
 */
import javax.swing.JFrame;

public class GameFrame extends JFrame{

    GameFrame(){


        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
