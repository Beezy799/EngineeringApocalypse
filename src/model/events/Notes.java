package src.model.events;


import src.model.Hitbox;
import src.model.IModel;

public class Notes extends Event {

	public Notes(Hitbox r, IModel m, int i) {
		super(r, m, i);
		message = "hai trovato degli appunti!";

	}

	@Override
	public void interact() {
//		model.getController().getPlay().getPlayer().addNotes();
//
//		model.getView().playSE(SoundManager.APPUNTI);
//		model.getView().showMessageInUI(message);
//
//		endInteraction = true;
	}

}
