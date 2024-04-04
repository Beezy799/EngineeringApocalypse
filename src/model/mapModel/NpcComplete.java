package src.model.mapModel;

import src.controller.entitycontroller.EntityController;
import src.view.entityView.npc.NpcView;


public class NpcComplete {

    private NpcView npcView;
    private EntityController entityController;

    public NpcComplete(EntityController c, NpcView v){
        this.entityController = c;
        this.npcView = v;

    }
    public NpcView getNpcView() {
        return npcView;
    }

    public EntityController getEntityController(){
        return entityController;
    }



}
