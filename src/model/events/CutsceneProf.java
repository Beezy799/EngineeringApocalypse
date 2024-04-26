package src.model.events;


import src.controller.Hitbox;
import src.model.EntityStates;
import src.model.GameState;
import src.model.IModel;
import src.view.inputs.InputState;

public class CutsceneProf extends Event {

	public CutsceneProf(Hitbox r, IModel m, int i) {
		super(r, m, i);
	}

	@Override
	public void interact() {
		GameState.actualState = GameState.BOSS_CUTSCENE;
		model.getController().getPlayerController().resetDirectionVector();
		model.getController().getPlayerController().changeActualState(EntityStates.IDLE);
		InputState.resetBooleans();

		//il boss è l'unica entità nella stanza oltre al player
		model.getController().setIndexEntityInteraction(0);
		endInteraction = true;
	}

}
