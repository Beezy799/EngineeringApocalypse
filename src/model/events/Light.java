package src.model.events;


import src.controller.Hitbox;
import src.controller.entitycontroller.enemy.GhostController;
import src.model.Constants;
import src.model.EntityStates;
import src.model.IModel;
import src.model.Rooms;
import src.model.entity.EnemyComplete;
import src.model.entity.EntityComplete;
import src.model.entity.NpcComplete;
import src.view.entityView.npc.NpcView;
import src.view.entityView.npc.ProfView;
import src.view.entityView.npc.PupaView;
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
				//sennò resta la musica horror quando rientri nella stanza
				Rooms.actualRoom.setNewMusic(Constants.SoundConstants.DORMITORIO_MUSIC);

				model.getView().getPlayStateView().getScreenOverlay().setDark(false);

				model.getController().getPlayerController().changeActualState(EntityStates.CFU_FOUND);
				model.getController().getPlayerController().getMovementVector().resetDirections();
				model.getController().getPlayerController().getMovementVector().setY(1);
				model.getController().getPlayerController().lockState();

				for(EnemyComplete ghost : Rooms.actualRoom.getEnemy()){
					if(ghost.getEnemyController() instanceof GhostController){
						ghost.setAlive(false);
					}
				}

				for(NpcComplete pupa : Rooms.AULA_STUDIO.getNpc()){
					if(pupa.getNpcView() instanceof PupaView){
						pupa.getNpcView().setNextDialogue();
					}
				}

				endInteraction = true;
			}
		}
	}

	public void reset(){
		super.reset();
		Rooms.DORMITORIO.setNewMusic(Constants.SoundConstants.DORMITORIO_BUIO);
		model.getView().getPlayStateView().getScreenOverlay().setDark(true);
	}
	
	
}
