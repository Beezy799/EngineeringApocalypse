package src.model;

import src.controller.IController;
import src.model.mapModel.Map;
import src.model.mapModel.TilesetModel;
import src.view.IView;

public class IModel {

    private IController controller;
    private IView view;

    public IModel(IController contr){
        this.controller = contr;
        Map map = new Map();
        map.loadMap("res/esempio.json");
        TilesetModel tileset = new TilesetModel();
        tileset.loadTileset("res/tileset.json");
    }

    public void setView(IView v) {
        this.view = v;
    }

    public void changeGameState(GameState nextGameState){
        GameState.actualState = nextGameState;
    }
    


}
