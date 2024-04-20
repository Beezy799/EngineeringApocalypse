package src.view.entityView;

import src.model.Constants;
import src.model.EntityStates;
import src.view.IView;
import src.view.ViewUtils;
import src.view.entityView.enemy.EnemyView;
import src.view.entityView.npc.NpcView;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.model.EntityStates.*;

public class BossView extends EnemyView {
    public BossView(IView v, int i) {
        super(v, i);
        loadImages();
        xOffset = animation[0][0][0].getWidth()/2;
        yOffset = animation[0][0][0].getHeight()/2;

    }

    protected void loadImages() {
        animation = new BufferedImage[6][][];
        loadRunAnimation();
        loadIdleAnimation();
        loadOndaAnimation();
        loadPugnoAnimation();
        loadShieldAnimation();
    }

    private void loadShieldAnimation() {
        BufferedImage image = null;
        BufferedImage temp = null;
        animation[PARRING.getConstantInAnimationArray()] = new BufferedImage[4][10];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/bossPugnoScudo.png"));

            for(int direction = 0; direction < 4; direction++) {
                for(int index = 0; index < 5; index++) {
                    temp = image.getSubimage(index*42, 168 + direction*42, 42, 42);
                    temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE*1.5f, temp.getHeight()*GamePanel.SCALE*1.5f);
                    animation[PARRING.getConstantInAnimationArray()][direction][index] = temp;
                }
            }
            for(int i = 0; i < 5; i++){
                temp = image.getSubimage(i*42, 336, 42, 42);
                temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE*1.5f, temp.getHeight()*GamePanel.SCALE*1.5f);
                animation[PARRING.getConstantInAnimationArray()][Constants.EntityConstants.DOWN][5 + i] = temp;
                animation[PARRING.getConstantInAnimationArray()][Constants.EntityConstants.UP][5 + i] = temp;
                animation[PARRING.getConstantInAnimationArray()][Constants.EntityConstants.LEFT][5 + i] = temp;
                animation[PARRING.getConstantInAnimationArray()][Constants.EntityConstants.RIGHT][5 + i] = temp;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPugnoAnimation() {
        BufferedImage image = null;
        BufferedImage temp = null;
        animation[ATTACKING.getConstantInAnimationArray()] = new BufferedImage[4][5];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/bossPugnoScudo.png"));

            for(int direction = 0; direction < 4; direction++) {
                for(int index = 0; index < 5; index++) {
                    temp = image.getSubimage(index*42, direction*42, 42, 42);
                    temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE*1.5f, temp.getHeight()*GamePanel.SCALE*1.5f);
                    animation[ATTACKING.getConstantInAnimationArray()][direction][index] = temp;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadOndaAnimation() {
        BufferedImage image = null;
        BufferedImage temp = null;
        animation[THROWING.getConstantInAnimationArray()] = new BufferedImage[4][5];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/bossMovimentiOnda1.png"));

            for(int direction = 0; direction < 4; direction++) {
                for(int index = 0; index < 5; index++) {
                    temp = image.getSubimage(index*42, 42*5 + direction*42, 42, 42);
                    temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE*1.5f, temp.getHeight()*GamePanel.SCALE*1.5f);
                    animation[THROWING.getConstantInAnimationArray()][direction][index] = temp;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRunAnimation() {
        BufferedImage image = null;
        BufferedImage temp = null;
        animation[MOVE.getConstantInAnimationArray()] = new BufferedImage[4][3];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/bossMovimentiOnda1.png"));

            for(int direction = 0; direction < 4; direction++) {
                for(int index = 0; index < 3; index++) {
                    temp = image.getSubimage(index*42, 42 + direction*42, 42, 42);
                    temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE*1.5f, temp.getHeight()*GamePanel.SCALE*1.5f);
                    animation[MOVE.getConstantInAnimationArray()][direction][index] = temp;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadIdleAnimation() {
        animation[EntityStates.IDLE.getConstantInAnimationArray()] = new BufferedImage[4][1];

        animation[EntityStates.IDLE.getConstantInAnimationArray()][Constants.EntityConstants.DOWN][0] = animation[MOVE.getConstantInAnimationArray()][Constants.EntityConstants.DOWN][1];
        animation[EntityStates.IDLE.getConstantInAnimationArray()][Constants.EntityConstants.LEFT][0] = animation[MOVE.getConstantInAnimationArray()][Constants.EntityConstants.LEFT][1];
        animation[EntityStates.IDLE.getConstantInAnimationArray()][Constants.EntityConstants.RIGHT][0] = animation[MOVE.getConstantInAnimationArray()][Constants.EntityConstants.RIGHT][1];
        animation[EntityStates.IDLE.getConstantInAnimationArray()][Constants.EntityConstants.UP][0] = animation[MOVE.getConstantInAnimationArray()][Constants.EntityConstants.UP][1];
    }

    @Override
    protected int getAnimationLenght() {
        switch (currentState){
            case IDLE:
                return 1;
            case MOVE:
            case CHASE:
                return 3;
            case THROWING:
            case ATTACKING:
                return 5;
            case PARRING:
                return 10;
        }
        return 0;
    }

    protected void loadDialogues() {

    }
}
