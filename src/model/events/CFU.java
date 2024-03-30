package src.model.events;


import src.model.Constants;
import src.model.EntityStates;
import src.model.Hitbox;
import src.model.IModel;
import src.view.inputs.InputState;

public class CFU extends Event{

	public CFU(Hitbox r, IModel m, int i) {
		super(r, m, i);
		message = "ti stai avvicinando alla laurea!";

	}

	@Override
	public void interact() {
		if(!endInteraction) {
			int cfu = 10;
			model.getController().getPlayerController().addCFU(cfu);
			model.getView().getSoundManager().playSE(Constants.SoundConstants.CFU_SE);
			model.getView().getPlayStateView().getPlayUI().setMessageToShow(message);

			model.getController().getPlayerController().changeActualState(EntityStates.CFU_FOUND);
			model.getController().getPlayerController().getMovementVector().resetDirections();
			model.getController().getPlayerController().getMovementVector().setY(1);
			model.getController().getPlayerController().lockState();

			endInteraction = true;
		}
	}

}
