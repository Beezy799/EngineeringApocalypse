package src.controller.entitycontroller.npc;

import src.controller.IController;
import src.controller.entitycontroller.EntityController;
import src.controller.pathFinding.Node;
import src.model.EntityStates;
import src.view.gameWindow.GamePanel;

public class ErmenegildoController extends EntityController {
    private final int hitboxWidth = 16;
    private final int hitboxHeight = 23;

    public ErmenegildoController(int x, int y, IController c, int index) {
        super(x, y, c, index);
        setHitbox(hitboxWidth, hitboxHeight);
        //bisogna personalizzare la posizione della sua hitbox per non farlo disegnare sopra al player
        YhitboxOffset = hitbox.getHeight()/2;
        hitbox.setY(hitbox.getY() + YhitboxOffset);

    }

    public void update(){
        switch(currentState){
            case IDLE:
                randomMove();

//                Node goal = new Node(14, 10);
//                Node start = new Node(yPos/GamePanel.TILES_SIZE, xPos/GamePanel.TILES_SIZE);
//
//                controller.getPathFinder().setNodes(start, goal);
//                path = controller.getPathFinder().findPath(start, goal);
//
//                if(path != null){
//                    currentState = EntityStates.MOVE;
//                    System.out.println("path trovato");
//                }

                break;
            case SPEAKING:
                turnToPlayer();
                currentState = EntityStates.IDLE;
                break;
            case MOVE:
                updatePosition();

//                if(pathNodeIndex < path.size())
//                    followPath();
//
//                else {
//                    System.out.println("arrivato");
//                    path.clear();
//                    currentState = EntityStates.IDLE;
//                }
                break;

            default:
                currentState = EntityStates.IDLE;
                break;
        }
    }


}
