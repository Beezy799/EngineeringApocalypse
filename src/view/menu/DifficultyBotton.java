package src.view.menu;

import src.model.Constants;
import src.model.GameState;
import src.view.IView;
import src.view.ViewUtils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static src.view.gameWindow.GamePanel.GAME_HEIGHT;
import static src.view.gameWindow.GamePanel.SCALE;

public class DifficultyBotton extends AbstractMenuButton {

    private int difficulty;
    private String explaination;
    private Font descriptionFont;

    public DifficultyBotton(BufferedImage[] img, Rectangle rect, int diff, String s, IView v){
       mouseAwayImage = img[0];
       mouseOverImage = img[1];
       mousePressedImage = img[2];
       bounds = rect;
       difficulty = diff;
       explaination = s;

       int fontSize = (int)(12*SCALE);
       descriptionFont = new Font("Arial", Font.PLAIN, fontSize);

       view = v;
    }

    @Override
    public void draw(Graphics2D g2) {

        g2.drawImage(mouseAwayImage, (int)bounds.getX(), (int)bounds.getY(), null);
        if(mouseOver){
            g2.drawImage(mouseOverImage, (int)bounds.getX(), (int)bounds.getY(), null);
            g2.setColor(Color.CYAN);
            g2.setFont(descriptionFont);
            int x = ViewUtils.getXforCenterText(explaination, g2);
            int y = GAME_HEIGHT/2 + (int)(160*SCALE);
            g2.drawString(explaination, x, y);
        }
        if(mousePressed)
            g2.drawImage(mousePressedImage, (int)bounds.getX(), (int)bounds.getY(), null);
    }

    @Override
    public void reactToMouse(MouseEvent e) {
        reactToEnter();
    }

    @Override
    public void reactToEnter() {
        GameState.difficulty = difficulty;
        view.getSoundManager().playSE(Constants.SoundConstants.DIALOGUE_SE);
    }

    @Override
    public void reactToDrag(MouseEvent e) {

    }
}
