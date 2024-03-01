package src.controller;

public class PlayerController {

    private IController controller;
    private float xPosPlayer , yPosPlayer; //posizione del player
    private  Vector movementVector; //"direzione" del player

    //creare macchina a stati player


    public PlayerController(IController c){
        controller = c;
        movementVector = new Vector();
    }

    public void update(){
        updatePosition();
        //dice al playerView di cambiare direzione
        controller.getView().getPlayerWiew().setCurrenDirection(movementVector.getX(), movementVector.getY());
        resetDirectionVector();
    }

    private void updatePosition() {
        xPosPlayer += movementVector.getX();
        yPosPlayer += movementVector.getY();
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
