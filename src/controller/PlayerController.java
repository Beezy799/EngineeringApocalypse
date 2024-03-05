package src.controller;

import src.model.EntityStates;

public class PlayerController {

    private IController controller;
    private float xPosPlayer , yPosPlayer; //posizione del player
    private  Vector movementVector; //"direzione" del player

    private EntityStates actualState = EntityStates.IDLE;

    public PlayerController(IController c){
        controller = c;
        movementVector = new Vector();
    }

    public void update(){
        //in base allo stato attuale il player agirà in modo diverso
        switch (actualState){
            case IDLE:
                break;
            case MOVE:
                updatePosition();
                break;
        }

    }

    private void updatePosition() {
        //resetDirectionVector();
        xPosPlayer += movementVector.getX();
        yPosPlayer += movementVector.getY();
    }

    public void resetDirectionVector() {
        movementVector.setX(0);
        movementVector.setY(0);
    }

    public void setxPosPlayer(float xPosPlayer) {
        this.xPosPlayer = xPosPlayer;
    }

    public void setyPosPlayer(float yPosPlayer) {
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
        movementVector.setY(movementVector.getY() + 1);
    }

    public void setDirectionDown() {
        movementVector.setY(movementVector.getY() - 1);
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
        actualState = newState;
    }

    public EntityStates getCurrentState(){
        return actualState;
    }
}
