package src.view;

import src.model.EntityStates;
import src.model.GameState;
import src.view.IView;
import src.view.ViewUtils;
import src.view.gameWindow.GamePanel;
import src.view.playStateView.SortableElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.model.Constants.EntityConstants.*;
import static src.view.gameWindow.GamePanel.SCALE;

import javax.imageio.ImageIO;

public class PlayerView extends SortableElement {

    private IView view;
    //campo 0 = tipo(ragazzo-ragazza), primo campo = azione, secondo = direzione, terzo = sprite
    private BufferedImage[][][][] playerAnimation;
    private static int RAGAZZO = 0, RAGAZZA = 1;
    private int gender = RAGAZZO;
    private int animationCounter; //aumenta di 1 ad ogni repaint, si azzera quando raggiunge animationspeed,
    // e in quel momento cambia sprite
    private int animationSpeed = 20;
    private int numSprite;  //indica in quale sprite dell'animazione siamo
    private EntityStates currentState = EntityStates.IDLE;
    private EntityStates previousState = EntityStates.IDLE;
    private int currenDirection = DOWN;

    //siccome il giocatore viene disegnato dal punto in alto a sinistra, bisogna aggiungere un
    //offset per disegnarlo al centro dello schermo
    public static int xOffset = GamePanel.TILES_SIZE/2 - (int)(3*GamePanel.SCALE);
    public static int yOffset = GamePanel.TILES_SIZE/2;
    //la posizione del player è sempre al centro della finestra di gioco
    public static final int xOnScreen = GamePanel.GAME_WIDTH/2 - xOffset;
    public static final int yOnScreen = GamePanel.GAME_HEIGHT/2 - yOffset;


    public PlayerView(IView v) {
        this.view = v;

        typeElemtToSort = 5;
        yPosMapForSort = view.getController().getPlayerController().getyPosPlayer();
        loadImages();
    }

    public void draw(Graphics2D g2, int x, int y) {

        getCurrentStateFromController();

        switch (currentState){
            case IDLE:
                normalDraw(g2);
                break;
            case MOVE:
                normalDraw(g2);
                break;
            case ATTACKING:
                animationSpeed = 15;
                specialDraw(g2);
                //drawOndaEnergetica(g2);
                break;
            case THROWING:
                specialDraw(g2);
                break;
            case PARRING:
                parringDraw(g2);
                //drawShield(g2);
                break;
            case DYING:
                dyingDraw(g2);
                break;
            case CFU_FOUND:
                animationSpeed = 30;
                specialDraw(g2);
                animationSpeed = 20;
                break;
        }

        try {
            //disegna il giocatore
            g2.drawImage(playerAnimation[gender][currentState.getConstantInAnimationArray()][currenDirection][numSprite],
                                                                                     xOnScreen, yOnScreen, null);

        }
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        //disegna la zona occupata dalla sprite
//        g2.drawRect(xOnScreen, yOnScreen,
//                playerAnimation[gender][currentState.getConstantInAnimationArray()][currenDirection][numSprite].getWidth(),
//                playerAnimation[gender][currentState.getConstantInAnimationArray()][currenDirection][numSprite].getHeight());

        //disegna la sua posizione come un piccolo quadratino
        g2.setColor(Color.red);
        g2.fillRect(GamePanel.CENTER_X_GAME_PANEL, GamePanel.CENTER_Y_GAME_PANEL, 5, 5);

        //disegna la hitbox del giocatore
        g2.setColor(Color.blue);
        g2.drawRect(GamePanel.CENTER_X_GAME_PANEL - view.getController().getPlayerController().getHitbox().getWidth()/2,
                       GamePanel.CENTER_Y_GAME_PANEL - GamePanel.TILES_SIZE/4,
                       view.getController().getPlayerController().getHitbox().getWidth(),
                       view.getController().getPlayerController().getHitbox().getHeight());
    }

    //finita l'animazione della morte, il gioco va nello stato game over
    private void dyingDraw(Graphics2D g2) {
        animationCounter++;

        getCurrentDirectionFromController();

        //siccome l'animazione della morte è solo in due direzioni,
        if(currenDirection == UP)
            currenDirection = LEFT;

        if(currenDirection == DOWN)
            currenDirection = RIGHT;

        if (animationCounter > animationSpeed) {
            numSprite++;

            if (numSprite >= getAnimationLenght()) {
                view.changeGameState(GameState.GAME_OVER);
                numSprite = 0;
            }
            animationCounter = 0;
        }

    }

    private void parringDraw(Graphics2D g2) {
        animationCounter++;

        getCurrentDirectionFromController();

        if (animationCounter > animationSpeed) {
            numSprite++;

            if (numSprite >= getAnimationLenght()) {
                //sblocca lo stato una volta finita l'animazione
                //il playercontroller resta nello stato attack o throw fino alla fine dell'animazione
                //finita l'anmazione, dice al controller di sbloccare lo stato e aggiornarlo

                //arrivato all'ultima sprite resta lì
                numSprite = getAnimationLenght() - 1;
               // view.getController().getPlayerController().unlockState();
            }
            animationCounter = 0;
        }

    }

    private void drawShield(Graphics2D g2) {
        animationCounter++;

        if (animationCounter > animationSpeed)
            numSprite++;

        if(numSprite > 5)
            numSprite = 0;

        BufferedImage image = null;
        try {
            if(numSprite == 0)
                image = ImageIO.read(getClass().getResourceAsStream("/res/entity/energyShield1.png"));

            else if(numSprite == 1)
                image = ImageIO.read(getClass().getResourceAsStream("/res/entity/energyShield2.png"));

            else if(numSprite == 2)
                image = ImageIO.read(getClass().getResourceAsStream("/res/entity/energyShield3.png"));

            else if(numSprite == 3)
                image = ImageIO.read(getClass().getResourceAsStream("/res/entity/energyShield4.png"));

            else if(numSprite == 4)
                image = ImageIO.read(getClass().getResourceAsStream("/res/entity/energyShield5.png"));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        image = ViewUtils.scaleImage(image, 2*GamePanel.TILES_SIZE, 2*GamePanel.TILES_SIZE);
        g2.drawImage(image, GamePanel.CENTER_X_GAME_PANEL - GamePanel.TILES_SIZE, GamePanel.CENTER_Y_GAME_PANEL - GamePanel.TILES_SIZE, null);

        if(animationCounter >= 240) {
            view.getController().getPlayerController().unlockState();
            animationCounter = 0;
        }
    }

    private void specialDraw(Graphics2D g2) {
        //disegna l'animazione fino alla fine
        animationCounter++;

        getCurrentDirectionFromController();

        if (animationCounter > animationSpeed) {
            numSprite++;

            if (numSprite >= getAnimationLenght()) {
                //sblocca lo stato una volta finita l'animazione
                //il playercontroller resta nello stato attack o throw fino alla fine dell'animazione
                //finita l'anmazione, dice al controller di sbloccare lo stato e aggiornarlo
                view.getController().getPlayerController().unlockState();
                numSprite = 0;
                animationSpeed = 20;
            }
            animationCounter = 0;
        }

    }

    private void drawOndaEnergetica(Graphics2D g2) {

        animationCounter++;
        String attackStream = "001010011010100101001011101011011001110101010100110111101011101001101110100111010100110101010";
        String attackStream2 = "10101011101111010000101101100101000011110011000111111000001010001010001001010000101111001001";
        String attackStream3 = "01110100101001101001001001101001001001101000100000010111110101001010001001010110111010001011";

        // tutto lo schermo si scurice, per rendere l'attacco più epico
        g2.setColor(Color.black);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        g2.fillRect(0,0, GamePanel.GAME_WIDTH,GamePanel.GAME_HEIGHT);

        //larghezza dei due rect che formano l'onda
        int widthAttackRect, widthattackBackgroundRect, heightAttackRect, heightAttackRectBackground;
        int x, y;

        //per disegnare le stringhe dall'alto verso il basso e viceversa
        AffineTransform defaultAt = g2.getTransform();
        AffineTransform at2;
        //font degli 0,1
        int fontSize = (int)(8*SCALE);
        Font font = new Font("Arial", Font.PLAIN, fontSize);
        g2.setFont(font);

        switch (currenDirection){
            case DOWN:
                //larghezza rettangolo interno
                widthAttackRect = 2 + animationCounter*GamePanel.TILES_SIZE/240;
                //larghezza rect esterno mezzo trasparente
                widthattackBackgroundRect = 2 + animationCounter*GamePanel.TILES_SIZE/120;

                //il rect trasparente si allarga prima, una volta arrivato alla larghezza massima si ferma
                if(widthattackBackgroundRect > 2 + GamePanel.TILES_SIZE)
                    widthattackBackgroundRect = 2 + GamePanel.TILES_SIZE;

                //rect trasparente
                x = GamePanel.CENTER_X_GAME_PANEL - widthattackBackgroundRect /2 + 4;
                y = yOnScreen + GamePanel.TILES_SIZE;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2.fillRoundRect(x, y, widthattackBackgroundRect, yOnScreen + 10, 20, 20);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

                //rect interno
                x = GamePanel.CENTER_X_GAME_PANEL - widthAttackRect /2 + 4;
                y = yOnScreen + GamePanel.TILES_SIZE;
                g2.fillRoundRect(x, y, widthAttackRect, yOnScreen + 10, 20, 20);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                //stream di bits
                g2.setColor(Color.green);
                g2.rotate(Math.PI / 2);

                //le stringhe di bit si alternano 6 volte
                if(animationCounter < 40)
                    g2.drawString(attackStream, GamePanel.CENTER_Y_GAME_PANEL, -GamePanel.CENTER_X_GAME_PANEL);
                else if(animationCounter >= 40 && animationCounter < 80)
                    g2.drawString(attackStream2, GamePanel.CENTER_Y_GAME_PANEL, -GamePanel.CENTER_X_GAME_PANEL);
                else if (animationCounter >= 80 && animationCounter < 120)
                    g2.drawString(attackStream3, GamePanel.CENTER_Y_GAME_PANEL, -GamePanel.CENTER_X_GAME_PANEL);
                else if (animationCounter >= 120 && animationCounter < 160)
                    g2.drawString(attackStream, GamePanel.CENTER_Y_GAME_PANEL, -GamePanel.CENTER_X_GAME_PANEL);
                else if (animationCounter >= 160 && animationCounter < 200)
                    g2.drawString(attackStream2, GamePanel.CENTER_Y_GAME_PANEL, -GamePanel.CENTER_X_GAME_PANEL);
                else if (animationCounter >= 200 && animationCounter < 240)
                    g2.drawString(attackStream3, GamePanel.CENTER_Y_GAME_PANEL, -GamePanel.CENTER_X_GAME_PANEL);

                g2.rotate(-Math.PI / 2);
                break;

            case UP:
                //larghezza rettangolo interno
                widthAttackRect = 2 + animationCounter*GamePanel.TILES_SIZE/240;
                //larghezza rect esterno mezzo trasparente
                widthattackBackgroundRect = 2 + animationCounter*GamePanel.TILES_SIZE/120;

                //il rect trasparente si allarga prima, una volta arrivato alla larghezza massima si ferma
                if(widthattackBackgroundRect > 2 + GamePanel.TILES_SIZE)
                    widthattackBackgroundRect = 2 + GamePanel.TILES_SIZE;

                //rect trasparente
                x = GamePanel.CENTER_X_GAME_PANEL - widthattackBackgroundRect /2 + 4;
                y = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2.fillRoundRect(x, y, widthattackBackgroundRect, yOnScreen, 20, 20);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

                //rect interno
                x = GamePanel.CENTER_X_GAME_PANEL - widthAttackRect /2 + 4;
                g2.fillRoundRect(x, y, widthAttackRect, yOnScreen, 20, 20);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                //stream di bits
                g2.setColor(Color.green);
                g2.rotate(Math.PI / 2);

                //le stringhe di bit si alternano 6 volte
                if(animationCounter < 40)
                    g2.drawString(attackStream, -GamePanel.CENTER_Y_GAME_PANEL + 2*GamePanel.TILES_SIZE, -GamePanel.CENTER_X_GAME_PANEL);
                else if(animationCounter >= 40 && animationCounter < 80)
                    g2.drawString(attackStream2, -GamePanel.CENTER_Y_GAME_PANEL + 2*GamePanel.TILES_SIZE, -GamePanel.CENTER_X_GAME_PANEL);
                else if (animationCounter >= 80 && animationCounter < 120)
                    g2.drawString(attackStream3, -GamePanel.CENTER_Y_GAME_PANEL + 2*GamePanel.TILES_SIZE, -GamePanel.CENTER_X_GAME_PANEL);
                else if (animationCounter >= 120 && animationCounter < 160)
                    g2.drawString(attackStream, -GamePanel.CENTER_Y_GAME_PANEL + 2*GamePanel.TILES_SIZE, -GamePanel.CENTER_X_GAME_PANEL);
                else if (animationCounter >= 160 && animationCounter < 200)
                    g2.drawString(attackStream2, -GamePanel.CENTER_Y_GAME_PANEL + 2*GamePanel.TILES_SIZE, -GamePanel.CENTER_X_GAME_PANEL);
                else if (animationCounter >= 200 && animationCounter < 240)
                    g2.drawString(attackStream3, -GamePanel.CENTER_Y_GAME_PANEL + 2*GamePanel.TILES_SIZE, -GamePanel.CENTER_X_GAME_PANEL);

                g2.rotate(-Math.PI / 2);
                break;

            case RIGHT:
                //altezza rettangolo interno
                heightAttackRect = 2 + animationCounter*GamePanel.TILES_SIZE/240;
                //altezza rect esterno mezzo trasparente
                heightAttackRectBackground = 2 + animationCounter*GamePanel.TILES_SIZE/120;

                //il rect trasparente si allarga prima, una volta arrivato alla larghezza massima si ferma
                if(heightAttackRectBackground > 2 + GamePanel.TILES_SIZE)
                    heightAttackRectBackground = 2 + GamePanel.TILES_SIZE;

                //rect trasparente
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2.fillRoundRect(GamePanel.CENTER_X_GAME_PANEL + 20,
                                 GamePanel.CENTER_Y_GAME_PANEL - heightAttackRectBackground/2,
                                GamePanel.GAME_WIDTH/2, heightAttackRectBackground, 20, 20);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

                //rect trasparente
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2.fillRoundRect(GamePanel.CENTER_X_GAME_PANEL + 20,
                        GamePanel.CENTER_Y_GAME_PANEL - heightAttackRectBackground/2,
                        GamePanel.GAME_WIDTH/2, heightAttackRectBackground, 20, 20);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

                //rect interno
                g2.fillRoundRect(GamePanel.CENTER_X_GAME_PANEL + 20,
                        GamePanel.CENTER_Y_GAME_PANEL - heightAttackRect/2,
                        GamePanel.GAME_WIDTH/2, heightAttackRect, 20, 20);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                g2.setColor(Color.green);

                //le stringhe di bit si alternano 6 volte
                if(animationCounter < 40)
                    g2.drawString(attackStream, GamePanel.CENTER_X_GAME_PANEL + 20, GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream, g2)/2);
                else if(animationCounter >= 40 && animationCounter < 80)
                    g2.drawString(attackStream2, GamePanel.CENTER_X_GAME_PANEL + 20, GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream2, g2)/2);
                else if (animationCounter >= 80 && animationCounter < 120)
                    g2.drawString(attackStream3, GamePanel.CENTER_X_GAME_PANEL + 20, GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream3, g2)/2);
                else if (animationCounter >= 120 && animationCounter < 160)
                    g2.drawString(attackStream, GamePanel.CENTER_X_GAME_PANEL + 20, GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream, g2)/2);
                else if (animationCounter >= 160 && animationCounter < 200)
                    g2.drawString(attackStream2, GamePanel.CENTER_X_GAME_PANEL + 20, GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream2, g2)/2);
                else if (animationCounter >= 200 && animationCounter < 240)
                    g2.drawString(attackStream3, GamePanel.CENTER_X_GAME_PANEL + 20, GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream3, g2)/2);

                break;

            case LEFT:
                //altezza rettangolo interno
                heightAttackRect = 2 + animationCounter*GamePanel.TILES_SIZE/240;
                //altezza rect esterno mezzo trasparente
                heightAttackRectBackground = 2 + animationCounter*GamePanel.TILES_SIZE/120;

                //il rect trasparente si allarga prima, una volta arrivato alla larghezza massima si ferma
                if(heightAttackRectBackground > 2 + GamePanel.TILES_SIZE)
                    heightAttackRectBackground = 2 + GamePanel.TILES_SIZE;

                //rect trasparente
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2.fillRoundRect(0 - 10,
                                 GamePanel.CENTER_Y_GAME_PANEL - heightAttackRectBackground/2,
                                 GamePanel.GAME_WIDTH/2, heightAttackRectBackground, 20, 20);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

                //rect interno
                g2.fillRoundRect(0 -10,
                        GamePanel.CENTER_Y_GAME_PANEL - heightAttackRect/2,
                        GamePanel.GAME_WIDTH/2, heightAttackRect, 20, 20);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                g2.setColor(Color.green);

                //le stringhe di bit si alternano 6 volte
                if(animationCounter < 40)
                    g2.drawString(attackStream, GamePanel.CENTER_X_GAME_PANEL - ViewUtils.getStringLenght(attackStream, g2), GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream, g2)/2);
                else if(animationCounter >= 40 && animationCounter < 80)
                    g2.drawString(attackStream2, GamePanel.CENTER_X_GAME_PANEL - ViewUtils.getStringLenght(attackStream, g2), GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream2, g2)/2);
                else if (animationCounter >= 80 && animationCounter < 120)
                    g2.drawString(attackStream3, GamePanel.CENTER_X_GAME_PANEL - ViewUtils.getStringLenght(attackStream, g2), GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream3, g2)/2);
                else if (animationCounter >= 120 && animationCounter < 160)
                    g2.drawString(attackStream, GamePanel.CENTER_X_GAME_PANEL - ViewUtils.getStringLenght(attackStream, g2), GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream, g2)/2);
                else if (animationCounter >= 160 && animationCounter < 200)
                    g2.drawString(attackStream2, GamePanel.CENTER_X_GAME_PANEL - ViewUtils.getStringLenght(attackStream, g2), GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream2, g2)/2);
                else if (animationCounter >= 200 && animationCounter < 240)
                    g2.drawString(attackStream3, GamePanel.CENTER_X_GAME_PANEL - ViewUtils.getStringLenght(attackStream, g2), GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream3, g2)/2);

                break;
        }

        if(animationCounter >= 240) {
            view.getController().getPlayerController().unlockState();
            animationCounter = 0;
        }

    }

    private void normalDraw(Graphics2D g2) {
        animationCounter++;

        getCurrentDirectionFromController();

        if (animationCounter > animationSpeed) {
            numSprite++;

            if (numSprite >= getAnimationLenght()) {
                numSprite = 0;
            }
            animationCounter = 0;
        }

    }

    private void getCurrentStateFromController() {
        currentState = view.getController().getPlayerController().getCurrentState();
        //se è cambiata l'azione, resetta il valore di numSprite, visto che le azioni hanno numero sprite diverso
        if(previousState != currentState){
            previousState = currentState;
            numSprite = 0;
        }
    }

    private void getCurrentDirectionFromController() {

        float vectorX = view.getController().getPlayerController().getMovementVector().getX();
        float vectorY = view.getController().getPlayerController().getMovementVector().getY();

        if(vectorY < 0) {
            currenDirection = UP;
        }
        else if(vectorY > 0) {
            currenDirection = DOWN;
        }

        if(vectorX > 0) {
            currenDirection = RIGHT;
        }
        else if(vectorX < 0) {
            currenDirection = LEFT;
        }

    }

    public int getAnimationLenght() {
        if (currentState == EntityStates.IDLE)
            return 4;
        else if (currentState == EntityStates.MOVE)
            return 6;
        else if (currentState == EntityStates.ATTACKING)
            return 5;
        else if (currentState == EntityStates.DYING)
            return 9;
        else if (currentState == EntityStates.PARRING)
            return 2;
        else if (currentState == EntityStates.THROWING)
            return 2;
        else if (currentState == EntityStates.CFU_FOUND) {
            return 4;
        }
        return 0;
    }

    private void loadImages() {
        BufferedImage image = null; //serveper prendere immagine completa di tutte le sprite di una azione
        BufferedImage temp = null; //serve per prendere ciascuna sprite separatamente

        playerAnimation = new BufferedImage[2][][][];            //due avatar (1 campo)
        playerAnimation[RAGAZZO] = new BufferedImage[7][][];    //sette azioni (2 campo)
        playerAnimation[RAGAZZA] = new BufferedImage[7][][];

        loadIdleImages(image, temp);
        loadRunImages(image, temp);
        loadAttackImages(image, temp);
        loadDeathImages(image, temp);
        loadItemImage();
        loadParryImages(image, temp);
        loadThrowImages(image, temp);

    }

    private void loadThrowImages(BufferedImage image, BufferedImage temp) {
        playerAnimation[RAGAZZO][EntityStates.THROWING.getConstantInAnimationArray()] = new BufferedImage[4][2];        //ci sono 4 direzioni, ogni direzione ha 2 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/SpearBoy.png"));

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 25, 0, 25, 38);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.THROWING.getConstantInAnimationArray()][DOWN][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 32, 38, 32, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.THROWING.getConstantInAnimationArray()][RIGHT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 32, 38 + 34, 32, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.THROWING.getConstantInAnimationArray()][LEFT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 26, 38 + 34 + 34, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.THROWING.getConstantInAnimationArray()][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][EntityStates.THROWING.getConstantInAnimationArray()] = new BufferedImage[4][2];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/SpellGirl.png"));

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 28, 0, 28, 30);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.THROWING.getConstantInAnimationArray()][DOWN][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 25, 30, 25, 29);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.THROWING.getConstantInAnimationArray()][RIGHT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 25, 30 + 29, 25, 29);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.THROWING.getConstantInAnimationArray()][LEFT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 24, 30 + 29 + 29, 24, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.THROWING.getConstantInAnimationArray()][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void loadParryImages(BufferedImage image, BufferedImage temp) {
        playerAnimation[RAGAZZO][EntityStates.PARRING.getConstantInAnimationArray()] = new BufferedImage[4][2];        //ci sono 1 direzioni, ogni direzione ha 6 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/BowBoypc.png"));

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 25, 0, 25, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.PARRING.getConstantInAnimationArray()][DOWN][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 29, 34, 29, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.PARRING.getConstantInAnimationArray()][RIGHT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 28, 34 + 33, 28, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.PARRING.getConstantInAnimationArray()][LEFT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 32, 34 + 33 + 33, 32, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.PARRING.getConstantInAnimationArray()][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][EntityStates.PARRING.getConstantInAnimationArray()] = new BufferedImage[4][2];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/BowGirlpc.png"));

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.PARRING.getConstantInAnimationArray()][DOWN][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 32, 34, 32, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.PARRING.getConstantInAnimationArray()][RIGHT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 32, 34 + 33, 32, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.PARRING.getConstantInAnimationArray()][LEFT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 31, 34 + 33 + 33, 31, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.PARRING.getConstantInAnimationArray()][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadDeathImages(BufferedImage image, BufferedImage temp) {
        playerAnimation[RAGAZZO][EntityStates.DYING.getConstantInAnimationArray()] = new BufferedImage[4][9];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/deathBoyCorr.png"));

            for (int i = 0; i < 9; i++) {
                temp = image.getSubimage(i * 36, 0, 36, 37);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.DYING.getConstantInAnimationArray()][LEFT][i] = temp;
            }

            for (int i = 0; i < 9; i++) {
                temp = image.getSubimage(i * 36, 37, 36, 37);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.DYING.getConstantInAnimationArray()][RIGHT][i] = temp;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][EntityStates.DYING.getConstantInAnimationArray()] = new BufferedImage[4][9];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/deadGirl.png"));

            for (int i = 0; i < 9; i++) {
                temp = image.getSubimage(i * 43, 0, 43, 38);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.DYING.getConstantInAnimationArray()][LEFT][i] = temp;
            }

            for (int i = 0; i < 9; i++) {
                temp = image.getSubimage(i * 43, 38, 43, 38);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.DYING.getConstantInAnimationArray()][RIGHT][i] = temp;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void loadAttackImages(BufferedImage image, BufferedImage temp) {
        playerAnimation[RAGAZZO][EntityStates.ATTACKING.getConstantInAnimationArray()] = new BufferedImage[4][5];        //ci sono 4 direzioni, ogni direzione ha 5 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/attaccoRagazzo1.png"));

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 42);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.ATTACKING.getConstantInAnimationArray()][DOWN][i] = temp;
            }

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 35, 42, 35, 36);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.ATTACKING.getConstantInAnimationArray()][RIGHT][i] = temp;
            }

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 35, 80, 35, 37);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.ATTACKING.getConstantInAnimationArray()][LEFT][i] = temp;
            }

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 24, 119, 24, 38);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.ATTACKING.getConstantInAnimationArray()][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][EntityStates.ATTACKING.getConstantInAnimationArray()] = new BufferedImage[4][5];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/attaccoRagazza.png"));

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 25, 0, 25, 43);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.ATTACKING.getConstantInAnimationArray()][DOWN][i] = temp;
            }

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 37, 44, 37, 36);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.ATTACKING.getConstantInAnimationArray()][RIGHT][i] = temp;
            }

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 37, 83, 37, 35);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.ATTACKING.getConstantInAnimationArray()][LEFT][i] = temp;
            }

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 29, 118, 29, 37);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.ATTACKING.getConstantInAnimationArray()][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadIdleImages(BufferedImage image, BufferedImage temp) {
        playerAnimation[RAGAZZO][EntityStates.IDLE.getConstantInAnimationArray()] = new BufferedImage[4][4];        //ci sono 4 direzioni, ogni direzione ha 4 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/idleSpriteBoy.png"));

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 24, 0, 24, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.IDLE.getConstantInAnimationArray()][DOWN][i] = temp;
            }

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 21, 33, 21, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.IDLE.getConstantInAnimationArray()][RIGHT][i] = temp;
            }

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 21, 33 + 32, 21, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.IDLE.getConstantInAnimationArray()][LEFT][i] = temp;
            }

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 22, 33 + 32 + 32, 22, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.IDLE.getConstantInAnimationArray()][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][EntityStates.IDLE.getConstantInAnimationArray()] = new BufferedImage[4][4];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/IdleGirl.png"));

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 23, 0, 23, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.IDLE.getConstantInAnimationArray()][DOWN][i] = temp;
            }

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 22, 32, 22, 31);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.IDLE.getConstantInAnimationArray()][RIGHT][i] = temp;
            }

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 22, 32 + 31, 22, 31);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.IDLE.getConstantInAnimationArray()][LEFT][i] = temp;
            }

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 22, 32 + 31 + 31, 22, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.IDLE.getConstantInAnimationArray()][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadRunImages(BufferedImage image, BufferedImage temp) {
        playerAnimation[RAGAZZO][EntityStates.MOVE.getConstantInAnimationArray()] = new BufferedImage[4][6];        //ci sono 4 direzioni, ogni direzione ha 6 immagini

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/walkingSpritesBoyCorr.png"));

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 23, 0, 23, 35);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.MOVE.getConstantInAnimationArray()][DOWN][i] = temp;
            }

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 24, 35, 24, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.MOVE.getConstantInAnimationArray()][RIGHT][i] = temp;
            }

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 24, 35 + 33, 24, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.MOVE.getConstantInAnimationArray()][LEFT][i] = temp;
            }

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 26, 35 + 33 + 33, 26, 36);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.MOVE.getConstantInAnimationArray()][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][EntityStates.MOVE.getConstantInAnimationArray()] = new BufferedImage[4][6];        //ci sono 4 direzioni, ogni direzione ha 6 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/MoveGirl.png"));

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.MOVE.getConstantInAnimationArray()][DOWN][i] = temp;
            }

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 24, 34, 24, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.MOVE.getConstantInAnimationArray()][RIGHT][i] = temp;
            }

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 24, 34 + 33, 24, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.MOVE.getConstantInAnimationArray()][LEFT][i] = temp;
            }

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 31, 34 + 33 + 33, 31, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.MOVE.getConstantInAnimationArray()][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadItemImage(){
        BufferedImage image = null;
        BufferedImage temp = null;
        playerAnimation[RAGAZZO][EntityStates.CFU_FOUND.getConstantInAnimationArray()] = new BufferedImage[4][4];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/ItemBoy.png"));

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.CFU_FOUND.getConstantInAnimationArray()][DOWN][i] = temp;
            }
            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.CFU_FOUND.getConstantInAnimationArray()][UP][i] = temp;
            }
            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.CFU_FOUND.getConstantInAnimationArray()][LEFT][i] = temp;
            }
            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][EntityStates.CFU_FOUND.getConstantInAnimationArray()][RIGHT][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][EntityStates.CFU_FOUND.getConstantInAnimationArray()] = new BufferedImage[4][4];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/ItemGirl.png"));

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.CFU_FOUND.getConstantInAnimationArray()][DOWN][i] = temp;
            }
            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.CFU_FOUND.getConstantInAnimationArray()][UP][i] = temp;
            }
            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.CFU_FOUND.getConstantInAnimationArray()][LEFT][i] = temp;
            }
            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][EntityStates.CFU_FOUND.getConstantInAnimationArray()][RIGHT][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGender(int i){
        if(gender == RAGAZZO || gender == RAGAZZA)
            gender = i;
    }

    public void setYposMapToSort(int playerPos){
        this.yPosMapForSort = playerPos - yOffset;
    }

} //end class



