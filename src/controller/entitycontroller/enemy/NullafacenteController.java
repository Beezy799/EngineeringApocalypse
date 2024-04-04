package src.controller.entitycontroller.enemy;

import src.controller.Hitbox;
import src.controller.IController;
import src.controller.pathFinding.Node;
import src.model.EntityStates;
import src.view.gameWindow.GamePanel;

public class NullafacenteController extends EnemyController{

    private int rechargeCounter = 0;
    private int hitboxWidth = 32, hitboxHeight = 32;


    public NullafacenteController(int x, int y, IController c, int index) {
        super(x, y, c, index);
        setHitbox(hitboxWidth, hitboxHeight);
        interactionHitbox = new Hitbox( xPos - 15*GamePanel.TILES_SIZE/2,
                                        yPos - 15*GamePanel.TILES_SIZE/2,
                                        15*GamePanel.TILES_SIZE,
                                        15*GamePanel.TILES_SIZE);
    }

    @Override
    public void update() {
        switch (currentState){
            case IDLE:
                //controlla se il giocatore è vicino
                if(interactionHitbox.intersects(controller.getPlayerController().getHitbox())){
                    Node start = new Node(yPos/ GamePanel.TILES_SIZE, xPos/GamePanel.TILES_SIZE);
                    int playerCol = controller.getPlayerController().getyPosPlayer()/GamePanel.TILES_SIZE;
                    int playerRow = controller.getPlayerController().getxPosPlayer()/GamePanel.TILES_SIZE;
                    Node goal = new Node(playerRow, playerCol);
                    path = controller.getPathFinder().findPath(start, goal);
                    //se può raggiungerlo
                    if(path != null){
                        currentState = EntityStates.CHASE;
                    }
                    else{
                        //per evitare che calcoli il cammino troppo frequentemente
                        currentState = EntityStates.RECHARGE;
                    }
                }
                //se il player è lontano, si muovea caso
                else{
                    randomMove();
                }
                break;

            case MOVE:
                if(interactionHitbox.intersects(controller.getPlayerController().getHitbox())) {
                    Node start = new Node(yPos / GamePanel.TILES_SIZE, xPos / GamePanel.TILES_SIZE);
                    int playerCol = controller.getPlayerController().getyPosPlayer() / GamePanel.TILES_SIZE;
                    int playerRow = controller.getPlayerController().getxPosPlayer() / GamePanel.TILES_SIZE;
                    Node goal = new Node(playerRow, playerCol);
                    path = controller.getPathFinder().findPath(start, goal);
                    //se può raggiungerlo
                    if (path != null) {
                        currentState = EntityStates.CHASE;
                    }
                }
                else
                    updatePosition();
                break;

            case CHASE:
                //controlla se il player è sotto tiro
                int xdist = Math.abs(xPos - controller.getPlayerController().getxPosPlayer());
                int ydist = Math.abs(yPos - controller.getPlayerController().getyPosPlayer());
                if(xdist < GamePanel.TILES_SIZE && ydist < GamePanel.TILES_SIZE){
                    pathNodeIndex = 0;
                    currentState = EntityStates.ATTACKING;
                }
                //se il player è ancora lontano, continua a seguire il percorso
                else if (pathNodeIndex < path.size() - 1){
                    followPath();
                }
                //se il player non è a tiro ed ha finito il percorso
                else {
                    pathNodeIndex = 0;
                    currentState = EntityStates.IDLE;
                }
                break;

            case ATTACKING:
                currentState = EntityStates.RECHARGE;
                break;

            case RECHARGE:
                rechargeCounter++;
                if(rechargeCounter >= 200){
                    rechargeCounter = 0;
                    currentState = EntityStates.IDLE;
                }
                break;
        }
    }


}
