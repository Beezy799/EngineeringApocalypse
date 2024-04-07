package src.view.entityView.enemy;

import src.view.IView;
import src.view.ViewUtils;
import src.view.entityView.enemy.EnemyView;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.model.Constants.EntityConstants.*;
import static src.model.EntityStates.*;

public class NullafacenteView extends EnemyView {

    public NullafacenteView(IView v, int i) {
        super(v, i);
        xOffset = animation[0][0][0].getWidth()/2;
        yOffset = animation[0][0][0].getHeight()/2;

        animationSpeed = 30;
    }

    protected void loadImages() {
        BufferedImage image = null;
        BufferedImage temp = null;

        animation = new BufferedImage [6][][];

        animation[MOVE.getConstantInAnimationArray()] = new BufferedImage[4][3];	//ci sono 4 direzioni, ogni direzione ha 3 immagini
        animation[IDLE.getConstantInAnimationArray()] = new BufferedImage[4][1];	//ci sono 4 direzioni, ogni direzione ha 1 immagine
        animation[DYING.getConstantInAnimationArray()] = new BufferedImage[4][2];
        animation[ATTACKING.getConstantInAnimationArray()] = new BufferedImage[4][2];

        loadRunImages(image, temp);
        loadIdleImages();
        loadDieImages(image, temp);
        loadAttackImages(image, temp);
    }

    private void loadRunImages(BufferedImage image, BufferedImage temp) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/nullafacente.png"));

            for(int img = 0; img < 3; img++) {
                temp = image.getSubimage(img*16, 0, 16, 24);
                temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f* GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
                animation[MOVE.getConstantInAnimationArray()][DOWN][img] = temp;
            }

            for(int img = 0; img < 3; img++) {
                temp = image.getSubimage(img*16, 24, 16, 23);
                temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
                animation[MOVE.getConstantInAnimationArray()][RIGHT][img] = temp;
            }

            for(int img = 0; img < 3; img++) {
                temp = image.getSubimage(img*16, 24 + 23, 16, 23);
                temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
                animation[MOVE.getConstantInAnimationArray()][LEFT][img] = temp;
            }

            for(int img = 0; img < 3; img++) {
                temp = image.getSubimage(img*16, 24 + 23 + 23, 16, 24);
                temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
                animation[MOVE.getConstantInAnimationArray()][UP][img] = temp;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadIdleImages() {
        animation[IDLE.getConstantInAnimationArray()][DOWN][0] = animation[MOVE.getConstantInAnimationArray()][DOWN][0];
        animation[IDLE.getConstantInAnimationArray()][RIGHT][0] = animation[MOVE.getConstantInAnimationArray()][RIGHT][1];
        animation[IDLE.getConstantInAnimationArray()][LEFT][0] = animation[MOVE.getConstantInAnimationArray()][LEFT][1];
        animation[IDLE.getConstantInAnimationArray()][UP][0] = animation[MOVE.getConstantInAnimationArray()][UP][0];
    }

    private void loadDieImages(BufferedImage image, BufferedImage temp) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/nullafacente.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        temp = image.getSubimage(0, 190, 15, 24);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[DYING.getConstantInAnimationArray()][DOWN][0] = temp;
        animation[DYING.getConstantInAnimationArray()][LEFT][0] = temp;

        temp = image.getSubimage(15, 190, 24, 24);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[DYING.getConstantInAnimationArray()][DOWN][1] = temp;
        animation[DYING.getConstantInAnimationArray()][LEFT][1] = temp;

        temp = image.getSubimage(0, 214, 15, 24);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[DYING.getConstantInAnimationArray()][RIGHT][0] = temp;
        animation[DYING.getConstantInAnimationArray()][UP][0] = temp;

        temp = image.getSubimage(15, 214, 24, 24);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[DYING.getConstantInAnimationArray()][RIGHT][1] = temp;
        animation[DYING.getConstantInAnimationArray()][UP][1] = temp;

    }

    private void loadAttackImages(BufferedImage image, BufferedImage temp) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/nullafacente.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        for(int img = 0; img < 2; img++) {
            temp = image.getSubimage(img*16, 94, 16, 24);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
            animation[ATTACKING.getConstantInAnimationArray()][DOWN][img] = temp;
        }

        temp = image.getSubimage(0, 118, 12, 24);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[ATTACKING.getConstantInAnimationArray()][RIGHT][0] = temp;

        temp = image.getSubimage(12, 118, 16, 23);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[ATTACKING.getConstantInAnimationArray()][RIGHT][1] = temp;


        temp = image.getSubimage(0, 118 + 24, 12, 24);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[ATTACKING.getConstantInAnimationArray()][LEFT][0] = temp;

        temp = image.getSubimage(12, 118 + 24, 16, 23);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[ATTACKING.getConstantInAnimationArray()][LEFT][1] = temp;


        temp = image.getSubimage(0, 118 + 24 + 24, 16, 24);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[ATTACKING.getConstantInAnimationArray()][UP][0] = temp;

        temp = image.getSubimage(16, 118 + 24 + 24, 17, 24);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[ATTACKING.getConstantInAnimationArray()][UP][1] = temp;

    }

    @Override
    protected int getAnimationLenght() {
        if(currentState == IDLE)
            return 1;

        else if(currentState == RECHARGE)
            return 1;

        else if(currentState == MOVE)
            return 3;

        else if(currentState == CHASE)
            return 3;

        else if(currentState == DYING)
            return 2;

        else if(currentState == ATTACKING)
            return 2;

        else
            return 0;
    }

}
