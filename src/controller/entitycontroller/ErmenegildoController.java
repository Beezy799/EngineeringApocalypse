package src.controller.entitycontroller;

import src.controller.IController;
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



        path.add(new Node(15,16));
        path.add(new Node(15,17));
        path.add(new Node(15,18));
        path.add(new Node(16,18));
        path.add(new Node(17,18));
        path.add(new Node(18,18));
        path.add(new Node(18,19));

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
                //updatePosition();


                if(pathNodeIndex < path.size())
                    followPath();
                else {
                    currentState = EntityStates.IDLE;
                }
                break;


            default:
                currentState = EntityStates.IDLE;
                break;
        }
    }


}
