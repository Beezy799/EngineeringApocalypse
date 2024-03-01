package src.controller;

public class PlayerController {

    private float xPosPlayer , yPosPlayer; //posizione del player
    private  Vector movementVector; //"direzione" del player
    public PlayerController(){
        movementVector = new Vector();
    }

    public void update(){
        resetDirectionVector();
    }

    private void resetDirectionVector() {
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
}
