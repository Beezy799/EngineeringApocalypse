package src.view.entityView.enemy;

import src.model.EntityStates;
import src.view.IView;
import src.view.ViewUtils;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.model.Constants.EntityConstants.*;

public class GhostView extends EnemyView{


    public GhostView(IView v, int i) {
        super(v, i);
        xOffset = animation[0][0][0].getWidth()/2;
        yOffset = animation[0][0][0].getHeight()/2;
    }

    protected void loadImages() {

        animation = new BufferedImage[1][4][3];

        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/ghost.png"));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedImage temp = null;
        for (int i = 0; i < 2; i++) {
            temp = image.getSubimage(52 + i * 20, 0, 20, 28);
            temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
            animation[EntityStates.IDLE.getConstantInAnimationArray()][DOWN][i] = temp;
            animation[EntityStates.IDLE.getConstantInAnimationArray()][UP][i] = temp;
        }

        temp = image.getSubimage(0, 0, 17, 28);
        temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
        animation[EntityStates.IDLE.getConstantInAnimationArray()][LEFT][0] = temp;

        temp = image.getSubimage(17, 0, 18, 28);
        temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
        animation[EntityStates.IDLE.getConstantInAnimationArray()][LEFT][1] = temp;

        temp = image.getSubimage(17 + 18, 0, 17, 28);
        temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
        animation[EntityStates.IDLE.getConstantInAnimationArray()][LEFT][2] = temp;


        temp = image.getSubimage(113, 0, 17, 28);
        temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
        animation[EntityStates.IDLE.getConstantInAnimationArray()][RIGHT][0] = temp;

        temp = image.getSubimage(113 + 17, 0, 18, 28);
        temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
        animation[EntityStates.IDLE.getConstantInAnimationArray()][RIGHT][1] = temp;

        temp = image.getSubimage(113 + 17 + 18, 0, 17, 28);
        temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
        animation[EntityStates.IDLE.getConstantInAnimationArray()][RIGHT][2] = temp;
    }

    @Override
    protected int getAnimationLenght() {
        switch (currentDirection){
            case LEFT:
            case RIGHT:
                return 3;

            default:
                return 2;

        }
    }

}
