package src.controller.entitycontroller.enemy;

import src.controller.Hitbox;
import src.controller.IController;
import src.controller.pathFinding.Node;
import src.model.EntityStates;
import src.view.gameWindow.GamePanel;

public class NullafacenteController extends EnemyController{

    private int rechargeCounter, hittedCounter;
    private int hitboxWidth = 32, hitboxHeight = 32;


    public NullafacenteController(int x, int y, IController c, int index) {
        super(x, y, c, index);
        setHitbox(hitboxWidth, hitboxHeight);
        interactionHitbox = new Hitbox( xPos - 8*GamePanel.TILES_SIZE/2,
                                        yPos - 8*GamePanel.TILES_SIZE/2,
                                        8*GamePanel.TILES_SIZE,
                                        8*GamePanel.TILES_SIZE);

        attackHitbox = new Hitbox(0,0, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
        life = 100;
        defence = 2;
    }

    @Override
    public void update() {
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
                else if(icanHitThePlayer()){
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
               if(icanHitThePlayer()) {
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
                //ora come ora attacca troppo velocemente, possiamo creare uno stato "prepara attacco"
                //che avverte il player di pararsi e uno stato attacca.
                turnToPlayer();
                shiftAttackHitbox();
                if(attackHitbox.intersects(controller.getPlayerController().getHitbox())){
                    controller.getPlayerController().hitted(10, movementVector);
                }
                changeState(EntityStates.RECHARGE);
                break;

            case CHASE:
                if(icanHitThePlayer()) {
                    path = null;
                    pathNodeIndex = 0;
                    changeState(EntityStates.ATTACKING);
                    stateLocked = true;
                }
                //se è arrivato a fine percorso
                else if (pathNodeIndex == path.size() -1) {
                    pathNodeIndex = 0;
                    path = null;

                    System.out.println("arrivato");

                    if(iCanSeeThePlayer()){
                        if(iCanReachThePlayer()){
                            changeState(EntityStates.CHASE);
                        }
                    }
                    else {
                        changeState(EntityStates.RECHARGE);
                    }
                }
                //se non è arrivato e il player è lontano, cammina nel percorso
                else {
                    followPath();
                }
                break;

            case HITTED:
                hittedCounter++;
                if(hittedCounter >= 200){
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

        //System.out.println(currentState);

    }

    private boolean iCanSeeThePlayer() {
       return interactionHitbox.intersects(controller.getPlayerController().getHitbox());
    }

    private boolean icanHitThePlayer() {
        int xdist = Math.abs(xPos - controller.getPlayerController().getxPosPlayer());
        int ydist = Math.abs(yPos - controller.getPlayerController().getyPosPlayer());

        if(xdist < GamePanel.TILES_SIZE*1.4f && ydist < GamePanel.TILES_SIZE*1.4f) {
            if(xPos/GamePanel.TILES_SIZE == controller.getPlayerController().getxPosPlayer()/GamePanel.TILES_SIZE ||
                    yPos/GamePanel.TILES_SIZE == controller.getPlayerController().getyPosPlayer()/GamePanel.TILES_SIZE) {
                return true;
            }
        }
        return false;
    }

    private void shiftAttackHitbox() {
        if(movementVector.getX() > 0){
            attackHitbox.setX(hitbox.getX() + hitbox.getWidth());
            attackHitbox.setY(yPos - (float) attackHitbox.getHeight() /2);
        }
        else if (movementVector.getX() < 0) {
            attackHitbox.setX(hitbox.getX() - attackHitbox.getWidth());
            attackHitbox.setY(yPos - (float) attackHitbox.getHeight() /2);
        }
        else if (movementVector.getY() < 0) {
            attackHitbox.setY(hitbox.getY() - attackHitbox.getHeight());
            attackHitbox.setX(xPos - (float) attackHitbox.getWidth() /2);
        }
        else if (movementVector.getY() > 0) {
            attackHitbox.setY(hitbox.getY() + hitbox.getHeight());
            attackHitbox.setX(xPos - (float) attackHitbox.getWidth() /2);
        }
    }

    private boolean iCanReachThePlayer(){
        Node start = new Node(yPos/GamePanel.TILES_SIZE, xPos/GamePanel.TILES_SIZE);
        int playerRow = controller.getPlayerController().getyPosPlayer()/GamePanel.TILES_SIZE;
        int playerCol = controller.getPlayerController().getxPosPlayer()/GamePanel.TILES_SIZE;
        Node goal = new Node(playerRow, playerCol);
        path = controller.getPathFinder().findPath(start, goal);
        return path != null;
    }

}
