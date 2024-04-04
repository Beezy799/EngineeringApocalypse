package src.view.entityView;

import src.view.IView;
import src.view.ViewUtils;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.model.Constants.EntityConstants.*;
import static src.model.EntityStates.*;

public class CatView extends NpcView {

    public CatView(IView v, int i) {
        super(v, i);
        loadImages();

        xOffset = animation[0][0][0].getWidth()/2;
        yOffset = animation[0][0][0].getHeight()/2;

        animationSpeed = 30;
    }

    private void loadImages() {
        BufferedImage image = null;
        BufferedImage temp = null;

        animation = new BufferedImage[2][][]; //due azioni, cammina e sta fermo

        loadIdleImages(image, temp);
        loadRunImages(image, temp);
    }

    @Override
    protected int getAnimationLenght() {
        if(currentState == IDLE)
            return 1;

        else if(currentState == MOVE)
            return 3;

        else if ((currentState == RUNAWAY))
            return 3;

        return 0;
    }

    @Override
    protected void loadDialogues() {
        dialogues = new String[1];
        dialogues[0] = "il gatto ti guarda con odio dopo che gli hai preso il gioco";
    }

    private void loadRunImages(BufferedImage image, BufferedImage temp) {
        animation[MOVE.getConstantInAnimationArray()] = new BufferedImage[4][3];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/gattoNeroGiusto.png"));

            for(int direction = 0; direction < 4 ; direction++) {
                for(int img = 0; img < 3; img++) {
                    temp = image.getSubimage(img*32, direction*32, 32, 32);
                    temp = ViewUtils.scaleImage(temp, temp.getWidth()* GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
                    animation[MOVE.getConstantInAnimationArray()][direction][img] = temp ;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadIdleImages(BufferedImage image, BufferedImage temp) {
        animation[IDLE.getConstantInAnimationArray()] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 1 immaginE
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/gattoNeroGiusto.png"));

            temp = image.getSubimage(32, 0, 32, 32);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
            animation[IDLE.getConstantInAnimationArray()][DOWN][0] = temp ;


            temp = image.getSubimage(32, 64, 32, 32);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
            animation[IDLE.getConstantInAnimationArray()][RIGHT][0] = temp ;

            temp = image.getSubimage(32, 32, 32, 32);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
            animation[IDLE.getConstantInAnimationArray()][LEFT][0] = temp ;

            temp = image.getSubimage(32, 96, 32, 32);
            temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
            animation[IDLE.getConstantInAnimationArray()][UP][0] = temp ;

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
