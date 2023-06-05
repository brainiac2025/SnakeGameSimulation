import javax.swing.*;
public class Main extends JFrame {

    public Main(){
        add(new BoardPanel());
        pack();
        // setting the window to center of the screen
        setLocationRelativeTo(null);
        //setting the window not to be maximize
        setResizable(false);
        //setting the title of the window
        setTitle("Snake Game");
    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}

