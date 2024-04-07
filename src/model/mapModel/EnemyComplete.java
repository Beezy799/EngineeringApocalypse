package src.model.mapModel;

import src.controller.entitycontroller.enemy.EnemyController;
import src.view.entityView.enemy.EnemyView;

public class EnemyComplete {
    private EnemyView enemyView;
    private EnemyController enemyController;

    public EnemyComplete(EnemyController c, EnemyView v){
        this.enemyController = c;
        this.enemyView = v;

    }
    public EnemyView getEnemyView() {
        return enemyView;
    }

    public EnemyController getEnemyController(){
        return enemyController;
    }

    public void lowIndex() {
        int newIndex = enemyController.getEntityIndex() -1;
        enemyController.setIndex(newIndex);
        enemyView.setIndex(newIndex);
    }
}
