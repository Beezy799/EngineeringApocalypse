package src.model.events;


import src.model.Hitbox;
import src.model.IModel;

public class Light extends Event {

	
	public Light(Hitbox r, IModel m, int i) {
		super(r, m, i);
		message = "hai acceso la luce, quest completata!";		
	}

	@Override
	public void interact() {
//		model.getView().getPlay().getUI().setDark(false);
//		model.getView().getPlay().getUI().setMessage(message);
//		model.getView().getPlay().getUI().setShowMessage(true);
//		model.getView().getPlay().getUI().setDark(false);
//
//		model.getController().getPlay().getPlayer().addCFU(30);
//
//		endInteraction = true;
	}
	
	
}
