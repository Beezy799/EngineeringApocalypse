package src.view.mapView;

import src.view.ViewUtils;
import src.view.main.GamePanel;

import java.awt.image.BufferedImage;

public class TileView {
    //anche i tile possono essere animati, hanno un array di immagini che cambiano
    private BufferedImage[] tileAnimation;
    private int currentImageIndex;
    private int counter;
    private int freq = 20;

    public TileView(BufferedImage[] images) {
        tileAnimation = images;
        //scala le immagini ricevute
        for(BufferedImage image : tileAnimation) {
            image = ViewUtils.scaleImage(image, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
        }
    }

    public BufferedImage getImage() {
        //spreca tempo per per scegliere l'immagine solo se ci sono più immagini, sennò restituisce l'unica immagine
        if(tileAnimation.length > 1)
            selectImageToShow();

        return tileAnimation[currentImageIndex];
    }

    private void selectImageToShow() {
        counter++;
        //in modo tale da non cambiare immagine troppo velocemente
        if (counter >= freq) {
            currentImageIndex++;
            //se l'indice è fuori dall'array, si resettano i contatori
            if(currentImageIndex >= tileAnimation.length) {
                counter = 0;
                currentImageIndex = 0;
            }
        }
    }


}
