package src.controller.entitycontroller;

import src.controller.IController;
import src.controller.Vector;
import src.model.EntityStates;
import src.model.Hitbox;
import src.view.gameWindow.GamePanel;

import java.util.Random;

import static src.model.Constants.EntityConstants.*;
import static src.model.EntityStates.*;

public abstract class EntityController {

    protected Hitbox hitbox, interactionHitbox, tempHitbox;
    protected int XhitboxOffset, YhitboxOffset;
    protected int xPos, yPos, speed;
    protected EntityStates currentState = IDLE;
    protected Vector movementVector;
    protected int actionCounter;
    protected Random randomGenerator;
    protected IController controller;

    protected int index;

    public EntityController(int x, int y, IController c, int index){
        xPos = x * GamePanel.TILES_SIZE;
        yPos = y * GamePanel.TILES_SIZE;

        speed = 1;
        movementVector = new Vector(2);
        randomGenerator = new Random();
        controller = c;
        this.index = index;
    }

    // molti npc si muovono a caso nella stanza usando questo medoto
    protected void randomMove() {
        actionCounter++;
        //ogni due secondi cambia azione e direzione
        if (actionCounter >= 200) {
            choseAction();
            choseDirection();
            actionCounter = 0;
        }
    }

    protected void updatePosition() {
        //dopo aver scelto di muoversi, vede se può farlo, altrimenti si ferma
        if(canMove(tempHitbox)) {

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

    protected boolean canMove(Hitbox tempHitbox) {
        boolean canGo = false;

        //se va a destra
        if(movementVector.getX() > 0) {
            tempHitbox.setX(hitbox.getX() + speed);
            tempHitbox.setY(hitbox.getY());
            boolean solidTiles = controller.getPlayStateController().getCollisionChecker().canGoRight(tempHitbox);
            boolean entities = controller.getPlayStateController().getCollisionChecker().isNotCollisionWithoOtherEntities(this);
            canGo = solidTiles && entities;
        }
        //se va a sinistra
        else if(movementVector.getX() < 0) {
            tempHitbox.setX(hitbox.getX() - speed);
            tempHitbox.setY(hitbox.getY());
            boolean solidTiles = controller.getPlayStateController().getCollisionChecker().canGoLeft(tempHitbox);
            boolean entities = controller.getPlayStateController().getCollisionChecker().isNotCollisionWithoOtherEntities(this);
            canGo = solidTiles && entities;
        }
        //su
        if(movementVector.getY() < 0) {
            tempHitbox.setY(hitbox.getY() - speed);
            tempHitbox.setX(hitbox.getX());
            boolean solidTiles = controller.getPlayStateController().getCollisionChecker().canGoUp(tempHitbox);
            boolean entities = controller.getPlayStateController().getCollisionChecker().isNotCollisionWithoOtherEntities(this);
            canGo = solidTiles && entities;

        }
        //giu
        else if(movementVector.getY() > 0) {
            tempHitbox.setY(hitbox.getY() + speed);
            tempHitbox.setX(hitbox.getX());
            boolean solidTiles = controller.getPlayStateController().getCollisionChecker().canGoDown(tempHitbox);
            boolean entities = controller.getPlayStateController().getCollisionChecker().isNotCollisionWithoOtherEntities(this);
            canGo = solidTiles && entities;
        }

        return canGo;
    }

    protected void choseAction() {
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

    protected void turnToPlayer(){
        int playerX = controller.getPlayerController().getxPosPlayer();
        int playerY = controller.getPlayerController().getyPosPlayer();
        movementVector.resetDirections();
        //controllo se la posizione del player è in un fascio largo un tile che ha centro nella posizione della
        //entità. questo fascio è come se fosse una sorta di colonna
        if(xPos - GamePanel.TILES_SIZE/2 <= playerX && playerX <= xPos + GamePanel.TILES_SIZE/2){

            if(playerY > yPos){
                movementVector.setY(1);
            }
            else{
                movementVector.setY(-1);
            }
        }
        //se non si trova in questo fascio, si gira a destra o a sinistra in base alla posizione x del player
        else{
            if(playerX > xPos){
                movementVector.setX(1);
            }
            else{
                movementVector.setX(-1);
            }
        }

        currentState = IDLE;

    }

    protected void moveNearDoor(int xLeft, int xRight) {

        if(movementVector.getX() > 0){
            goRight(xRight);
        }
        else {
            goLeft(xLeft);
        }
    }

    protected void goLeft(int xLeft){
        //se non ha raggiunto il punto puù a sinistra, continua ad andare a sinsitra
        if(xPos > xLeft * GamePanel.TILES_SIZE){
            //se va a sinistra
            tempHitbox.setX(hitbox.getX() - speed);
            tempHitbox.setY(hitbox.getY());

            boolean solidTiles = controller.getPlayStateController().getCollisionChecker().canGoLeft(tempHitbox);
            boolean entities = controller.getPlayStateController().getCollisionChecker().isNotCollisionWithoOtherEntities(this);
            boolean canGo = solidTiles && entities;

            if(canGo){
                setxPos(getxPos() - speed);
                hitbox.setX(xPos - XhitboxOffset);
                interactionHitbox.setX(xPos - interactionHitbox.getWidth()/2);
            }
        }
        else{
            //se è arrivato a destinazione, cambia direzione e va a detra
            movementVector.setX(1);
        }
    }

    protected void goRight(int xRight){
        //se non ha raggiunto il punto puù a sinistra, continua ad andare a sinsitra
        if(xPos < xRight * GamePanel.TILES_SIZE){
            //se va a detsra
            tempHitbox.setX(hitbox.getX() + speed);
            tempHitbox.setY(hitbox.getY());

            boolean solidTiles = controller.getPlayStateController().getCollisionChecker().canGoRight(tempHitbox);
            boolean entities = controller.getPlayStateController().getCollisionChecker().isNotCollisionWithoOtherEntities(this);
            boolean canGo = solidTiles && entities;

            if(canGo){
                setxPos(getxPos() + speed);
                hitbox.setX(xPos - XhitboxOffset);
                interactionHitbox.setX(xPos - interactionHitbox.getWidth()/2);
            }
        }
        else{
            //se è arrivato a destinazione, cambia direzione e va a detra
            movementVector.setX(-1);
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

        tempHitbox = new Hitbox(hitbox.getX(), hitbox.getY(), (int)(hitboxWidth*GamePanel.SCALE),
                                                              (int)(hitboxHeight*GamePanel.SCALE));

        int interactionHitboxWidth = 2*GamePanel.TILES_SIZE;
        int interactionHitboxHeight = 2*GamePanel.TILES_SIZE;

        interactionHitbox = new Hitbox( xPos - interactionHitboxWidth/2,
                                        yPos - interactionHitboxHeight/2,
                                        interactionHitboxWidth,
                                        interactionHitboxHeight);
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

    public abstract void update();

    public int getIndex(){
        return index;
    }

    public Hitbox getTempHitbox(){
        return  tempHitbox;
    }


    public void speak() {
        currentState = SPEAKING;
    }
}
