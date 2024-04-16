package src.controller.entitycontroller.npc;

import src.controller.IController;
import src.controller.entitycontroller.EntityController;
import src.model.EntityStates;
import src.view.gameWindow.GamePanel;

public class ProfController extends EntityController {

    private final int hitboxWidth = 25;
    private final int hitboxHeight = 30;

    public ProfController(int x, int y, IController c, int index) {
        super(x, y, c, index);
        speed = GamePanel.SCALE*0.5f;
        setHitbox(hitboxWidth, hitboxHeight, 2, 2);

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
                moveNearDoor(16, 21);
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

