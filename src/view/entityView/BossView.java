package src.view.entityView;

import src.controller.Hitbox;
import src.controller.entitycontroller.BossController;
import src.model.Constants;
import src.model.EntityStates;
import src.model.entity.EnemyComplete;
import src.view.IView;
import src.view.ViewUtils;
import src.view.entityView.enemy.EnemyView;
import src.view.entityView.npc.NpcView;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.model.Constants.EntityConstants.*;
import static src.model.Constants.EntityConstants.LEFT;
import static src.model.EntityStates.*;
import static src.view.gameWindow.GamePanel.SCALE;

public class BossView extends EnemyView {
    public BossView(IView v, int i) {
        super(v, i);
        loadImages();
        xOffset = animation[0][0][0].getWidth()/2;
        yOffset = animation[0][0][0].getHeight()/2;

    }

    public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap){
        takeCurrentStateFromController();
        takeCurrentDirectionFromController();

        switch (currentState){
            default:
                animationSpeed = 30;
                normaldraw(g2, xPlayerMap, yPlayerMap);
                break;
            case THROWING:
                //drawOndaEnergetica(g2, xPlayerMap, yPlayerMap);
                drawOndaDiProva(g2, xPlayerMap, yPlayerMap);
                break;
            case ATTACKING:
                animationSpeed = 10;
                specialDraw(g2, xPlayerMap, yPlayerMap);
                break;
            case PARRING:
                parringDraw(g2, xPlayerMap, yPlayerMap);
                break;
            case DYING:
                animationSpeed = 20;
                dyingDraw(g2,xPlayerMap, yPlayerMap);
                break;
        }

        //drawLifeRect(g2);
    }

    private void drawOndaDiProva(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
        animationCounter++;

        if (animationCounter > animationSpeed) {
            numSprite ++;

            if(numSprite >= getAnimationLenght()) {
                //finita l'animazione, il nemico può cambiare stato
                ((EnemyComplete)entityComplete).getEnemyController().setStateLocked(false);
                numSprite = 0;
            }

            animationCounter = 0;
        }

        Hitbox stream = (Hitbox) (((EnemyComplete)entityComplete).getEnemyController()).getStreamHitbox();
        switch (currentDirection){
            case RIGHT:
                g2.fillRect(xPosOnScreen, yPosOnScreen, stream.getWidth(), stream.getHeight());
                break;
            case LEFT:
                g2.fillRect(0, yPosOnScreen, stream.getWidth(), stream.getHeight());
                break;
            case DOWN:
                g2.fillRect(xPosOnScreen, yPosOnScreen, stream.getWidth(), stream.getHeight());
                break;
            case UP:
                g2.fillRect(xPosOnScreen, 0, stream.getWidth(), stream.getHeight());
                break;
        }
        g2.fillRect(xPosOnScreen, yPosOnScreen, stream.getWidth(), stream.getHeight());
        drawSprite(g2, xPlayerMap, yPlayerMap);
    }

    private void parringDraw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
        animationCounter++;

        if (animationCounter > animationSpeed) {
            numSprite++;

            if (numSprite >= getAnimationLenght()) {
                //sblocca lo stato una volta finita l'animazione
                //il playercontroller resta nello stato attack o throw fino alla fine dell'animazione
                //finita l'anmazione, dice al controller di sbloccare lo stato e aggiornarlo

                //arrivato all'ultima sprite resta lì
                numSprite = 4;
                // view.getController().getPlayerController().unlockState();
            }
            animationCounter = 0;
        }
    }

    private void drawOndaEnergetica(Graphics2D g2, int xPlayerMap, int yPlayerMap) {

//    animationCounter++;
//
//    // sposta la posizione del player velocemente per far sembrare un terremoto, peccato che può dare problemi per la collisione
//    // bisogna mettere una camera indipendente dal personaggio, che potrebbe servire anche nella scena col boss. Da vedere
////        if(animationCounter % 5 == 0){
////            int x = (int)view.getController().getPlayerController().getxPosPlayer();
////            view.getController().getPlayerController().setxPosPlayer(x + (animationCounter%10==0 ? 10 : - 10));
////            if(animationCounter == 240){
////                view.getController().getPlayerController().setxPosPlayer(x -10);
////            }
////        }
//
//
//    String attackStream = "001010011010100101001011101011011001110101010100110111101011101001101110100111010100110101010";
//    String attackStream2 = "10101011101111010000101101100101000011110011000111111000001010001010001001010000101111001001";
//    String attackStream3 = "01110100101001101001001001101001001001101000100000010111110101001010001001010110111010001011";
//
//    // tutto lo schermo si scurice, per rendere l'attacco più epico
//        g2.setColor(Color.black);
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
//        g2.fillRect(0,0, GamePanel.GAME_WIDTH,GamePanel.GAME_HEIGHT);
//
//    //larghezza dei due rect che formano l'onda
//    int widthAttackRect, widthattackBackgroundRect, heightAttackRect, heightAttackRectBackground;
//    int x, y;
//
//    //per disegnare le stringhe dall'alto verso il basso e viceversa
//    AffineTransform defaultAt = g2.getTransform();
//    AffineTransform at2;
//    //font degli 0,1
//    int fontSize = (int)(8*SCALE);
//    Font font = new Font("Arial", Font.PLAIN, fontSize);
//    g2.setFont(font);
//
//    //larghezza rettangolo interno
//    widthAttackRect = 2 + animationCounter*GamePanel.TILES_SIZE/240;
//    //larghezza rect esterno mezzo trasparente
//    widthattackBackgroundRect = 2 + animationCounter*GamePanel.TILES_SIZE/120;
//
//    //il rect trasparente si allarga prima, una volta arrivato alla larghezza massima si ferma
//    if(widthattackBackgroundRect > 2 + GamePanel.TILES_SIZE) {
//        widthattackBackgroundRect = 2 + GamePanel.TILES_SIZE;
//    }
//
//    switch (currentDirection){
//        case DOWN:
//            //rect trasparente
//            x = xPosOnScreen;
//            y = yPosOnScreen + GamePanel.TILES_SIZE;
//
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
//            g2.fillRoundRect(x, y, widthattackBackgroundRect, y + 10, 20, 20);
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
//
//            //rect interno
//            g2.fillRoundRect(x, y, widthAttackRect, y + 10, 20, 20);
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//
//            //stream di bits
//            g2.setColor(Color.green);
//            g2.rotate(Math.PI / 2);
//
//            //le stringhe di bit si alternano 6 volte
//            if(animationCounter < 40)
//                g2.drawString(attackStream, GamePanel.CENTER_Y_GAME_PANEL, -GamePanel.CENTER_X_GAME_PANEL);
//            else if(animationCounter >= 40 && animationCounter < 80)
//                g2.drawString(attackStream2, GamePanel.CENTER_Y_GAME_PANEL, -GamePanel.CENTER_X_GAME_PANEL);
//            else if (animationCounter >= 80 && animationCounter < 120)
//                g2.drawString(attackStream3, GamePanel.CENTER_Y_GAME_PANEL, -GamePanel.CENTER_X_GAME_PANEL);
//            else if (animationCounter >= 120 && animationCounter < 160)
//                g2.drawString(attackStream, GamePanel.CENTER_Y_GAME_PANEL, -GamePanel.CENTER_X_GAME_PANEL);
//            else if (animationCounter >= 160 && animationCounter < 200)
//                g2.drawString(attackStream2, GamePanel.CENTER_Y_GAME_PANEL, -GamePanel.CENTER_X_GAME_PANEL);
//            else if (animationCounter >= 200 && animationCounter < 240)
//                g2.drawString(attackStream3, GamePanel.CENTER_Y_GAME_PANEL, -GamePanel.CENTER_X_GAME_PANEL);
//
//            g2.rotate(-Math.PI / 2);
//            break;
//
//        case UP:
//            //rect trasparente
//            x = xPosOnScreen;
//            y = -(heightAttackRect - yPosOnScreen);
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
//            g2.fillRoundRect(x, y, widthattackBackgroundRect, y, 20, 20);
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
//
//            //rect interno
//            g2.fillRoundRect(x, y, widthAttackRect, y, 20, 20);
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//
//            //stream di bits
//            g2.setColor(Color.green);
//            g2.rotate(Math.PI / 2);
//
//            //le stringhe di bit si alternano 6 volte
//            if(animationCounter < 40)
//                g2.drawString(attackStream, -GamePanel.CENTER_Y_GAME_PANEL + 2*GamePanel.TILES_SIZE, -GamePanel.CENTER_X_GAME_PANEL);
//            else if(animationCounter >= 40 && animationCounter < 80)
//                g2.drawString(attackStream2, -GamePanel.CENTER_Y_GAME_PANEL + 2*GamePanel.TILES_SIZE, -GamePanel.CENTER_X_GAME_PANEL);
//            else if (animationCounter >= 80 && animationCounter < 120)
//                g2.drawString(attackStream3, -GamePanel.CENTER_Y_GAME_PANEL + 2*GamePanel.TILES_SIZE, -GamePanel.CENTER_X_GAME_PANEL);
//            else if (animationCounter >= 120 && animationCounter < 160)
//                g2.drawString(attackStream, -GamePanel.CENTER_Y_GAME_PANEL + 2*GamePanel.TILES_SIZE, -GamePanel.CENTER_X_GAME_PANEL);
//            else if (animationCounter >= 160 && animationCounter < 200)
//                g2.drawString(attackStream2, -GamePanel.CENTER_Y_GAME_PANEL + 2*GamePanel.TILES_SIZE, -GamePanel.CENTER_X_GAME_PANEL);
//            else if (animationCounter >= 200 && animationCounter < 240)
//                g2.drawString(attackStream3, -GamePanel.CENTER_Y_GAME_PANEL + 2*GamePanel.TILES_SIZE, -GamePanel.CENTER_X_GAME_PANEL);
//
//            g2.rotate(-Math.PI / 2);
//            break;
//
//        case RIGHT:
//            x = xPosOnScreen;
//            y = yPosOnScreen;
//            int w = GamePanel.GAME_WIDTH/2;
//            //rect trasparente
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
//            g2.fillRoundRect(x, y, w, heightAttackRectBackground, 20, 20);
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
//
//            //rect interno
//            g2.fillRoundRect(x, y,w, heightAttackRect, 20, 20);
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//
//            g2.setColor(Color.green);
//
//            //le stringhe di bit si alternano 6 volte
//            if(animationCounter < 40)
//                g2.drawString(attackStream, GamePanel.CENTER_X_GAME_PANEL + 20, GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream, g2)/2);
//            else if(animationCounter >= 40 && animationCounter < 80)
//                g2.drawString(attackStream2, GamePanel.CENTER_X_GAME_PANEL + 20, GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream2, g2)/2);
//            else if (animationCounter >= 80 && animationCounter < 120)
//                g2.drawString(attackStream3, GamePanel.CENTER_X_GAME_PANEL + 20, GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream3, g2)/2);
//            else if (animationCounter >= 120 && animationCounter < 160)
//                g2.drawString(attackStream, GamePanel.CENTER_X_GAME_PANEL + 20, GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream, g2)/2);
//            else if (animationCounter >= 160 && animationCounter < 200)
//                g2.drawString(attackStream2, GamePanel.CENTER_X_GAME_PANEL + 20, GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream2, g2)/2);
//            else if (animationCounter >= 200 && animationCounter < 240)
//                g2.drawString(attackStream3, GamePanel.CENTER_X_GAME_PANEL + 20, GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream3, g2)/2);
//            break;
//
//        case LEFT:
//            y = yPosOnScreen;
//            x = -(widthAttackRect - xPosOnScreen);
//            //rect trasparente
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
//            g2.fillRoundRect(x, y, widthAttackRect, heightAttackRect, 20, 20);
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
//
//            //rect interno
//            g2.fillRoundRect(0 -10,
//                    GamePanel.CENTER_Y_GAME_PANEL - heightAttackRect/2,
//                    GamePanel.GAME_WIDTH/2, heightAttackRect, 20, 20);
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//
//            g2.setColor(Color.green);
//
//            //le stringhe di bit si alternano 6 volte
//            if(animationCounter < 40)
//                g2.drawString(attackStream, GamePanel.CENTER_X_GAME_PANEL - ViewUtils.getStringLenght(attackStream, g2), GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream, g2)/2);
//            else if(animationCounter >= 40 && animationCounter < 80)
//                g2.drawString(attackStream2, GamePanel.CENTER_X_GAME_PANEL - ViewUtils.getStringLenght(attackStream, g2), GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream2, g2)/2);
//            else if (animationCounter >= 80 && animationCounter < 120)
//                g2.drawString(attackStream3, GamePanel.CENTER_X_GAME_PANEL - ViewUtils.getStringLenght(attackStream, g2), GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream3, g2)/2);
//            else if (animationCounter >= 120 && animationCounter < 160)
//                g2.drawString(attackStream, GamePanel.CENTER_X_GAME_PANEL - ViewUtils.getStringLenght(attackStream, g2), GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream, g2)/2);
//            else if (animationCounter >= 160 && animationCounter < 200)
//                g2.drawString(attackStream2, GamePanel.CENTER_X_GAME_PANEL - ViewUtils.getStringLenght(attackStream, g2), GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream2, g2)/2);
//            else if (animationCounter >= 200 && animationCounter < 240)
//                g2.drawString(attackStream3, GamePanel.CENTER_X_GAME_PANEL - ViewUtils.getStringLenght(attackStream, g2), GamePanel.CENTER_Y_GAME_PANEL + ViewUtils.getStringHeight(attackStream3, g2)/2);
//            break;
//    }
//
//        if(animationCounter >= 240) {
//        view.getController().getPlayerController().unlockState();
//        animationCounter = 0;
//    }

}

    public void normaldraw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
        animationCounter++;
        takeCurrentStateFromController();
        takeCurrentDirectionFromController();

        if (animationCounter > animationSpeed) {
            numSprite++;

            if (numSprite >= getAnimationLenght())
                numSprite = 0;

            animationCounter = 0;
        }

        //per far capire che è stato colpito
        if(currentState == EntityStates.HITTED){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            drawSprite(g2, xPlayerMap, yPlayerMap);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        else {
            drawSprite(g2, xPlayerMap, yPlayerMap);
        }

    }

    protected void loadImages() {
        animation = new BufferedImage[6][][];
        loadRunAnimation();
        loadIdleAnimation();
        loadOndaAnimation();
        loadPugnoAnimation();
        loadShieldAnimation();
    }

    private void loadShieldAnimation() {
        BufferedImage image = null;
        BufferedImage temp = null;
        animation[PARRING.getConstantInAnimationArray()] = new BufferedImage[4][10];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/bossPugnoScudo.png"));

            for(int direction = 0; direction < 4; direction++) {
                for(int index = 0; index < 5; index++) {
                    temp = image.getSubimage(index*42, 168 + direction*42, 42, 42);
                    temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE*1.5f, temp.getHeight()*GamePanel.SCALE*1.5f);
                    animation[PARRING.getConstantInAnimationArray()][direction][index] = temp;
                }
            }
            for(int i = 0; i < 5; i++){
                temp = image.getSubimage(i*42, 336, 42, 42);
                temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE*1.5f, temp.getHeight()*GamePanel.SCALE*1.5f);
                animation[PARRING.getConstantInAnimationArray()][Constants.EntityConstants.DOWN][5 + i] = temp;
                animation[PARRING.getConstantInAnimationArray()][Constants.EntityConstants.UP][5 + i] = temp;
                animation[PARRING.getConstantInAnimationArray()][Constants.EntityConstants.LEFT][5 + i] = temp;
                animation[PARRING.getConstantInAnimationArray()][Constants.EntityConstants.RIGHT][5 + i] = temp;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPugnoAnimation() {
        BufferedImage image = null;
        BufferedImage temp = null;
        animation[ATTACKING.getConstantInAnimationArray()] = new BufferedImage[4][5];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/bossPugnoScudo.png"));

            for(int direction = 0; direction < 4; direction++) {
                for(int index = 0; index < 5; index++) {
                    temp = image.getSubimage(index*42, direction*42, 42, 42);
                    temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE*1.5f, temp.getHeight()*GamePanel.SCALE*1.5f);
                    animation[ATTACKING.getConstantInAnimationArray()][direction][index] = temp;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadOndaAnimation() {
        BufferedImage image = null;
        BufferedImage temp = null;
        animation[THROWING.getConstantInAnimationArray()] = new BufferedImage[4][5];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/bossMovimentiOnda1.png"));

            for(int direction = 0; direction < 4; direction++) {
                for(int index = 0; index < 5; index++) {
                    temp = image.getSubimage(index*42, 42*5 + direction*42, 42, 42);
                    temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE*1.5f, temp.getHeight()*GamePanel.SCALE*1.5f);
                    animation[THROWING.getConstantInAnimationArray()][direction][index] = temp;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRunAnimation() {
        BufferedImage image = null;
        BufferedImage temp = null;
        animation[MOVE.getConstantInAnimationArray()] = new BufferedImage[4][3];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/entity/bossMovimentiOnda1.png"));

            for(int direction = 0; direction < 4; direction++) {
                for(int index = 0; index < 3; index++) {
                    temp = image.getSubimage(index*42, 42 + direction*42, 42, 42);
                    temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE*1.5f, temp.getHeight()*GamePanel.SCALE*1.5f);
                    animation[MOVE.getConstantInAnimationArray()][direction][index] = temp;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadIdleAnimation() {
        animation[EntityStates.IDLE.getConstantInAnimationArray()] = new BufferedImage[4][1];

        animation[EntityStates.IDLE.getConstantInAnimationArray()][Constants.EntityConstants.DOWN][0] = animation[MOVE.getConstantInAnimationArray()][Constants.EntityConstants.DOWN][1];
        animation[EntityStates.IDLE.getConstantInAnimationArray()][Constants.EntityConstants.LEFT][0] = animation[MOVE.getConstantInAnimationArray()][Constants.EntityConstants.LEFT][1];
        animation[EntityStates.IDLE.getConstantInAnimationArray()][Constants.EntityConstants.RIGHT][0] = animation[MOVE.getConstantInAnimationArray()][Constants.EntityConstants.RIGHT][1];
        animation[EntityStates.IDLE.getConstantInAnimationArray()][Constants.EntityConstants.UP][0] = animation[MOVE.getConstantInAnimationArray()][Constants.EntityConstants.UP][1];
    }

    @Override
    protected int getAnimationLenght() {
        switch (currentState){
            case IDLE:
            case RECHARGE:
            case SPEAKING:
                return 1;
            case MOVE:
            case CHASE:
                return 3;
            case THROWING:
            case ATTACKING:
                return 5;
            case PARRING:
                return 10;
        }
        return 0;
    }

    protected void loadDialogues() {

    }
}
