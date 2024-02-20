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
        GameState.actualState = newState;
    }


    @Override
    public void reactToEnter() {
        GameState.actualState = newState;
    }

    @Override
    public void reactToDrag(MouseEvent e) {

    }
}
