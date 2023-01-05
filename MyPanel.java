import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MyPanel extends JPanel implements ActionListener, KeyListener {

    ImageIcon snakeIcon = new ImageIcon(getClass().getResource( "snaketitle.jpg"));
    ImageIcon right = new ImageIcon(getClass().getResource("ajaysmall.jpg"));
    ImageIcon snakeBody = new ImageIcon(getClass().getResource("snakeimage.png"));

    ImageIcon up = new ImageIcon(getClass().getResource("ajaysmall.jpg"));
    ImageIcon down = new ImageIcon(getClass().getResource("ajaysmall.jpg"));
    ImageIcon left = new ImageIcon(getClass().getResource("ajaysmall.jpg"));
    ImageIcon food = new ImageIcon(getClass().getResource("thiru.jpeg"));


    int snakeX [] = new int[750];
    int snakeY [] = new int[750];
    int move =0;
    int snakeLength = 3;
    int score =0;

    boolean isUp = false;
    boolean isdown = false;
    boolean isright = true;
    boolean isleft = false;

    int[] xpos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int[] ypos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    Random random = new Random();
    int foodx = 150;
    int foody = 150;
    Timer time;
    boolean gameover = false;

    MyPanel(){
        addKeyListener(this);
        setFocusable(true);
         time=new Timer(100,this);
        time.start();
    }

    public void paint(Graphics g){
        g.setColor(Color.white);
        g.drawRect(24 ,10 ,851, 55);
        g.drawRect(24 ,74 ,851, 576);
        snakeIcon.paintIcon(this,g,25,11);
        g.setColor(Color.black);
        g.fillRect(25,75,850,575);

//        super.paint(g);

        if(move==0){
            snakeX[0] = 100;
            snakeX[1] = 75;
            snakeX[2] = 50;

            snakeY[0] = 100;
            snakeY[1] = 100;
            snakeY[2] = 100;
        }
        if(isUp)
            up.paintIcon(this,g,snakeX[0],snakeY[0]);
        if(isdown)
            down.paintIcon(this,g,snakeX[0],snakeY[0]);
        if(isright)
            right.paintIcon(this,g,snakeX[0],snakeY[0]);
        if(isleft)
            left.paintIcon(this,g,snakeX[0],snakeY[0]);

        for(int i=1;i<snakeLength;i++)
            snakeBody.paintIcon(this,g,snakeX[i],snakeY[i]);

        food.paintIcon(this,g,foodx,foody);
        if(gameover){
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
            g.drawString("GAME OVER" , 300,300);
            g.setFont(new Font(Font.SANS_SERIF,Font.ROMAN_BASELINE,20));
            g.drawString("PRESS SPACE TO RESTART",330,360);
        }
        g.setColor(Color.green);
        g.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        g.drawString("SCORE" + score,700,30);
        g.drawString("length"+ snakeLength,700,50);
        g.dispose();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=snakeLength-1;i>0;i--){
            snakeX[i] = snakeX[i-1];
            snakeY[i] = snakeY[i-1];
        }
        if(isleft)
            snakeX[0] = snakeX[0]-25;
        if(isright)
            snakeX[0] = snakeX[0]+25;
        if(isUp)
            snakeY[0] = snakeY[0]-25;
        if(isdown)
            snakeY[0] = snakeY[0]+25;

        if(snakeX[0]>850) snakeX[0]=25;
        if(snakeX[0]<25) snakeX[0]=850;
        if(snakeY[0]>625) snakeY[0]=75;
        if(snakeY[0]<75)   snakeY[0]=625;

        collisionWithFood();
        collisionWithBody();
        repaint();

    }

    private void collisionWithFood() {
        if(snakeX[0]==foodx && snakeY[0]==foody){
            newfood();
            snakeLength++;
            score++;
//            snakeX[snakeLength-2] = snakeX[snakeLength-1];
//            snakeY[snakeLength-2] = snakeY[snakeLength-1];
        }
    }
    void collisionWithBody(){
        for(int i=snakeLength-1;i>0;i--){
            if(snakeX[i]==snakeX[0]&& snakeY[i] == snakeY[0]){
                time.stop();
                score=0;
                snakeLength = 3;
                gameover=true;
            }
        }
    }

    private void newfood() {
        foodx = xpos[random.nextInt(xpos.length-1)];
        foody = ypos[random.nextInt(ypos.length-1)];
        for(int i=snakeLength-1;i>0;i--)
            if(foodx==snakeX[i] && foody==snakeY[i])
                newfood();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE && gameover)
            restart();
        if(e.getKeyCode()==KeyEvent.VK_UP&& (!isdown)){
            isUp = true;
            isdown = false;
            isright = false;
            isleft = false;
            move++;
        } if(e.getKeyCode()==KeyEvent.VK_DOWN && (!isUp)){
            isUp = false;
            isdown = true;
            isright = false;
            isleft = false;
            move++;
        } if(e.getKeyCode()==KeyEvent.VK_LEFT && (!isright)){
            isUp = false;
            isdown = false;
            isright = false;
            isleft = true;
            move++;
        } if(e.getKeyCode()==KeyEvent.VK_RIGHT  && (!isleft)){
            isUp = false;
            isdown = false;
            isright = true;
            isleft = false;
            move++;
        }
    }

    private void restart() {
        gameover =false;
            move=0;
        snakeLength = 3;
        isleft = false;
        isright = true;
        isUp = false;
        isdown = false;
        time.start();
        newfood();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
