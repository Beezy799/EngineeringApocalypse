package src.view.entityView;

import src.model.EntityStates;
import src.model.entity.EntityComplete;
import src.view.IView;
import src.view.gameWindow.GamePanel;
import src.view.playStateView.SortableElement;

import java.awt.*;
import java.awt.image.BufferedImage;

import static src.model.Constants.EntityConstants.*;
import static src.model.Constants.EntityConstants.LEFT;

public abstract class EntityView extends SortableElement {

    protected int animationCounter;
    protected int animationSpeed;
    protected int numSprite;
    protected int indexInEntityArray;
    protected int xPosOnScreen, yPosOnScreen;


    protected IView view;
    protected BufferedImage[][][] animation;    //azione,direzione,sprite
    protected EntityStates currentState = EntityStates.IDLE;
    protected EntityStates previousState = EntityStates.IDLE;
    protected int currentDirection = DOWN;
    //la posizione non coincide col punto in alto a
    // sinistra dell'immagine, quindi dobbiamo compensare
    protected int yOffset, xOffset;
    protected EntityComplete entityComplete;

    public EntityView(IView v, int i){
        view = v;
        indexInEntityArray = i;
        typeElemtToSort = 5;
        animationSpeed = 30;
    }

    @Override
    public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {

        animationCounter++;
        takeCurrentStateFromController();
        takeCurrentDirectionFromController();

        if (animationCounter > animationSpeed) {
            numSprite ++;

            if(numSprite >= getAnimationLenght())
                numSprite = 0;

            animationCounter = 0;
        }

        drawSprite(g2, xPlayerMap, yPlayerMap);

    }

    protected void drawSprite(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
        //fatti dare dal controller la posizione dell'entity
        int entityXPosMap = (int)entityComplete.getEntityController().getxPos();
        int entityYPosMap = (int)entityComplete.getEntityController().getyPos();

        //distanza dell'entita dal giocatore nella mappa
        int xDistanceFromPlayer = entityXPosMap - xPlayerMap;
        int yDistanceFromPlayer = entityYPosMap - yPlayerMap;

        //riproponiamo la stessa distanza nello schermo
        xPosOnScreen = GamePanel.CENTER_X_GAME_PANEL + xDistanceFromPlayer;
        yPosOnScreen = GamePanel.CENTER_Y_GAME_PANEL + yDistanceFromPlayer;


        //siccome la posizone dell'entità non coincide col punto in alto a sinistra dell'immagine, compensiamo con gli offset
        g2.drawImage(animation[currentState.getConstantInAnimationArray()][currentDirection][numSprite], xPosOnScreen - xOffset, yPosOnScreen - yOffset, null);


        //disegna la zona occupata dalla sprite
//        g2.drawRect(xPosOnScreen - xOffset, yPosOnScreen - yOffset,
//                animation[currentState.getConstantInAnimationArray()][currentDirection][numSprite].getWidth(),
//                animation[currentState.getConstantInAnimationArray()][currentDirection][numSprite].getHeight());
        //disegna la posizione
//        g2.fillRect(xPosOnScreen, yPosOnScreen, 5, 5);

        //disegna la sua hitbox
//        g2.setColor(Color.red);
//        int hitboxW = entityComplete.getEntityController().getHitbox().getWidth();
//        int hitboxH = entityComplete.getEntityController().getHitbox().getHeight();
//        int xoffsetH = entityComplete.getEntityController().getXhitboxOffset();
//        int yoffsetH = entityComplete.getEntityController().getYhitboxOffset();
//        g2.drawRect(xPosOnScreen - xoffsetH, yPosOnScreen - yoffsetH, hitboxW, hitboxH);

        //disegna interaction
//        g2.setColor(Color.blue);
//        int inthitboxW = entityComplete.getEntityController().getInteractionHitbox().getWidth();
//        int inthitboxH = entityComplete.getEntityController().getInteractionHitbox().getHeight();
//        int intxoffsetH = entityComplete.getEntityController().getInteractionHitbox().getWidth()/2;
//        int intyoffsetH = entityComplete.getEntityController().getInteractionHitbox().getHeight()/2;
//        g2.drawRect(xPosOnScreen - intxoffsetH, yPosOnScreen - intyoffsetH, inthitboxW, inthitboxH);
    }

    protected void takeCurrentStateFromController() {
        try {
            currentState = entityComplete.getEntityController().getCurrentState();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        //se è cambiata l'azione, resetta il valore di numSprite, visto che le azioni hanno numero sprite diverso
        if(previousState != currentState){
            previousState = currentState;
            numSprite = 0;
        }
    }

    protected void takeCurrentDirectionFromController() {

        int vectorX = (int) entityComplete.getEntityController().getMovementVector().getX();
        int vectorY = (int) entityComplete.getEntityController().getMovementVector().getY();

        if(vectorY < 0) {
            currentDirection = UP;
        }
        else if(vectorY > 0) {
            currentDirection = DOWN;
        }

        if(vectorX > 0) {
            currentDirection = RIGHT;
        }
        else if(vectorX < 0) {
            currentDirection = LEFT;
        }

    }

    protected abstract int getAnimationLenght();

    //ci serve per essere sicuri che l'entità sappia dove si trova prima di essere ordinata
    public void updatePositionForSort() {
        yPosMapForSort = (int)entityComplete.getEntityController().getyPos() - yOffset;
    }

    public void setEntityComplete(EntityComplete ec){
        entityComplete = ec;
    }
    public void reset(){}
}