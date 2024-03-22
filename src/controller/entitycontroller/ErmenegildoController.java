package src.controller.entitycontroller;

import src.controller.IController;
import src.model.EntityStates;
import src.model.Hitbox;
import src.view.main.GamePanel;

public class ErmenegildoController extends EntityController {
    private final int hitboxWidth = 16;
    private final int hitboxHeight = 20;

    public ErmenegildoController(int x, int y, IController c) {
        super(x, y, c);
        setHitbox(hitboxWidth, hitboxHeight);
        YhitboxOffset = 3*hitboxHeight/4;
    }



}
