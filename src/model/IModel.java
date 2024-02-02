package src.model;

import src.controller.IController;
import src.view.IView;

public class IModel {

    private IController controller;
    private IView view;

    public IModel(IController contr){
        this.controller = contr;
    }

    public void setView(IView v) {
        this.view = v;
    }

    public void changeGameState(GameState nextGameState){
        GameState.actualState = nextGameState;
    }
    


}
