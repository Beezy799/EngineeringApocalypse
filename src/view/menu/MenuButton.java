package src.view.menu;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

import src.model.GameState;

public class MenuButton {

//il bottone ha tre immagini: quando mouse non c'è, quando mouse ci clicca sopra , e quando mousa ci sta sopra 
private BufferedImage mouseAwayImage, mouseOverImage, mousePressedImage;
private Rectangle bounds;
private GameState nexState;

    public MenuButton(BufferedImage [] i, Rectangle b, GameState nS){
        this.mouseAwayImage = i[0];
        this.mouseOverImage = i[1];
        this.mousePressedImage = i[2];
  
        this.bounds = b;
        this.nexState = nS;
    }

    //disegna le sue 3 immagini in base alla posizione del muouse
    public void draw(Graphics2D g2) {
        g2.drawImage(mouseAwayImage, bounds.x, bounds.y, null);
    }
}
