package src.controller.entitycontroller;

import src.controller.IController;
import src.model.Hitbox;
import src.view.main.GamePanel;

public class ProfController extends EntityController{

    private final int hitboxWidth = 16;
    private final int hitboxHeight = 35;

    public ProfController(int x, int y, IController c) {
        super(x, y, c);
        setHitbox(hitboxWidth, hitboxHeight);

        int interactionHitboxWidth = 2* GamePanel.TILES_SIZE;
        int interactionHitboxHeight = 2*GamePanel.TILES_SIZE;

        interactionHitbox = new Hitbox( x * GamePanel.TILES_SIZE - interactionHitboxWidth/2,
                y * GamePanel.TILES_SIZE - interactionHitboxHeight/2,
                interactionHitboxWidth,
                interactionHitboxHeight);
    }
}
