package src.view.playStateView;

import src.model.mapModel.EntityComplete;
import src.model.mapModel.Rooms;
import src.view.IView;
import src.view.entityView.EntityView;
import src.view.entityView.GhostView;
import src.view.entityView.PlayerView;
import src.view.main.GamePanel;
import src.view.mapView.TileImageLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class PlayStateView {

    private IView iView;
    private TileImageLoader tileImageLoader;
    private PlayerView playerView;
    private PlayUI playUI;

    private GhostView ghostView;

    private ArrayList<SortableElement> elementsAboveTheFloor;

    public PlayStateView(IView v){
        iView = v;
        playerView = new PlayerView(v);
        tileImageLoader = new TileImageLoader(v);
        tileImageLoader = null;
        elementsAboveTheFloor = new ArrayList<>();
        playUI = new PlayUI(this);

        ghostView = new GhostView();
    }

    public void draw(Graphics2D g2){
        //per disegnare gli oggetti nella giusta posizione sullo schermo, ci prendiamo la posizione del player nella mappa
        //ci troviamo la posizione dell'oggetto relativa al player e capiamo dove esso si trova sullo schermo, sapendo
        //che il player è sempre al centro
        int xPlayer = iView.getController().getPlayerController().getxPosPlayer();
        int yPlayer = iView.getController().getPlayerController().getyPosPlayer();

        drawFirstLayer(g2, xPlayer, yPlayer);
        drawSecondLayer(g2, xPlayer, yPlayer);

        //mettimao i tile dei livelli 3 e 4 nella lista
        addTilesToSortList(xPlayer, yPlayer);
        //mettiamo le creature, compreso il giocatore
        addEntitiesToSortList(xPlayer, yPlayer);
        //ordiniamo la lista
        //collections è una classe di utilità che implementa un algoritmo veloce di ordinamento
        Collections.sort(elementsAboveTheFloor);
        //disegnamo le cose in ordine
        drawAllEnementsAboveTheFloor(g2, xPlayer, yPlayer);
        //svuotiamo la lista, perchè verrà ridisegnato ogni volta
        elementsAboveTheFloor.clear();

        playUI.draw(g2);
    }

    private void addEntitiesToSortList(int xPlayer, int yPlayer) {

        playerView.setYposMapToSort(iView.getController().getPlayerController().getyPosPlayer());
        elementsAboveTheFloor.add(playerView);

        //scandisce la lista delle entità della stanza attuale
        for(EntityComplete entity : Rooms.actualRoom.getEntities()){
            entity.getEntityView().updatePositionForSort();
            elementsAboveTheFloor.add(entity.getEntityView());
        }


    }

    private void drawAllEnementsAboveTheFloor(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
        for(int i = 0; i < elementsAboveTheFloor.size(); i++)
            elementsAboveTheFloor.get(i).draw(g2, xPlayerMap, yPlayerMap);
    }

    private void addTilesToSortList(int xPlayerPos, int yPlayerPos) {

        //per non perdere tempo, visitiamo solo la parte di matrice dove sono i tile da disegnare
        int colFirstTileToDraw = xPlayerPos/GamePanel.TILES_SIZE - 10;
        int rowFirstTileToDraw = yPlayerPos/GamePanel.TILES_SIZE - 8;

        int[][] thirdLayer = Rooms.actualRoom.getMap().getTthirdLayer();
        for(int row = rowFirstTileToDraw; row <= rowFirstTileToDraw + GamePanel.TILES_IN_HEIGHT + 1; row++){
            for(int col = colFirstTileToDraw; col <= colFirstTileToDraw + GamePanel.TILES_IN_WIDTH + 1; col++) {
                int tileIndex = 0;
                try {
                    tileIndex = thirdLayer[row][col];
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    tileIndex = 0;
                }
                if (tileIndex > 0) {
                    SortableTile tileToAdd = new SortableTile(iView, tileIndex, 3, col, row);
                    elementsAboveTheFloor.add(tileToAdd);
                }
            }
        }

        int[][] fourthLayer = Rooms.actualRoom.getMap().getFouthLayer();
        for(int row = rowFirstTileToDraw; row <= rowFirstTileToDraw + GamePanel.TILES_IN_HEIGHT + 1; row++){
            for(int col = colFirstTileToDraw; col <= colFirstTileToDraw + GamePanel.TILES_IN_WIDTH + 1; col++){
                int tileIndex = 0;
                try{
                    tileIndex = fourthLayer[row][col];
                }
                catch (ArrayIndexOutOfBoundsException e){
                    tileIndex = 0;
                }
                if(tileIndex > 0) {
                    SortableTile tileToAdd = new SortableTile(iView, tileIndex, 4, col, row);
                    elementsAboveTheFloor.add(tileToAdd);
                }
            }
        }
    }

    private void drawFirstLayer(Graphics2D g2, int xPlayerPos, int yPlayerPos) {

        //per non perdere tempo, visitiamo solo la parte di matrice dove sono i tile da disegnare
        int colFirstTileToDraw = xPlayerPos/GamePanel.TILES_SIZE - 10;
        int rowFirstTileToDraw = yPlayerPos/GamePanel.TILES_SIZE - 8;

        //prende la mappa
        int[][] tileNumbers = Rooms.actualRoom.getMap().getFirstLayer();

        for(int righe = rowFirstTileToDraw; righe <= rowFirstTileToDraw + GamePanel.TILES_IN_HEIGHT + 1; righe++){
            for(int colonne = colFirstTileToDraw; colonne <= colFirstTileToDraw + GamePanel.TILES_IN_WIDTH + 1; colonne++){

                //prende dalla mappa il numero del tile in quella posizione
                int tileNumber = 0;
                try {
                    tileNumber = tileNumbers[righe][colonne];
                }
                catch (ArrayIndexOutOfBoundsException e){
                    tileNumber = 0;
                }

                //prende la posizione del tile all'interno della mappa
                int tileXPositionOnMap = colonne * GamePanel.TILES_SIZE;
                int tileYPositionOnMap = righe * GamePanel.TILES_SIZE;

                //vede quanto dista il tile dal player
                int xDistanceFromPlayer = tileXPositionOnMap - xPlayerPos;
                int yDistanceFromPlayer = tileYPositionOnMap - yPlayerPos;

                //la distanza del tile dal centro dello schermo è uguale alla distanza del tile dal plaer nella mappa
                int xScreen = GamePanel.CENTER_X_GAME_PANEL + xDistanceFromPlayer;
                int yScreen = GamePanel.CENTER_Y_GAME_PANEL + yDistanceFromPlayer;

                if(tileNumber > 0) {
                    g2.drawImage(iView.getModel().getTileset().getTile(tileNumber).getTileView().getImage(), xScreen, yScreen, null);
                    //disegna i bordi del tile, per controllo
                    //g2.drawRect(xScreen, yScreen, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
                }
            }
        }

    }

    private void drawSecondLayer(Graphics2D g2, int xPlayerPos, int yPlayerPos) {
        //per non perdere tempo, visitiamo solo la parte di matrice dove sono i tile da disegnare
        int colFirstTileToDraw = xPlayerPos/GamePanel.TILES_SIZE - 10;
        int rowFirstTileToDraw = yPlayerPos/GamePanel.TILES_SIZE - 8;

        //prende la mappa
        int[][] tileNumbers = Rooms.actualRoom.getMap().getSecondLayer();

        for(int righe = rowFirstTileToDraw; righe <= rowFirstTileToDraw + GamePanel.TILES_IN_HEIGHT + 1; righe++){
            for(int colonne = colFirstTileToDraw; colonne <= colFirstTileToDraw + GamePanel.TILES_IN_WIDTH + 1; colonne++){

                //prende dalla mappa il numero del tile in quella posizione
                int tileNumber = 0;
                try {
                    tileNumber = tileNumbers[righe][colonne];
                }
                catch (ArrayIndexOutOfBoundsException e){
                    tileNumber = 0;
                }

                //prende la posizione del tile all'interno della mappa
                int tileXPositionOnMap = colonne * GamePanel.TILES_SIZE;
                int tileYPositionOnMap = righe * GamePanel.TILES_SIZE;

                //vede quanto dista il tile dal player
                int xDistanceFromPlayer = tileXPositionOnMap - xPlayerPos;
                int yDistanceFromPlayer = tileYPositionOnMap - yPlayerPos;

                //la distanza del tile dal centro dello schermo è uguale alla distanza del tile dal plaer nella mappa
                int xScreen = GamePanel.CENTER_X_GAME_PANEL + xDistanceFromPlayer;
                int yScreen = GamePanel.CENTER_Y_GAME_PANEL + yDistanceFromPlayer;

                if(tileNumber > 0) {
                    g2.drawImage(iView.getModel().getTileset().getTile(tileNumber).getTileView().getImage(), xScreen, yScreen, null);
                    //disegna i bordi del tile, per controllo
                    //g2.drawRect(xScreen, yScreen, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
                }
            }
        }
    }

    public PlayerView getPlayerView(){
        return  playerView;
    }

    public IView getView() {
        return iView;
    }
}
