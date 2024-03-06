package src.model;

import src.controller.IController;
import src.view.IView;

public class IModel {

    private IController controller;
    private IView view;

    public IModel(IController contr){
        this.controller = contr;
        Map map = new Map();
        map.loadMap("res/esempio.json");
        Tileset tileset = new Tileset();
        tileset.loadTileset("res/tileset.json");
    }

    public void setView(IView v) {
        this.view = v;
    }

    public void changeGameState(GameState nextGameState){
        GameState.actualState = nextGameState;
    }
    


}
