package src.controller.entitycontroller.enemy;
import src.controller.IController;
import src.view.gameWindow.GamePanel;

public class GhostController extends EnemyController{

    private int hitboxWidth = 30, hitboxHeight = 30;

    public GhostController(int x, int y, IController c, int index) {
        super(x, y, c, index);

        speed = GamePanel.SCALE*0.4f;

        setHitbox(hitboxWidth, hitboxHeight, 12, 12);
        tempHitbox.setHeight(0);
        tempHitbox.setWidth(0);

        life = 100;
        defence = 100;
        attack = 10;
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

        movementVector.resetDirections();
        //se sta a sinistra del centro, deve andare a destra
        if (xPos < xPlayer) {
            movementVector.setX(1);
        }
        //se sta sopra al centro, deve scendere
        else if (yPos < yPlayer) {
            movementVector.setY(1);
        }
        //se sta a destra, deve andare a sinistra
        else if (xPos > xPlayer) {
            movementVector.setX(-1);
        }
        //se sta sotto al centro, deve salire
        else if (yPos > yPlayer) {
            movementVector.setY(-1);
        }

        goToPlayerTile(xPlayer, yPlayer);
    }

    private void goToPlayerTile(int xPlayer, int yPlayer){

        if(movementVector.getY() < 0) {
            setyPos(getyPos() - speed);
            hitbox.setY(yPos - YhitboxOffset);
        }
        else if(movementVector.getY() > 0) {
            setyPos(getyPos() + speed);
            hitbox.setY(yPos - YhitboxOffset);
        }
        else if(movementVector.getX() < 0) {
            setxPos(getxPos() - speed);
            hitbox.setX(xPos - XhitboxOffset);
        }
        else if(movementVector.getX() > 0) {
            setxPos(getxPos() + speed);
            hitbox.setX(xPos - XhitboxOffset);
        }

        if(Math.abs(xPos - xPlayer) < speed){
            xPos = xPlayer;
        }
        if(Math.abs(yPos - yPlayer) < speed){
            yPos = yPlayer;
        }

    }

    private void hitPlayer(int xPlayer, int yPlayer) {
        if(hitbox.intersects(controller.getPlayerController().getHitbox())){
            controller.getPlayerController().hitted(attack, movementVector);
            xPos = initialXpos;
            yPos = initialYpos;
            hitbox.setX(initialXpos);
            hitbox.setY(initialYpos);
        }
    }


}
