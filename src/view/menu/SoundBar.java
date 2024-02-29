package src.view.menu;

import src.view.IView;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SoundBar extends AbstractMenuButton{

    private Rectangle backgroundRect, volumeRect;
    private boolean isMusicVolume;

    public SoundBar(Rectangle r1, Rectangle r2, IView v, boolean b){
        backgroundRect = r1;
        volumeRect = r2;
        bounds = r1;
        view = v;
        isMusicVolume = b;
    }
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.draw(backgroundRect);

        if(isMusicVolume){
            volumeRect.width = (int) (view.getMusicVolume() * backgroundRect.width);
        }
        else{
            volumeRect.width = (int) (view.getSeVolume() * backgroundRect.width);
        }

        if(mouseOver){
            g2.setColor(Color.CYAN);
        }
        else{
            g2.setColor(Color.red);
        }
        g2.fill(volumeRect);

    }

    @Override
    public void reactToMouse(MouseEvent e) {
        int cursorX = e.getX();
        int xRect = bounds.x;

        volumeRect.width = cursorX - xRect;
        if(isMusicVolume){
            view.setMusicVolume((float) volumeRect.width/backgroundRect.width);
        }
        else {
            view.setSeVolume((float) volumeRect.width /backgroundRect.width);
        }
    }

    public void reactToDrag(MouseEvent e) {
        int cursorX = e.getX();
        int xRect = bounds.x;

        volumeRect.width = cursorX - xRect;
        if(isMusicVolume){
            view.setMusicVolume((float) volumeRect.width /backgroundRect.width);
        }
        else{
            view.setSeVolume((float) volumeRect.width /backgroundRect.width);
        }

    }

    @Override
    public void reactToEnter() {

        int cursorX = (int)view.getCursorPosition().getX();
        int xRect = bounds.x;

        volumeRect.width = cursorX - xRect;
        if(isMusicVolume){
            view.setMusicVolume((float) volumeRect.width/backgroundRect.width);
        }
        else {
            view.setSeVolume((float) volumeRect.width /backgroundRect.width);

        }
    }
}
