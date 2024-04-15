package src.view.entityView.npc;

import src.view.IView;
import src.view.ViewUtils;
import src.view.entityView.npc.NpcView;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.model.Constants.EntityConstants.*;
import static src.model.EntityStates.IDLE;
import static src.model.EntityStates.MOVE;

public class PupaView extends NpcView {

    public PupaView(IView v, int i) {
        super(v, i);

        animation = new BufferedImage[2][][];
        animation[0] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 1 immagini
        animation[1] = new BufferedImage[4][4];		//ci sono 4 direzioni, ogni direzione ha 4 immagini

        loadRunImages();
        loadIdleImages();

        xOffset = animation[0][0][0].getWidth()/2;
        yOffset = animation[0][0][0].getHeight()/2;
        animationSpeed = 40;

    }

    protected void loadDialogues() {
        dialogues = new String[2][];
        dialogues[0] = new String[7];
        dialogues[0][0] = "Ciao, non ti ho mai visto da queste parti \n ti andrebbe aiutare una bella ragazza in difficoltà?";
        dialogues[0][1] = "Nel dormitorio è saltata la luce, non vedo più niente!";
        dialogues[0][2] = "Mi fa paura il buio \n non posso vedere il mio bellissimo faccino riflesso";
        dialogues[0][3] = "Se riesci ad accendere la luce \n ti consiglio un esame a scelta molto facile \n con tanti CFU";
        dialogues[0][4] = "Vai a sinistra, vicino alla tv, l'interruttore è da quelle parti";
        dialogues[0][6] = "Attento a non farti prendere dal fantasma dell'ansia \n ha fatto scappare tanti studenti";
        dialogues[0][5] = "Se sopravvivi ti aggiungo su instagram <3";

        dialogues[1] = new String[1];
        dialogues[1][0] = "grazie! \n ti aggiungo subito su instagram <3";
    }

    @Override
    protected int getAnimationLenght() {
        if(currentState == IDLE)
            return 1;

        else if(currentState == MOVE)
            return 4;

        return 0;
    }

    private void loadIdleImages() {
        //prendi le immagini già caricate, prendi la seconda ogni tre
        for(int direction = 0;  direction < 4; direction++)
            animation[IDLE.getConstantInAnimationArray()][direction][0] = animation[MOVE.getConstantInAnimationArray()][direction][1];
    }

    private void loadRunImages() {
        BufferedImage image = null;
        BufferedImage temp = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/pupa.png"));

            temp = image.getSubimage(0, 0, 16, 31);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
            animation[MOVE.getConstantInAnimationArray()][DOWN][0] = temp;

            temp = image.getSubimage(16, 0, 17, 30);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
            animation[MOVE.getConstantInAnimationArray()][DOWN][1] = temp;

            temp = image.getSubimage(16 + 17, 0, 17, 31);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
            animation[MOVE.getConstantInAnimationArray()][DOWN][2] = temp;

            temp = image.getSubimage(16 + 17 + 17, 0, 16, 30);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
            animation[MOVE.getConstantInAnimationArray()][DOWN][3] = temp;




            temp = image.getSubimage(0, 31, 23, 31);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
            animation[MOVE.getConstantInAnimationArray()][RIGHT][0] = temp;

            temp = image.getSubimage(23, 31, 23, 30);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
            animation[MOVE.getConstantInAnimationArray()][RIGHT][1] = temp;

            temp = image.getSubimage(23 + 23, 31, 23, 31);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
            animation[MOVE.getConstantInAnimationArray()][RIGHT][2] = temp;

            temp = image.getSubimage(23 + 23 + 23, 31, 23, 30);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
            animation[MOVE.getConstantInAnimationArray()][RIGHT][3] = temp;




            temp = image.getSubimage(0, 62, 23, 31);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
            animation[MOVE.getConstantInAnimationArray()][LEFT][0] = temp;

            temp = image.getSubimage(23, 62, 23, 30);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
            animation[MOVE.getConstantInAnimationArray()][LEFT][1] = temp;

            temp = image.getSubimage(23 + 23, 62, 23, 31);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
            animation[MOVE.getConstantInAnimationArray()][LEFT][2] = temp;

            temp = image.getSubimage(23 + 23 + 23, 62, 23, 30);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
            animation[MOVE.getConstantInAnimationArray()][LEFT][3] = temp;



            for(int i = 0; i < 4; i++) {
                temp = image.getSubimage(i*17, 93, 17, 30);
                temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
                animation[MOVE.getConstantInAnimationArray()][UP][i] = temp;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
