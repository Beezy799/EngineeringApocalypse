package src.view.entityView;

import src.model.EntityStates;
import src.model.mapModel.Rooms;
import src.view.IView;
import src.view.ViewUtils;
import src.view.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.model.EntityStates.IDLE;
import static src.model.EntityStates.MOVE;

public class ErmenegildoView extends EntityView{

    public ErmenegildoView(IView v, int i) {
        super(v, i);
        loadImages();
        loadRunImages();
    }

    private void loadImages() {
        animation = new BufferedImage[2][][];		//un tipo di vecchio, due azioni

        animation[0] = new BufferedImage[4][1];
        animation[1] = new BufferedImage[4][3];

        loadRunImages();
        loadIdleImages();
    }

    private void loadRunImages() {
        BufferedImage image = null;
        BufferedImage temp = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/fuoricorso.png"));

            int counter = 0;
            for(int direction = 0; direction < 4; direction++) {
                for(int index = 0; index < 3; index++) {
                    temp = image.getSubimage(index*16 + 16*counter, 0, 16, 23);
                    temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f* GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
                    animation[MOVE.getConstantInAnimationArray()][direction][index] = temp;
                }
                counter += 3;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadIdleImages(){
        //prendi le immagini già caricate, prendi la seconda ogni tre
        for(int direction = 0;  direction < 4; direction++)
            animation[IDLE.getConstantInAnimationArray()][direction][0] = animation[MOVE.getConstantInAnimationArray()][direction][1];

    }

    @Override
    protected int getAnimationLenght() {
        if(currentState == IDLE)
            return 1;

        else if(currentState == MOVE)
            return 3;

        return 0;
    }

}
