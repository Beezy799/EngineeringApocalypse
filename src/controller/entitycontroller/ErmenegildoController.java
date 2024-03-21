package src.controller.entitycontroller;

import src.model.EntityStates;
import src.model.Hitbox;
import src.view.main.GamePanel;

public class ErmenegildoController extends EntityController {
    private final int hitboxWidth = 16;
    private final int hitboxHeight = 35;

    public ErmenegildoController(int x, int y) {
        super(x, y);
        setHitbox(hitboxWidth, hitboxHeight);

        int interactionHitboxWidth = 2*GamePanel.TILES_SIZE;
        int interactionHitboxHeight = 2*GamePanel.TILES_SIZE;

        interactionHitbox = new Hitbox( x * GamePanel.TILES_SIZE - interactionHitboxWidth/2,
                                        y * GamePanel.TILES_SIZE - interactionHitboxHeight/2,
                                        interactionHitboxWidth,
                                        interactionHitboxHeight);

    }



}
