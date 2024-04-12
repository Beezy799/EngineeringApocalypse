package src.controller.entitycontroller.enemy;

import src.controller.Hitbox;
import src.controller.IController;
import src.controller.pathFinding.Node;
import src.model.EntityStates;
import src.view.gameWindow.GamePanel;

public class NullafacenteController extends EnemyController{

    private int rechargeCounter, hittedCounter;
    private int hitboxWidth = 32, hitboxHeight = 32;
    private float range = GamePanel.TILES_SIZE*1.4f;
    private int attackCounter;


    public NullafacenteController(int x, int y, IController c, int index) {
        super(x, y, c, index);

        setHitbox(hitboxWidth, hitboxHeight, 8, 8);
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
                //ora come ora non si capisce quando parte l'attacco, possiamo creare uno stato "prepara attacco"
                //che avverte il player e uno stato attacca.
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

                break;

            case CHASE:
                if(icanHitThePlayer(range)) {
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


}