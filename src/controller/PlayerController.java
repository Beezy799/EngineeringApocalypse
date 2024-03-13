package src.controller;

import src.model.EntityStates;
import src.model.Hitbox;
import src.view.main.GamePanel;

public class PlayerController {

    private IController controller;
    private PlayStateController playStateController;
    private int xPosPlayer = 20*GamePanel.TILES_SIZE, yPosPlayer = 15*GamePanel.TILES_SIZE; //posizione del player
    private  Vector movementVector; //"direzione" del player
    private EntityStates actualState = EntityStates.IDLE;
    private boolean stateLocked = false;

    private Hitbox hitbox;

    //per evitare il problema dello sticky wall, prima di aggiornare la posizione della hitbox vera, aggiorniamo questa
    //hitbox temporanea nel punto dove andrebbe la vera hiybox dopo il movimento
    private Hitbox tempHitbox;

    public PlayerController(IController c, PlayStateController p){
        controller = c;
        movementVector = new Vector();
        playStateController = p;
        hitbox = new Hitbox(xPosPlayer,yPosPlayer, GamePanel.TILES_SIZE/2, GamePanel.TILES_SIZE/2);
        tempHitbox = new Hitbox(xPosPlayer,yPosPlayer, GamePanel.TILES_SIZE/2, GamePanel.TILES_SIZE/2);
    }

    public void update(){
        //in base allo stato attuale il player agirà in modo diverso
        switch (actualState){
            case IDLE:
                break;
            case MOVE:
                updatePosition();
                break;
            case ATTACKING:
                break;
            case PARRING:
                break;
            case THROWING:
                break;
            case SPEAKING:
                break;
            case DYING:
                lockState();
                break;

        }

    }

    private void updatePosition() {
        boolean canMove = true;
        //sta andando a sinistra
        if(movementVector.getX() < 0){
            tempHitbox.setX(hitbox.getX() + movementVector.getX());
            tempHitbox.setY(hitbox.getY() + movementVector.getY());
            canMove = playStateController.getCollisionChecker().canGoLeft(tempHitbox);
        }
        //sta andando su
        if(movementVector.getY() < 0){
            tempHitbox.setX(hitbox.getX() + movementVector.getX());
            tempHitbox.setY(hitbox.getY() + movementVector.getY());
            canMove = playStateController.getCollisionChecker().canGoUp(tempHitbox);
        }
        //sta andando giu
        if(movementVector.getY() > 0){
            tempHitbox.setX(hitbox.getX() + movementVector.getX());
            tempHitbox.setY(hitbox.getY() + movementVector.getY());
            canMove = playStateController.getCollisionChecker().canGoDown(tempHitbox);
        }
        //sta andando a destra
        if(movementVector.getX() > 0){
            tempHitbox.setX(hitbox.getX() + movementVector.getX());
            tempHitbox.setY(hitbox.getY() + movementVector.getY());
            canMove = playStateController.getCollisionChecker().canGoRight(tempHitbox);
        }


        if(canMove){
            xPosPlayer = xPosPlayer + movementVector.getX();
            yPosPlayer = yPosPlayer + movementVector.getY();
            hitbox.setX(xPosPlayer + movementVector.getX());
            hitbox.setY(yPosPlayer + movementVector.getY());
        }



    }

    public void resetDirectionVector() {
        movementVector.setX(0);
        movementVector.setY(0);
    }

    public void setxPosPlayer(int xPosPlayer) {
        this.xPosPlayer = xPosPlayer;
    }

    public void setyPosPlayer(int yPosPlayer) {
        this.yPosPlayer = yPosPlayer;
    }

    public void setMovementVector(Vector movementVector) {
        this.movementVector = movementVector;
    }

    public float getxPosPlayer() {
        return xPosPlayer;
    }

    public float getyPosPlayer() {
        return yPosPlayer;
    }

    public Vector getMovementVector() {
        return movementVector;
    }

    public void setDirectionUp() {
        movementVector.setY(movementVector.getY() - 1);
    }

    public void setDirectionDown() {
        movementVector.setY(movementVector.getY() + 1);
    }

    public void setDirectionRight() {
        movementVector.setX(movementVector.getX() + 1);
    }

    public void setDirectionLeft() {
        movementVector.setX(movementVector.getX() - 1);
    }

    public void changeActualState(EntityStates newState){
        //possiamo mettere un controllo in modo che il player
        //cambia stato solo in determinate occasioni (qunado para/attacca non può cambiare stato
        //finchè non finisce l'azione o non lascia il tasto
        if(stateLocked == false) {
            actualState = newState;
        }
    }

    public void unlockState(){
        stateLocked = false;
    }

    public void lockState(){
        stateLocked = true;
    }

    public EntityStates getCurrentState(){
        return actualState;
    }

    public Hitbox getHitbox(){
        return hitbox;
    }
}
