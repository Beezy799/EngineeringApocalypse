package src.controller;

import src.model.EntityStates;
import src.model.Hitbox;
import src.model.mapModel.Rooms;
import src.view.main.GamePanel;

public class PlayerController {

    private IController controller;
    private PlayStateController playStateController;
    private int xPosPlayer = 21*GamePanel.TILES_SIZE, yPosPlayer = 15*GamePanel.TILES_SIZE; //posizione del player
    private  Vector movementVector; //"direzione" del player
    private EntityStates actualState = EntityStates.IDLE;
    private boolean stateLocked = false;


    private int life = 100;
    private int cfu = 0;
    private int notes = 0;

    private Hitbox hitbox;
    //per evitare il problema dello sticky wall, prima di aggiornare la posizione della hitbox vera, aggiorniamo questa
    //hitbox temporanea nel punto dove andrebbe la vera hiybox dopo il movimento
    private Hitbox tempHitbox;
    private final int hitboxWidth =  (int)(0.75*GamePanel.TILES_SIZE);
    private final int hitboxHeight = GamePanel.TILES_SIZE/2;

    //la posizione della hitbox è data dal punto in alto a sinistra, manetre la posizione del player è al centro della
    //sua hitbox. La hitbox del player è un quadrato grande mezzo tile
    private final int XhitboxOffset = hitboxWidth/2;


    public PlayerController(IController c, PlayStateController p){
        controller = c;
        movementVector = new Vector(2);
        playStateController = p;
        hitbox = new Hitbox(xPosPlayer - XhitboxOffset, yPosPlayer, hitboxWidth, hitboxHeight);
        tempHitbox = new Hitbox(xPosPlayer - XhitboxOffset, yPosPlayer, hitboxWidth, hitboxHeight);
    }

    public void update(){
        //in base allo stato attuale il player agirà in modo diverso
        switch (actualState){
            case IDLE:
                checkIfIsAbovePassage();
                break;
            case MOVE:
                updatePosition();
                checkIfIsAbovePassage();
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

    private void checkIfIsAbovePassage() {
        try {
            int passageIndex = -1;
            for (int i = 0; i < Rooms.actualRoom.getPassages().size(); i++) {
                Hitbox passageBorders = Rooms.actualRoom.getPassages().get(i).getBorders();
                if (hitbox.intersects(passageBorders)) {
                    passageIndex = i;
                    break;
                }
            }
            if(passageIndex > -1){
                Rooms.actualRoom.getPassages().get(passageIndex).changeRoom(cfu);
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void updatePosition() {

        //sta andando a sinistra
        if(movementVector.getX() < 0){
            tempHitbox.setX(hitbox.getX() + movementVector.getX());
            tempHitbox.setY(hitbox.getY());
            if(playStateController.getCollisionChecker().canGoLeft(tempHitbox)){
                xPosPlayer += movementVector.getX();
                hitbox.setX((xPosPlayer - XhitboxOffset));
            }

        }
        //sta andando a destra
        else if(movementVector.getX() > 0){
            tempHitbox.setX(hitbox.getX() + movementVector.getX());
            tempHitbox.setY(hitbox.getY());
            if(playStateController.getCollisionChecker().canGoRight(tempHitbox)){
                xPosPlayer += movementVector.getX();
                hitbox.setX((xPosPlayer - XhitboxOffset));
            }
        }
        //sta andando su
        if(movementVector.getY() < 0){
            tempHitbox.setX(hitbox.getX());
            tempHitbox.setY(hitbox.getY() + movementVector.getY());
            if(playStateController.getCollisionChecker().canGoUp(tempHitbox)){
                yPosPlayer += movementVector.getY();
                hitbox.setY(yPosPlayer);
            }

        }
        //sta andando giu
        else if(movementVector.getY() > 0){
            tempHitbox.setX(hitbox.getX());
            tempHitbox.setY(hitbox.getY() + movementVector.getY());
            if(playStateController.getCollisionChecker().canGoDown(tempHitbox)){
                yPosPlayer += movementVector.getY();
                hitbox.setY(yPosPlayer);
            }
        }

    }

    public void resetDirectionVector() {
        movementVector.setX(0);
        movementVector.setY(0);
    }

    public void setxPosPlayer(int xPosPlayer) {
        this.xPosPlayer = xPosPlayer;
        hitbox.setX(xPosPlayer - XhitboxOffset);
    }

    public void setyPosPlayer(int yPosPlayer) {
        this.yPosPlayer = yPosPlayer;
        hitbox.setY(yPosPlayer);
    }

    public void setMovementVector(Vector movementVector) {
        this.movementVector = movementVector;
    }

    public int getxPosPlayer() {
        return xPosPlayer;
    }

    public int getyPosPlayer() {
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

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getCfu() {
        return cfu;
    }

    public void setCfu(int cfu) {
        this.cfu = cfu;
    }

    public int getNotes() {
        return notes;
    }

    public void setNotes(int notes) {
        this.notes = notes;
    }
}
