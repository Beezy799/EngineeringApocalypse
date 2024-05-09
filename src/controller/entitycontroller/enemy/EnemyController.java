package src.controller.entitycontroller.enemy;

import src.controller.Hitbox;
import src.controller.IController;
import src.controller.entitycontroller.EntityController;
import src.controller.pathFinding.Node;
import src.model.EntityStates;
import src.model.GameState;
import src.view.gameWindow.GamePanel;

public abstract class EnemyController extends EntityController {

    protected int rechargeCounter, hittedCounter, attackCounter;

    protected int hitboxWidth = 30, hitboxHeight = 30;
    protected float range = GamePanel.TILES_SIZE*1.4f;
    protected int maxLife, life;
    protected Hitbox attackHitbox;
    protected float xAttackHitboxOffset, yAttackHitboxOffset;

    //per gestire i danni subiti-fatti
    protected int attack, defence;

    //quando una entità viene colpita,  viene temporaneamente resa immune agli attacchi per un istante tramite.
    // Durante questo periodo di immunità, ha ancora la capacità di contrattaccare il giocatore.
    // Questa meccanica impedisce al giocatore di uccidere facilmente i nemici attraverso attacchi in rapida successione.
    protected int noDamageCounter;
    //serve al player per avere un attimo di tempo per pararsi prima che il colpo gli faccia danno.
    protected int momentOfDamage = 200;


    public EnemyController(int x, int y, IController c, int index) {
        super(x, y, c, index);
        maxLife = 100;
        life = maxLife;
    }

    public void hitted(int playerAttack){
        if(currentState != EntityStates.HITTED && currentState != EntityStates.ATTACKING && currentState != EntityStates.DYING) {
            //momento immunità
            if(noDamageCounter < 150)
                return;

            resetCounters();
            changeState(EntityStates.HITTED);
            //calcolo dei danni
            int damage = playerAttack - defence - GameState.difficulty;
            if (damage > 0) {
                life -= damage;
            }
            if(life <= 0){
                changeState(EntityStates.DYING);
                stateLocked = true;
            }
        }
    }

    protected void resetCounters() {
        noDamageCounter = 0;
        attackCounter = 0;
    }

    public int getAttack(){
        return attack;
    }

    public int getLife(){
        return life;
    }

    public void reset() {
        super.reset();
        resetCounters();
        stateLocked = false;
        life = maxLife;
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