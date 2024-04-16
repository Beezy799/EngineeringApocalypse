package src.controller.entitycontroller.enemy;

import src.controller.BulletController;
import src.controller.Hitbox;
import src.controller.IController;
import src.controller.Vector;
import src.model.BulletComplete;
import src.model.EntityStates;
import src.model.Rooms;
import src.view.gameWindow.GamePanel;
import src.view.playStateView.BulletView;

public class RobotController extends  EnemyController{

    private int rechargeCounter, hittedCounter;
    private int hitboxWidth = 28, hitboxHeight = 28;
    private float range = GamePanel.TILES_SIZE*5;
    private int attackCounter;

    public RobotController(int x, int y, IController c, int index) {
        super(x, y, c, index);

        speed = GamePanel.SCALE*0.7f;

        setHitbox(hitboxWidth, hitboxHeight, 12, 12);
        attackHitbox = new Hitbox(0,0, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);

        xAttackHitboxOffset = attackHitbox.getWidth()/2;
        yAttackHitboxOffset = attackHitbox.getHeight()/2;

        life = 100;
        attack = 0;
        defence = 4;
    }

    @Override
    public void update() {
        updateDamageCounter();

        switch (currentState){
            case IDLE:
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
                    stateLocked = true;
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
                break;

            case MOVE:
                if(icanHitThePlayer(range)) {
                    changeState(EntityStates.ATTACKING);
                    stateLocked = true;
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
                break;

            case CHASE:
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
                    //npe.printStackTrace();
                    pathNodeIndex = 0;
                    currentState = EntityStates.RECHARGE;
                }
                break;

            case HITTED:
                hittedCounter++;
                if(hittedCounter >= 100){
                    hittedCounter = 0;
                    changeState(EntityStates.IDLE);
                }
                break;

            case RECHARGE:
                rechargeCounter++;
                if(rechargeCounter >= 200) {
                    rechargeCounter = 0;
                    changeState(EntityStates.IDLE);
                }
                break;
        }
    }


    private void shootToPlayer() {
        attackCounter++;
        if(attackCounter > 100) {

            int playerCol = (int)(controller.getPlayerController().getxPosPlayer())/GamePanel.TILES_SIZE;
            int playerRow = (int)(controller.getPlayerController().getyPosPlayer())/GamePanel.TILES_SIZE;

            int enemyCol = (int)(xPos)/GamePanel.TILES_SIZE;
            int enemyRow = (int)(yPos)/GamePanel.TILES_SIZE;

            if(playerCol == enemyCol || playerRow == enemyRow) {
                crateBullet();
            }


            currentState = EntityStates.RECHARGE;
            attackCounter = 0;
        }

    }

    private void crateBullet() {

        Vector bulletVector = new Vector(1);

        if(movementVector.getX() != 0){
            bulletVector.setX(movementVector.getX());
        }
        else {
            bulletVector.setY(movementVector.getY());
        }

        setNewBuet(bulletVector);
            //se la direzione del giocatore non è specificata, il proiettile non si crea
//            if(bulletVector.getX() != 0 || bulletVector.getY() != 0){
//                if(notes > 0){
//                    notes--;
//                    setNewBuet(bulletVector);
//                }
//                else {
//                    controller.getView().getPlayStateView().getPlayUI().setMessageToShow("non hai appunti da lanciare");
//                }
//            }
        }

    private void setNewBuet(Vector bulletVector) {
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
        BulletView bv = new BulletView(bulletVector);
        BulletComplete bulletComplete = new BulletComplete(bv, bc);

        int index = Rooms.actualRoom.getBuletList().size();
        bulletComplete.setIndexInList(index);
        Rooms.actualRoom.getBuletList().add(bulletComplete);
    }

}
