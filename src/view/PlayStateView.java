package src.view;

import src.model.Hitbox;
import src.model.mapModel.Rooms;
import src.view.entityView.PlayerView;
import src.view.main.GamePanel;
import src.view.mapView.TileImageLoader;

import java.awt.*;

public class PlayStateView {

    private IView iView;
    private TileImageLoader tileImageLoader;
    private PlayerView playerView;

    public PlayStateView(IView v){
        iView = v;
        playerView = new PlayerView(v);
        tileImageLoader = new TileImageLoader(v);
        tileImageLoader = null;
    }

    public void draw(Graphics2D g2){
        //per disegnare gli oggetti nella giusta posizione sullo schermo, ci prendiamo la posizione del player nella mappa
        //ci troviamo la posizione dell'oggetto relativa al player e capiamo dove esso si trova sullo schermo, sapendo
        //che il player è sempre al centro
        int xPlayer = (int)iView.getController().getPlayerController().getxPosPlayer();
        int yPlayer = (int)iView.getController().getPlayerController().getyPosPlayer();

        drawFirstLayer(g2, xPlayer, yPlayer);
        drawSecondLayer(g2, xPlayer, yPlayer);
        drawThirdLayer(g2, xPlayer, yPlayer);
        drawFourthLayer(g2, xPlayer, yPlayer);

        playerView.draw(g2);
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
