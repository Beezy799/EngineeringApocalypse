package src.controller.entitycontroller.enemy;

import src.controller.BulletController;
import src.controller.Hitbox;
import src.controller.IController;
import src.controller.Vector;
import src.model.BulletComplete;
import src.model.EntityStates;
import src.model.Rooms;
import src.model.entity.NpcComplete;
import src.view.entityView.npc.ProfView;
import src.view.gameWindow.GamePanel;
import src.view.playStateView.BulletView;

public class RobotController extends  EnemyController{

    private static int numRobots;
    private int hitboxWidth = 28, hitboxHeight = 28;
    private float range = GamePanel.TILES_SIZE*5;

    public RobotController(int x, int y, IController c, int index) {
        super(x, y, c, index);

        speed = GamePanel.SCALE*0.7f;

        setHitbox(hitboxWidth, hitboxHeight, 12, 12);
        attackHitbox = new Hitbox(0,0, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);

        xAttackHitboxOffset = attackHitbox.getWidth()/2;
        yAttackHitboxOffset = attackHitbox.getHeight()/2;

        life = 100;
        attack = 0;
        defence = 1;

        numRobots++;
    }
    @Override
    public void update() {
        updateDamageCounter();

        switch (currentState){
        case IDLE:
            normalState();
            break;

        case MOVE:
            if(icanHitThePlayer(range)) {
                changeState(EntityStates.ATTACKING);
                setStateLocked(true);
            }
            //controlla se lo vede
            else if(iCanSeeThePlayer()){
                if(iCanReachThePlayer()){
                    pathNodeIndex = 0;
                    changeState(EntityStates.CHASE);
                }
                else {
                    changeState(EntityStates.RECHARGE);
                }
            }
            //se il player è molto lontano, si muove
            else {
                updatePosition();
            }
            break;

        case ATTACKING:
            path = null;
            pathNodeIndex = 0;

            turnToPlayer();
            shootToPlayer();

            setStateLocked(false);
            changeState(EntityStates.RECHARGE);
            break;

        case CHASE:
            chaseThePlayer();
            break;

        case HITTED:
            hittedState();
            break;

        case RECHARGE:
            recharge();
            break;
        }
    }

    private void normalState() {
        //se mentre insegue il player sbatte controo un'altr entità,
        //ritorna allo stato idle, ma vogliamo evitare che faccia
        //pathfinding troppo spesso
        if(pathNodeIndex != 0 || path != null){
            path = null;
            pathNodeIndex = 0;
            changeState(EntityStates.RECHARGE);
        }

        //controlla se il player è sotto tiro
        else if(icanHitThePlayer(range)){
            changeState(EntityStates.ATTACKING);
            setStateLocked(true);
        }
        //controlla se lo vede
        else if(iCanSeeThePlayer()){
            if(iCanReachThePlayer()){
                pathNodeIndex = 0;
                changeState(EntityStates.CHASE);
            }
            else {
                changeState(EntityStates.RECHARGE);
            }
        }
        //se il player è molto lontano, gira a caso
        else{
            randomMove();
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
            //se è arrivato a fine percorso
            else if (pathNodeIndex == path.size() - 1) {
                pathNodeIndex = 0;
                path = null;

                if (iCanSeeThePlayer()) {
                    if (iCanReachThePlayer()) {
                        changeState(EntityStates.CHASE);
                    }
                } else {
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

    private void hittedState() {
        hittedCounter++;
        if(hittedCounter >= 140){
            hittedCounter = 0;
            changeState(EntityStates.IDLE);
        }
    }

    private void recharge() {
        rechargeCounter++;
        if(rechargeCounter >= 200) {
            rechargeCounter = 0;
            changeState(EntityStates.IDLE);
        }
    }

    protected boolean icanHitThePlayer(float range) {
        float playerX = controller.getPlayerController().getxPosPlayer();
        float playerY = controller.getPlayerController().getyPosPlayer();

        float xDistance = Math.abs(xPos - playerX);
        float yDistance = Math.abs(yPos - playerY);

        if(xDistance < range && yDistance < range) {
            boolean dentroLaHitboxLato = playerX > hitbox.getX() && playerX < hitbox.getX() + hitbox.getWidth();
            boolean dentroLaHitboxAltezza = playerY > hitbox.getY() && playerY < hitbox.getY() + hitbox.getHeight();
            return dentroLaHitboxLato || dentroLaHitboxAltezza;
        }
        return false;
    }

    private void shootToPlayer() {
        Vector bulletVector = new Vector(1);
        if(movementVector.getX() != 0){
            bulletVector.setX(movementVector.getNomalizedX());
        }
        else {
            bulletVector.setY(movementVector.getNormalizedY());
        }
        setNewBullet(bulletVector);
    }

    private void setNewBullet(Vector bulletVector) {
        Hitbox bulletHitbox = new Hitbox(0,0, GamePanel.TILES_SIZE/2, GamePanel.TILES_SIZE/2);
        float xBullet = 0, yBullet = 0;
        if(bulletVector.getX() > 0){
            xBullet = xPos + hitbox.getWidth()/2 + bulletHitbox.getWidth()/2 + 1;
            yBullet = yPos;
        }
        else if (bulletVector.getX() < 0) {
            xBullet = xPos - hitbox.getWidth()/2 - bulletHitbox.getWidth()/2 - 1;
            yBullet = yPos;
        }
        else if (bulletVector.getY() < 0) {
            xBullet = xPos;
            yBullet = yPos - hitbox.getHeight()/2 - bulletHitbox.getHeight()/2 - 1;
        }
        else if (bulletVector.getY() > 0) {
            xBullet = xPos;
            yBullet = yPos + hitbox.getHeight()/2 + bulletHitbox.getHeight()/2 + 1;
        }

        BulletController bc = new BulletController(bulletHitbox, xBullet, yBullet, bulletVector, controller, this);
        bc.setDamage(5);
        BulletView bv = new BulletView(bulletVector);
        BulletComplete bulletComplete = new BulletComplete(bv, bc);

        int index = Rooms.actualRoom.getBuletList().size();
        bulletComplete.setIndexInList(index);
        Rooms.actualRoom.getBuletList().add(bulletComplete);
    }

    public void abbassaNumeroRobot(){
        numRobots--;
        if(numRobots == 0){
            controller.getPlayerController().addCFU(50);
            controller.getView().getPlayStateView().getPlayUI().setMessageToShow("hai distrutto tutti i robot");
            for(NpcComplete prof : Rooms.AULA_STUDIO.getNpc()){
                if(prof.getNpcView() instanceof ProfView){
                    prof.getNpcView().setNextDialogue();
                }
            }
        }
    }

    public void reset(){
        super.reset();
        numRobots = 4;
    }

}
