package src.view.menu;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import src.model.GameState;

public class MenuButton extends AbstractMenuButton {



    public MenuButton  (BufferedImage [] i, Rectangle b, GameState nS){
        this.mouseAwayImage = i[0];
        this.mouseOverImage = i[1];
        this.mousePressedImage = i[2];
  
        this.bounds = b;
        this.newState = nS;
    }

    //disegna le sue 3 immagini in base alla posizione del muouse
    public void draw(Graphics2D g2) {
        g2.drawImage(mouseAwayImage, (int)bounds.getX(), (int)bounds.getY(), null);
		if(mouseOver)
			g2.drawImage(mouseOverImage, (int)bounds.getX(), (int)bounds.getY(), null);
		if(mousePressed)
			g2.drawImage(mousePressedImage, (int)bounds.getX(), (int)bounds.getY(), null);	
    }

    @Override
    public void reactToMouse(MouseEvent e) {
        reactToEnter();
    }

    @Override
    public void reactToEnter() {
        //se siamo sul tasto iscirviti, va nell'avatar, solo se non è iniziata la parita
        if(newState == GameState.SELECT_AVATAR){
            if(!GameState.playStateInStandBy)   {
                GameState.actualState = newState;
            }
        }
        //se siamo sul tasto riprendi, va in play solo se la partita era iniziata
        else if (newState == GameState.PLAYING) {
            if(GameState.playStateInStandBy)   {
                GameState.actualState = newState;
            }
        }
        //se non sei in nessuno dei due bottoni, vai tranquillo
        else{
            GameState.actualState = newState;
        }
    }

    @Override
    public void reactToDrag(MouseEvent e) {
    }
}
