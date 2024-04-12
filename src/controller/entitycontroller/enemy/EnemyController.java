package src.controller.entitycontroller.enemy;

import src.controller.Hitbox;
import src.controller.IController;
import src.controller.entitycontroller.EntityController;
import src.controller.pathFinding.Node;
import src.model.EntityStates;
import src.view.gameWindow.GamePanel;

public abstract class EnemyController extends EntityController {

    protected int life = 100;
    protected Hitbox attackHitbox;

    //per gestire i danni subiti-fatti
    protected int attack = 10, defence = 2;
    protected boolean stateLocked = false;


    public EnemyController(int x, int y, IController c, int index) {
        super(x, y, c, index);
    }

    public void hitted(int playerAttack){
        if(currentState != EntityStates.HITTED && currentState != EntityStates.ATTACKING && currentState != EntityStates.DYING) {
            changeState(EntityStates.HITTED);
            int damage = playerAttack - defence;
            if (damage > 0) {
                life -= damage;
            }
            if(life <= 0){
                changeState(EntityStates.DYING);
                stateLocked = true;
            }
        }
    }

    public int getAttack(){
        return attack;
    }

    public int getLife(){
        return life;
    }

    public  void setStateLocked(boolean b){
        stateLocked = b;
    }

    protected void changeState(EntityStates newState){
        if(!stateLocked){
            currentState = newState;
        }
    }

    public void reset() {
        super.reset();

        stateLocked = false;
        life = 100;
    }

    protected boolean iCanSeeThePlayer() {
        return interactionHitbox.intersects(controller.getPlayerController().getHitbox());
    }

    protected boolean icanHitThePlayer(float range) {
        int xdist = Math.abs(xPos - controller.getPlayerController().getxPosPlayer());
        int ydist = Math.abs(yPos - controller.getPlayerController().getyPosPlayer());

        if(xdist < range && ydist < range) {
            if(xPos/GamePanel.TILES_SIZE == controller.getPlayerController().getxPosPlayer()/GamePanel.TILES_SIZE ||
                    yPos/GamePanel.TILES_SIZE == controller.getPlayerController().getyPosPlayer()/GamePanel.TILES_SIZE) {
                return true;
            }
        }
        return false;
    }

    protected void shiftAttackHitbox() {
        if(movementVector.getX() > 0){
            attackHitbox.setX(hitbox.getX() + hitbox.getWidth());
            attackHitbox.setY(yPos - (float) attackHitbox.getHeight() /2);
        }
        else if (movementVector.getX() < 0) {
            attackHitbox.setX(hitbox.getX() - attackHitbox.getWidth());
            attackHitbox.setY(yPos - (float) attackHitbox.getHeight() /2);
        }
        else if (movementVector.getY() < 0) {
            attackHitbox.setY(hitbox.getY() - attackHitbox.getHeight());
            attackHitbox.setX(xPos - (float) attackHitbox.getWidth() /2);
        }
        else if (movementVector.getY() > 0) {
            attackHitbox.setY(hitbox.getY() + hitbox.getHeight());
            attackHitbox.setX(xPos - (float) attackHitbox.getWidth() /2);
        }
    }

    protected boolean iCanReachThePlayer(){
        Node start = new Node(yPos/GamePanel.TILES_SIZE, xPos/GamePanel.TILES_SIZE);
        int playerRow = controller.getPlayerController().getyPosPlayer()/GamePanel.TILES_SIZE;
        int playerCol = controller.getPlayerController().getxPosPlayer()/GamePanel.TILES_SIZE;
        Node goal = new Node(playerRow, playerCol);
        path = controller.getPathFinder().findPath(start, goal);
        return path != null;
    }

}