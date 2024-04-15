package src.controller.entitycontroller.npc;

import src.controller.IController;
import src.controller.entitycontroller.EntityController;
import src.controller.pathFinding.Node;
import src.model.EntityStates;
import src.view.gameWindow.GamePanel;

public class ErmenegildoController extends EntityController {
    private final int hitboxWidth = 30;
    private final int hitboxHeight = 23;

    public ErmenegildoController(int x, int y, IController c, int index) {
        super(x, y, c, index);
        setHitbox(hitboxWidth, hitboxHeight, 2, 2);
        speed = GamePanel.SCALE*0.5f;
    }

    public void update(){
        switch(currentState){
            case IDLE:
                randomMove();
                break;
            case SPEAKING:
                turnToPlayer();
                currentState = EntityStates.IDLE;
                break;
            case MOVE:
                updatePosition();
                break;
            default:
                currentState = EntityStates.IDLE;
                break;
        }
    }


}
