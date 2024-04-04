package src.model.mapModel;

import src.controller.entitycontroller.EntityController;
import src.controller.entitycontroller.enemy.EnemyController;
import src.view.entityView.EnemyView;
import src.view.entityView.EntityView;
import src.view.entityView.NpcView;

public class EnemyComplete {
    private EnemyView entityView;
    private EnemyController entityController;

    public EnemyComplete(EnemyController c, EnemyView v){
        this.entityController = c;
        this.entityView = v;

    }
    public EnemyView getEnemyView() {
        return entityView;
    }

    public EnemyController getEntityController(){
        return entityController;
    }

}
