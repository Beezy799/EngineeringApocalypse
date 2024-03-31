package src.view.playStateView;

import src.model.Hitbox;
import src.model.events.Event;
import src.model.mapModel.EnemyComplete;
import src.model.mapModel.NpcComplete;
import src.model.mapModel.Rooms;
import src.view.IView;
import src.view.PlayerView;
import src.view.ViewUtils;
import src.view.gameWindow.GamePanel;
import src.view.mapView.TileImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PlayStateView {

    private IView iView;
    private TileImageLoader tileImageLoader;
    private PlayerView playerView;
    private PlayUI playUI;
    private BufferedImage cfuImage;

    private ArrayList<SortableElement> elementsAboveTheFloor;

    public PlayStateView(IView v){
        iView = v;
        playerView = new PlayerView(v);
        tileImageLoader = new TileImageLoader(v);
        tileImageLoader = null;
        elementsAboveTheFloor = new ArrayList<>();
        playUI = new PlayUI(this);

        try {
            cfuImage = ImageIO.read(getClass().getResourceAsStream("/res/ui/punteggioPiccolo.png"));
            cfuImage = ViewUtils.scaleImage(cfuImage, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void draw(Graphics2D g2){
        //per disegnare gli oggetti nella giusta posizione sullo schermo, ci prendiamo la posizione del player nella mappa
        //ci troviamo la posizione dell'oggetto relativa al player e capiamo dove esso si trova sullo schermo, sapendo
        //che il player è sempre al centro
        int xPlayer = iView.getController().getPlayerController().getxPosPlayer();
        int yPlayer = iView.getController().getPlayerController().getyPosPlayer();

        drawFirstLayer(g2, xPlayer, yPlayer);
        drawSecondLayer(g2, xPlayer, yPlayer);
        drawCFU(g2, xPlayer, yPlayer);

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
        for(NpcComplete npc : Rooms.actualRoom.getNpc()){
            npc.getNpcView().updatePositionForSort();
            elementsAboveTheFloor.add(npc.getNpcView());
        }

        for(EnemyComplete enemy : Rooms.actualRoom.getEnemy()){
            enemy.getEntityView().updatePositionForSort();
            elementsAboveTheFloor.add(enemy.getEntityView());
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
                    g2.drawRect(xScreen, yScreen, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
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

    private void drawCFU(Graphics2D g2, int xPlayerPos, int yPlayerPos){
        //guarda gli eventi nella stanza
        for(Event e : Rooms.actualRoom.getEvents()){
            //trova quelli dei cfu dal messaggio
            if(e.getMessage().equals("ti stai avvicinando alla laurea!") && !e.isEndInteraction()){
                Hitbox h = e.getBounds();
                //se l'evento cfu è abbastanza vicino al player, lo disegna come fosse un tile
                if(Math.abs(h.getX() - xPlayerPos) < 5*GamePanel.TILES_SIZE && Math.abs(h.getY() - yPlayerPos) < 5*GamePanel.TILES_SIZE){
                    int xDistance = (int)(h.getX() - xPlayerPos);
                    int yDistance = (int)(h.getY() - yPlayerPos);
                    int xScreen = GamePanel.CENTER_X_GAME_PANEL + xDistance;
                    int yScreen = GamePanel.CENTER_Y_GAME_PANEL + yDistance;

                    //invece di disegnarlo e basta, aumenta la trasparenza quando è lontano e la diminuisce più si avvicina il giocatore
                    float xD = Math.abs(h.getX() - xPlayerPos);
                    float yD = Math.abs(h.getY() - yPlayerPos);
                    float alPhaValue = (1.2f - (xD+yD)/(10*GamePanel.TILES_SIZE));
                    alPhaValue = (alPhaValue > 1 ? 1f : alPhaValue);

                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alPhaValue));
                    g2.drawImage(cfuImage, xScreen, yScreen, null);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
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

    public PlayUI getPlayUI(){
        return playUI;
    }
}
