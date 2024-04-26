package src.model.events;


import src.controller.Hitbox;
import src.model.GameState;
import src.model.IModel;

public class CutsceneProf extends Event {

	public CutsceneProf(Hitbox r, IModel m, int i) {
		super(r, m, i);
	}

	@Override
	public void interact() {
		//potremmo mettere la musica del combattimento
		GameState.actualState = GameState.BOSS_CUTSCENE;
		endInteraction = true;
	}

}
