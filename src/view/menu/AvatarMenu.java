package src.view.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import src.model.GameState;
import src.view.IView;
import src.view.ViewUtils;

import static src.view.main.GamePanel.*;

//menu per la selezione dell'avatar
public class AvatarMenu extends AbstractMenu{
    private IView view;
    private int titleAvatarX, titleAvatarY;
    private BufferedImage titleAvatar; // immagine "scegli avatar"
    private String[] characterSkills;


    public AvatarMenu(IView v) {
        view = v;
        loadAvatarChoiceText();
        loadButtons();
      //  loadCharacterSkills();
    }

    // è la scritta "scegli il tuo avatar"
    private void loadAvatarChoiceText() {
        try {
            titleAvatar = ImageIO.read(getClass().getResourceAsStream("/res/avatarSelection/scritta1.png"));
            titleAvatar = ViewUtils.scaleImage(titleAvatar, titleAvatar.getWidth()*SCALE/1.5f, titleAvatar.getHeight()*SCALE/1.5f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        titleAvatarX = ViewUtils.getCenteredXPos(titleAvatar.getWidth());
        titleAvatarY = (int)(50*SCALE);
    }

    public void loadButtons() {
        buttons = new AbstractMenuButton[3];
        createGoBackButton();
        createAvatarBoyButton();
        createAvatarGirlButton();
    }

    private void createAvatarBoyButton() {
        BufferedImage[] gifAnimationBoy = new BufferedImage[6];
        BufferedImage temp = null;
        BufferedImage image = null; //prende l'immagine dove ci sono tutte le animazioni

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/walkingSpritesBoyCorr.png"));

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 23, 0, 23, 35);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 4f * SCALE, temp.getHeight() * 4f * SCALE);
                gifAnimationBoy[i] = temp;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        int boyButtonX = ViewUtils.getCenteredXPos(gifAnimationBoy[0].getWidth()) - (int)(80*SCALE);
        int boyButtonY = (int)(150*SCALE);
        Rectangle rect = new Rectangle(boyButtonX,boyButtonY, gifAnimationBoy[0].getWidth(), gifAnimationBoy[0].getHeight());
        buttons[1] = new AvatarMenuButton(gifAnimationBoy, rect, GameState.PLAYING);
    }


    private void createAvatarGirlButton() {
        BufferedImage[] gifAnimationGirl = new BufferedImage[6];
        BufferedImage temp = null;
        BufferedImage image = null; //prende l'immagine dove ci sono tutte le animazioni

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/MoveGirl.png"));

            for(int i = 0; i < 6; i++) {
                temp = image.getSubimage(i*26, 0, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth()*4f* SCALE, temp.getHeight()*4f* SCALE);
                gifAnimationGirl [i] = temp;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        int boyButtonX = ViewUtils.getCenteredXPos(gifAnimationGirl[0].getWidth()) + (int)(80*SCALE);
        int boyButtonY = (int)(150*SCALE);
        Rectangle rect = new Rectangle(boyButtonX,boyButtonY, gifAnimationGirl[0].getWidth(), gifAnimationGirl[0].getHeight());
        buttons[2] = new AvatarMenuButton(gifAnimationGirl, rect, GameState.PLAYING);
    }



    private void createGoBackButton() {
        //immagine per bottone "indietro" , carichiamo  tre immagini in base alla posizione del mouse
        BufferedImage[] goBackButtonImage = new BufferedImage[3];
        BufferedImage temp = null;
        try {
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/indietro1.png"));
            goBackButtonImage[0] = ViewUtils.scaleImage(temp, temp.getWidth() * 0.4f*SCALE , temp.getHeight() * SCALE * 0.4f);

            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/indietro2.png"));
            goBackButtonImage[1] = ViewUtils.scaleImage(temp, temp.getWidth() * 0.45f *SCALE , temp.getHeight() * SCALE * 0.45f);

            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/indietro3.png"));
            goBackButtonImage[2] = ViewUtils.scaleImage(temp, temp.getWidth() * 0.4f*SCALE , temp.getHeight() * SCALE * 0.4f);

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

    //sarebbero le scritte che appaiono se passi sopra a un personaggio
/*    private void loadCharacterSkills() {
        characterSkills = new String[2];
        characterSkills[0] = "Sara, viene dal classico, ha più concentrazione";
        characterSkills[1] = "Mario, viene dallo scientifico, ha più appunti";
    }*/

    public void draw(Graphics2D g2) {
   //     drawBackground(g2);
        drawAvatarChoiceText(g2);
   //     drawCharacterSkills(g2);
        drawButtons(g2);
    }

    // chiede al menu principale di disegnare il background, senza dover ricaricare le immagini dello sfondo
 /*   private void drawBackground(Graphics2D g2) {
        view.getMenu().drawBackground(g2);
    }*/

    private void drawAvatarChoiceText(Graphics2D g2) {
        g2.drawImage(titleAvatar, titleAvatarX, titleAvatarY, null);
    }

/*    private void drawCharacterSkills(Graphics2D g2) {
        if(buttons[RAGAZZO].getMouseOver() == true) {
            g2.setColor(Color.red);
            int x = ViewUtils.getXforCenterText(characterSkills[RAGAZZO -1], g2);
            int y = (int)(buttons[RAGAZZO].getBounds().getHeight() + buttons[RAGAZZO].getBounds().getY() + 30*SCALE);
            g2.drawString(characterSkills[RAGAZZO -1], x, y);
        }
        else if (buttons[RAGAZZA].getMouseOver() == true) {
            g2.setColor(Color.red);
            int x = ViewUtils.getXforCenterText(characterSkills[RAGAZZA -1], g2);
            int y = (int)(buttons[RAGAZZA].getBounds().getHeight() + buttons[RAGAZZA].getBounds().getY() + 30*SCALE);
            g2.drawString(characterSkills[RAGAZZA -1], x, y);
        }
    }*/

/*    public void keyReleased(int tasto) {

        switch(buttonIndex) {
            case RAGAZZO:
                keyboardInputsBoyButton(tasto);
                break;
            case RAGAZZA:
                keyboardInputsGirlButton(tasto);
                break;
            case INDIETRO:
                keyboardInputsBackButton(tasto);
                break;
        }
    }*/

    //metedo che serve per muoversi nel menù con la tastiera (WASD-frecce)
/*    private void keyboardInputsBoyButton(int tasto) {
        if(tasto == KeyEvent.VK_A || tasto == KeyEvent.VK_LEFT)
            view.setCursorPosition(buttons[RAGAZZO].getBounds().x, buttons[RAGAZZO].getBounds().y);

        if(tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN) {
            view.setCursorPosition(buttons[INDIETRO].getBounds().x, buttons[INDIETRO].getBounds().y);
            buttonIndex = INDIETRO;
        }
        if(tasto == KeyEvent.VK_D || tasto == KeyEvent.VK_RIGHT) {
            view.setCursorPosition(buttons[RAGAZZA].getBounds().x, buttons[RAGAZZA].getBounds().y);
            buttonIndex = RAGAZZA;
        }
        if(tasto == KeyEvent.VK_ENTER) {
            resetButtons();
            buttons[RAGAZZO].reactToMouse(null);
        }
    }

    private void keyboardInputsGirlButton(int tasto) {
        if(tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN) {
            view.setCursorPosition(buttons[INDIETRO].getBounds().x, buttons[INDIETRO].getBounds().y);
            buttonIndex = INDIETRO;
        }
        if(tasto == KeyEvent.VK_A || tasto == KeyEvent.VK_LEFT) {
            view.setCursorPosition(buttons[RAGAZZO].getBounds().x, buttons[RAGAZZO].getBounds().y);
            buttonIndex = RAGAZZO;
        }
        if(tasto == KeyEvent.VK_ENTER) {
            resetButtons();
            buttons[RAGAZZA].reactToMouse(null);
        }
    }

    private void keyboardInputsBackButton(int tasto) {
        if(tasto == KeyEvent.VK_A || tasto == KeyEvent.VK_LEFT) {
            view.setCursorPosition(buttons[RAGAZZO].getBounds().x, buttons[RAGAZZO].getBounds().y);
            buttonIndex = RAGAZZO;
        }
        if(tasto == KeyEvent.VK_D || tasto == KeyEvent.VK_RIGHT) {
            view.setCursorPosition(buttons[RAGAZZA].getBounds().x, buttons[RAGAZZA].getBounds().y);
            buttonIndex = RAGAZZA;
        }
        if(tasto == KeyEvent.VK_ENTER) {
            resetButtons();
            view.changeGameState(buttons[INDIETRO].getButtonState());
        }
    }*/

}

