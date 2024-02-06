package src.view.menu;

import java.awt.Button;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.model.GameState;
import src.view.ViewUtils;
import static src.view.main.GamePanel.SCALE;

public class MainMenu {
    
    private MenuButton [] buttons;

    public MainMenu(){

        buttons = new MenuButton[1];
        createPlayButton();
        
    }
    
    //primo bottone : iscriviti
    private void createPlayButton() {
        //rettangolo
        int widthPlayButton = (int)(110*SCALE);
        int heightPlayButton = (int)(16*SCALE);
        int xPlayButton = ViewUtils.getCenteredXPos(widthPlayButton);
        int yPlayButton = 10;
        Rectangle playRectangle = new Rectangle(xPlayButton,yPlayButton,widthPlayButton,heightPlayButton);
        //immagini
        BufferedImage[] playImages = new BufferedImage[3];
        BufferedImage temp;
		try {
			temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/iscriviti1.png"));
			playImages[0] = ViewUtils.scaleImage(temp, widthPlayButton, heightPlayButton);
			temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/iscriviti2.png"));
			playImages[1] = ViewUtils.scaleImage(temp, widthPlayButton, heightPlayButton);
			temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/iscriviti3.png"));
			playImages[2] = ViewUtils.scaleImage(temp, widthPlayButton, heightPlayButton);	
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
        //creiamo bottone con rettangolo+immagini
        buttons[0] = new MenuButton(playImages, playRectangle, GameState.SELECT_AVATAR);
    }

    //disegna background del menu e bottoni 
    public void draw(Graphics2D g2) {

        for (MenuButton m: buttons)
            m.draw(g2);
    }
}
