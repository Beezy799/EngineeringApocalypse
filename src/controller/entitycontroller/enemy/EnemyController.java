package src.controller.entitycontroller.enemy;

import src.controller.Hitbox;
import src.controller.IController;
import src.controller.entitycontroller.EntityController;

public abstract class EnemyController extends EntityController {

    private int life = 100;
    private int bullet = 0;
    private Hitbox attackHitbox;

    //per gestire i danni subiti-fstti
    private int attack = 10, defence = 10;


    public EnemyController(int x, int y, IController c, int index) {
        super(x, y, c, index);
    }

    public void hitted(int playerAttack){
        int damage = playerAttack - defence;
        if(damage > 0){
            life -= damage;
        }
    }

    public int getAttack(){
        return attack;
    }

    public int getLife(){
        return life;
    }

    public void die(){
        controller.getModel().removeEnemy(entityIndex);
    }

}
