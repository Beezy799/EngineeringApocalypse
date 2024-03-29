package src.model.events;


import src.model.Hitbox;
import src.model.IModel;

public class CFU extends Event{

	public CFU(Hitbox r, IModel m, int i) {
		super(r, m, i);
		message = "ti stai avvicinando alla laurea!";

	}

	@Override
	public void interact() {
//		if (!endInteraction) {
//			model.getController().getPlay().getPlayer().addCFU(20);
//
//			model.getView().playSE(SoundManager.CFU);
//
//			model.getView().getPlay().getUI().setMessage(message);
//			model.getView().getPlay().getUI().setShowMessage(true);
//
//			endInteraction = true;
//		}
	}

}
