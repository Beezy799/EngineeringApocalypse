package src.view.menu;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.model.GameState;
import src.view.ViewUtils;

import static src.view.gameWindow.GamePanel.SCALE;
import static src.view.gameWindow.GamePanel.GAME_HEIGHT;
import static src.view.gameWindow.GamePanel.GAME_WIDTH;

public class MainMenu extends AbstractMenu{
    
    private BufferedImage[] animatedBackground;
    //per distanziare i bottoni ugualmente 
    private final int distanceBetweenButtons = (int)(35 * SCALE);

    //sono due contatori uno per scorrere le immagini ed uno per contare il tempo
    private int indexBackground, counterbackground;
    private final int backgroundSpeed = 10;
    //contatori per far apparire il titolo lentamente 
    private int counterTitle;
    private int timer = 120*10;

    //crediti
    private String credits = "©GNgame Production";

    private BufferedImage title;
    private int titleX, titleY; 


    public MainMenu(){

        //lo eredita
        buttons = new AbstractMenuButton[5];

        createPlayButton();
        createOptionButton();
        createResumeButton();
        createCommandsButton();
        createQuitButton();
        loadBackgroundImages();
        createTitle();

    }

    private void createTitle() {
        try {
            title = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/logo.png"));
            title = ViewUtils.scaleImage(title, title.getWidth() * 0.8f * SCALE, title.getHeight() * SCALE * 0.8f);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        titleX = ViewUtils.getCenteredXPos(title.getWidth());
        titleY = GAME_HEIGHT/4;
    }

    //primo bottone : iscriviti
    private void createPlayButton() {
        //rettangolo
        int widthPlayButton = (int)(110*SCALE);
        int heightPlayButton = (int)(16*SCALE);
        int xPlayButton = ViewUtils.getCenteredXPos(widthPlayButton);
        int yPlayButton = (int)(250 * SCALE);
        Rectangle playRectangle = new Rectangle(xPlayButton,yPlayButton,widthPlayButton,heightPlayButton);
        //immagini
        BufferedImage[] playImages = new BufferedImage[3];
        BufferedImage temp;
        try {
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/iscriviti1.png"));
            playImages[0] = ViewUtils.scaleImage(temp, widthPlayButton, heightPlayButton);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/iscriviti2.png"));
            playImages[1] = ViewUtils.scaleImage(temp, widthPlayButton, heightPlayButton);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/iscriviti3.png"));
            playImages[2] = ViewUtils.scaleImage(temp, widthPlayButton, heightPlayButton);  
        }
        catch (IOException e) {
            e.printStackTrace();
        }       
        //creiamo bottone con rettangolo+immagini
        buttons[0] = new MenuButton(playImages, playRectangle, GameState.SELECT_AVATAR);
    }

    //secondo bottone: opzioni
    private void createOptionButton() {
        //rettangolo
        int widthOptionButton = (int)(270*SCALE);
        int heightOptionButton = (int)(16*SCALE);
        int xOptionButton = ViewUtils.getCenteredXPos(widthOptionButton);
        int yOptionButton =  (int)(250 * SCALE) + distanceBetweenButtons;
        ;
        Rectangle optionRectangle = new Rectangle(xOptionButton,yOptionButton,widthOptionButton,heightOptionButton);
        //immagini
        BufferedImage[] optionImages = new BufferedImage[3];
        BufferedImage temp;
        try {
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/opzioni1.png"));
            optionImages[0] = ViewUtils.scaleImage(temp, widthOptionButton, heightOptionButton);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/opzioni2.png"));
            optionImages[1] = ViewUtils.scaleImage(temp, widthOptionButton, heightOptionButton);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/opzioni3.png"));
            optionImages[2] = ViewUtils.scaleImage(temp, widthOptionButton, heightOptionButton);    
        }
        catch (IOException e) {
            e.printStackTrace();
        }       
        //creiamo bottone con rettangolo+immagini
        buttons[1] = new MenuButton(optionImages, optionRectangle, GameState.OPTIONS); 
    }

    //terzo bottone: riprendi
    private void createResumeButton() {
        //rettangolo
        int widthRestartButton = (int)(300*SCALE);
        int heightRestartButton = (int)(17*SCALE);
        int xRestartButton = ViewUtils.getCenteredXPos(widthRestartButton);
        int yRestartButton = (int)(250 * SCALE) + 2*distanceBetweenButtons;
        Rectangle restartRectangle = new Rectangle(xRestartButton,yRestartButton,widthRestartButton,heightRestartButton);
        //immagini
        BufferedImage[] restartImages = new BufferedImage[3];
        BufferedImage temp;
        try {
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/riprendi1.png"));
            restartImages[0] = ViewUtils.scaleImage(temp, widthRestartButton, heightRestartButton);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/riprendi2.png"));
            restartImages[1] = ViewUtils.scaleImage(temp, widthRestartButton, heightRestartButton);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/riprendi3.png"));
            restartImages[2] = ViewUtils.scaleImage(temp, widthRestartButton, heightRestartButton); 
        }
        catch (IOException e) {
            e.printStackTrace();
        }       
        //creiamo bottone con rettangolo+immagini
        buttons[2] = new MenuButton(restartImages, restartRectangle, GameState.COMMAND_EXPLAINATION);
       
    }

    //quarto bottone, comandi
    private void createCommandsButton() {
        //rettangolo
        int widthCommandButton = (int)(120*SCALE);
        int heightCommandButton = (int)(12*SCALE);
        int xCommandButton = ViewUtils.getCenteredXPos(widthCommandButton);
        int yCommandButton = (int)(250 * SCALE) + 3*distanceBetweenButtons;
        Rectangle quitRectangle = new Rectangle(xCommandButton,yCommandButton,widthCommandButton,heightCommandButton);
        //immagini
        BufferedImage[] commandImages = new BufferedImage[3];
        BufferedImage temp;
        try {
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/comandi1.png"));
            commandImages[0] = ViewUtils.scaleImage(temp, widthCommandButton, heightCommandButton);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/comandi2.png"));
            commandImages[1] = ViewUtils.scaleImage(temp, widthCommandButton, heightCommandButton);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/comandi3.png"));
            commandImages[2] = ViewUtils.scaleImage(temp, widthCommandButton, heightCommandButton);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //creiamo bottone con rettangolo+immagini
        buttons[3] = new MenuButton(commandImages, quitRectangle, GameState.COMMAND_EXPLAINATION);
    }

    //quinto bottone: rinuncia
    private void createQuitButton() {
        //rettangolo
        int widthQuitButton = (int)(250*SCALE); 
        int heightQuitButton = (int)(14*SCALE);
        int xQuitButton = ViewUtils.getCenteredXPos(widthQuitButton);
        int yQuitButton = (int)(250 * SCALE) + 4*distanceBetweenButtons;
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
        buttons[4] = new MenuButton(quitImages, quitRectangle, GameState.QUIT);
        
    }
   
    //sono le immagini che insieme formano l'animazione sullo sfondo
    private void loadBackgroundImages() {
        animatedBackground = new BufferedImage[24];
        BufferedImage temp = null;
        try {
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-0.png"));
            animatedBackground[0] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-1.png"));
            animatedBackground[1] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-2.png"));
            animatedBackground[2] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-3.png"));
            animatedBackground[3] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-4.png"));
            animatedBackground[4] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-5.png"));
            animatedBackground[5] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-6.png"));
            animatedBackground[6] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-7.png"));
            animatedBackground[7] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-8.png"));
            animatedBackground[8] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-9.png"));
            animatedBackground[9] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-10.png"));
            animatedBackground[10] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-11.png"));
            animatedBackground[11] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-12.png"));
            animatedBackground[12] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-13.png"));
            animatedBackground[13] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-14.png"));
            animatedBackground[14] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-15.png"));
            animatedBackground[15] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-16.png"));
            animatedBackground[16] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-17.png"));
            animatedBackground[17] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-18.png"));
            animatedBackground[18] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-19.png"));
            animatedBackground[19] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-20.png"));
            animatedBackground[20] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-21.png"));
            animatedBackground[21] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-22.png"));
            animatedBackground[22] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
            temp = ImageIO.read(getClass().getResourceAsStream("/res/backgroundMenu/sfondoMenuIniziale-23.png"));
            animatedBackground[23] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
        } 
        catch (IOException e) {
            e.printStackTrace();

        }
    }

    //disegna background del menu e bottoni ,crediti,titolo
    public void draw(Graphics2D g2) {

        drawBackground(g2);
        //ereditato
        drawButtons(g2);

        drawCredits(g2);

        drawTitle(g2);
     }
    
    //ogni tot centesimi di secondo, cambia l'immagine. in questo modo sembra che lo schermo sia in movimento
    public void drawBackground(Graphics2D g2) {
        //ogni 1/120 sec aumenta 
        counterbackground++;
        g2.drawImage(animatedBackground[indexBackground], 0, 0, null);
        if(counterbackground == backgroundSpeed) {
            indexBackground++;
            counterbackground = 0;
        }
        if(indexBackground == 23)
            indexBackground = 0;    
    
    }

    private void drawCredits(Graphics2D g2) {
        g2.setColor(Color.red);
        int x = ViewUtils.getXforCenterText(credits, g2);
        g2.drawString(credits, x, GAME_HEIGHT - (int)(2*SCALE));
    }   

    // il titolo diventa piano piano meno trasparente, il valore alpha è quello che indica la trasparenza e varia tra 0 e 1
    private void drawTitle(Graphics2D g2) {
        counterTitle++;
        if(counterTitle < timer) {
            float alPhaValue = (float) counterTitle/(timer);    //quando il counter = timer il titolo è completamente visibile
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alPhaValue));   //setta il valore di trasparenza
        }
        g2.drawImage(title, titleX, titleY, null);
    }

    public AbstractMenuButton getPlayButton(){
        return buttons[0];// che è il bottone iscriviti
    }

    public AbstractMenuButton getOptionButton(){
        return buttons[1];
    }

    public AbstractMenuButton getResumeButton(){
        return buttons[2];
    }

    public AbstractMenuButton getQuitButton(){
        return buttons[3];
    }

}
