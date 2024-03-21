package src.view.entityView;

import src.view.IView;
import src.view.ViewUtils;
import src.view.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static src.model.Constants.EntityConstants.*;
import static src.model.EntityStates.IDLE;
import static src.model.EntityStates.MOVE;

public class PupaView extends NpcView{

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
        dialogues = new String[6];
        dialogues[0] = "Fortuna che ci sei !";
        dialogues[1] = "Nel dormitorio è saltata la luce, non vedo più niente !";
        dialogues[2] = "Mi fa paura il buio \n non posso vedere il mio bellissimo faccino riflesso";
        dialogues[3] = "Se riesci ad accendere la luce \n ti consiglio un esame a scelta molto facile \n con tanti CFU";
        dialogues[4] = "Vai a destra, nella parte maschile del dormitorio,\n lì troverai il modo per accendere la luce";
        dialogues[5] = "So che puoi farcela !";
    }

    @Override
    protected int getAnimationLenght() {
        if(currentState == IDLE)
            return 1;

        else if(currentState == MOVE)
            return 4;

        return 0;
    }

    public String getNextDialogueLine(){
        dialogueIndex++;

        if(dialogueIndex >= dialogues.length)
            dialogueIndex = dialogues.length;

        return dialogues[dialogueIndex];
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
