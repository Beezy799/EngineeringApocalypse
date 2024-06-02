package src.view.playStateView;

import src.controller.Hitbox;
import src.controller.Vector;
import src.model.BulletComplete;
import src.view.ViewUtils;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BulletView {

    BulletComplete bulletComplete;
    int xPosMap, yPosMap, counter, animationIndex, animationSpeed = 20;
    BufferedImage[] animation;

    public BulletView(Vector direction){
        animation = new BufferedImage[2];	//1 direzione, ciascuna con due immagini
        loadAnimation(direction);
    }

    public void setBulletComplete(BulletComplete bulletComplete) {
        this.bulletComplete = bulletComplete;
    }

    public void draw(Graphics2D g2, int playerMapPositionX, int playerMapPositionY){
        counter++;
        //decidiamo quando disegnare
        if(counter >= animationSpeed) {
            animationIndex++;

            //decidiamo cosa disegnare
            if(animationIndex > 1) {
                animationIndex = 0;
            }

            counter = 0;
        }

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

        g2.drawImage(animation[animationIndex], xPosOnScreen, yPosOnScreen, null);
    }

    private void loadAnimation(Vector direction) {
        BufferedImage image = null;
        try {
            if(direction.getX() < 0) {
                image = ImageIO.read(getClass().getResourceAsStream("/res/entity/fireball_left_1.png"));
                image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
                animation[0] = image;

                image = ImageIO.read(getClass().getResourceAsStream("/res/entity/fireball_left_2.png"));
                image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
                animation[1] = image;
            }

            else if(direction.getX() > 0) {
                image = ImageIO.read(getClass().getResourceAsStream("/res/entity/fireball_right_1.png"));
                image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
                animation[0] = image;

                image = ImageIO.read(getClass().getResourceAsStream("/res/entity/fireball_right_2.png"));
                image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
                animation[1] = image;
            }

            else if(direction.getY() < 0) {
                image = ImageIO.read(getClass().getResourceAsStream("/res/entity/fireball_up_1.png"));
                image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
                animation[0] = image;

                image = ImageIO.read(getClass().getResourceAsStream("/res/entity/fireball_up_2.png"));
                image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
                animation[1] = image;
            }

            else if(direction.getY() > 0) {
                image = ImageIO.read(getClass().getResourceAsStream("/res/entity/fireball_down_1.png"));
                image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
                animation[0] = image;

                image = ImageIO.read(getClass().getResourceAsStream("/res/entity/fireball_down_2.png"));
                image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
                animation[1] = image;
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
