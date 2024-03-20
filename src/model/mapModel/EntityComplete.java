package src.model.mapModel;

import src.controller.entitycontroller.EntityController;
import src.view.entityView.EntityView;


public class EntityComplete {

    private EntityView entityView;
    private EntityController entityController;

    public EntityComplete(EntityController c, EntityView v){
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
