package src.view.menu;

import src.model.GameState;
import src.view.IView;
import src.view.gameWindow.GamePanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PauseMenuButton extends AbstractMenuButton {

    boolean isHome;


    public PauseMenuButton(IView v, String iconsPath, int x, int y, GameState state, boolean home) {
        view = v;
        newState = state;
        loadIconFromFile(iconsPath);
        bounds = new Rectangle(x, y, (int)(32*GamePanel.SCALE), (int)(32*GamePanel.SCALE));
        isHome = home;
    }


    private void loadIconFromFile(String iconsPath) {
        BufferedImage temp;
        try {
            temp = ImageIO.read(getClass().getResourceAsStream(iconsPath));
            mouseAwayImage = temp.getSubimage(0, 0, 56, 56);
            mouseOverImage = temp.getSubimage(56, 0, 56, 56);
            mousePressedImage = temp.getSubimage(56 + 56, 0, 56, 56);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        g2.drawImage(mouseAwayImage, (int)bounds.getX(), (int)bounds.getY(), null);
        if(mouseOver)
            g2.drawImage(mouseOverImage, (int)bounds.getX(), (int)bounds.getY(), null);
        if(mousePressed)
            g2.drawImage(mousePressedImage, (int)bounds.getX(), (int)bounds.getY(), null);

    }

    @Override
    public void reactToMouse(MouseEvent e) {
        reactToEnter();
    }

    @Override
    public void reactToEnter() {
        if(isHome) {
            view.getTransitionState().setNext(GameState.MAIN_MENU);
            view.getTransitionState().setPrev(GameState.PLAYING);
            GameState.playStateInStandBy = true;
            view.changeGameState(GameState.TRANSITION_STATE);
        }
        else {
            view.changeGameState(newState);
            GameState.playStateInStandBy = false;
        }
    }

    @Override
    public void reactToDrag(MouseEvent e) {

    }

}
