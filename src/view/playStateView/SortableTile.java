package src.view.playStateView;

import src.view.IView;
import src.view.gameWindow.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

//è un oggetto che contiene il tipo di tile da disegnare e la sua posizione. Siccome contiene solo tre int
//è molto leggero è può essere istanziato/eliminato più velocemente di un tile normale
public class SortableTile extends SortableElement{

    private int tileIndex;
    private int xPosMap;
    private IView view;

    public SortableTile(IView v, int tileInxed, int strato, int col, int row) {
        view = v;
        tileIndex = tileInxed;

        typeElemtToSort = strato;				//più è basso lo strato di questo tile, prima dovrà essere disegnato

        xPosMap = col * GamePanel.TILES_SIZE;		//posizione nella mappa = num colonna * grandezza quadratino
        yPosMapForSort = row * GamePanel.TILES_SIZE;
    }

    public void draw(Graphics2D g2, int playerMapPositionX, int playerMapPositionY) {

        //la distanza tra il player e il tile è la stessa, sia nella mappa che nella finestra di gioco
        //prendendo la distanza nella mappa rispetto al giocatore, possiamo capire dove disegnare il tile
        //rispetto alla posizione del giocatore nella finestra (che è sempre al centro)
        int xDistanceFromPlayer = xPosMap - playerMapPositionX;
        int yDistanceFromPlayer = yPosMapForSort - playerMapPositionY;

        //ci serve un offset perchè la distanza del tile nella mappa rispetto al player è riferita al punto in
        //alto a sinistra della hitbox. Per mantenere la stessa distanza, dobbiamo aggiungere questo offset
        int xPosOnScreen = GamePanel.CENTER_X_GAME_PANEL + xDistanceFromPlayer;
        int yPosOnScreen = GamePanel.CENTER_Y_GAME_PANEL + yDistanceFromPlayer;

        BufferedImage img = view.getModel().getTileset().getTile(tileIndex).getTileView().getImage();
        g2.drawImage(img, xPosOnScreen, yPosOnScreen, null);

    }

}
