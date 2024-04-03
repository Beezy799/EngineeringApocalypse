package src.view.entityView;

import src.view.IView;
import src.view.ViewUtils;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.model.EntityStates.IDLE;
import static src.model.EntityStates.MOVE;

public class NerdView extends NpcView{

    public NerdView(IView v, int i) {
        super(v, i);
        loadDialogues();
        loadImages();

        xOffset = animation[0][0][0].getWidth()/2;
        yOffset = animation[0][0][0].getHeight()/2;

        animationSpeed = 20;
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
        dialogues = new String[7];
        dialogues[0] = "capiti proprio nel momento giusto";
        dialogues[1] = "che ci faccio una tenda? semplice, \n non ho abbastanza soldi per affitare una casa \n "
                                + "e sono troppo asociale per vivere in un dormitorio";
        dialogues[2] = "noi nerd siamo fatti così";
        dialogues[3] = "la tua presenza mi disturba, ma ho bisogno di una mano";
        dialogues[4] = "sto programmando un videogioco per una esame, \n ma un gatto mi ha rubato l'hard disk con il codice sorgente!";
        dialogues[5] = "se lo trovi, portamenlo e \n condividerò con te la mia conoscenza nerdosa";
        dialogues[6] = "uscirei io stesso a cercarlo, ma ci sono troppi \n esseri umani in giro per i miei gusti";
    }


    private void loadImages() {
        BufferedImage image = null;
        BufferedImage temp = null;

        animation = new BufferedImage[2][][];		//un tipo di vecchio, due azioni

        animation[IDLE.getConstantInAnimationArray()] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
        animation[MOVE.getConstantInAnimationArray()] = new BufferedImage[4][3];		//ci sono 4 direzioni, ogni direzione ha 3 immagini

        loadRunImages(image, temp);
        loadIdleImages();

    }

    private void loadIdleImages() {
        //prendi le immagini già caricate, prendi la seconda ogni tre
        for(int direction = 0;  direction < 4; direction++)
            animation[IDLE.getConstantInAnimationArray()][direction][0] = animation[MOVE.getConstantInAnimationArray()][direction][1];
    }

    private void loadRunImages(BufferedImage image, BufferedImage temp) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/NerdGiusto.png"));

            int counter = 0;
            for(int direction = 0; direction < 4; direction++) {
                for(int index = 0; index < 3; index++) {
                    temp = image.getSubimage(index*16 + 16*counter, 0, 16, 27);
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

}
