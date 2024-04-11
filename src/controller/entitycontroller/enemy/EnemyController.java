package src.controller.entitycontroller.enemy;

import src.controller.Hitbox;
import src.controller.IController;
import src.controller.entitycontroller.EntityController;
import src.model.EntityStates;

public abstract class EnemyController extends EntityController {

    protected int life = 100;
    protected int bullet = 0;
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


}