package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;

import static java.awt.event.KeyEvent.*;

public class BoardPanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;


    static final int UNIT_SIZE = 25;
    static final int NUM_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    int Delay = 80;

    final int[] snakePosXArray = new int[NUM_UNITS];
    final int[] snakePosYArray = new int[NUM_UNITS];
   


    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean gameRunning = false;
    Timer timer;
    Random random;



   BoardPanel(){
       random = new Random();
       this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
       this.setBackground(Color.white);
       this.setFocusable(true);
       this.requestFocus();
       this.addKeyListener(new MyKeyAdapter());
       this.setDoubleBuffered(true);
       startGame();

   }

   public void startGame() {
       gameRunning = true;
       newApple();
       timer = new Timer(Delay,this);
       timer.start();

   }
    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }
   public void draw(Graphics graphic) {

       if (gameRunning) {
           //Draw an apple
           graphic.setColor(Color.red);
           graphic.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
           //Draw snake
           for (int i = 0; i < bodyParts; i++) {
               if (i == 0) {
                   graphic.setColor(new Color(45, 170, 0));
                   graphic.fillRect(snakePosXArray[i], snakePosYArray[i], UNIT_SIZE, UNIT_SIZE);
               } else {
                   graphic.setColor(new Color(45, 125, 0));
                   graphic.fillRect(snakePosXArray[i], snakePosYArray[i], UNIT_SIZE, UNIT_SIZE);
               }
           }
           graphic.setColor(Color.black);
           graphic.setFont(new Font("Calibri",Font.BOLD,20));
           FontMetrics fontMetrics = getFontMetrics(graphic.getFont());
           graphic.drawString("Score: "+applesEaten,(SCREEN_WIDTH-fontMetrics.stringWidth("Score"+applesEaten))/2,graphic.getFont().getSize());

       }
       else {
           endGame(graphic);
       }
   }

   public void newApple(){
    appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
    appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }
   public void moveSnake() {
       for(int i = bodyParts; i>0;i--) {
           snakePosXArray[i] = snakePosXArray[i-1];
           snakePosYArray[i] = snakePosYArray[i-1];
       }
       switch (direction) {
           case 'U' -> snakePosYArray[0] = snakePosYArray[0] - UNIT_SIZE;
           case 'D' -> snakePosYArray[0] = snakePosYArray[0] + UNIT_SIZE;
           case 'L' -> snakePosXArray[0] = snakePosXArray[0] - UNIT_SIZE;
           case 'R' -> snakePosXArray[0] = snakePosXArray[0] + UNIT_SIZE;
       }

   }
   public void checkApple() {
       if((snakePosXArray[0] == appleX)&&snakePosYArray[0] == appleY){
           bodyParts++;
           if (Delay>40){
               Delay = Delay-5;
           }
           applesEaten++;
           newApple();
       }

   }
   public void checkCollisions(){
       //Checks for head colliding with body
       for (int i = bodyParts; i>0; i--){
           if ((snakePosXArray[0] == snakePosXArray[i]) && (snakePosYArray[0] == snakePosYArray[i])) {
               gameRunning = false;
               break;
           }
       }
       //check for left border collision
       if(snakePosXArray[0]<0 ){
           gameRunning = false;
       }
       //check for right border collision
       if(snakePosXArray[0]> SCREEN_WIDTH){
           gameRunning = false;
       }
       //check top border collision
       if(snakePosYArray[0]< 0){
           gameRunning = false;
       }
       //check bottom border collision
       if(snakePosYArray[0]> SCREEN_HEIGHT){
           gameRunning = false;
       }
       if (!gameRunning){
           timer.stop();
       }

   }
   public void endGame(Graphics graphics){
       graphics.setColor(Color.cyan);
       graphics.setFont(new Font("Calibri",Font.BOLD,75));
       FontMetrics fontMetrics = getFontMetrics(graphics.getFont());
       graphics.drawString("Game Over",(SCREEN_WIDTH-fontMetrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
   }
    @Override
    public void actionPerformed(ActionEvent event) {
       if(gameRunning){
           moveSnake();
           checkApple();
           checkCollisions();
       }
       repaint();
    }
    public class MyKeyAdapter extends KeyAdapter {
       @Override
       public void keyPressed(KeyEvent event){

           switch(event.getKeyCode()) {

               case VK_W:
                   if (direction != 'D' && snakePosYArray[0] < 300) {
                       direction = 'U';
                   }
                   break;
               case VK_S:
                   if (direction != 'U' && snakePosYArray[0] < 300) {
                       direction = 'D';
                   }
                   break;
               case VK_D:
                   if (direction != 'L' && snakePosYArray[0] < 300) {
                       direction = 'R';
                   }
                   break;
               case VK_A:
                   if(direction != 'R' && (snakePosYArray[0]  < 300)) {
                       direction = 'L';
                   }
                   break;
               case VK_LEFT:

                   if(direction != 'R' && snakePosYArray[0] > 300){
                       direction = 'L';
                   }
                   break;
               case VK_RIGHT:
                   if (direction != 'L' && snakePosYArray[0] > 300) {
                       direction = 'R';
                   }
                   break;
               case VK_DOWN:
                   if (direction != 'U' && snakePosYArray[0] > 300) {
                       direction = 'D';
                   }
                   break;
               case VK_UP:
                   if (direction != 'D' && snakePosYArray[0] > 300) {
                       direction = 'U';
                   }
                   break;
               case VK_ENTER:
                   try {
                       new GameWindow();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }


           }
           }




        }

    }








