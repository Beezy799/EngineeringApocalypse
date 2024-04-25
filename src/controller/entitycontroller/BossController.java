package src.controller.entitycontroller;

import src.controller.Hitbox;
import src.controller.IController;
import src.controller.entitycontroller.enemy.EnemyController;
import src.model.EntityStates;
import src.model.GameState;
import src.view.gameWindow.GamePanel;

import java.util.Random;

public class BossController extends EnemyController {

    private int streamCounter;
    private Hitbox streamHitbox, shieldHitbox;
    private Random random;

    public BossController(int x, int y, IController c, int index) {
        super(x, y, c, index);

        speed = GamePanel.SCALE * 0.7f;

        setHitbox(hitboxWidth, hitboxHeight, 12, 12);
        attackHitbox = new Hitbox(0, 0, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);

        xAttackHitboxOffset = attackHitbox.getWidth() / 2;
        yAttackHitboxOffset = attackHitbox.getHeight() / 2;

        life = 400;
        attack = 15;
        defence = 5;

        streamHitbox = new Hitbox(0, 0, 0, 0);
        shieldHitbox = new Hitbox(0, 0, GamePanel.TILES_SIZE*2, GamePanel.TILES_SIZE*2);
        random = new Random();
        currentState = EntityStates.RECHARGE;
    }

    @Override
    public void update() {

        updateDamageCounter();
        updateStreamCounter();

        switch (currentState) {
            case IDLE:
                normalState();
                break;

            case ATTACKING:
                attackPlayer();
                break;

            case PARRING:
                createShield();
                break;

            case THROWING:
                streamCounter = 0;
                shootToPlayer();
                break;

            case CHASE:
                chaseThePlayer();
                break;

            case HITTED:
                hittedCounter++;
                if (hittedCounter >= 100) {
                    hittedCounter = 0;
                    changeState(EntityStates.IDLE);
                }
                break;

            case RECHARGE:
                rechargeCounter++;
                if (rechargeCounter >= 150) {
                    rechargeCounter = 0;
                    changeState(EntityStates.IDLE);
                }
                break;

            case SPEAKING:
                break;

            default:
                changeState(EntityStates.RECHARGE);
                break;
        }

        //System.out.println(currentState);

    }

    private void createShield() {
        attackCounter++;
        defence = 20;
        if (attackCounter >= 200) {
            attackCounter = 0;

            shieldHitbox.setX(xPos - (float) shieldHitbox.getWidth() /2);
            shieldHitbox.setY(yPos - (float) shieldHitbox.getHeight() /2);
        }

        if (shieldHitbox.intersects(controller.getPlayerController().getHitbox())) {
            controller.getPlayerController().hitted(10, movementVector);
        }

        defence = 4;
        changeState(EntityStates.RECHARGE);
    }

    private void normalState() {
        if(pathNodeIndex != 0 || path != null){
            path = null;
            pathNodeIndex = 0;
            changeState(EntityStates.RECHARGE);
        }
        else if(icanHitThePlayer(range)){
            chooseBetweenPunchAndShield();
        }
        else if (iCanShotToPlayer()) {
            turnToPlayer();
            changeState(EntityStates.THROWING);
            setStateLocked(true);
        }
        else if (iCanReachThePlayer()) {
            pathNodeIndex = 0;
            changeState(EntityStates.CHASE);
        }
        else{
            changeState(EntityStates.RECHARGE);
        }
    }

    private void chooseBetweenPunchAndShield() {
        attackCounter = 0;
        if(life < 200 && random.nextBoolean()){
            changeState(EntityStates.PARRING);
            setStateLocked(true);
        }
        changeState(EntityStates.ATTACKING);
        setStateLocked(true);
    }

    private void updateStreamCounter() {
        streamCounter++;
        if(streamCounter >= 200*10){
            streamCounter = 200*10;
        }
    }

    private void attackPlayer() {
        turnToPlayer();
        shiftAttackHitbox();
        attackCounter++;
        if(attackCounter >= momentOfDamage/2){
            attackCounter = 0;
            if(attackHitbox.intersects(controller.getPlayerController().getHitbox())){
                controller.getPlayerController().hitted(10, movementVector);
            }
            changeState(EntityStates.RECHARGE);
        }
    }

    private void chaseThePlayer() {
        try {
            if (icanHitThePlayer(range)) {
                path = null;
                pathNodeIndex = 0;
                chooseBetweenPunchAndShield();
            }
            else if(iCanShotToPlayer()){
                turnToPlayer();
                changeState(EntityStates.THROWING);
                setStateLocked(true);
            }
            //se è arrivato a fine percorso
            else if (pathNodeIndex == path.size()) {
                pathNodeIndex = 0;
                path = null;

                if (iCanReachThePlayer()) {
                    changeState(EntityStates.CHASE);
                }
                else {
                    changeState(EntityStates.RECHARGE);
                }
            }
            //se non è arrivato e il player è lontano, cammina nel percorso
            else {
                followPath();
            }
        }
        catch (NullPointerException npe){
            pathNodeIndex = 0;
            currentState = EntityStates.RECHARGE;
        }
    }

    private void shootToPlayer() {
        attackCounter++;
        defence = 2;
        if(attackCounter >= momentOfDamage){
            attackCounter = 0;

            //up
            if(movementVector.getY() < 0){
                streamHitbox.setWidth(GamePanel.TILES_SIZE);
                streamHitbox.setHeight((int)yPos);
                streamHitbox.setY(0);
                streamHitbox.setX(xPos - (float) streamHitbox.getWidth() /2);
            }

            //down
            if(movementVector.getY() > 0){
                streamHitbox.setWidth(GamePanel.TILES_SIZE);
                streamHitbox.setHeight((GamePanel.GAME_HEIGHT));
                streamHitbox.setY(yPos);
                streamHitbox.setX(xPos - (float) streamHitbox.getWidth() /2);
            }

            //right
            if(movementVector.getX() > 0){
                streamHitbox.setWidth((GamePanel.GAME_WIDTH));
                streamHitbox.setHeight(GamePanel.TILES_SIZE);
                streamHitbox.setX(xPos);
                streamHitbox.setY(yPos - (float) streamHitbox.getHeight() /2);
            }

            //left
            if(movementVector.getX() < 0){
                streamHitbox.setWidth((int)xPos);
                streamHitbox.setHeight(GamePanel.TILES_SIZE);
                streamHitbox.setX(0);
                streamHitbox.setY(yPos - (float) streamHitbox.getHeight() /2);
            }

            if(streamHitbox.intersects(controller.getPlayerController().getHitbox())){
                controller.getPlayerController().hitted(10, movementVector);
            }

            defence = 4;
            changeState(EntityStates.RECHARGE);
        }
    }

    private boolean iCanShotToPlayer(){

        if(streamCounter < 200*0){
            return false;
        }

        float playerX = controller.getPlayerController().getxPosPlayer();
        float playerY = controller.getPlayerController().getyPosPlayer();

        float xDistance = Math.abs(xPos - playerX);
        float yDistance = Math.abs(yPos - playerY);

        if(xDistance < 10*GamePanel.TILES_SIZE && yDistance < 10*GamePanel.TILES_SIZE) {
            boolean dentroLaHitboxLato = playerX > hitbox.getX() && playerX < hitbox.getX() + hitbox.getWidth();
            boolean dentroLaHitboxAltezza = playerY > hitbox.getY() && playerY < hitbox.getY() + hitbox.getHeight();

            return dentroLaHitboxLato || dentroLaHitboxAltezza;
        }

        return false;
    }

    public Hitbox getStreamHitbox(){
        return streamHitbox;
    }

    public void hitted(int playerAttack){
        if(currentState != EntityStates.HITTED && currentState != EntityStates.ATTACKING && currentState != EntityStates.DYING) {

            if(noDamageCounter < 150)
                return;

            noDamageCounter = 0;
            changeState(EntityStates.HITTED);
            int damage = playerAttack - defence - GameState.difficulty;
            if (damage > 0) {
                life -= damage;
            }
            if(life <= 0){
                changeState(EntityStates.SPEAKING);
                System.out.println("morto");
            }
        }
    }

}
