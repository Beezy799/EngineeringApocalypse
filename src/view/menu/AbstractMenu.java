package src.view.menu;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
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

	public void enterReleased(KeyEvent e){
		for(AbstractMenuButton mb : buttons) {
			if (mb.mouseOver == true) {
				mb.reactToEnter();
				resetButtons();
			}
		}

	}

	public void resetButtons() {
		for(AbstractMenuButton mb : buttons)
			mb.resetBooleans();
	}

	//resetta i bottoni e controlla se il mouse è sopra al bottone
	public void mouseMoved(MouseEvent e) {
		for(AbstractMenuButton mb : buttons)
			mb.setMouseOver(false);
		
		for(AbstractMenuButton mb : buttons)
			if(mb.checkIfMouseIsIn(e)) 
				mb.setMouseOver(true);
	}
	
}
