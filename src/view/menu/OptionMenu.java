package src.view.menu;

import src.model.GameState;
import src.view.IView;
import src.view.ViewUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.view.main.GamePanel.GAME_HEIGHT;
import static src.view.main.GamePanel.SCALE;

public class OptionMenu extends AbstractMenu{

    private static final int DIFF_MATRICOLA = 0, DIFF_FUORICORSO = 1, DIFF_LAVORATORE = 2;
    private IView view;

    public OptionMenu(IView v){
        view = v;

        buttons = new AbstractMenuButton[4]; //6
        createGoBackButton();
        createDifficultyButtons();


    }


    private void createGoBackButton() {
        //immagine per bottone "indietro" , carichiamo  tre immagini in base alla posizione del mouse
        BufferedImage[] goBackButtonImage = new BufferedImage[3];
        BufferedImage temp = null;
        try {
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/indietro1.png"));
            goBackButtonImage[0] = ViewUtils.scaleImage(temp, temp.getWidth() * 0.30f*SCALE , temp.getHeight() * SCALE * 0.30f);

            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/indietro2.png"));
            goBackButtonImage[1] = ViewUtils.scaleImage(temp, temp.getWidth() * 0.35f *SCALE , temp.getHeight() * SCALE * 0.35f);

            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/indietro3.png"));
            goBackButtonImage[2] = ViewUtils.scaleImage(temp, temp.getWidth() * 0.35f*SCALE , temp.getHeight() * SCALE * 0.35f);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        int xPosGoBackButton = ViewUtils.getCenteredXPos(goBackButtonImage[0].getWidth());
        int yPosGoBackButton = GAME_HEIGHT - (int)(40*SCALE);
        Rectangle rect = new Rectangle(xPosGoBackButton, yPosGoBackButton, goBackButtonImage[0].getWidth(), goBackButtonImage[0].getHeight());
        //creo bottotne
        buttons[0] = new MenuButton(goBackButtonImage, rect, GameState.MAIN_MENU);
    }

    private void createDifficultyButtons() {
        BufferedImage[] difficultyButtonImage = new BufferedImage[3];
        int distanceBetweenButtons = (int)(35 * SCALE);
        int yFirstButton = GAME_HEIGHT/2 + (int)(40*SCALE);

        // PRIMO BOTTONE: MATRICOLA
        BufferedImage temp = null;
        try {
            temp = ImageIO.read(getClass().getResourceAsStream("/res/opzioni/matricola1.png"));
            difficultyButtonImage[0] = ViewUtils.scaleImage(temp, temp.getWidth() * 0.30f*SCALE , temp.getHeight() * SCALE * 0.30f);

            temp = ImageIO.read(getClass().getResourceAsStream("/res/opzioni/matricola2.png"));
            difficultyButtonImage[1] = ViewUtils.scaleImage(temp, temp.getWidth() * 0.35f *SCALE , temp.getHeight() * SCALE * 0.35f);

            temp = ImageIO.read(getClass().getResourceAsStream("/res/opzioni/matricola3.png"));
            difficultyButtonImage[2] = ViewUtils.scaleImage(temp, temp.getWidth() * 0.35f*SCALE , temp.getHeight() * SCALE * 0.35f);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String descrizioneMatricola = "Ti sei appena diplomato, sei pieno di speranze ed energia.. povero illuso";

        int xPosMatricolaButton = ViewUtils.getCenteredXPos(difficultyButtonImage[0].getWidth());
        int yPosMatricolaButton = yFirstButton;
        Rectangle rect = new Rectangle(xPosMatricolaButton, yPosMatricolaButton, difficultyButtonImage[0].getWidth(), difficultyButtonImage[0].getHeight());
        //creo bottotne
        buttons[1] = new DifficultyBotton(difficultyButtonImage, rect, DIFF_MATRICOLA, descrizioneMatricola);

        //SECONDO BOTTONE: FUORICORSO
        BufferedImage tempF = null;
        try {
            tempF = ImageIO.read(getClass().getResourceAsStream("/res/opzioni/fuoricorso1.png"));
            difficultyButtonImage[0] = ViewUtils.scaleImage(tempF, tempF.getWidth() * 0.30f*SCALE , tempF.getHeight() * SCALE * 0.30f);

            tempF = ImageIO.read(getClass().getResourceAsStream("/res/opzioni/fuoricorso2.png"));
            difficultyButtonImage[1] = ViewUtils.scaleImage(tempF, tempF.getWidth() * 0.35f *SCALE , tempF.getHeight() * SCALE * 0.35f);

            tempF = ImageIO.read(getClass().getResourceAsStream("/res/opzioni/fuoricorso3.png"));
            difficultyButtonImage[2] = ViewUtils.scaleImage(tempF, tempF.getWidth() * 0.35f*SCALE , tempF.getHeight() * SCALE * 0.35f);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String descrizioneFuoriCorso = "Forgiato dal fuoco di mille ritardi di Trenitalia";

        int xPosFuoriCorsoButton = ViewUtils.getCenteredXPos(difficultyButtonImage[0].getWidth());
        int yPosFuoriCorsoButton = yFirstButton + distanceBetweenButtons;
        Rectangle rectF = new Rectangle(xPosFuoriCorsoButton, yPosFuoriCorsoButton, difficultyButtonImage[0].getWidth(), difficultyButtonImage[0].getHeight());
        //creo bottotne
        buttons[2] = new DifficultyBotton(difficultyButtonImage, rectF, DIFF_FUORICORSO, descrizioneFuoriCorso);

        //TERZO BOTTONE: LAVORATORE
        BufferedImage tempL = null;
        try {
            tempL = ImageIO.read(getClass().getResourceAsStream("/res/opzioni/lavoratore1.png"));
            difficultyButtonImage[0] = ViewUtils.scaleImage(tempL, tempL.getWidth() * 0.6f*SCALE , tempL.getHeight() * SCALE * 0.6f);

            tempL = ImageIO.read(getClass().getResourceAsStream("/res/opzioni/lavoratore2.png"));
            difficultyButtonImage[1] = ViewUtils.scaleImage(tempL, tempL.getWidth() * 0.6f *SCALE , tempL.getHeight() * SCALE * 0.6f);

            tempL = ImageIO.read(getClass().getResourceAsStream("/res/opzioni/lavoratore3.png"));
            difficultyButtonImage[2] = ViewUtils.scaleImage(tempL, tempL.getWidth() * 0.6f*SCALE , tempL.getHeight() * SCALE * 0.6f);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String descrizioneLavoratore = "Hai a malapena il tempo per chiederti 'chi me l'ha fatto fare?'";
        int xPosLavoratoreButton = ViewUtils.getCenteredXPos(difficultyButtonImage[0].getWidth());
        int yPosLavoratoreButton = yFirstButton + 2*distanceBetweenButtons;
        Rectangle rectL = new Rectangle(xPosLavoratoreButton, yPosLavoratoreButton, difficultyButtonImage[0].getWidth(), difficultyButtonImage[0].getHeight());
        //creo bottotne
        buttons[3] = new DifficultyBotton(difficultyButtonImage, rectL, DIFF_LAVORATORE, descrizioneLavoratore);

    }

    public void draw(Graphics2D g2) {
        // chiede al menu principale di disegnare il background, senza dover ricaricare le immagini dello sfondo
        view.getMainMenu().drawBackground(g2);
        drawButtons(g2);
    }
    public void mouseMovedInOptionMenu(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }

    public void mouseDraggedInOption(MouseEvent e) {

    }
    
}
