package src.model;

import src.controller.IController;
import src.model.mapModel.Map;
import src.model.mapModel.Rooms;
import src.model.mapModel.TilesetModel;
import src.view.IView;

import java.io.File;
import java.net.URL;

public class IModel {

    private IController controller;
    private IView view;

    public IModel(IController contr){
        this.controller = contr;

        int v = Rooms.AULA_STUDIO.getValorePerProvare();

        TilesetModel tileset = new TilesetModel();
        //tileset.loadTileset("res/map/tileset.json");
    }

    public void setView(IView v) {
        this.view = v;
    }

    public void changeGameState(GameState nextGameState){
        GameState.actualState = nextGameState;
    }
    

}
