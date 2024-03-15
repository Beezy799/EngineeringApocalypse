package src.model;

import src.controller.IController;
import src.model.mapModel.Rooms;
import src.model.mapModel.Tileset;
import src.view.IView;

public class IModel {

    private IController controller;
    private IView view;

    private Tileset tileset;

    public IModel(IController contr){
        this.controller = contr;

        tileset = new Tileset();
        tileset.loadTileset("/res/map/tilesetChat.json");

        //una volta finito di crearsi, passa il suo riferimento alle stanze
        Rooms.setModel(this);
    }

    public void setView(IView v) {
        this.view = v;
    }

    public void changeGameState(GameState nextGameState){
        GameState.actualState = nextGameState;
    }

    public Tileset getTileset(){
        return tileset;
    }

    public IController getController(){
        return controller;
    }

}
