package src.view.entityView;

import src.controller.Hitbox;
import src.controller.entitycontroller.BossController;
import src.controller.entitycontroller.enemy.EnemyController;
import src.model.Constants;
import src.model.EntityStates;
import src.model.GameState;
import src.model.entity.EnemyComplete;
import src.view.IView;
import src.view.ViewUtils;
import src.view.entityView.enemy.EnemyView;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.model.Constants.EntityConstants.*;
import static src.model.Constants.EntityConstants.LEFT;
import static src.model.EntityStates.*;
import static src.view.gameWindow.GamePanel.*;

public class BossView extends EnemyView {

    private String[][] dialogues;
    private int dialogueLine, dialogueIndex;
    private String attackStream = "0010100110101001010010111010110110011101010101001101111010111010011011101001110101001101010100010100101000100100001101101010111101000110001100110";
    private String attackStream2 = "101010111011110100001011011001010000111100110001111110000010100010100010010100001011110010010011101101001000101101011011001001011010101001001010";
    private String attackStream3 = "011101001010011010010010011010010010011010001000000101111101010010100010010101101110100010110001010100110100100010010001000100111010100011000101";

    //larghezza dei due rect che formano l'onda
    private int widthStreamAttackRect, widthStreamAttackBackgroundRect, heightStreamAttackRect, heightStreamAttackRectBackground;
    private int xStreamAttack, yStreamAttack;
    private int fontSize = (int)(8*SCALE);
    //    //font degli 0,1
    private Font streamFont = new Font("Arial", Font.PLAIN, fontSize);

    private int attackStreamCounter;

    public BossView(IView v, int i) {
        super(v, i);
        loadImages();
        loadDialogues();
        xOffset = animation[0][0][0].getWidth()/2;
        yOffset = animation[0][0][0].getHeight()/2;
    }

    public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap){
        takeCurrentStateFromController();
        takeCurrentDirectionFromController();
        view.getPlayStateView().setEarthSheakeEffect(false);
        view.getPlayStateView().getScreenOverlay().setBossSpecialAttack(false);

        switch (currentState){
            default:
                animationSpeed = 30;
                normaldraw(g2, xPlayerMap, yPlayerMap);
                break;
            case THROWING:
                animationSpeed = 20;
                drawOndaEnergetica(g2, xPlayerMap, yPlayerMap);
                break;
            case ATTACKING:
                animationSpeed = 10;
                specialDraw(g2, xPlayerMap, yPlayerMap);
                break;

            case PARRING:
                animationSpeed = 15;
                specialDraw(g2, xPlayerMap, yPlayerMap);
                break;
        }
    }

    private void drawOndaEnergetica(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
    attackStreamCounter++;

    preparationAnimation();
    drawSprite(g2, xPlayerMap, yPlayerMap);

    //l'onda parte solo dopo che ha finito l'animazione
    if(numSprite < getAnimationLenght() - 1)
        return;

    makeEpicThisAttack(g2);

    g2.setFont(streamFont);

    switch (currentDirection){
        case DOWN:
            //larghezza rettangolo interno
            widthStreamAttackRect = 2 + attackStreamCounter*GamePanel.TILES_SIZE/240;
            //larghezza rect esterno mezzo trasparente
            widthStreamAttackBackgroundRect = 2 + attackStreamCounter*GamePanel.TILES_SIZE/120;

            //il rect trasparente si allarga prima, una volta arrivato alla larghezza massima si ferma
            if(widthStreamAttackBackgroundRect > 2 + GamePanel.TILES_SIZE) {
                widthStreamAttackBackgroundRect = 2 + GamePanel.TILES_SIZE;
            }

            heightStreamAttackRect = GAME_HEIGHT;
            heightStreamAttackRectBackground = GAME_HEIGHT;

            //rect trasparente
            xStreamAttack = xPosOnScreen - widthStreamAttackBackgroundRect/2;
            yStreamAttack = yPosOnScreen + GamePanel.TILES_SIZE;

            //rect trasparente
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2.fillRoundRect(xStreamAttack, yStreamAttack, widthStreamAttackBackgroundRect, heightStreamAttackRect, 20, 20);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

            //rect interno
            xStreamAttack = xPosOnScreen - widthStreamAttackRect/2;
            g2.fillRoundRect(xStreamAttack, yStreamAttack, widthStreamAttackRect, heightStreamAttackRect, 20, 20);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            //stream di bits
            g2.setColor(Color.green);
            g2.rotate(Math.PI / 2);

            xStreamAttack = -(xPosOnScreen - ViewUtils.getStringHeight(attackStream, g2)/4);
            int temp = yStreamAttack;
            yStreamAttack = xStreamAttack;
            xStreamAttack = temp;
            drawBits(g2);

            g2.rotate(-Math.PI / 2);
            break;

        case UP:
            //larghezza rettangolo interno
            widthStreamAttackRect = 2 + attackStreamCounter*GamePanel.TILES_SIZE/240;
            //larghezza rect esterno mezzo trasparente
            widthStreamAttackBackgroundRect = 2 + attackStreamCounter*GamePanel.TILES_SIZE/120;

            //il rect trasparente si allarga prima, una volta arrivato alla larghezza massima si ferma
            if(widthStreamAttackBackgroundRect > 2 + GamePanel.TILES_SIZE) {
                widthStreamAttackBackgroundRect = 2 + GamePanel.TILES_SIZE;
            }

            heightStreamAttackRect = GAME_HEIGHT;
            heightStreamAttackRectBackground = GAME_HEIGHT;

            xStreamAttack = xPosOnScreen - widthStreamAttackBackgroundRect/2;
            yStreamAttack = -(heightStreamAttackRect-yPosOnScreen);

            //rect trasparente
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2.fillRoundRect(xStreamAttack, yStreamAttack, widthStreamAttackBackgroundRect, heightStreamAttackRect, 20, 20);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

            //rect interno
            xStreamAttack = xPosOnScreen - widthStreamAttackRect/2;
            g2.fillRoundRect(xStreamAttack, yStreamAttack, widthStreamAttackRect, heightStreamAttackRect, 20, 20);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            //stream di bits
            g2.setColor(Color.green);
            g2.rotate(Math.PI / 2);

            int streamLength = ViewUtils.getStringLenght(attackStream, g2);
            xStreamAttack = -(xPosOnScreen - ViewUtils.getStringHeight(attackStream, g2)/4);
            yStreamAttack = xStreamAttack;
            xStreamAttack = yPosOnScreen-streamLength;

            //le stringhe di bit si alternano 6 volte
            drawBits(g2);
            g2.rotate(-Math.PI / 2);
            drawSprite(g2, xPlayerMap, yPlayerMap);
            break;

        case RIGHT:
            //larghezza rettangolo interno
            heightStreamAttackRect = 2 + attackStreamCounter*GamePanel.TILES_SIZE/240;
            //larghezza rect esterno mezzo trasparente
            heightStreamAttackRectBackground = 2 + attackStreamCounter*GamePanel.TILES_SIZE/120;

            //il rect trasparente si allarga prima, una volta arrivato alla larghezza massima si ferma
            if(heightStreamAttackRectBackground > 2 + GamePanel.TILES_SIZE) {
                heightStreamAttackRectBackground = 2 + GamePanel.TILES_SIZE;
            }

            widthStreamAttackRect = GAME_WIDTH;
            widthStreamAttackBackgroundRect = GAME_WIDTH;

            xStreamAttack = xPosOnScreen + TILES_SIZE/2;
            yStreamAttack = yPosOnScreen - heightStreamAttackRectBackground/2;

            //rect trasparente
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2.fillRoundRect(xStreamAttack, yStreamAttack, widthStreamAttackBackgroundRect, heightStreamAttackRectBackground, 20, 20);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

            //rect interno
            yStreamAttack = yPosOnScreen - heightStreamAttackRect/2;
            g2.fillRoundRect(xStreamAttack, yStreamAttack, widthStreamAttackRect, heightStreamAttackRect, 20, 20);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            //stringhe
            g2.setColor(Color.green);
            yStreamAttack = yPosOnScreen;

            //le stringhe di bit si alternano 6 volte
            drawBits(g2);
            break;

        case LEFT:
            //larghezza rettangolo interno
            heightStreamAttackRect = 2 + attackStreamCounter*GamePanel.TILES_SIZE/240;
            //larghezza rect esterno mezzo trasparente
            heightStreamAttackRectBackground = 2 + attackStreamCounter*GamePanel.TILES_SIZE/120;

            //il rect trasparente si allarga prima, una volta arrivato alla larghezza massima si ferma
            if(heightStreamAttackRectBackground > 2 + GamePanel.TILES_SIZE) {
                heightStreamAttackRectBackground = 2 + GamePanel.TILES_SIZE;
            }

            widthStreamAttackRect = GAME_WIDTH;
            widthStreamAttackBackgroundRect = GAME_WIDTH;

            xStreamAttack = xPosOnScreen - widthStreamAttackRect - TILES_SIZE/2;
            yStreamAttack = yPosOnScreen - heightStreamAttackRectBackground/2;

            //rect trasparente
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2.fillRoundRect(xStreamAttack, yStreamAttack, widthStreamAttackBackgroundRect, heightStreamAttackRectBackground, 20, 20);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

            //rect interno
            yStreamAttack = yPosOnScreen - heightStreamAttackRect/2;
            g2.fillRoundRect(xStreamAttack, yStreamAttack, widthStreamAttackRect, heightStreamAttackRect, 20, 20);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            //stringhe
            g2.setColor(Color.green);
            xStreamAttack = xPosOnScreen - ViewUtils.getStringLenght(attackStream, g2) - TILES_SIZE/2;
            yStreamAttack = yPosOnScreen + ViewUtils.getStringHeight(attackStream, g2)/2;
            //le stringhe di bit si alternano 6 volte
            drawBits(g2);
            break;
    }

    if(attackStreamCounter >= 240) {
        resetValuesAfterStream();
    }

}

    private void resetValuesAfterStream() {
        view.getPlayStateView().setEarthSheakeEffect(false);
        ((EnemyComplete)entityComplete).getEnemyController().setStateLocked(false);
        attackStreamCounter = 0;
        widthStreamAttackBackgroundRect = 0;
        widthStreamAttackRect = 0;
        heightStreamAttackRect = 0;
        heightStreamAttackRectBackground = 0;
    }

    private void drawBits(Graphics2D g2) {
        if(attackStreamCounter < 40)
            g2.drawString(attackStream, xStreamAttack, yStreamAttack);
        else if(attackStreamCounter >= 40 && attackStreamCounter < 80)
            g2.drawString(attackStream2, xStreamAttack, yStreamAttack);
        else if (attackStreamCounter >= 80 && attackStreamCounter < 120)
            g2.drawString(attackStream3, xStreamAttack, yStreamAttack);
        else if (attackStreamCounter >= 120 && attackStreamCounter < 160)
            g2.drawString(attackStream, xStreamAttack, yStreamAttack);
        else if (attackStreamCounter >= 160 && attackStreamCounter < 200)
            g2.drawString(attackStream2, xStreamAttack, yStreamAttack);
        else if (attackStreamCounter >= 200 && attackStreamCounter < 240)
            g2.drawString(attackStream3, xStreamAttack, yStreamAttack);
    }

    private void makeEpicThisAttack(Graphics2D g2) {
        // tutto lo schermo si scurice, per rendere l'attacco più epico
        view.getPlayStateView().getScreenOverlay().setBossSpecialAttack(true);
        view.getPlayStateView().setEarthSheakeEffect(true);
    }

    private void preparationAnimation() {
        animationCounter++;
        if (animationCounter > animationSpeed) {
            numSprite ++;

            if(numSprite >= getAnimationLenght()) {
                //finita l'animazione, il nemico può cambiare stato
                //((EnemyComplete)entityComplete).getEnemyController().setStateLocked(false);
                numSprite = getAnimationLenght() - 1;
            }
            animationCounter = 0;
        }
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
        dialogues = new String[2][];
        dialogues[0] = new String[3];
        dialogues[1] = new String[3];

        dialogues[0][0] = "Ti stavo aspettando, \n ho visto come correvi in giro per la facoltà";
        dialogues[0][1] = "Il tuo viaggio si ferma quì. \n Hai imparato bene, <ma non hai imparato tutto<...";
        dialogues[0][2] = "Non sai sfruttare al massimo la potenza di <Vim< e copilot, \n ora ti mostrerò cosa è capace di fare un vero programmatore!";

        dialogues[1][0] = "Ahh, i tuoi design pattern mi hanno veramente stupito..";
        dialogues[1][1] = "Dannazione, sei molto più bravo di quanto pensassi, \n tutta colpa di chi mi ha programmato così debole!";
        dialogues[1][2] = "Ecco la tua <laurea<, te la sei meritata";
    }

    public void reset(){
        dialogueIndex = 0;
        dialogueLine = 0;
        view.getCutsceneView().reset();
    }

    public void setNextDialogueLine(){
        dialogueLine++;

        if(dialogueLine >= dialogues[dialogueIndex].length && dialogueIndex == 0) {
            dialogueLine = dialogues[dialogueIndex].length - 1;

            setNextDialogue();
            view.getPlayStateView().getScreenOverlay().setFigthBoss(true);
            entityComplete.getEntityController().changeState(RECHARGE);
            view.getSoundManager().stopMusic();
            view.getSoundManager().loopMusic(Constants.SoundConstants.BOSS_SECOND_PHASE_MUSIC);
            GameState.actualState = GameState.PLAYING;
        }
        else if(dialogueLine >= dialogues[dialogueIndex].length && dialogueIndex == 1){
            dialogueLine = dialogues[dialogueIndex].length - 1;
            view.getController().getPlayerController().changeActualState(CFU_FOUND);
            view.getTransitionState().setPrev(GameState.DIALOGUE);
            view.getTransitionState().setNext(GameState.END_GAME);
            GameState.actualState = GameState.TRANSITION_STATE;
        }
    }

    public void setNextDialogue(){
        dialogueIndex++;
        dialogueLine = 0;
        if(dialogueIndex >= dialogues.length){
            dialogueIndex = dialogues.length -1;
        }
    }

    public String getDialogueLine(){
        return dialogues[dialogueIndex][dialogueLine];
    }

}
