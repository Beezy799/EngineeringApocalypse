package src.view.playStateView;

import src.model.Rooms;
import src.view.ViewUtils;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenOverlay {
    //buio, attacchi prof, altro che non saprei

    private BufferedImage buio;
    private boolean isDark = true;

    public ScreenOverlay(PlayStateView p){
        try {
            buio = ImageIO.read(getClass().getResourceAsStream("/res/ui/effettoBuioFinale.png"));
            buio = ViewUtils.scaleImage(buio, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        }
        catch (Exception e){

        }
    }

    public void draw(Graphics2D g2){
        if(Rooms.actualRoom == Rooms.DORMITORIO && isDark) {
            g2.drawImage(buio, 0, 0, null);
        }
    }

    public void setDark(boolean b){
        isDark = b;
    }

}
