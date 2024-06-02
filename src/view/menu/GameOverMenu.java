package src.view.menu;

import src.model.GameState;
import src.view.IView;
import src.view.ViewUtils;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.view.gameWindow.GamePanel.SCALE;

public class GameOverMenu extends AbstractMenu {
    //bottone torna alla home
    //bottone rinuncia agli studi
    private String homeIconPath = "/res/pauseMenu/bottoneHome.png";

    int fontSize = (int)(15*SCALE);
    Font gameOverFont = new Font("Arial", Font.PLAIN, fontSize);
    private String gameOverString = "mi spiace, non le posso mettere più di 17...";
    private BufferedImage gameOverImage;
    private GameOverMenuButton home;
    private int yGameOverImage, yGameOverString;
    private int yDistance = (int)(50*SCALE);

    public GameOverMenu(IView v){
        loadGameOverImage();
        yGameOverImage = GamePanel.GAME_HEIGHT/2 - gameOverImage.getHeight();
        yGameOverString = yGameOverImage + gameOverImage.getHeight() + yDistance;
        buttons = new AbstractMenuButton[2];
        createButtons(v);
    }

    private void loadGameOverImage() {
        BufferedImage temp = null;
        try {
            temp = ImageIO.read(getClass().getResourceAsStream("/res/game over.png"));
            temp = ViewUtils.scaleImage(temp, (int)200* SCALE, (int)80* SCALE);
            gameOverImage = temp;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createButtons(IView v) {
        home = new GameOverMenuButton(v, homeIconPath, 0, 0);
        int centeredX = GamePanel.GAME_WIDTH/2 - home.bounds.width/2;
        home.bounds.setBounds(centeredX, yGameOverString + yDistance, home.bounds.width, home.bounds.height);
        buttons[0] = home;

        //rettangolo
        int widthQuitButton = (int)(250*SCALE);
        int heightQuitButton = (int)(14*SCALE);
        int xQuitButton = GamePanel.GAME_WIDTH/2 - widthQuitButton/2;
        int yQuitButton = home.bounds.y + home.bounds.height + yDistance;
        Rectangle quitRectangle = new Rectangle(xQuitButton,yQuitButton,widthQuitButton,heightQuitButton);
        //immagini
        BufferedImage[] quitImages = new BufferedImage[3];
        BufferedImage temp;
        try {
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/rinuncia1.png"));
            quitImages[0] = ViewUtils.scaleImage(temp, widthQuitButton, heightQuitButton);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/rinuncia2.png"));
            quitImages[1] = ViewUtils.scaleImage(temp, widthQuitButton, heightQuitButton);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/rinuncia3.png"));
            quitImages[2] = ViewUtils.scaleImage(temp, widthQuitButton, heightQuitButton);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //creiamo bottone con rettangolo+immagini
        buttons[1] = new MenuButton(quitImages, quitRectangle, GameState.QUIT);

    }

    public void draw(Graphics2D g2){
        drawBackground(g2);
        drawButtons(g2);

        g2.setFont(gameOverFont);
        g2.setColor(Color.CYAN);
        int centeredXGameOverString = ViewUtils.getXforCenterText(gameOverString, g2);
        g2.drawString(gameOverString, centeredXGameOverString, yGameOverString);

        int centeredXImage = GamePanel.GAME_WIDTH/2 - gameOverImage.getWidth()/2;
        g2.drawImage(gameOverImage, centeredXImage, yGameOverImage, null);
    }

    private void drawBackground(Graphics2D g2) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
