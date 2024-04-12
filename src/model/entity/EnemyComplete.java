package src.model.entity;

import src.controller.entitycontroller.enemy.EnemyController;
import src.view.entityView.enemy.EnemyView;

public class EnemyComplete extends EntityComplete{
    //in caso di game over, il gioco si deve resettare. quando il nemico muore, non lo eliminiamo dalla memoria, ma lo lasciamo
    //"dormiente" mettendo alive = false
    //quando il player muore, il gioco si resetta e tutti i nemici tornano in vita
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


    public void reset() {
        setAlive(true);
        getEnemyController().reset();
    }
}