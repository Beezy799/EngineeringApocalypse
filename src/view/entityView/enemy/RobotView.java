package src.view.entityView.enemy;

import src.controller.entitycontroller.enemy.RobotController;
import src.model.EntityStates;
import src.model.entity.EnemyComplete;
import src.view.IView;
import src.view.ViewUtils;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static javax.swing.TransferHandler.MOVE;
import static src.model.Constants.EntityConstants.*;
import static src.model.EntityStates.ATTACKING;
import static src.model.EntityStates.IDLE;

public class RobotView extends EnemyView{

    public RobotView(IView v, int i) {
        super(v, i);
        xOffset = animation[0][0][0].getWidth()/2;
        yOffset = animation[0][0][0].getHeight()/2;

        animationSpeed = 30;
    }

    protected void dyingDraw(Graphics2D g2, int xPlayerMap, int yPlayerMap ) {
        animationCounter++;
        takeCurrentStateFromController();
        takeCurrentDirectionFromController();

        if (animationCounter > animationSpeed) {
            numSprite++;

            if (numSprite >= getAnimationLenght()) {
                //finita l'animazione, il nemico può muorire
                ((EnemyComplete) entityComplete).setAlive(false);
                //quando muore, regala cfu al player
                ((RobotController)entityComplete.getEntityController()).abbassaNumeroRobot();
                return;
            }

            animationCounter = 0;
        }
    }

    protected void loadImages() {
        BufferedImage image = null;
        BufferedImage temp = null;

        animation = new BufferedImage[6][][];

        animation[EntityStates.ATTACKING.getConstantInAnimationArray()] = new BufferedImage[4][1];
        animation[EntityStates.MOVE.getConstantInAnimationArray()] = new BufferedImage[4][3];	//ci sono 4 direzioni, ogni direzione ha 3 immagini
        animation[IDLE.getConstantInAnimationArray()] = new BufferedImage[4][1];	//ci sono 4 direzioni, ogni direzione ha 1 immagine
        animation[EntityStates.DYING.getConstantInAnimationArray()] = new BufferedImage[4][2];

        loadRunImages(image, temp);
        loadIdleImages();
        loadDieImages(image, temp);

    }

    private void loadRunImages(BufferedImage image, BufferedImage temp) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/robotGiusto.png"));

            for(int direction = 0; direction < 4 ; direction++) {
                for(int img = 0; img < 3; img++) {
                    temp = image.getSubimage(img*16, direction*24, 16, 24);
                    temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f* GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
                    animation[EntityStates.MOVE.getConstantInAnimationArray()][direction][img] = temp;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadIdleImages() {
        animation[IDLE.getConstantInAnimationArray()][DOWN][0] = animation[EntityStates.MOVE.getConstantInAnimationArray()][DOWN][0];
        animation[IDLE.getConstantInAnimationArray()][RIGHT][0] = animation[EntityStates.MOVE.getConstantInAnimationArray()][RIGHT][0];
        animation[IDLE.getConstantInAnimationArray()][LEFT][0] = animation[EntityStates.MOVE.getConstantInAnimationArray()][LEFT][0];
        animation[IDLE.getConstantInAnimationArray()][UP][0] = animation[EntityStates.MOVE.getConstantInAnimationArray()][UP][0];

        animation[ATTACKING.getConstantInAnimationArray()][DOWN][0] = animation[EntityStates.MOVE.getConstantInAnimationArray()][DOWN][0];
        animation[ATTACKING.getConstantInAnimationArray()][RIGHT][0] = animation[EntityStates.MOVE.getConstantInAnimationArray()][RIGHT][0];
        animation[ATTACKING.getConstantInAnimationArray()][LEFT][0] = animation[EntityStates.MOVE.getConstantInAnimationArray()][LEFT][0];
        animation[ATTACKING.getConstantInAnimationArray()][UP][0] = animation[EntityStates.MOVE.getConstantInAnimationArray()][UP][0];

    }

    private void loadDieImages(BufferedImage image, BufferedImage temp) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/robotGiusto.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        temp = image.getSubimage(0*16, 4*24, 16, 24);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[EntityStates.DYING.getConstantInAnimationArray()][DOWN][0] = temp;
        animation[EntityStates.DYING.getConstantInAnimationArray()][RIGHT][0] = temp;

        temp = image.getSubimage(0*16, 5*24, 16, 24);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[EntityStates.DYING.getConstantInAnimationArray()][LEFT][0] = temp;
        animation[EntityStates.DYING.getConstantInAnimationArray()][UP][0] = temp;

        temp = image.getSubimage(1*16, 4*24, 24, 24);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[EntityStates.DYING.getConstantInAnimationArray()][DOWN][1] = temp;
        animation[EntityStates.DYING.getConstantInAnimationArray()][RIGHT][1] = temp;

        temp = image.getSubimage(1*16, 5*24, 24, 24);
        temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
        animation[EntityStates.DYING.getConstantInAnimationArray()][LEFT][1] = temp;
        animation[EntityStates.DYING.getConstantInAnimationArray()][UP][1] = temp;

    }
    @Override
    protected int getAnimationLenght() {
        switch (currentState){
            case IDLE:
            case ATTACKING:
            case RECHARGE:
                return 1;
            case DYING:
                return 2;
            case MOVE:
            case CHASE:
                return 3;
            default:
                return 0;
        }
    }

}
