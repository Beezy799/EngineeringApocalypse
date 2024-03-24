package src.controller.entitycontroller;

import src.controller.IController;
import src.model.EntityStates;
import src.model.Hitbox;
import src.view.main.GamePanel;

public class PupaController extends EntityController{

    private final int hitboxWidth = 28;
    private final int hitboxHeight = 30;

    public PupaController(int x, int y, IController c, int index) {
        super(x, y, c, index);

        setHitbox(hitboxWidth, hitboxHeight);
        movementVector.setX(-1);
    }

    @Override
    public void update() {
        switch(currentState){
            case MOVE:
                moveNearDoor(31, 37);
                break;
            default:
                currentState = EntityStates.MOVE;
                break;
        }
    }

}


