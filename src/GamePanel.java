/*
 * Name:       Ahmed Osman
 *
 * Course:     Summer 2021
 *
 * Date:       08/10/2021
 *
 * Filename:   GamePanel.java
 *
 * Purpose:    Defines all the game aspects of our Snake Game.
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;            // Sets the game's width
    static final int SCREEN_HEIGHT = 600;           // Sets the game's height
    static final int UNIT_SIZE = 25;                // Sets the size of apples
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;           // Sets the size of snake
    static final int DELAY = 75;            // Sets the time delay of game
    final int x[] = new int[GAME_UNITS];            // Sets the x coordinates of the snake
    final int y[] = new int[GAME_UNITS];            // Sets the y coordinates of the snake
    int bodyParts = 6;          // Sets the body parts of the snake
    int applesEaten;            // Apples eaten in beginning of the game
    int appleX;                 // X coordinate of apple located
    int appleY;                 // Y coordinate of apple located
    char direction = 'R';           // Sets the direction of snake moving in start of game
    boolean running = false;        // Defines if the game is running
    Timer timer;                    // Helps set the speed of our game
    Random random;                  // Declaring a random variable

    // Method for creating the panel of the game
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));          // Size of panel
        this.setBackground(Color.white);            // Color of background
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }

    // Method for starting the game
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }

    // Draws everything in our game
    public void draw(Graphics g){

        if(running){
        // Sets grid to allow easier play
            /*for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++){
                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0,i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
             */
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        // Draws the snake's head and body
            for(int i = 0; i < bodyParts; i++){
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(45, 180, 0));
                    // g.setColor(new Color(random.nextInt(255)));         //Displays different color snakes
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            // Sets the Score feature of our game.
            g.setColor(Color.black);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }
    }

    // Generates new Apples for game
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;


    }

    // Moves our snake
    public void move(){
        for(int i = bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        // Allows the snake to switch directions
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    // Checks if apple is eaten and adds body part to snake
    public void checkApple(){
        if ((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }

    // Checks if our snake has collided 
    public void checkCollisions() {
        // Checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        // checks if head hits left border
        if (x[0] < 0) {
            running = false;
        }
        // checks if head hits right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // checks if head hits bottom border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // checks if head hits upper border
        if (y[0] < 0) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        // Displays the Game Over
        g.setColor(Color.black);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        // Displays the Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if (running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
