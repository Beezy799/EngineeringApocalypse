package src.view.menu;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

//classe astratta che contiene i metodi e le risorse comuni a tutti i tipi di menu. 
public abstract class AbstractMenu {	



    //ogni menu ha un array di bottoni che saranno implemenentati diversamemente nei vari menu
	protected AbstractMenuButton[] buttons;


	protected void drawButtons(Graphics2D g2) {
		for (AbstractMenuButton gb : buttons)
			gb.draw(g2);		
	}

	public void mousePressed(MouseEvent e) {
		for(AbstractMenuButton mb : buttons)
			if(mb.checkIfMouseIsIn(e))
				mb.setMousePressed(true);		
	}

	public void mouseReleased(MouseEvent e) {
		for(AbstractMenuButton mb : buttons)
			if(mb.checkIfMouseIsIn(e) && mb.isMousePressed())
				mb.reactToMouse(e);
		
		resetButtons();
	}

	protected void resetButtons() {
		for(AbstractMenuButton mb : buttons)
			mb.resetBooleans();
	}

	public void mouseMoved(MouseEvent e) {
		for(AbstractMenuButton mb : buttons)
			mb.setMouseOver(false);
		
		for(AbstractMenuButton mb : buttons)
			if(mb.checkIfMouseIsIn(e)) 
				mb.setMouseOver(true);
	}
	
	public void mouseDragged(MouseEvent e) {
		for(AbstractMenuButton mb : buttons)
			mb.reactToDrag(e);
	}
	
}
