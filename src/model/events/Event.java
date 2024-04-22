package src.model.events;


import src.controller.Hitbox;
import src.model.IModel;
import src.view.gameWindow.GamePanel;

public abstract class Event {

	protected int index;
	protected Hitbox bounds;
	protected IModel model;
	protected String message;
	protected boolean endInteraction;
	
	
	public Event(Hitbox r, IModel m, int ind) {
		bounds = r;
		model = m;
		index = ind;

		//siccome la stanza restituisce una hitbox con righe e colonne, la convertiamo in x, y
		bounds.setX(bounds.getX() * GamePanel.TILES_SIZE);
		bounds.setY(bounds.getY() * GamePanel.TILES_SIZE);

		bounds.setWidth((int)(bounds.getWidth() * GamePanel.SCALE));
		bounds.setHeight((int)(bounds.getHeight() * GamePanel.SCALE));

	}
	
	public abstract void interact();
	
	public Hitbox getBounds() {
		return bounds;
	}
	
	public boolean isEndInteraction() {
		return endInteraction;
	}
	
	public String getMessage() {
		return message;
	}

	public void setIndex(int i){
		index = i;
	}

	public int getIndex(){
		return index;
	}

	public void setEndInteraction(boolean b){
		endInteraction = b;
	}

	public void reset() {
		setEndInteraction(false);
	}
}
