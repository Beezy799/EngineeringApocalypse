package src.view.entityView.enemy;

import src.model.EntityStates;
import src.model.Rooms;
import src.model.entity.EnemyComplete;
import src.view.IView;
import src.view.ViewUtils;
import src.view.entityView.EntityView;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class EnemyView extends EntityView {

    protected BufferedImage puntoEsclamativo;
    protected Rectangle lifeRect;


    public EnemyView(IView v, int i) {
        super(v, i);
        loadImages();
        lifeRect = new Rectangle(0,0, animation[0][0][0].getWidth(), GamePanel.TILES_SIZE/8);
        loadPuntoEsclamativo();
    }

    @Override
    protected abstract int getAnimationLenght();

    protected abstract void loadImages();

    public void loadPuntoEsclamativo(){
        try {
            puntoEsclamativo = ImageIO.read(getClass().getResourceAsStream("/res/ui/exclamation.png"));
            puntoEsclamativo = ViewUtils.scaleImage(puntoEsclamativo, puntoEsclamativo.getWidth()/8*GamePanel.SCALE,
                    puntoEsclamativo.getHeight()/8*GamePanel.SCALE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap){
        takeCurrentStateFromController();

        switch (currentState){
            case IDLE:
            case MOVE:
            case RECHARGE:
            case CHASE:
            case HITTED:
                normaldraw(g2, xPlayerMap, yPlayerMap);
                break;
            case ATTACKING:
            case THROWING:
            case PARRING:
                animationSpeed = 50;
                specialDraw(g2, xPlayerMap, yPlayerMap);
                break;
            case DYING:
                dyingDraw(g2,xPlayerMap, yPlayerMap);
                break;
            default:
                break;
        }

        drawLifeRect(g2);
    }

    protected void drawLifeRect(Graphics2D g2) {
        if(currentState != EntityStates.DYING && currentState != EntityStates.ATTACKING){
            int life = ((EnemyComplete)entityComplete).getEnemyController().getLife();

            if(life > 30)
                g2.setColor(Color.green);
            else
                g2.setColor(Color.red);

            lifeRect.width = life*animation[0][0][0].getWidth()/100;
            g2.fillRect(xPosOnScreen - xOffset, yPosOnScreen - lifeRect.height - yOffset, lifeRect.width, lifeRect.height);
        }
    }

    protected void dyingDraw(Graphics2D g2, int xPlayerMap, int yPlayerMap ) {
        animationCounter++;
        takeCurrentStateFromController();
        takeCurrentDirectionFromController();

        if (animationCounter > animationSpeed) {
            numSprite ++;

            if(numSprite >= getAnimationLenght()) {
                //finita l'animazione, il nemico può muorire
                ((EnemyComplete) entityComplete).setAlive(false);
                //quando muore, regala cfu al player
                view.getController().getPlayerController().addCFU(10);
                return;
            }

            animationCounter = 0;
        }

        drawSprite(g2, xPlayerMap, yPlayerMap);
    }

    protected void specialDraw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
        animationCounter++;
        takeCurrentStateFromController();
        takeCurrentDirectionFromController();

        if (animationCounter > animationSpeed) {
            numSprite ++;

            if(numSprite >= getAnimationLenght()) {
                //finita l'animazione, il nemico può cambiare stato
                view.getModel().unlockState(indexInEntityArray);
                numSprite = 0;
            }

            animationCounter = 0;
        }

        drawSprite(g2, xPlayerMap, yPlayerMap);
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

        if(currentState == EntityStates.CHASE){
            g2.drawImage(puntoEsclamativo, xPosOnScreen, yPosOnScreen - 2*puntoEsclamativo.getHeight(), null);
        }

    }

    public void setIndex(int newIndex) {
        indexInEntityArray = newIndex;
    }
}