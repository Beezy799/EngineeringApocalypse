package src.model;

import src.controller.BulletController;
import src.view.playStateView.BulletView;

public class BulletComplete {
    private BulletController bulletController;
    private BulletView bulletView;
    private int indexInList;


    public BulletComplete(BulletView bv, BulletController bc){
        bulletController = bc;
        bulletView = bv;
        bulletController.setBulletComplete(this);
        bulletView.setBulletComplete(this);
    }

    public BulletController getBulletController() {
        return bulletController;
    }

    public BulletView getBulletView() {
        return bulletView;
    }

    public void setIndexInList(int index){
        indexInList = index;
    }

    public void abbassaIndice(){
        indexInList--;
    }

    public int getIndexInList(){
        return indexInList;
    }
}
