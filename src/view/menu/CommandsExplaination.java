package src.view.menu;

import src.model.GameState;
import src.view.IView;
import src.view.ViewUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.view.gameWindow.GamePanel.GAME_HEIGHT;
import static src.view.gameWindow.GamePanel.SCALE;

public class CommandsExplaination extends AbstractMenu {

    private IView view;
    private Font descriptionFont;
    private final int distanceBetweenString = (int)(50*SCALE);


    private final int heightFirstString = (int)(90* SCALE);
    private String movementExplaination = "CAMMINA:   w, a, s, d";
    private String attackExplaination = "ATTACCA:   invio, opure";
    private String parryExplaination = "PARATI:   spazio, oppure";
    private String throwExplaination = "LANCIA APPUNTI: p, oppure ";
    private String interactExplaination = "INTERAGISCI:   e";
    private String pauseExplaination = "PAUSA:   esc";
    private BufferedImage rightClick, leftClick, middleClick;


    public CommandsExplaination(IView v){
        view = v;
        loadGoBackButton();
        loadMouseImages();
        int fontSize = (int)(20*SCALE);
        descriptionFont = new Font("Arial", Font.PLAIN, fontSize);
    }

    private void loadMouseImages() {
        BufferedImage temp = null;
        try {
            temp = ImageIO.read(getClass().getResourceAsStream("/res/leftClick.png"));
            leftClick = ViewUtils.scaleImage(temp, temp.getWidth() * 0.05f * SCALE, temp.getHeight() * SCALE * 0.05f);

            temp = ImageIO.read(getClass().getResourceAsStream("/res/rightClick.png"));
            rightClick = ViewUtils.scaleImage(temp, temp.getWidth() * 0.05f * SCALE, temp.getHeight() * SCALE * 0.05f);

            temp = ImageIO.read(getClass().getResourceAsStream("/res/middleClick.png"));
            middleClick = ViewUtils.scaleImage(temp, temp.getWidth() * 0.05f * SCALE, temp.getHeight() * SCALE * 0.05f);

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadGoBackButton() {
        buttons = new AbstractMenuButton[1];
        BufferedImage[] goBackButtonImage = new BufferedImage[3];
        BufferedImage temp = null;
        try {
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/indietro1.png"));
            goBackButtonImage[0] = ViewUtils.scaleImage(temp, temp.getWidth() * 0.35f*SCALE , temp.getHeight() * SCALE * 0.35f);

            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/indietro2.png"));
            goBackButtonImage[1] = ViewUtils.scaleImage(temp, temp.getWidth() * 0.40f *SCALE , temp.getHeight() * SCALE * 0.40f);

            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/indietro3.png"));
            goBackButtonImage[2] = ViewUtils.scaleImage(temp, temp.getWidth() * 0.40f*SCALE , temp.getHeight() * SCALE * 0.40f);

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

    public void draw(Graphics2D g2){
        view.getMainMenu().drawBackground(g2);
        //disegna il bottone "indietro"
        drawButtons(g2);
        //si prepara a scrivere le stringhe
        g2.setFont(descriptionFont);
        g2.setColor(Color.red);
        //movimento
        g2.drawString(movementExplaination, ViewUtils.getXforCenterText(movementExplaination, g2), heightFirstString);
        //attacco
        g2.drawString(attackExplaination, ViewUtils.getXforCenterText(attackExplaination, g2), heightFirstString + distanceBetweenString);
        int xLeftClickImage = ViewUtils.getXforCenterText(attackExplaination, g2) + ViewUtils.getStringLenght(attackExplaination, g2) + (int)(20* SCALE);
        int yLeftClickImage = heightFirstString + distanceBetweenString - ViewUtils.getStringHeight(attackExplaination, g2);
        g2.drawImage(leftClick, xLeftClickImage, yLeftClickImage, null);
        //parata
        g2.drawString(parryExplaination, ViewUtils.getXforCenterText(parryExplaination, g2), heightFirstString + 2*distanceBetweenString);
        int xRightClickImage = ViewUtils.getXforCenterText(parryExplaination, g2) + ViewUtils.getStringLenght(parryExplaination, g2) + (int)(20* SCALE);
        int yRightClickImage = heightFirstString + 2*distanceBetweenString - ViewUtils.getStringHeight(parryExplaination, g2);
        g2.drawImage(rightClick, xRightClickImage, yRightClickImage, null);
        //lancio
        g2.drawString(throwExplaination, ViewUtils.getXforCenterText(throwExplaination, g2), heightFirstString + 3*distanceBetweenString);
        int xMiddleClickImage = ViewUtils.getXforCenterText(throwExplaination, g2) + ViewUtils.getStringLenght(throwExplaination, g2) + (int)(20* SCALE);
        int yMiddleClickImage = heightFirstString + 3*distanceBetweenString - ViewUtils.getStringHeight(throwExplaination, g2);
        g2.drawImage(middleClick, xMiddleClickImage, yMiddleClickImage, null);
        //interagisci
        g2.drawString(interactExplaination, ViewUtils.getXforCenterText(interactExplaination, g2), heightFirstString + 4*distanceBetweenString);
        //pausa
        g2.drawString(pauseExplaination, ViewUtils.getXforCenterText(pauseExplaination, g2), heightFirstString + 5*distanceBetweenString);

    }

}
