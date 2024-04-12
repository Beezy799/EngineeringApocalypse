package src.controller.entitycontroller.npc;

import src.controller.IController;
import src.controller.entitycontroller.EntityController;
import src.model.EntityStates;


public class PupaController extends EntityController {

    private final int hitboxWidth = 28;
    private final int hitboxHeight = 30;
    private int waitCounter;

    public PupaController(int x, int y, IController c, int index) {
        super(x, y, c, index);

        setHitbox(hitboxWidth, hitboxHeight, 2, 2);
        movementVector.setX(-1);
    }

    @Override
    public void update() {
        switch(currentState){
            case IDLE:
                wait(2, EntityStates.MOVE);
                break;
            case MOVE:
                moveNearDoor(31, 37);
                break;
            case SPEAKING:
                turnToPlayer();
                currentState = EntityStates.IDLE;
                break;
            default:
                currentState = EntityStates.IDLE;
                break;
        }
    }

    public void wait(int Limit, EntityStates nextState){
        waitCounter++;
        if(waitCounter >= Limit * 200){
            waitCounter = 0;
            currentState = nextState;
        }
    }

}


