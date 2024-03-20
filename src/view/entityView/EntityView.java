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


    public EntityView(IView v, int i){
        view = v;
        indexInEntityArray = i;
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


        try {
            g2.drawImage(animation[currentState.getConstantInAnimationArray()][currentDirection][numSprite], xPosOnScreen, yPosOnScreen, null);
        }
        catch (ArrayIndexOutOfBoundsException a) {
            a.printStackTrace();
        }
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

}
