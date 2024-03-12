package src.view.mapView;

import src.view.ViewUtils;
import src.view.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

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
        //System.out.println("primo strato " + (tiles.size()-1));

        saveSecondLayerImages();
        //System.out.println("secondo strato " + (tiles.size()-1));

        saveSeconAnimatedLayerImages();
        //System.out.println("tempo strato " + (tiles.size()-1));

        saveThirdLayerImages();
        //System.out.println("quarto strato " + (tiles.size()-1));
    }

    private void saveSeconAnimatedLayerImages() {

        //la frequenza di cambiamento immagine dei tile animati è randomica
        Random randomFreq = new Random();

        BufferedImage sourceLayerImage = null;
        BufferedImage temp = null;
        try {
            sourceLayerImage = ImageIO.read(getClass().getResourceAsStream(secondAnimatedLayerPath));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        //numero tile nell'immagine
        int numberTileSecondLayer = sourceLayerImage.getHeight()/GamePanel.TILES_DEFAULT_SIZE;

        //System.out.println("numero tile secondo strato animato " + numberTileSecondLayer);

        //stavolta prende coppie di tile, perchè quelli animati hanno due immagini
        for(int i = 0; i < numberTileSecondLayer; i += 2) {

            BufferedImage[] imagesInTile = new BufferedImage[2];
            //prende il primo della coppia
            temp = sourceLayerImage.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
            imagesInTile[0] = temp;

            //prende il secondo
            temp = sourceLayerImage.getSubimage(0, (i+1) * GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
            imagesInTile[1] = temp;

            //aggiunge i tile al tileset
            if(i > 8) {
                //la frequenza varia tra 2 e 21
                int freq = randomFreq.nextInt(20) + 2;
                tiles.add(new TileView(imagesInTile, freq));
            }
            //i tile della tv devono cambiare con la stessa freq
            else{
                tiles.add(new TileView(imagesInTile));
            }
        }
    }

    public BufferedImage getTileImage(int index){
        return tiles.get(index).getImage();
    }

    private void saveSecondLayerImages() {
        BufferedImage sourceLayerImage = null;
        BufferedImage temp = null;
        try {
            sourceLayerImage = ImageIO.read(getClass().getResourceAsStream(secondLayerPath));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        int numberTileSecondLayer = sourceLayerImage.getHeight()/GamePanel.TILES_DEFAULT_SIZE;

        //System.out.println("numero tile secondo strato " + numberTileSecondLayer);

        for(int i = 0; i < numberTileSecondLayer; i++) {
            temp = sourceLayerImage.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
            BufferedImage[] imagesInTile = new BufferedImage[1];
            imagesInTile[0] = temp;
            tiles.add(new TileView(imagesInTile));
        }
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


        //quanti tile ci sono = altezza immagine / altezza quadratino
        int numberTileFirstLayer = sourceLayerImage.getHeight()/GamePanel.TILES_DEFAULT_SIZE;

        //System.out.println("numero tile primo strato " + numberTileFirstLayer);

        for(int i = 0; i < numberTileFirstLayer; i++) {
            //ritaglia il tile dall'immagine originale
            temp = sourceLayerImage.getSubimage(0, i* GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
            //scala le immagini dei tile
            temp = ViewUtils.scaleImage(temp, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
            BufferedImage[] imagesInTile = new BufferedImage[1];
            imagesInTile[0] = temp;

            tiles.add(new TileView(imagesInTile));
        }
    }

    private void saveThirdLayerImages() {
        BufferedImage sourceLayerImage = null;
        BufferedImage temp = null;
        try {
            sourceLayerImage = ImageIO.read(getClass().getResourceAsStream(thirdLayerPath));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        int numberTileSecondLayer = sourceLayerImage.getHeight()/GamePanel.TILES_DEFAULT_SIZE;
        for(int i = 0; i < numberTileSecondLayer; i++) {
            temp = sourceLayerImage.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
            BufferedImage[] imagesInTile = new BufferedImage[1];
            imagesInTile[0] = temp;
            tiles.add(new TileView(imagesInTile));
        }
    }
}
