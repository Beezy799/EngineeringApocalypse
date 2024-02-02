package src.view;

import javax.swing.JFrame;

public class GameWindow extends JFrame{
    
    public GameWindow(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("prova"); 
        setVisible(true);

    }
    
    
}
