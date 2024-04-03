package src.model.events;


import src.model.Constants;
import src.model.EntityStates;
import src.controller.Hitbox;
import src.model.IModel;
import src.view.inputs.InputState;

public class Caffe extends Event{

	public Caffe(Hitbox r, IModel m, int i) {
		super(r, m, i);
		message = "il caffe' aumenta la tua concentrazione!";
	}

	@Override
	public void interact() {

		if(!endInteraction) {
			model.getView().getPlayStateView().getPlayUI().setMessageToShow("premi E per interagire");

			if (InputState.E.getPressed()) {
				int life = 10;
				model.getController().getPlayerController().addLife(life);
				model.getView().getSoundManager().playSE(Constants.SoundConstants.CAFFE_SE);
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
