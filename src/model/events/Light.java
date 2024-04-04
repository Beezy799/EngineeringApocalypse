package src.model.events;


import src.controller.Hitbox;
import src.model.Constants;
import src.model.EntityStates;
import src.model.IModel;
import src.model.mapModel.Rooms;
import src.view.inputs.InputState;

public class Light extends Event {

	
	public Light(Hitbox r, IModel m, int i) {
		super(r, m, i);
		message = "hai acceso la luce, quest completata!";		
	}

	@Override
	public void interact() {

		if(!endInteraction) {
			model.getView().getPlayStateView().getPlayUI().setMessageToShow("premi E per interagire");

			if (InputState.E.getPressed()) {
				int cfu = 50;
				model.getController().getPlayerController().addCFU(cfu);
				model.getView().getSoundManager().playSE(Constants.SoundConstants.CFU_SE);
				model.getView().getSoundManager().stopMusic();
				model.getView().getSoundManager().loopMusic(Constants.SoundConstants.DORMITORIO_MUSIC);
				//sennò resta la musica horro quando rientri nella stanza
				Rooms.actualRoom.setNewMusic(Constants.SoundConstants.DORMITORIO_MUSIC);

				model.getView().getPlayStateView().getScreenOverlay().setDark(false);

				model.getController().getPlayerController().changeActualState(EntityStates.CFU_FOUND);
				model.getController().getPlayerController().getMovementVector().resetDirections();
				model.getController().getPlayerController().getMovementVector().setY(1);
				model.getController().getPlayerController().lockState();

				endInteraction = true;
			}
		}
	}
	
	
}
