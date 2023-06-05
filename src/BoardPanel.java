import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BoardPanel extends JPanel implements ActionListener {

    private Image apple;
    private Image head;
    private Image dot;
    private int dotz;
    private boolean onGame=true;
    private boolean leftDirect=false;
    private boolean rightDirect=true;
    private boolean upDirect=false;
    private boolean downDirect=false;


    private final int DOT_SIZE=10;
    private final int ALL_DOT=900;
    private final int RANDOM_POS=29;
    private final int x[]=new int[ALL_DOT];
    private final int y[]=new int[ALL_DOT];
    private int apple_Xaxis;
    private int apple_Yaxis;

    private Timer timing;

    public BoardPanel(){
        addKeyListener(new TAdapter());
        setBackground(Color.blue);
        setPreferredSize(new Dimension(300, 300));
        setFocusable(true);
        loadImages();
        initializeGame();
    }

    public void loadImages(){
        ImageIcon img1= new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
        apple=img1.getImage();
        ImageIcon img2= new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
        head=img2.getImage();
        ImageIcon img3= new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        dot=img3.getImage();
    }

    public void initializeGame(){
        dotz=3;
        for(int i=0; i<dotz; i++) {
            x[i] = 50 - i * DOT_SIZE;
            y[i] = 50;
        }
            locatingApple();
            timing =new Timer(140, this);
            timing.start();


    }

    public void locatingApple(){
        int rand=(int)(Math.random()*RANDOM_POS);
        apple_Xaxis=(rand*DOT_SIZE);
        rand=(int)(Math.random()*RANDOM_POS);
        apple_Yaxis=(rand*DOT_SIZE);
    }

    public void checkAppleEaten(){
        if(x[0]==apple_Xaxis && y[0]==apple_Yaxis){
            dotz++;
            locatingApple();
        }
    }

    public void draw(Graphics g){
        if(onGame){
            g.drawImage(apple, apple_Xaxis, apple_Yaxis, this);

            for(int i=0; i<dotz; i++){
                if(i==0){
                    g.drawImage(head, x[i], y[i], this);
                }
                else{
                    g.drawImage(dot, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
    }

    public void gameOver(Graphics g){
        String message="GAME OVER";
        Font font= new Font("Ariel", Font.BOLD, 14);
        FontMetrics metrics =getFontMetrics(font);

        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString(message, (300-metrics.stringWidth(message)) / 2, 300/2);
    }

    public void moveAction(){
        for(int i=dotz; i>0; i--){
            x[i]=x[i-1];
            y[i]=y[i-1];

        }

        if(leftDirect){
            x[0]=x[0]-DOT_SIZE;
        }

        if(rightDirect){
            x[0]=x[0]+DOT_SIZE;
        }

        if(upDirect){
            y[0]=y[0]-DOT_SIZE;
        }

        if(downDirect){
            y[0]=y[0]+DOT_SIZE;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void checkingCollision(){
        for(int i=dotz; i>0; i--){
            if((i>4) && x[0]==x[i] && (y[0]==y[i])){
                onGame=false;
            }
        }
        if(y[0]>=300){
            onGame=false;
        }
        if(x[0]>=300){
            onGame=false;
        }
        if(y[0]<0){
            onGame=false;
        }
        if(x[0]<0){
            onGame=false;
        }
        if (!onGame){
            timing.stop();
        }
    }

    public void actionPerformed(ActionEvent ae){
        if(onGame){
            checkAppleEaten();
            checkingCollision();
            moveAction();
        }
        repaint();
    }
    private class TAdapter extends KeyAdapter{

        public void keyPressed(KeyEvent e){
            int key=e.getKeyCode();
            if (key==KeyEvent.VK_LEFT && (!rightDirect)){
                leftDirect=true;
                upDirect=false;
                downDirect=false;

            }

            if (key==KeyEvent.VK_RIGHT && (!leftDirect)){
                rightDirect=true;
                upDirect=false;
                downDirect=false;

            }
            if (key==KeyEvent.VK_UP && (!downDirect)){
                upDirect=true;
                leftDirect=false;
                rightDirect=false;

            }
            if (key==KeyEvent.VK_DOWN && (!upDirect)){
                downDirect=true;
                leftDirect=false;
                rightDirect=false;
            }

        }
    }
}
