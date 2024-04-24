package src.controller.entitycontroller;

import src.controller.Hitbox;
import src.controller.IController;
import src.controller.entitycontroller.enemy.EnemyController;
import src.model.EntityStates;
import src.view.gameWindow.GamePanel;

public class BossController extends EnemyController {

    private int streamCounter;
    private Hitbox streamHitbox;

    public BossController(int x, int y, IController c, int index) {
        super(x, y, c, index);

        speed = GamePanel.SCALE*0.7f;

        setHitbox(hitboxWidth, hitboxHeight, 12, 12);
        attackHitbox = new Hitbox(0,0, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);

        xAttackHitboxOffset = attackHitbox.getWidth()/2;
        yAttackHitboxOffset = attackHitbox.getHeight()/2;

        life = 100;
        attack = 15;
        defence = 2;

        streamHitbox = new Hitbox(0,0,0,0);

        currentState = EntityStates.RECHARGE;
    }

    @Override
    public void update() {

        updateDamageCounter();
        updateStreamCounter();

        switch (currentState){
            case IDLE:
                if(pathNodeIndex != 0 || path != null){
                    path = null;
                    pathNodeIndex = 0;
                    changeState(EntityStates.RECHARGE);
                }
                else if(icanHitThePlayer(range)){
                    changeState(EntityStates.ATTACKING);
                    setStateLocked(true);
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

            case ATTACKING:
                attackPlayer();
                break;

            case THROWING:
                streamCounter = 0;
                //turnToPlayer();
                shootToPlayer();
                break;

            case CHASE:
                chaseThePlayer();
                break;

            case RECHARGE:
                rechargeCounter++;
                if(rechargeCounter >= 100){
                    rechargeCounter = 0;
                    changeState(EntityStates.IDLE);
                }
                break;

            default:
                changeState(EntityStates.RECHARGE);
                break;
        }

        //System.out.println(currentState);

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
        if(attackCounter >= 100){
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
                changeState(EntityStates.ATTACKING);
                stateLocked = true;
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
        if(attackCounter >= 200){
            attackCounter = 0;

            //up
            if(movementVector.getY() < 0){
                streamHitbox.setY(0);
                streamHitbox.setX(xPos);
                streamHitbox.setWidth(GamePanel.TILES_SIZE);
                streamHitbox.setHeight((int)yPos);
            }

            //down
            if(movementVector.getY() > 0){
                streamHitbox.setY(yPos);
                streamHitbox.setX(xPos);
                streamHitbox.setWidth(GamePanel.TILES_SIZE);
                streamHitbox.setHeight((GamePanel.GAME_HEIGHT));
            }

            //right
            if(movementVector.getX() > 0){
                streamHitbox.setX(xPos);
                streamHitbox.setY(yPos);
                streamHitbox.setWidth((GamePanel.GAME_WIDTH));
                streamHitbox.setHeight(GamePanel.TILES_SIZE);
            }

            //left
            if(movementVector.getX() < 0){
                streamHitbox.setX(0);
                streamHitbox.setY(yPos);
                streamHitbox.setWidth((int)xPos);
                streamHitbox.setHeight(GamePanel.TILES_SIZE);
            }

            if(streamHitbox.intersects(controller.getPlayerController().getHitbox())){
                controller.getPlayerController().hitted(10, movementVector);
            }
            changeState(EntityStates.RECHARGE);
        }
    }

    private boolean iCanShotToPlayer(){

        if(streamCounter < 200*10){
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
}
