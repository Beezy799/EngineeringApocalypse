package src.model.mapModel;

import src.controller.entitycontroller.EntityController;
import src.view.entityView.EntityView;
import src.view.entityView.NpcView;

public class EnemyComplete {
    private EntityView entityView;
    private EntityController entityController;

    public EnemyComplete(EntityController c, NpcView v){
        this.entityController = c;
        this.entityView = v;

    }
    public EntityView getEntityView() {
        return entityView;
    }

    public EntityController getEntityController(){
        return entityController;
    }

}
