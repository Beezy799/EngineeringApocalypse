package src.view.mapView;

import src.view.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TilesetView {

    //lista completa dei tile, c'è una corrispondenza tra l'indice nell lista e il numero associato
    // al tile nella mappa.
    private ArrayList<TileView> tiles;
    private String firstLayerPath = "/res/map/strato1.png";
    private String secondLayerPath = "/res/map/strato2NONanimato.png";
    private String secondAnimatedLayerPath = "/res/map/strato2SoloAnimato.png";
    private String thirdLayerPath = "/res/map/strato3.png";

    public TilesetView(){
        tiles = new ArrayList<>();
        saveFirstLayerImages();
    }

    public BufferedImage getTileImage(int index){
        return tiles.get(index).getImage();
    }

    private void saveFirstLayerImages() {
        //siccome la numerazione parte da uno e 0 vuol dire "non disegnare", per far coincidere la numerazione
        //della mappa e quella del tileset usiamo una immagine segnaposto che non sarà mai disegnata
        BufferedImage imgZero = null;

        //immagine con dentro tutte le immagini del primo strato
        BufferedImage sourceLayerImage = null;

        BufferedImage temp = null;

        try {
            imgZero = ImageIO.read(getClass().getResourceAsStream("/res/map/000.png"));

            sourceLayerImage = ImageIO.read(getClass().getResourceAsStream(firstLayerPath));
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        //i tiles del primo strato hanno tutti una sola immagine, non sono animati
        BufferedImage[] nullImageTile = new BufferedImage[1];

        nullImageTile[0] = imgZero;
        tiles.add(new TileView(nullImageTile));

        int numberTileFirstLayer = sourceLayerImage.getHeight()/GamePanel.TILES_DEFAULT_SIZE;
        for(int i = 0; i < numberTileFirstLayer; i++) {
            temp = sourceLayerImage.getSubimage(0, i* GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);

            BufferedImage[] imagesInTile = new BufferedImage[1];
            imagesInTile[0] = temp;

            tiles.add(new TileView(imagesInTile));
        }
    }

}
