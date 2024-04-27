package src.controller;

import src.model.*;
import src.view.gameWindow.GamePanel;
import src.view.playStateView.BulletView;

public class PlayerController {

    private IController controller;
    private EntityStates actualState = EntityStates.IDLE;
    private boolean stateLocked = false;
    private boolean nearEntity;
    private int hittedCounter, rechargeLifeCounter;
    private int life = 100, cfu = 180, notes;
    private int defence = 20, attack = 20;
    //velocità e posizione sono dei float, perchè così possiamo scalarli meglio
    //inoltre il movimento diagonale non è più veloce
    private float xPosPlayer = 19*GamePanel.TILES_SIZE, yPosPlayer = 15*GamePanel.TILES_SIZE; //posizione del player
    private final float initialxPosPlayer = xPosPlayer, initialyPosPlayer = yPosPlayer;
    private float speed = GamePanel.SCALE;
    //"direzione" del player. oldDirection ci salva la direzione del player quando non preme i tasti di movimento
    private Vector movementVector, oldDirection;
    //per evitare il problema dello sticky wall, prima di aggiornare la posizione della hitbox vera, aggiorniamo questa
    //hitbox temporanea nel punto dove andrebbe la vera hiybox dopo il movimento
    private Hitbox hitbox, attackHitbox, tempHitbox;
    private final int hitboxWidth =  (int)(0.72*GamePanel.TILES_SIZE);
    private final int hitboxHeight = 3*GamePanel.TILES_SIZE/4;
    //la posizione della hitbox è data dal punto in alto a sinistra, manetre la posizione del player è al centro della
    //sua hitbox. La hitbox del player è un quadrato grande mezzo tile
    private final int XhitboxOffset = hitboxWidth/2, YhitboxOffset = GamePanel.TILES_SIZE/4;


    public PlayerController(IController c){
        controller = c;
        movementVector = new Vector(speed);
        oldDirection = new Vector();
        setHitboxes();
    }

    private void setHitboxes() {
        hitbox = new Hitbox((int)xPosPlayer - XhitboxOffset, (int)yPosPlayer - YhitboxOffset, hitboxWidth, hitboxHeight);
        tempHitbox = new Hitbox((int)xPosPlayer - XhitboxOffset, (int)yPosPlayer - YhitboxOffset, hitboxWidth, hitboxHeight);
        attackHitbox = new Hitbox(0, 0, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
    }

    public void update(){
        rechargeLife();
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
            case HITTED:
                hittedCounter++;
                if(hittedCounter >= 100){
                    hittedCounter = 0;
                    unlockState();
                }
                break;
            case PARRING:
            case SPEAKING:
            case THROWING:
            case DYING:
                break;
        }

    }

    private void rechargeLife() {
        rechargeLifeCounter++;
        if(rechargeLifeCounter >= 800*(1+GameState.difficulty)){
            addLife(1);
            rechargeLifeCounter = 0;
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
            if(controller.getCollisionChecker().canGoLeft(tempHitbox)){       //controlla i tile
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
            if(controller.getCollisionChecker().canGoRight(tempHitbox)){
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
            if(controller.getCollisionChecker().canGoUp(tempHitbox)){
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
            if(controller.getCollisionChecker().canGoDown(tempHitbox)){
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

    public void setDirezioneRisultatnte() {
        float xRisultante = movementVector.getX();
        float yRisultante = movementVector.getY();

        if(xRisultante != 0 || yRisultante != 0) {
            changeActualState(EntityStates.MOVE);

            oldDirection.setX(movementVector.getNomalizedX());
            oldDirection.setY(movementVector.getNormalizedY());

            //se si muove in diagonale, divido la velocità per radice di due
            float oldModuleSpeed = getSpeed();
            if(xRisultante != 0 && yRisultante != 0){
                float newModule = oldModuleSpeed * 0.71f;
                movementVector.setModule(newModule);
            }
            //altrimenti lascio la velocità iniziale
            else {
                movementVector.setModule(oldModuleSpeed);
            }
        }
        else {
            movementVector.setX(oldDirection.getX());
            movementVector.setY(oldDirection.getY());
            changeActualState(EntityStates.IDLE);
        }
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
        if(!stateLocked) {
            stateLocked = true;

            if(actualState == EntityStates.ATTACKING)
                controller.getView().getSoundManager().playSE(Constants.SoundConstants.COLPO_SE);

            else if (actualState == EntityStates.THROWING)
                controller.getView().getSoundManager().playSE(Constants.SoundConstants.APPUNTI_SE);
        }
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

    public void addLife(int gainedLife) {
        setLife(life + gainedLife);
        if(life > 100){
            life = 100;
        }
    }

    public void addCFU(int cfu) {
        setCfu(getCfu() + cfu);
    }

    public void addNotes(int n) {
        setNotes(notes + n);
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

    public float getSpeed(){
        return speed;
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

        if(actualState == EntityStates.HITTED){
            hitted = false;
        }

        if(hitted){
            int damage = enemyAttack + 5*GameState.difficulty - defence;
            if(damage > 0){
                actualState = EntityStates.HITTED;
                lockState();
                life -= damage;
            }
            if(life <= 0){
                lockState();
                actualState = EntityStates.DYING;
            }
        }
    }

    public void createBullet() {
        Vector bulletVector = new Vector(1);
        if(movementVector.getX() != 0){
            bulletVector.setX(movementVector.getX());
        }
        else {
            bulletVector.setY(movementVector.getY());
        }
        //se la direzione del giocatore non è specificata, il proiettile non si crea
        if(bulletVector.getX() != 0 || bulletVector.getY() != 0){
            if(notes > 0){
                notes--;
                setNewBuet(bulletVector);
            }
            else {
                controller.getView().getPlayStateView().getPlayUI().setMessageToShow("non hai appunti da lanciare");
            }
        }
    }

    private void setNewBuet(Vector bulletVector) {
        Hitbox bulletHitbox = new Hitbox(0,0, GamePanel.TILES_SIZE/2, GamePanel.TILES_SIZE/2);
        float xBullet = 0, yBullet = 0;
        if(bulletVector.getX() > 0){
            xBullet = xPosPlayer + hitbox.getWidth()/2 + bulletHitbox.getWidth()/2;
            yBullet = yPosPlayer;
        }
        else if (bulletVector.getX() < 0) {
            xBullet = xPosPlayer - hitbox.getWidth()/2 - bulletHitbox.getWidth()/2;
            yBullet = yPosPlayer;
        }
        else if (bulletVector.getY() < 0) {
            xBullet = xPosPlayer;
            yBullet = yPosPlayer - hitbox.getHeight()/2 - bulletHitbox.getHeight()/2;
        }
        else if (bulletVector.getY() > 0) {
            xBullet = xPosPlayer;
            yBullet = yPosPlayer + hitbox.getHeight()/2 + bulletHitbox.getHeight()/2;
        }

        BulletController bc = new BulletController(bulletHitbox, xBullet, yBullet, bulletVector, controller, this);
        BulletView bv = new BulletView(bulletVector);
        BulletComplete bulletComplete = new BulletComplete(bv, bc);

        int index = Rooms.actualRoom.getBuletList().size();
        bulletComplete.setIndexInList(index);
        Rooms.actualRoom.getBuletList().add(bulletComplete);
    }

    public void reset() {
        life = 100;
        cfu = 0;
        notes = 0;
        xPosPlayer = initialxPosPlayer;
        yPosPlayer = initialyPosPlayer;
        setHitboxes();
        movementVector.resetDirections();
        actualState = EntityStates.IDLE;
        stateLocked = false;
    }

    public void setGender(int gender) {
        if(gender == Constants.EntityConstants.RAGAZZO){
            life = 70;
            notes = 20;
            defence = 2;
            speed = GamePanel.SCALE;
        }
        else{
            life = 90;
            notes = 5;
            defence = 4;
            speed = GamePanel.SCALE*0.8f;
        }
    }



}