package src.view.menu;

import src.model.GameState;
import src.view.IView;
import src.view.ViewUtils;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static src.view.main.GamePanel.SCALE;

//questa classe serve per gestire le "gif" degli avantar nel menu di selezione
public class AvatarMenuButton extends  AbstractMenuButton{

    private String skillsDescription;
    private BufferedImage[] gifButton;
    private int animationCounter = 0; //aumenta ogni ciclo di repaint
    private  int animationSpeed = 20; // serve a regolare la velocità gif
    private int animationLenght = 6; //durata gif
    private int numSprite = 0; //indice della sprite disegnata


    public AvatarMenuButton (BufferedImage[] img, Rectangle b, GameState nS, String s){
        skillsDescription = s;

        gifButton = new BufferedImage[6];

        for(int i=0; i<=5 ; i++){
            gifButton[i] = img[i];
        }


        this.bounds = b;
        this.newState = nS;
    }

    @Override
    //disegna gli avatar che si muovono quando ci andiamo sopra con il mouse, altrimenti non si muovono
    public void draw(Graphics2D g2) {

        if(mouseOver) {
            drawGif(g2);
            drawSkillsDescription(g2);
        }
        else {
            //se mouse non ci sta sopra disegna personaggio fermo e semitrasparente
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g2.drawImage(gifButton[0], bounds.x, bounds.y, null);
            //resetta il valore di trasparenza, quindi disegnerà in modo non trasparente il resto del menu
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }


    }

    private void drawSkillsDescription(Graphics2D g2) {
        int x = ViewUtils.getXforCenterText(skillsDescription, g2);
        g2.setColor(Color.red);
        g2.drawString(skillsDescription, x, (int)(350*SCALE));
    }

    private void drawGif(Graphics2D g2) {
        animationCounter++;

        if (animationCounter > animationSpeed) {
            numSprite++;

            if(numSprite >= animationLenght)
                numSprite = 0;

            animationCounter = 0;
        }

        g2.drawImage(gifButton[numSprite], bounds.x, bounds.y, null);
    }
//metodo per reagire ad enter come se fosse un clic
    @Override
    public void reactToMouse(MouseEvent e) {
        GameState.actualState = newState;
    }

    @Override
    public void reactToDrag(MouseEvent e) {
    }

    @Override
    public void reactToEnter() {
        GameState.actualState = newState;
    }
}
