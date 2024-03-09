package src.view;

import src.model.mapModel.Rooms;
import src.view.entityView.PlayerView;
import src.view.main.GamePanel;
import src.view.mapView.TilesetView;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayStateView {

    private IView iView;
    private TilesetView tilesetView;
    private PlayerView playerView;

    public PlayStateView(IView v){
        iView = v;
        playerView = new PlayerView(v);
        tilesetView = new TilesetView();
    }
    public void draw(Graphics2D g2){
        //per disegnare i tile nella giusta posizione sullo schermo, ci prendiamo la posizione del player nella mappa
        //ci troviamo la posizione del tile relativa al player e capiamo dove si trova il tile sullo schermo, sapendo
        //che il player è sempre al centro
        int xPlayer = (int)iView.getController().getPlayerController().getxPosPlayer();
        int yPlayer = (int)iView.getController().getPlayerController().getyPosPlayer();

        drawFirstLayer(g2, xPlayer, yPlayer);

        playerView.draw(g2);
        //System.out.println(yPlayer/GamePanel.TILES_SIZE + ", " + xPlayer/GamePanel.TILES_SIZE + "   IView, drawFirstLayer");

    }

    private void drawFirstLayer(Graphics2D g2, int xPlayerpos, int yPlayerPos) {

        int[][] tileNumbers = Rooms.actualRoom.getMap().getFirstLayer();

        for(int righe = 0; righe < tileNumbers.length; righe++){
            for(int colonne = 0; colonne < tileNumbers[0].length; colonne++){
                int firstTileNumber = tileNumbers[righe][colonne];

                int tileXPositionOnMap = colonne * GamePanel.TILES_SIZE;
                int tileYPositionOnMap = righe * GamePanel.TILES_SIZE;

                int xDistanceFromPlayer = tileXPositionOnMap - xPlayerpos;
                int yDistanceFromPlayer = tileYPositionOnMap - yPlayerPos;

                int xScreen = PlayerView.xOnScreen + xDistanceFromPlayer;
                int yScreen = PlayerView.yOnScreen + yDistanceFromPlayer;

                g2.drawImage(tilesetView.getTileImage(firstTileNumber), xScreen, yScreen, null);
                g2.drawRect(xScreen, yScreen, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
            }
        }


    }

    public PlayerView getPlayerView(){
        return  playerView;
    }




}
