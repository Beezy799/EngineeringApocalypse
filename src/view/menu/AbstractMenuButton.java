package src.view.menu;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import src.model.GameState;

import src.view.IView;

//classe astratta che contiene tutti i meetodi e le risorse comuni a tutti i tipi di bottone
public abstract class AbstractMenuButton {

	protected IView view;
    //il bottone ha tre immagini: quando mouse non c'è, quando mouse ci clicca sopra , e quando mousa ci sta sopra 
	protected BufferedImage mouseOverImage, mousePressedImage, mouseAwayImage;
	protected boolean mouseOver, mousePressed;
	protected Rectangle bounds;
	protected GameState newState;

	
	public boolean checkIfMouseIsIn(MouseEvent e) {
		return bounds.contains(e.getX(), e.getY());
	}
	
	public void resetBooleans(){
		mouseOver = false;
		mousePressed = false;
	}
	
	public void setMouseOver(Boolean b) {
		mouseOver = b;
	}
	
	public boolean isMousePressed() {
		return mousePressed;
	}
	
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public GameState getPointedState() {
		return newState;
	}
	
	public boolean getMouseOver() {
		return mouseOver;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public abstract void draw(Graphics2D g2);
	
	public abstract void reactToMouse(MouseEvent e);

	public abstract  void reactToDrag(MouseEvent e); //soundbar

	public abstract void reactToEnter(); //qunado premi enter su un bottone
	

}
