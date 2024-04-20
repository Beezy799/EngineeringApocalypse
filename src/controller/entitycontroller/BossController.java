package src.controller.entitycontroller;

import src.controller.Hitbox;
import src.controller.IController;
import src.controller.entitycontroller.enemy.EnemyController;
import src.model.EntityStates;
import src.view.gameWindow.GamePanel;

public class BossController extends EnemyController {

    private int rechargeCounter, hittedCounter;
    private int hitboxWidth = 30, hitboxHeight = 30;
    private float range = GamePanel.TILES_SIZE*1.4f;
    private int attackCounter;

    public BossController(int x, int y, IController c, int index) {
        super(x, y, c, index);

        speed = GamePanel.SCALE*0.7f;

        setHitbox(hitboxWidth, hitboxHeight, 12, 12);
        attackHitbox = new Hitbox(0,0, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);

        xAttackHitboxOffset = attackHitbox.getWidth()/2;
        yAttackHitboxOffset = attackHitbox.getHeight()/2;

        life = 100;
        attack = 15;
        defence = 2;

        currentState = EntityStates.PARRING;
        movementVector.resetDirections();
        movementVector.setY(-1);
    }

    @Override
    public void update() {
        switch (currentState){
            case IDLE:
                randomMove();
                break;
            case MOVE:
                updatePosition();
                break;
            default:
                break;
        }
    }
}
