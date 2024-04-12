package src.controller.entitycontroller.npc;

import src.controller.IController;
import src.controller.entitycontroller.EntityController;
import src.controller.pathFinding.Node;
import src.model.EntityStates;
import src.view.gameWindow.GamePanel;

public class NerdController extends EntityController {

    private final int hitboxWidth = 32;
    private final int hitboxHeight = 32;
    public NerdController(int x, int y, IController c, int index) {
        super(x, y, c, index);
        setHitbox(hitboxWidth, hitboxHeight, 2, 2);
    }

    @Override
    public void update() {
        switch(currentState){
            case IDLE:
                movementVector.resetDirections();
                movementVector.setY(1);
                break;
            case SPEAKING:
                turnToPlayer();
                currentState = EntityStates.IDLE;
                break;
            default:
                currentState = EntityStates.IDLE;
                break;
        }
//        switch (currentState){
//            case IDLE:
//                pathNodeIndex = 0;
//                Node start = new Node(yPos/ GamePanel.TILES_SIZE, xPos/GamePanel.TILES_SIZE);
//                Node goal = new Node(controller.getPlayerController().getyPosPlayer()/GamePanel.TILES_SIZE,
//                                     controller.getPlayerController().getxPosPlayer()/GamePanel.TILES_SIZE);
//                path = controller.getPathFinder().findPath(start, goal);
//                if(path != null){
//                    currentState = EntityStates.MOVE;
//                }
//                break;
//            case MOVE:
//                followPath();
//                if(pathNodeIndex == path.size() - 2) {
//                    pathNodeIndex = 0;
//                    currentState = EntityStates.IDLE;
//                }
//                break;
//            case SPEAKING:
//                turnToPlayer();
//                currentState = EntityStates.IDLE;
//                break;
//        }
    }
}
