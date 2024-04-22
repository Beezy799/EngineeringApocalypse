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

public class ProfView extends NpcView {
    public ProfView(IView v, int i) {
        super(v, i);
        loadImages();

        xOffset = animation[0][0][0].getWidth()/2;
        yOffset = animation[0][0][0].getHeight()/2;

        animationSpeed = 20;
    }

    private void loadImages() {
        animation = new BufferedImage[2][][];

        animation[MOVE.getConstantInAnimationArray()] = new BufferedImage[4][3];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
        animation[IDLE.getConstantInAnimationArray()] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 1 immagine

        loadRunImages();
        loadIdleImages();
    }

    private void loadRunImages() {
        BufferedImage image = null;
        BufferedImage temp = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/ProfLab.png"));

            for(int direction = 0; direction < 4 ; direction++) {
                for(int img = 0; img < 3; img++) {
                    temp = image.getSubimage(img*16, direction*24, 16, 24);
                    temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f* GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
                    animation[MOVE.getConstantInAnimationArray()][direction][img] = temp;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadIdleImages() {
        animation[IDLE.getConstantInAnimationArray()][DOWN][0] = animation[MOVE.getConstantInAnimationArray()][DOWN][0];
        animation[IDLE.getConstantInAnimationArray()][RIGHT][0] = animation[MOVE.getConstantInAnimationArray()][RIGHT][0];
        animation[IDLE.getConstantInAnimationArray()][LEFT][0] = animation[MOVE.getConstantInAnimationArray()][LEFT][0];
        animation[IDLE.getConstantInAnimationArray()][UP][0] = animation[MOVE.getConstantInAnimationArray()][UP][0];
    }

    @Override
    protected int getAnimationLenght() {
        if(currentState == IDLE)
            return 1;

        else if(currentState == MOVE)
            return 3;

        return 0;
    }

    @Override
    protected void loadDialogues() {
        dialogues = new String[2][];
        dialogues[0] = new String[6];
        dialogues[0][0] = "hey tu, ti prego aiutami! \n non sembri una matricola nullafacente come le altre";
        dialogues[0][1] = "sono un assistente di <Paul Bags<, \n il professore di robotica della facoltà";
        dialogues[0][2] = "stavo modificando dei turtlebot, ma sono impazziti \n e ora vogliono uccidere tutti gli umani!";
        dialogues[0][3] = "so che sembra un film, ma chi scrive \n i miei dialoghi non ha molta fantasia...";
        dialogues[0][4] = "se riesci a fermarlo, ti aiuterò con l'esame <sistemi di controllo<";
        dialogues[0][5] = "buona fortuna";

        dialogues[1] = new String[1];
        dialogues[1][0] = "ce l'hai fatta davvero, complimenti! \n puoi contare sul mio aiuto quando ti servirà";
    }
}
