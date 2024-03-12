package src.model.mapModel;

import src.view.mapView.TileView;

public class TileCmplete {
    private TileModel tileModel;
    private TileView tileView;


    public TileCmplete(TileModel tilem){
        this.tileModel = tilem;
    }
    public TileView getTileView() {
        return tileView;
    }


    public TileModel getTileModel() {
        return tileModel;
    }
    //siccome la view si crea dopo, ci serve un metodo per salvare il tileview quando viene creato
    public void addTileView(TileView tilev){
        this.tileView = tilev;
    }


}
