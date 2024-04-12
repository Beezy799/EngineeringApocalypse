package src.model.entity;

import src.controller.entitycontroller.EntityController;
import src.view.entityView.EntityView;

public class EntityComplete {

    protected EntityView entityView;
    protected EntityController entityController;

    public EntityComplete(EntityController ec, EntityView ev){
        entityController = ec;
        entityView = ev;

        entityView.setEntityComplete(this);
        entityController.setEntityComplete(this);
    }

    public EntityView getEntityView(){
        return entityView;
    }

    public EntityController getEntityController(){
        return entityController;
    }

}
