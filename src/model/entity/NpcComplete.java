package src.model.entity;

import src.controller.entitycontroller.EntityController;
import src.view.entityView.npc.NpcView;


public class NpcComplete extends EntityComplete {

    public NpcComplete(EntityController c, NpcView v){
        super(c, v);

    }
    public NpcView getNpcView() {
        return (NpcView) entityView;
    }

}