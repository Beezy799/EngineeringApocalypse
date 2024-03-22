package src.controller.entitycontroller;

import src.controller.IController;
import src.controller.Vector;
import src.model.EntityStates;
import src.model.Hitbox;
import src.view.main.GamePanel;

import java.util.Random;

import static src.model.Constants.EntityConstants.*;
import static src.model.EntityStates.IDLE;
import static src.model.EntityStates.MOVE;

public class EntityController {

    protected Hitbox hitbox, interactionHitbox, tempHitbox;
    protected int XhitboxOffset, YhitboxOffset;
    protected int xPos, yPos, speed;
    protected EntityStates currentState = IDLE;
    protected Vector movementVector;
    protected int actionCounter;
    protected Random randomGenerator;
    protected IController controller;

    public EntityController(int x, int y, IController c){
        xPos = x * GamePanel.TILES_SIZE;
        yPos = y * GamePanel.TILES_SIZE;

        speed = 1;
        movementVector = new Vector(2);
        randomGenerator = new Random();
        controller = c;
    }

    // molti npc si muovono a caso nella stanza usando questo medoto
    public void randomMove() {
        actionCounter++;
        //ogni due secondi cambia azione e direzione
        if(actionCounter >= 400) {
            choseDirection();
            choseAction();
            actionCounter = 0;
        }

        //dopo aver scelto di muoversi, vede se può farlo, altrimenti si ferma
        if(currentState == MOVE && canMove(tempHitbox)) {

            if(movementVector.getY() < 0) {
                setyPos(getyPos() - speed);
                hitbox.setY(yPos - YhitboxOffset);
                interactionHitbox.setY(yPos - interactionHitbox.getHeight()/2);
            }
            else if(movementVector.getY() > 0) {
                setyPos(getyPos() + speed);
                hitbox.setY(yPos - YhitboxOffset);
                interactionHitbox.setY(yPos - interactionHitbox.getHeight()/2);
            }
            else if(movementVector.getX() < 0) {
                setxPos(getxPos() - speed);
                hitbox.setX(xPos - XhitboxOffset);
                interactionHitbox.setX(xPos - interactionHitbox.getWidth()/2);
            }
            else if(movementVector.getX() > 0) {
                setxPos(getxPos() + speed);
                hitbox.setX(xPos - XhitboxOffset);
                interactionHitbox.setX(xPos - interactionHitbox.getWidth()/2);
            }

        }
        else
            currentState = IDLE;
    }

    private boolean canMove(Hitbox tempHitbox) {
        boolean canGo = false;

        //se va a destra
        if(movementVector.getX() > 0) {
            tempHitbox.setX(hitbox.getX() + speed);
            tempHitbox.setY(hitbox.getY());
            canGo = controller.getPlayStateController().getCollisionChecker().canGoRight(tempHitbox);
        }
        //se va a sinistra
        else if(movementVector.getX() < 0) {
            tempHitbox.setX(hitbox.getX() - speed);
            tempHitbox.setY(hitbox.getY());
            canGo = controller.getPlayStateController().getCollisionChecker().canGoLeft(tempHitbox);
        }
        //su
        if(movementVector.getY() < 0) {
            tempHitbox.setY(hitbox.getY() - speed);
            tempHitbox.setX(hitbox.getX());
            canGo = controller.getPlayStateController().getCollisionChecker().canGoUp(tempHitbox);
        }
        //giu
        else if(movementVector.getY() > 0) {
            tempHitbox.setY(hitbox.getY() + speed);
            tempHitbox.setX(hitbox.getX());
            canGo = controller.getPlayStateController().getCollisionChecker().canGoDown(tempHitbox);
        }

        return canGo;
    }

    private void choseAction() {
        int randomAction = randomGenerator.nextInt(2);

        if (randomAction == 0)
            currentState = IDLE;

        else
            currentState = MOVE;

    }

    protected void choseDirection() {
        int randomDirection = randomGenerator.nextInt(4);

        movementVector.resetDirections();

        if(randomDirection == DOWN) {
            movementVector.setY(1);
        }

        else if (randomDirection == RIGHT) {
            movementVector.setX(1);
        }

        else if(randomDirection == LEFT) {
            movementVector.setX(-1);
        }

        else {
            movementVector.setY(-1);
        }
    }

    protected void setHitbox(int hitboxWidth, int hitboxHeight) {
        hitbox = new Hitbox(xPos, yPos, (int)(hitboxWidth*GamePanel.SCALE), (int)(hitboxHeight*GamePanel.SCALE));
        XhitboxOffset = hitbox.getWidth()/2;
        YhitboxOffset = hitbox.getHeight()/2;
        hitbox.setX(xPos - XhitboxOffset);
        hitbox.setY(yPos - YhitboxOffset);

        XhitboxOffset = hitbox.getWidth()/2;
        YhitboxOffset = hitbox.getHeight()/2;

        tempHitbox = new Hitbox(hitbox.getX(), hitbox.getY(), hitboxWidth, hitboxHeight);

        int interactionHitboxWidth = 2*GamePanel.TILES_SIZE;
        int interactionHitboxHeight = 2*GamePanel.TILES_SIZE;

        interactionHitbox = new Hitbox( xPos - interactionHitboxWidth/2,
                                        yPos - interactionHitboxHeight/2,
                                        interactionHitboxWidth,
                                        interactionHitboxHeight);
    }

    public void turnToPlayer(int playerX, int playerY){
        if(playerX >= xPos){
            movementVector.setX(1);
        }
        else {
            movementVector.setX(-1);
        }

        //se stanno sulla stessa colonna, si gira verso l'alto o versoil basso
        if(xPos/GamePanel.TILES_SIZE == playerX/GamePanel.TILES_SIZE){
           if(playerY >= yPos){
               movementVector.setY(1);
               movementVector.setX(0);
           }
            else {
               movementVector.setY(-1);
               movementVector.setX(0);
           }
        }

    }
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public Hitbox getHitbox(){
        return  hitbox;
    }

    public EntityStates getCurrentState(){
        return currentState;
    }

    public Vector getMovementVector(){
        return movementVector;
    }

    public int getXhitboxOffset() {
        return XhitboxOffset;
    }

    public int getYhitboxOffset() {
        return YhitboxOffset;
    }

    public Hitbox getInteractionHitbox(){
        return interactionHitbox;
    }
}
