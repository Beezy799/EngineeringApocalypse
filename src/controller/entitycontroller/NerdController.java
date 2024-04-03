package src.controller.entitycontroller;

import src.controller.IController;
import src.model.EntityStates;

public class NerdController extends EntityController{

    private final int hitboxWidth = 32;
    private final int hitboxHeight = 32;
    public NerdController(int x, int y, IController c, int index) {
        super(x, y, c, index);
        setHitbox(hitboxWidth, hitboxHeight);
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
    }
}
