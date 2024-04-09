package src.model.entity;

import src.controller.entitycontroller.enemy.EnemyController;
import src.view.entityView.enemy.EnemyView;

public class EnemyComplete extends EntityComplete{
//    private EnemyView enemyView;
//    private EnemyController enemyController;

    protected boolean alive;

    public EnemyComplete(EnemyController c, EnemyView v){
        super(c, v);
        alive = true;
    }

    public EnemyView getEnemyView() {
        return (EnemyView) entityView;
    }

    public EnemyController getEnemyController(){
        return (EnemyController) entityController;
    }

    public void setAlive(boolean b){
        alive = b;
    }

    public boolean isAlive(){
        return alive;
    }


}