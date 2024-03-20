package src.view.entityView;

import src.model.EntityStates;
import src.model.mapModel.Rooms;
import src.view.IView;
import src.view.main.GamePanel;
import src.view.playStateView.SortableElement;

import java.awt.*;
import java.awt.image.BufferedImage;

import static src.model.Constants.EntityConstants.*;
import static src.model.Constants.EntityConstants.LEFT;

public abstract class EntityView extends SortableElement {

    int animationCounter;
    int animationSpeed;
    int numSprite;
    int indexInEntityArray;

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
        int entityXPosMap = view.getModel().getEntityXpos(indexInEntityArray);
        int entityYPosMap = view.getModel().getEntityYpos(indexInEntityArray);



        //distanza dell'entita dal giocatore nella mappa
        int xDistanceFromPlayer = entityXPosMap - xPlayerMap;
        int yDistanceFromPlayer = entityYPosMap - yPlayerMap;


        //riproponiamo la stessa distanza nello schermo
        int xPosOnScreen = GamePanel.CENTER_X_GAME_PANEL + xDistanceFromPlayer;
        int yPosOnScreen = GamePanel.CENTER_Y_GAME_PANEL + yDistanceFromPlayer;
        g2.drawImage(animation[currentState.getConstantInAnimationArray()][currentDirection][numSprite], xPosOnScreen - xOffset, yPosOnScreen - yOffset, null);

        //disegna la zona occupata dalla sprite
        g2.drawRect(xPosOnScreen - xOffset, yPosOnScreen - yOffset,
                animation[currentState.getConstantInAnimationArray()][currentDirection][numSprite].getWidth(),
                animation[currentState.getConstantInAnimationArray()][currentDirection][numSprite].getHeight());
        //disegna la posizione
        g2.fillRect(xPosOnScreen, yPosOnScreen, 5, 5);

        //disegna la sua hitbox
        g2.setColor(Color.red);
        int hitboxW = Rooms.actualRoom.getEntities().get(indexInEntityArray).getEntityController().getHitbox().getWidth();
        int hitboxH = Rooms.actualRoom.getEntities().get(indexInEntityArray).getEntityController().getHitbox().getHeight();
        int xoffsetH = Rooms.actualRoom.getEntities().get(indexInEntityArray).getEntityController().getXhitboxOffset();
        int yoffsetH = Rooms.actualRoom.getEntities().get(indexInEntityArray).getEntityController().getYhitboxOffset();
        g2.drawRect(xPosOnScreen - xoffsetH, yPosOnScreen - yoffsetH, hitboxW, hitboxH);

        //disegna interaction
        g2.setColor(Color.green);
        int inthitboxW = Rooms.actualRoom.getEntities().get(indexInEntityArray).getEntityController().getInteractionHitbox().getWidth();
        int inthitboxH = Rooms.actualRoom.getEntities().get(indexInEntityArray).getEntityController().getInteractionHitbox().getHeight();
        int intxoffsetH = Rooms.actualRoom.getEntities().get(indexInEntityArray).getEntityController().getInteractionHitbox().getWidth()/2;
        int intyoffsetH = Rooms.actualRoom.getEntities().get(indexInEntityArray).getEntityController().getInteractionHitbox().getHeight()/2;
        g2.drawRect(xPosOnScreen - intxoffsetH, yPosOnScreen - intyoffsetH, inthitboxW, inthitboxH);

    }

    protected void getCurrentStateFromController() {
        try {
//            if(view == null)
//                System.out.println("v");
//
//            if(view.getModel() == null)
//                System.out.println("m");
//
//            if(Rooms.actualRoom == null)
//                System.out.println("r");

            currentState = view.getModel().getCurrentStateOfEntity(indexInEntityArray);
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

        float vectorX = view.getModel().getCurrentDirectionOfEntity(indexInEntityArray).getX();
        float vectorY = view.getModel().getCurrentDirectionOfEntity(indexInEntityArray).getY();

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
         yPosMapForSort = view.getModel().getEntityYpos(indexInEntityArray);
    }
}
