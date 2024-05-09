package src.controller.entitycontroller.npc;

import src.controller.IController;
import src.controller.entitycontroller.EntityController;
import src.controller.pathFinding.Node;
import src.model.EntityStates;
import src.model.Rooms;
import src.model.entity.EntityComplete;
import src.view.entityView.npc.CatView;
import src.view.gameWindow.GamePanel;

public class NerdController extends EntityController {

    private final int hitboxWidth = 32;
    private final int hitboxHeight = 32;

    private int waitCounter;

    public NerdController(int x, int y, IController c, int index) {
        super(x, y, c, index);
        speed = GamePanel.SCALE*0.6f;
        setHitbox(hitboxWidth, hitboxHeight, 2, 2);
    }

    @Override
    public void update() {
        switch (currentState){
            case IDLE:
                wait(400, EntityStates.CHASE);
                break;
            case CHASE:
                findPath();
                currentState = EntityStates.MOVE;
                break;
            case MOVE:
                followPath();
                if(pathNodeIndex == path.size() - 1) {
                    pathNodeIndex = 0;
                    currentState = EntityStates.IDLE;
                }
                break;
            case SPEAKING:
                turnToPlayer();
                for(int i = 0; i < Rooms.AULA_STUDIO.getNpc().size(); i++){
                    EntityComplete e = Rooms.AULA_STUDIO.getNpc().get(i);
                    if(e.getEntityController() instanceof CatController){
                        ((CatController) e.getEntityController()).setQuestActivated(true);
                        ((CatView)e.getEntityView()).setNextDialogue();
                    }
                }
                currentState = EntityStates.IDLE;
                break;
        }
    }

    private void findPath() {
        pathNodeIndex = 0;
        Node start = new Node((int)yPos/GamePanel.TILES_SIZE, (int)xPos/GamePanel.TILES_SIZE);

        int playerRow = (int)controller.getPlayerController().getyPosPlayer()/GamePanel.TILES_SIZE;
        int playerCol = (int)controller.getPlayerController().getxPosPlayer()/GamePanel.TILES_SIZE;
        Node goal = new Node(playerRow, playerCol);

        path = controller.getPathFinder().findPath(start, goal);
    }

    public void wait(int Limit, EntityStates nextState){
        waitCounter++;
        if(waitCounter >= Limit){
            waitCounter = 0;
            currentState = nextState;
        }
    }

}
