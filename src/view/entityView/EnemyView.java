package src.view.entityView;

import src.model.mapModel.Rooms;
import src.view.IView;
import src.view.gameWindow.GamePanel;

import java.awt.*;

public abstract class EnemyView extends EntityView{

    public EnemyView(IView v, int i) {
        super(v, i);
    }

    @Override
    protected abstract int getAnimationLenght();

    public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap){
        getCurrentStateFromController();

        switch (currentState){
            case IDLE:
            case CHASE:
            case MOVE:
                super.draw(g2, xPlayerMap, yPlayerMap);
                break;
            case ATTACKING:
            case THROWING:
            case PARRING:
                specialDraw(g2, xPlayerMap, yPlayerMap);
                break;
            case DYING:
                dyingDraw(g2,xPlayerMap, yPlayerMap);
                break;
            default:
                break;
        }
    }

    private void dyingDraw(Graphics2D g2, int xPlayerMap, int yPlayerMap ) {
        animationCounter++;
        getCurrentStateFromController();
        getCurrentDirectionFromController();

        if (animationCounter > animationSpeed) {
            numSprite ++;

            if(numSprite >= getAnimationLenght()) {
                //finita l'animazione, il nemico può muorire

                //distruggi entità
                numSprite = 0;
            }

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

    }

    protected void specialDraw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
        animationCounter++;
        getCurrentStateFromController();
        getCurrentDirectionFromController();

        if (animationCounter > animationSpeed) {
            numSprite ++;

            if(numSprite >= getAnimationLenght()) {
                //finita l'animazione, il nemico può cambiare stato

                //vai nell'enemy controller, sblocca lo stato
                numSprite = 0;
            }

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

    }

}
