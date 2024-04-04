package src.view.entityView;

import src.model.EntityStates;
import src.model.mapModel.Rooms;
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

    protected IView view;
    protected BufferedImage[][][] animation;    //azione,direzione,sprite
    protected EntityStates currentState = EntityStates.IDLE;
    protected EntityStates previousState = EntityStates.IDLE;
    protected int currentDirection = DOWN;
    //la posizione non coincide col punto in alto a
    // sinistra dell'immagine, quindi dobbiamo compensare
    protected int yOffset, xOffset;


    public EntityView(IView v, int i){
        view = v;
        indexInEntityArray = i;
        typeElemtToSort = 5;
        animationSpeed = 30;
    }

    @Override
    public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {

        animationCounter++;
        getCurrentStateFromController();
        getCurrentDirectionFromController();

        if (animationCounter > animationSpeed) {
            numSprite ++;

            if(numSprite >= getAnimationLenght())
                numSprite = 0;

            animationCounter = 0;
        }

        //fatti dare dal controller la posizione dell'entity
        int entityXPosMap = view.getModel().getEntityXpos(this);
        int entityYPosMap = view.getModel().getEntityYpos(this);

        //distanza dell'entita dal giocatore nella mappa
        int xDistanceFromPlayer = entityXPosMap - xPlayerMap;
        int yDistanceFromPlayer = entityYPosMap - yPlayerMap;

        //riproponiamo la stessa distanza nello schermo
        int xPosOnScreen = GamePanel.CENTER_X_GAME_PANEL + xDistanceFromPlayer;
        int yPosOnScreen = GamePanel.CENTER_Y_GAME_PANEL + yDistanceFromPlayer;

        //siccome la posizone dell'entità non coincide col punto in alto a sinistra dell'immagine, compensiamo con gli offset
        g2.drawImage(animation[currentState.getConstantInAnimationArray()][currentDirection][numSprite], xPosOnScreen - xOffset, yPosOnScreen - yOffset, null);

        //disegna la zona occupata dalla sprite
        g2.drawRect(xPosOnScreen - xOffset, yPosOnScreen - yOffset,
                animation[currentState.getConstantInAnimationArray()][currentDirection][numSprite].getWidth(),
                animation[currentState.getConstantInAnimationArray()][currentDirection][numSprite].getHeight());
        //disegna la posizione
        g2.fillRect(xPosOnScreen, yPosOnScreen, 5, 5);

        //disegna la sua hitbox
        g2.setColor(Color.red);
        int hitboxW = Rooms.actualRoom.getNpc().get(indexInEntityArray).getEntityController().getHitbox().getWidth();
        int hitboxH = Rooms.actualRoom.getNpc().get(indexInEntityArray).getEntityController().getHitbox().getHeight();
        int xoffsetH = Rooms.actualRoom.getNpc().get(indexInEntityArray).getEntityController().getXhitboxOffset();
        int yoffsetH = Rooms.actualRoom.getNpc().get(indexInEntityArray).getEntityController().getYhitboxOffset();
        g2.drawRect(xPosOnScreen - xoffsetH, yPosOnScreen - yoffsetH, hitboxW, hitboxH);

        //disegna interaction
        g2.setColor(Color.green);
        int inthitboxW = Rooms.actualRoom.getNpc().get(indexInEntityArray).getEntityController().getInteractionHitbox().getWidth();
        int inthitboxH = Rooms.actualRoom.getNpc().get(indexInEntityArray).getEntityController().getInteractionHitbox().getHeight();
        int intxoffsetH = Rooms.actualRoom.getNpc().get(indexInEntityArray).getEntityController().getInteractionHitbox().getWidth()/2;
        int intyoffsetH = Rooms.actualRoom.getNpc().get(indexInEntityArray).getEntityController().getInteractionHitbox().getHeight()/2;
        g2.drawRect(xPosOnScreen - intxoffsetH, yPosOnScreen - intyoffsetH, inthitboxW, inthitboxH);

    }

    protected void getCurrentStateFromController() {
        try {
            currentState = view.getModel().getCurrentStateOfEntity(this);
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

    protected void getCurrentDirectionFromController() {

        int vectorX = (int)view.getModel().getCurrentDirectionOfEntity(this).getX();
        int vectorY = (int)view.getModel().getCurrentDirectionOfEntity(this).getY();

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

    public int getIndexInEntityArray(){
        return indexInEntityArray;
    }

    protected abstract int getAnimationLenght();

    //ci serve per essere sicuri che l'entità sappia dove si trova prima di essere ordinata
    public void updatePositionForSort() {
         yPosMapForSort = view.getModel().getEntityYpos(this);
    }
}
