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
    protected float xAttackHitboxOffset, yAttackHitboxOffset;

    //per gestire i danni subiti-fatti
    protected int attack = 20, defence = 4;
    protected boolean stateLocked = false;

    protected int noDamageCounter;


    public EnemyController(int x, int y, IController c, int index) {
        super(x, y, c, index);
    }

    public void hitted(int playerAttack){
        if(currentState != EntityStates.HITTED && currentState != EntityStates.ATTACKING && currentState != EntityStates.DYING) {

            if(noDamageCounter < 150)
                return;

            noDamageCounter = 0;
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
        float xdist = Math.abs(xPos - controller.getPlayerController().getxPosPlayer());
        float ydist = Math.abs(yPos - controller.getPlayerController().getyPosPlayer());

        if(xdist < range && ydist < range) {
            int enemyCol = (int)(xPos/GamePanel.TILES_SIZE);
            int enemyRow = (int)(yPos/GamePanel.TILES_SIZE);
            int playerCol = (int)(controller.getPlayerController().getxPosPlayer()/GamePanel.TILES_SIZE);
            int playerRow = (int)(controller.getPlayerController().getyPosPlayer()/GamePanel.TILES_SIZE);

            if(enemyCol == playerCol || enemyRow == playerRow) {
                return true;
            }
        }
        return false;
    }

    protected void shiftAttackHitbox() {
        if(movementVector.getX() > 0){
            attackHitbox.setX(hitbox.getX() + hitbox.getWidth());
            attackHitbox.setY(yPos - yAttackHitboxOffset);
        }
        else if (movementVector.getX() < 0) {
            attackHitbox.setX(hitbox.getX() - attackHitbox.getWidth());
            attackHitbox.setY(yPos - yAttackHitboxOffset);
        }
        else if (movementVector.getY() < 0) {
            attackHitbox.setY(hitbox.getY() - attackHitbox.getHeight());
            attackHitbox.setX(xPos - xAttackHitboxOffset);
        }
        else if (movementVector.getY() > 0) {
            attackHitbox.setY(hitbox.getY() + hitbox.getHeight());
            attackHitbox.setX(xPos - xAttackHitboxOffset);
        }
    }

    protected boolean iCanReachThePlayer(){
        Node start = new Node((int)(yPos/GamePanel.TILES_SIZE), (int)(xPos/GamePanel.TILES_SIZE));
        int playerRow = (int)controller.getPlayerController().getyPosPlayer()/GamePanel.TILES_SIZE;
        int playerCol = (int)controller.getPlayerController().getxPosPlayer()/GamePanel.TILES_SIZE;
        Node goal = new Node(playerRow, playerCol);
        path = controller.getPathFinder().findPath(start, goal);
        return path != null;
    }

    protected void updateDamageCounter() {
        noDamageCounter++;
        if(noDamageCounter >= 150){
            noDamageCounter = 150;
        }
    }

}