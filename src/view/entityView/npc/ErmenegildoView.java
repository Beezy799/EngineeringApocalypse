package src.view.entityView.npc;

import src.view.IView;
import src.view.ViewUtils;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.model.EntityStates.IDLE;
import static src.model.EntityStates.MOVE;

public class ErmenegildoView extends NpcView {

    public ErmenegildoView(IView v, int i) {
        super(v, i);
        loadImages();
        loadRunImages();

        xOffset = animation[0][0][0].getWidth()/2;
        yOffset = animation[0][0][0].getHeight()/2;

        animationSpeed = 30;
    }

    protected void loadDialogues() {
        dialogues = new String[1][10];
        dialogues[0][0] = "ciao, sei una matricola?";
        dialogues[0][1] = "che invidia, io sono qui da un po'...\n il prof <Luke Crickets< mi ha bocciato 100 volte";
        dialogues[0][2] = "lascia che ti dia qualche dritta";
        dialogues[0][3] = "per laurearti, ti servono <180 CFU<, \n che puoi trovare in giro per la facoltà";

        dialogues[0][4] = "non ti fare influenzare dagli <studenti nullafacenti<, \n abbassano la tua concentrazione!";
        dialogues[0][5] = "usa il <computer< e gli <appunti< per difenderti \n puoi trovare gli appunti vicino ai libri";

        dialogues[0][6] = "il <caffè< è un tuo alleato, \n prendi ogni tazzina disponibile se ti senti stanco";
        dialogues[0][7] = "conosco qualche scorciatoia per racimolare CFU più in fretta:";
        dialogues[0][8] = "parla con i tuoi colleghi, \n possono aiutarti negli esami in cambio di qualche favore";
        dialogues[0][9] = "se ti serve qualche altro suggerimento, mi trovi in biblioteca. \n Qui sei al sicuro, i nullafacenti non entrano mai";

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
