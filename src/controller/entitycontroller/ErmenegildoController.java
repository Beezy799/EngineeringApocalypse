package src.controller.entitycontroller;

import src.controller.IController;
import src.model.EntityStates;

public class ErmenegildoController extends EntityController {
    private final int hitboxWidth = 16;
    private final int hitboxHeight = 23;

    public ErmenegildoController(int x, int y, IController c, int index) {
        super(x, y, c, index);
        setHitbox(hitboxWidth, hitboxHeight);
        //bisogna personalizzare la posizione della sua hitbox per non farlo disegnare sopra al player
        YhitboxOffset = hitbox.getHeight()/4;
        hitbox.setY(hitbox.getY() + YhitboxOffset);
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
