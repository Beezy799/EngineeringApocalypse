package src.view.playStateView;

import src.model.BulletComplete;
import src.view.gameWindow.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BulletView {

    BulletComplete bulletComplete;
    int xPosMap, yPosMap;

    public BulletView(){

    }

    public void setBulletComplete(BulletComplete bulletComplete) {
        this.bulletComplete = bulletComplete;
    }

    public void draw(Graphics2D g2, int playerMapPositionX, int playerMapPositionY){

        xPosMap = (int)bulletComplete.getBulletController().getxPos();
        yPosMap = (int)bulletComplete.getBulletController().getyPos();

        //la distanza tra il player e il tile è la stessa, sia nella mappa che nella finestra di gioco
        //prendendo la distanza nella mappa rispetto al giocatore, possiamo capire dove disegnare il tile
        //rispetto alla posizione del giocatore nella finestra (che è sempre al centro)
        int xDistanceFromPlayer = xPosMap - playerMapPositionX;
        int yDistanceFromPlayer = yPosMap - playerMapPositionY;

        //ci serve un offset perchè la distanza del tile nella mappa rispetto al player è riferita al punto in
        //alto a sinistra della hitbox. Per mantenere la stessa distanza, dobbiamo aggiungere questo offset
        int xPosOnScreen = GamePanel.CENTER_X_GAME_PANEL + xDistanceFromPlayer;
        int yPosOnScreen = GamePanel.CENTER_Y_GAME_PANEL + yDistanceFromPlayer;

        g2.setColor(Color.BLUE);
        g2.fillRect(xPosOnScreen, yPosOnScreen, GamePanel.TILES_SIZE/2, GamePanel.TILES_SIZE/2);

    }
}
