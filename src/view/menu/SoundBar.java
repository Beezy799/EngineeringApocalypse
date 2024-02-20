package src.view.menu;

import src.view.IView;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SoundBar extends AbstractMenuButton{

    private Rectangle backgroundRect, volumeRect;
    public SoundBar(Rectangle r1, Rectangle r2, IView v){
        backgroundRect = r1;
        volumeRect = r2;
        bounds = r1;
        view = v;
    }
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.draw(backgroundRect);

        volumeRect.width = (int) (view.getVolume() * backgroundRect.width);
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
        view.setVolume((float) volumeRect.width /backgroundRect.width);
    }

    public void reactToDrag(MouseEvent e) {
        int cursorX = e.getX();
        int xRect = bounds.x;

        volumeRect.width = cursorX - xRect;
        view.setVolume((float) volumeRect.width /backgroundRect.width);

    }

    @Override
    public void reactToEnter() {

    }
}
