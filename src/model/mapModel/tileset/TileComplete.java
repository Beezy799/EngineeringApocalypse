package src.model.mapModel.tileset;

import src.view.mapView.TileView;


//i tile completi contengono sia la hitbox che le immagini, in questo modo evitiamo di creare due array
//di oggetti che logicamente sono collegati ma fisicamente sono separati ed hanno in comune solo l'indice
public class TileComplete {
    private TileModel tileModel;
    private TileView tileView;


    public TileComplete(TileModel tilem){
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
