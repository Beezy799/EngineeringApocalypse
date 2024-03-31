package src.controller.entitycontroller;

import src.controller.IController;
import src.model.EntityStates;

public class ProfController extends EntityController{

    private final int hitboxWidth = 16;
    private final int hitboxHeight = 30;

    public ProfController(int x, int y, IController c, int index) {
        super(x, y, c, index);
        setHitbox(hitboxWidth, hitboxHeight);

        YhitboxOffset = hitbox.getHeight()/4;
        hitbox.setY(hitbox.getY() + YhitboxOffset);

        movementVector.resetDirections();
        movementVector.setX(-1);
    }

    @Override
    public void update() {
        switch(currentState){
            case IDLE:
                randomMove();
                break;
            case MOVE:
                moveNearDoor(13, 18);
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

