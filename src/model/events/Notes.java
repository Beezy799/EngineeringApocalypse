package src.model.events;


import src.controller.Hitbox;
import src.model.Constants;
import src.model.EntityStates;
import src.model.IModel;
import src.view.inputs.InputState;

public class Notes extends Event {

	public Notes(Hitbox r, IModel m, int i) {
		super(r, m, i);
		message = "hai trovato degli appunti!";

	}

	@Override
	public void interact() {
		if (!endInteraction) {
			model.getView().getPlayStateView().getPlayUI().setMessageToShow("premi E per interagire");

			if (InputState.E.getPressed()) {
				int notes = 10;
				model.getController().getPlayerController().addNotes(notes);
				model.getView().getSoundManager().playSE(Constants.SoundConstants.APPUNTI_SE);
				model.getView().getPlayStateView().getPlayUI().setMessageToShow(message);

				model.getController().getPlayerController().changeActualState(EntityStates.CFU_FOUND);
				model.getController().getPlayerController().getMovementVector().resetDirections();
				model.getController().getPlayerController().getMovementVector().setY(1);
				model.getController().getPlayerController().lockState();

				endInteraction = true;
			}
		}
	}

}
