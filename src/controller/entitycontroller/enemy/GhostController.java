package src.controller.entitycontroller.enemy;

import src.controller.Hitbox;
import src.controller.IController;
import src.model.EntityStates;
import src.view.gameWindow.GamePanel;

public class GhostController extends EnemyController{

    private int hitboxWidth = 30, hitboxHeight = 30;

    public GhostController(int x, int y, IController c, int index) {
        super(x, y, c, index);

        speed = GamePanel.SCALE*0.3f;

        setHitbox(hitboxWidth, hitboxHeight, 12, 12);
        tempHitbox.setHeight(0);
        tempHitbox.setWidth(0);

        life = 100;
        defence = 100;
        attack = 30;

        currentState = EntityStates.IDLE;
    }

    @Override
    public void update() {

        int xPlayer = (int)controller.getPlayerController().getxPosPlayer();
        int yPlayer = (int)controller.getPlayerController().getyPosPlayer();

        //si avvicina lentamente al giocatore, oltrepassando oggetti ed entità
        goToPlayer(xPlayer, yPlayer);

        //quando è vicino al player gli fa male e poi riappare in un'altro punto
        hitPlayer(xPlayer, yPlayer);

    }

    private void goToPlayer(int xPlayer, int yPlayer) {
        goToCenterOfTile(xPlayer,yPlayer);
    }

    private void hitPlayer(int xPlayer, int yPlayer) {
        if(hitbox.intersects(controller.getPlayerController().getHitbox())){
            controller.getPlayerController().hitted(attack, movementVector);
            xPos = 12;
            yPos = 12;
            hitbox.setX(10);
            hitbox.setY(10);
        }
    }


}
