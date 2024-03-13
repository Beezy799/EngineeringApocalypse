package src.view.playStateView;

import src.model.mapModel.Rooms;
import src.view.IView;
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

    private ArrayList<SortableElement> elementsAboveTheFloor;

    public PlayStateView(IView v){
        iView = v;
        playerView = new PlayerView(v);
        tileImageLoader = new TileImageLoader(v);
        tileImageLoader = null;
        elementsAboveTheFloor = new ArrayList<>();
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
        addTilesToSortList();
        //mettiamo le creature, per ora solo il giocatore
        elementsAboveTheFloor.add(playerView);
        //ordiniamo la lista
        //collections è una classe di utilità che implementa un algoritmo veloce di ordinamento
        Collections.sort(elementsAboveTheFloor);
        //disegnamo le cose in ordine
        drawAllEnementsAboveTheFloor(g2, xPlayer, yPlayer);
        //svuotiamo la lista, perchè verrà ridisegnato ogni volta
        elementsAboveTheFloor.clear();

    }

    private void drawAllEnementsAboveTheFloor(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
        for(int i = 0; i < elementsAboveTheFloor.size(); i++)
            elementsAboveTheFloor.get(i).draw(g2, xPlayerMap, yPlayerMap);
    }

    private void addTilesToSortList() {
        int[][] thirdLayer = Rooms.actualRoom.getMap().getTthirdLayer();
        for(int row = 0; row < thirdLayer.length; row++){
            for(int col = 0; col < thirdLayer[0].length; col++){
                if(thirdLayer[row][col] > 0) {
                    SortableTile tileToAdd = new SortableTile(iView, thirdLayer[row][col], 3, col, row);
                    elementsAboveTheFloor.add(tileToAdd);
                }
            }
        }

        int[][] fourthLayer = Rooms.actualRoom.getMap().getFouthLayer();
        for(int row = 0; row < fourthLayer.length; row++){
            for(int col = 0; col < fourthLayer[0].length; col++){
                if(fourthLayer[row][col] > 0) {
                    SortableTile tileToAdd = new SortableTile(iView, fourthLayer[row][col], 4, col, row);
                    elementsAboveTheFloor.add(tileToAdd);
                }
            }
        }
    }

    private void drawFirstLayer(Graphics2D g2, int xPlayerpos, int yPlayerPos) {

        //prende la mappa
        int[][] tileNumbers = Rooms.actualRoom.getMap().getFirstLayer();

        for(int righe = 0; righe < tileNumbers.length; righe++){
            for(int colonne = 0; colonne < tileNumbers[0].length; colonne++){

                //prende dalla mappa il numero del tile in quella posizione
                int tileNumber = tileNumbers[righe][colonne];

                //prende la posizione del tile all'interno della mappa
                int tileXPositionOnMap = colonne * GamePanel.TILES_SIZE;
                int tileYPositionOnMap = righe * GamePanel.TILES_SIZE;

                //vede quanto dista il tile dal player
                int xDistanceFromPlayer = tileXPositionOnMap - xPlayerpos;
                int yDistanceFromPlayer = tileYPositionOnMap - yPlayerPos;

                //qui bisogna mettere un if: il tile viene disegnato solo se è nella finestra di gioco
                if(Math.abs(xDistanceFromPlayer) < GamePanel.GAME_WIDTH/2 && Math.abs(yDistanceFromPlayer) < GamePanel.GAME_HEIGHT/2){

                    //la distanza del tile dal centro dello schermo è uguale alla distanza del tile dal plaer nella mappa
                    int xScreen = GamePanel.CENTER_X_GAME_PANEL + xDistanceFromPlayer;
                    int yScreen = GamePanel.CENTER_Y_GAME_PANEL + yDistanceFromPlayer;

                    if(tileNumber > 0) {
                        g2.drawImage(iView.getModel().getTileset().getTile(tileNumber).getTileView().getImage(), xScreen, yScreen, null);
                        //disegna i bordi del tile, per controllo
                        g2.drawRect(xScreen, yScreen, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
                    }
                }

            }
        }


    }

    private void drawSecondLayer(Graphics2D g2, int xPlayer, int yPlayer) {
        //prende la mappa
        int[][] tileNumbers = Rooms.actualRoom.getMap().getSecondLayer();

        for(int righe = 0; righe < tileNumbers.length; righe++){
            for(int colonne = 0; colonne < tileNumbers[0].length; colonne++){

                //prende dalla mappa il numero del tile in quella posizione
                int tileNumber = tileNumbers[righe][colonne];

                //prende la posizione del tile all'interno della mappa
                int tileXPositionOnMap = colonne * GamePanel.TILES_SIZE;
                int tileYPositionOnMap = righe * GamePanel.TILES_SIZE;

                //vede quanto dista il tile dal player
                int xDistanceFromPlayer = tileXPositionOnMap - xPlayer;
                int yDistanceFromPlayer = tileYPositionOnMap - yPlayer;

                //qui bisogna mettere un if: il tile viene disegnato solo se è nella finestra di gioco
                if(Math.abs(xDistanceFromPlayer) < GamePanel.GAME_WIDTH/2 && Math.abs(yDistanceFromPlayer) < GamePanel.GAME_HEIGHT/2){

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
    }

    private void drawThirdLayer(Graphics2D g2, int xPlayer, int yPlayer) {
        //prende la mappa
        int[][] tileNumbers = Rooms.actualRoom.getMap().getTthirdLayer();

        for(int righe = 0; righe < tileNumbers.length; righe++){
            for(int colonne = 0; colonne < tileNumbers[0].length; colonne++){

                //prende dalla mappa il numero del tile in quella posizione
                int tileNumber = tileNumbers[righe][colonne];

                //prende la posizione del tile all'interno della mappa
                int tileXPositionOnMap = colonne * GamePanel.TILES_SIZE;
                int tileYPositionOnMap = righe * GamePanel.TILES_SIZE;

                //vede quanto dista il tile dal player
                int xDistanceFromPlayer = tileXPositionOnMap - xPlayer;
                int yDistanceFromPlayer = tileYPositionOnMap - yPlayer;

                //qui bisogna mettere un if: il tile viene disegnato solo se è nella finestra di gioco
                if(Math.abs(xDistanceFromPlayer) < GamePanel.GAME_WIDTH/2 && Math.abs(yDistanceFromPlayer) < GamePanel.GAME_HEIGHT/2){

                    //la distanza del tile dal centro dello schermo è uguale alla distanza del tile dal plaer nella mappa
                    int xScreen = GamePanel.CENTER_X_GAME_PANEL + xDistanceFromPlayer;
                    int yScreen = GamePanel.CENTER_Y_GAME_PANEL + yDistanceFromPlayer;

                    if(tileNumber > 0) {
                        g2.drawImage(iView.getModel().getTileset().getTile(tileNumber).getTileView().getImage(), xScreen, yScreen, null);
                        int x = xScreen + iView.getModel().getTileset().getTile(tileNumber).getTileModel().getHitbox().getX();
                        int y = yScreen + iView.getModel().getTileset().getTile(tileNumber).getTileModel().getHitbox().getY();
                        int w = iView.getModel().getTileset().getTile(tileNumber).getTileModel().getHitbox().getWidth();
                        int h = iView.getModel().getTileset().getTile(tileNumber).getTileModel().getHitbox().getHeight();
                        g2.setColor(Color.red);
                        g2.drawRect(x, y, w, h);
                    }
                }

            }
        }
    }

    private void drawFourthLayer(Graphics2D g2, int xPlayer, int yPlayer) {
        //prende la mappa
        int[][] tileNumbers = Rooms.actualRoom.getMap().getFouthLayer();

        for(int righe = 0; righe < tileNumbers.length; righe++){
            for(int colonne = 0; colonne < tileNumbers[0].length; colonne++){

                //prende dalla mappa il numero del tile in quella posizione
                int tileNumber = tileNumbers[righe][colonne];

                //prende la posizione del tile all'interno della mappa
                int tileXPositionOnMap = colonne * GamePanel.TILES_SIZE;
                int tileYPositionOnMap = righe * GamePanel.TILES_SIZE;

                //vede quanto dista il tile dal player
                int xDistanceFromPlayer = tileXPositionOnMap - xPlayer;
                int yDistanceFromPlayer = tileYPositionOnMap - yPlayer;

                //qui bisogna mettere un if: il tile viene disegnato solo se è nella finestra di gioco
                if(Math.abs(xDistanceFromPlayer) < GamePanel.GAME_WIDTH/2 && Math.abs(yDistanceFromPlayer) < GamePanel.GAME_HEIGHT/2){

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
    }

    public PlayerView getPlayerView(){
        return  playerView;
    }
}
