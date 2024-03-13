package src.view.mapView;

import src.view.IView;
import src.view.ViewUtils;
import src.view.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Random;


//classe che contiene i metodi per prendere le immagini dei tile e salvarle nel tileset, viene usato una volta sola
//per caricare le immagini, poi lo eliminiamo
public class TileImageLoader {

    private String groundTilesetPath = "/res/map/strato1.png";
    private String solidTilesetPath = "/res/map/strato2NONanimato.png";
    private String solidAnimatedTilesetPath = "/res/map/strato2SoloAnimato.png";
    private String decorationTilesetPath = "/res/map/strato3.png";

    private final int groundTilesetStartIndex = 1;

    //i tile del secondo layer iniziano ad indice 8
    private final int solidTilesetStartIndex = 8;
    private final int solidAnimatedTilesetStartIndex = 119;

    private final int decorationTilesetStartIndex = 128;


    public TileImageLoader(IView view){

        saveImageZero(view);

        saveFirstLayerImages(view);

        saveSecondLayerImages(view);

        saveSeconAnimatedLayerImages(view);

        saveThirdLayerImages(view);

    }


    //siccome la numerazione parte da uno e 0 vuol dire "non disegnare", per far coincidere la numerazione
    //della mappa e quella del tileset usiamo una immagine segnaposto che non sarà mai disegnata
    private void saveImageZero(IView view) {

        BufferedImage imgZero = null;

        try {
            imgZero = ImageIO.read(getClass().getResourceAsStream("/res/map/000.png"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        //i tiles del primo strato hanno tutti una sola immagine, non sono animati
        BufferedImage[] nullImageTile = new BufferedImage[1];

        nullImageTile[0] = imgZero;

        //prendo il tileset completo, prendo il tile completo corrispondente al tileView appena creato e ci metto dentro il tileView
        view.getModel().getTileset().getTile(0).addTileView(new TileView(nullImageTile));
    }

    private void saveFirstLayerImages(IView view) {

        BufferedImage sourceLayerImage = null;
        BufferedImage temp = null;
        try {
            sourceLayerImage = ImageIO.read(getClass().getResourceAsStream(groundTilesetPath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //quanti tile ci sono = altezza immagine / altezza quadratino
        int numberTileFirstLayer = sourceLayerImage.getHeight() / GamePanel.TILES_DEFAULT_SIZE;

        for (int i = 0; i < numberTileFirstLayer; i++) {
            //ritaglia il tile dall'immagine originale
            temp = sourceLayerImage.getSubimage(0, i * GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
            //scala le immagini dei tile
            temp = ViewUtils.scaleImage(temp, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
            BufferedImage[] imagesInTile = new BufferedImage[1];
            imagesInTile[0] = temp;

            view.getModel().getTileset().getTile(i + groundTilesetStartIndex).addTileView(new TileView(imagesInTile));
        }

    }

    private void saveSecondLayerImages(IView view) {
        BufferedImage sourceLayerImage = null;
        BufferedImage temp = null;
        try {
            sourceLayerImage = ImageIO.read(getClass().getResourceAsStream(solidTilesetPath));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        int numberTileSecondLayer = sourceLayerImage.getHeight()/GamePanel.TILES_DEFAULT_SIZE;

        for(int i = 0; i < numberTileSecondLayer; i++) {
            temp = sourceLayerImage.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
            temp = ViewUtils.scaleImage(temp, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
            BufferedImage[] imagesInTile = new BufferedImage[1];
            imagesInTile[0] = temp;

            view.getModel().getTileset().getTile(i + solidTilesetStartIndex).addTileView(new TileView(imagesInTile));
        }
    }

    private void saveSeconAnimatedLayerImages(IView view) {

        //la frequenza di cambiamento immagine dei tile animati è randomica
        Random randomFreq = new Random();

        BufferedImage sourceLayerImage = null;
        BufferedImage temp = null;
        try {
            sourceLayerImage = ImageIO.read(getClass().getResourceAsStream(solidAnimatedTilesetPath));
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        //numero tile nell'immagine.. in realtà il num dei tile è la metà, perchè ogni tile qui ha due immagini
        int numberTileSecondLayer = sourceLayerImage.getHeight()/GamePanel.TILES_DEFAULT_SIZE;

        //stavolta prende coppie di tile, perchè quelli animati hanno due immagini
        for(int i = 0; i < numberTileSecondLayer; i += 2) {

            BufferedImage[] imagesInTile = new BufferedImage[2];
            //prende il primo della coppia
            temp = sourceLayerImage.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
            temp = ViewUtils.scaleImage(temp, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
            imagesInTile[0] = temp;

            //prende il secondo
            temp = sourceLayerImage.getSubimage(0, (i+1) * GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
            temp = ViewUtils.scaleImage(temp, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
            imagesInTile[1] = temp;

            //aggiunge i tile al tileset
            if(i > 8) {
                //la frequenza varia tra 100 e 200
                int freq = randomFreq.nextInt(200) + 100;
                view.getModel().getTileset().getTile(i/2 + solidAnimatedTilesetStartIndex).addTileView(new TileView(imagesInTile, freq));
            }
            //i tile della tv devono cambiare con la stessa freq
            else{
                view.getModel().getTileset().getTile(i/2 + solidAnimatedTilesetStartIndex).addTileView(new TileView(imagesInTile));
            }
        }
    }

    private void saveThirdLayerImages(IView view) {
        BufferedImage sourceLayerImage = null;
        BufferedImage temp = null;
        try {
            sourceLayerImage = ImageIO.read(getClass().getResourceAsStream(decorationTilesetPath));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        int numberTileThirdLayer = sourceLayerImage.getHeight()/GamePanel.TILES_DEFAULT_SIZE;

        for(int i = 0; i < numberTileThirdLayer - 1; i++) {
            temp = sourceLayerImage.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
            temp = ViewUtils.scaleImage(temp, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
            BufferedImage[] imagesInTile = new BufferedImage[1];
            imagesInTile[0] = temp;
            view.getModel().getTileset().getTile(i + decorationTilesetStartIndex).addTileView(new TileView(imagesInTile));
        }
    }
}
