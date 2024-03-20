package src.controller.entitycontroller;

import src.model.EntityStates;
import src.model.Hitbox;
import src.view.main.GamePanel;

public class ErmenegildoController extends EntityController {


    public ErmenegildoController(Hitbox h, int x, int y) {
        super(h, x, y);
        int interactionHitboxWidth = 2*GamePanel.TILES_SIZE;
        int interactionHitboxHeight = 2*GamePanel.TILES_SIZE;

        interactionHitbox = new Hitbox( x * GamePanel.TILES_SIZE - interactionHitboxWidth/2,
                                        y * GamePanel.TILES_SIZE - interactionHitboxHeight/2,
                                        interactionHitboxWidth,
                                        interactionHitboxHeight);

    }




}
