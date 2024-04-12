package src.controller;

import src.model.EntityStates;
import src.model.Rooms;
import src.view.gameWindow.GamePanel;

public class PlayerController {

    private IController controller;
    private PlayStateController playStateController;

    //velocità e posizione sono dei float, perchè così possiamo scalarli meglio
    //inoltre il movimento diagonale non è più veloce
    private float xPosPlayer = 19*GamePanel.TILES_SIZE, yPosPlayer = 15*GamePanel.TILES_SIZE; //posizione del player
    private final float initialxPosPlayer = xPosPlayer, initialyPosPlayer = yPosPlayer;
    private float speed = 2f;
    private Vector movementVector; //"direzione" del player
    private EntityStates actualState = EntityStates.IDLE;
    private boolean stateLocked = false;
    private boolean nearEntity;


    private int life = 100;
    private int cfu = 0;
    private int notes = 0;
    private int defence = 2;

    private int attack = 80;

    private Hitbox hitbox, attackHitbox;
    //per evitare il problema dello sticky wall, prima di aggiornare la posizione della hitbox vera, aggiorniamo questa
    //hitbox temporanea nel punto dove andrebbe la vera hiybox dopo il movimento
    private Hitbox tempHitbox;
    private final int hitboxWidth =  (int)(0.72*GamePanel.TILES_SIZE);
    private final int hitboxHeight = 3*GamePanel.TILES_SIZE/4;

    //la posizione della hitbox è data dal punto in alto a sinistra, manetre la posizione del player è al centro della
    //sua hitbox. La hitbox del player è un quadrato grande mezzo tile
    private final int XhitboxOffset = hitboxWidth/2;
    private final int YhitboxOffset = GamePanel.TILES_SIZE/4;


    public PlayerController(IController c, PlayStateController p){
        controller = c;
        movementVector = new Vector(speed);
        playStateController = p;
        setHitboxes();
    }

    private void setHitboxes() {
        hitbox = new Hitbox((int)xPosPlayer - XhitboxOffset, (int)yPosPlayer - YhitboxOffset, hitboxWidth, hitboxHeight);
        tempHitbox = new Hitbox((int)xPosPlayer - XhitboxOffset, (int)yPosPlayer - YhitboxOffset, hitboxWidth, hitboxHeight);
        attackHitbox = new Hitbox(0, 0, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
    }

    public void update(){
        //in base allo stato attuale il player agirà in modo diverso
        switch (actualState){
            case IDLE:
                checkIfIsAbovePassage();
                checkInteraction();
                checkEvents();
                break;
            case MOVE:
                updatePosition();
                checkIfIsAbovePassage();
                checkInteraction();
                checkEvents();
                break;
            case ATTACKING:
                checkHittedEnemy();
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

    private void checkHittedEnemy() {
        if(movementVector.getX() > 0){
            attackHitbox.setX(hitbox.getX() + hitbox.getWidth());
            attackHitbox.setY(yPosPlayer - (float) attackHitbox.getHeight() /2);
        }
        else if (movementVector.getX() < 0) {
            attackHitbox.setX(hitbox.getX() - attackHitbox.getWidth());
            attackHitbox.setY(yPosPlayer - (float) attackHitbox.getHeight() /2);
        }
        else if (movementVector.getY() < 0) {
            attackHitbox.setY(hitbox.getY() - attackHitbox.getHeight());
            attackHitbox.setX(xPosPlayer - (float) attackHitbox.getWidth() /2);
        }
        else if (movementVector.getY() > 0) {
            attackHitbox.setY(hitbox.getY() + hitbox.getHeight());
            attackHitbox.setX(xPosPlayer - (float) attackHitbox.getWidth() /2);
        }

        controller.getModel().checkHittedEnemy(attackHitbox, attack);
    }

    private void checkInteraction() {
        try {
            int entityIndex = -1;
            for (int i = 0; i < Rooms.actualRoom.getNpc().size(); i++) {
                Hitbox entityInteractionBorders = Rooms.actualRoom.getNpc().get(i).getEntityController().getInteractionHitbox();
                if (hitbox.intersects(entityInteractionBorders)) {
                    entityIndex = i;
                    break;
                }
            }
            if(entityIndex > -1){
                nearEntity = true;
                controller.setIndexEntityInteraction(entityIndex);
                controller.getView().getPlayStateView().getPlayUI().setMessageToShow("premi E per parlare");
            }
            else {
                nearEntity = false;
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
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

    private void checkEvents(){
        try {
            int eventIndex = -1;
            for (int i = 0; i < Rooms.actualRoom.getEvents().size(); i++) {
                if(!Rooms.actualRoom.getEvents().get(i).isEndInteraction()){
                    Hitbox eventBorders = Rooms.actualRoom.getEvents().get(i).getBounds();
                    if (hitbox.intersects(eventBorders)) {
                        eventIndex = i;
                        break;
                    }
                }
            }
            if(eventIndex > -1){
                Rooms.actualRoom.getEvents().get(eventIndex).interact();
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
            if(playStateController.getCollisionChecker().canGoLeft(tempHitbox)){       //controlla i tile
                if(!isEntityCollision()){                               //controlla le entità, se non c'è collisione
                    xPosPlayer += movementVector.getX();                //aggiorna la posizione
                    hitbox.setX((xPosPlayer - XhitboxOffset));
                }
            }

        }
        //sta andando a destra
        else if(movementVector.getX() > 0){
            tempHitbox.setX(hitbox.getX() + movementVector.getX());
            tempHitbox.setY(hitbox.getY());
            if(playStateController.getCollisionChecker().canGoRight(tempHitbox)){
                if(!isEntityCollision()){
                    xPosPlayer += movementVector.getX();
                    hitbox.setX((xPosPlayer - XhitboxOffset));
                }
            }
        }
        //sta andando su
        if(movementVector.getY() < 0){
            tempHitbox.setX(hitbox.getX());
            tempHitbox.setY(hitbox.getY() + movementVector.getY());
            if(playStateController.getCollisionChecker().canGoUp(tempHitbox)){
                if(!isEntityCollision()) {
                    yPosPlayer += movementVector.getY();
                    hitbox.setY(yPosPlayer - YhitboxOffset);
                }
            }

        }
        //sta andando giu
        else if(movementVector.getY() > 0){
            tempHitbox.setX(hitbox.getX());
            tempHitbox.setY(hitbox.getY() + movementVector.getY());
            if(playStateController.getCollisionChecker().canGoDown(tempHitbox)){
                if(!isEntityCollision()) {
                    yPosPlayer += movementVector.getY();
                    hitbox.setY(yPosPlayer - YhitboxOffset);
                }
            }
        }

        //System.out.println(yPosPlayer/GamePanel.TILES_SIZE + ", " + xPosPlayer/GamePanel.TILES_SIZE);
    }

    private boolean isEntityCollision() {
        boolean collision = false;
        for(int i = 0; i < Rooms.actualRoom.getNpc().size(); i++){
            Hitbox hitboxEntity = Rooms.actualRoom.getNpc().get(i).getEntityController().getHitbox();
            if(hitboxEntity.intersects(tempHitbox))
                collision = true;
        }
        for(int i = 0; i < Rooms.actualRoom.getEnemy().size(); i++){
            if(Rooms.actualRoom.getEnemy().get(i).isAlive()){
                Hitbox hitboxEntity = Rooms.actualRoom.getEnemy().get(i).getEnemyController().getHitbox();
                if(hitboxEntity.intersects(tempHitbox))
                    collision = true;
            }
        }
        return collision;
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
        return (int)xPosPlayer;
    }

    public int getyPosPlayer() {
        return (int)yPosPlayer;
    }

    public Vector getMovementVector() {
        return movementVector;
    }

    public void setDirectionUp() {
        movementVector.setY(movementVector.getNormalizedY() - 1);
    }

    public void setDirectionDown() {
        movementVector.setY(movementVector.getNormalizedY() + 1);
    }

    public void setDirectionRight() {
        movementVector.setX(movementVector.getNomalizedX() + 1);
    }

    public void setDirectionLeft() {
        movementVector.setX(movementVector.getNomalizedX() - 1);
    }

    public void changeActualState(EntityStates newState){
        //possiamo mettere un controllo in modo che il player
        //cambia stato solo in determinate occasioni (qunado para/attacca non può cambiare stato
        //finchè non finisce l'azione o non lascia il tasto
        if(!stateLocked) {
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

    public boolean isNearEntity() {
        return nearEntity;
    }

    public void setNearEntity(boolean nearEntity) {
        this.nearEntity = nearEntity;
    }

    public void speak() {
        setNearEntity(false);
        resetDirectionVector();
        unlockState();
        changeActualState(EntityStates.IDLE);
    }

    public void addLife(int gainedLife) {
        setLife(life + gainedLife);
        if(life > 100){
            life = 100;
        }
    }

    public void addCFU(int cfu) {
        setCfu(getCfu() + cfu);
    }

    public float getSpeed(){
        return speed;
    }

    public void addNotes(int n) {
        setNotes(notes + n);
    }

    public void hitted(int enemyAttack, Vector attackDirection){
        boolean hitted = true;

        if(actualState == EntityStates.PARRING){
            if(attackDirection.getNomalizedX() != 0){
                if(attackDirection.getNomalizedX() == -movementVector.getNomalizedX())
                    hitted = false;
            }
            else if(attackDirection.getNormalizedY() != 0){
                if(attackDirection.getNormalizedY() == -movementVector.getNormalizedY())
                    hitted = false;
            }
        }

        if(hitted){
            int damage = enemyAttack - defence;
            if(damage > 0){
                life -= damage;
            }
        }

    }

    public int getAttack(){
        return attack;
    }

    public void reset() {
        life = 100;
        cfu = 0;
        xPosPlayer = initialxPosPlayer;
        yPosPlayer = initialyPosPlayer;
        setHitboxes();
        movementVector.resetDirections();
        actualState = EntityStates.IDLE;
        stateLocked = false;
    }
}