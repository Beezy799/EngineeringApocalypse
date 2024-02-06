package src.view.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import src.model.GameState;

//import controller.main.Gamestate;

import src.view.IView;
//import view.playState.entityView.EntityView;


public class KeyboardInputs implements KeyListener {

	private IView view;
	private MainMenuInputs mainMenuInputs;
	private OptionMenuInputs optionMenuInputs;
	private AvatarMenuInputs avatarMenuInputs;
	private PauseMenuInputs pauseMenuInputs;
	private PlayStateInputs playStateInputs;
	
	public KeyboardInputs(IView v) {
		this.view = v;
		mainMenuInputs = new MainMenuInputs(view.getMainMenu());
	}

	@Override
	public void keyPressed(KeyEvent e) {
/* 	 	switch (GameState.actualState) {
			case PAUSE:
				view.getPause().keyPressed(e.getKeyCode());
				break; */
				
/* 				case PLAYING:
				handleKeypressedDuringPlayState(e);
				break; */

/* 			case DIALOGUE:
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE ||
				   e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A ||
				   e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_W) {
					
					view.changeGameState(Gamestate.PLAYING);
					view.getController().resetPlayerBooleans();
				}
				else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					int index = view.getController().getIndexOfInteractingEntity();
					view.getPlay().getRoom(view.getCurrentRoomIndex()).getNPC(index).nextDialogueLine();
				}
				break;
				
			case BOSS_CUTSCENE:
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					view.getController().resetPlayerBooleans();
					view.changeGameState(Gamestate.PLAYING);
				}
				else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					//l'indice è sempre zero, perchè il boss è l'unico NPC della stanza
					view.getPlay().getRoom(view.getCurrentRoomIndex()).getBossView().nextDialogueLine();
				}
				break; 
				
			default:
				break;
		}  */

		//System.out.println("hai premuto un tasto ,keyboradInp");

	}


	//lui sente solo che un tasto è stato rilasciato, poi delega la gestione del fatto ai vari gamestate
	@Override
	public void keyReleased(KeyEvent e) {
	 	switch (GameState.actualState) {
			case START_TITLE:
				if(e.getKeyCode() == KeyEvent.VK_ENTER) 
					view.getStartTitle().skipTitle();
				break;
			case MAIN_MENU:
				mainMenuInputs.keyReleasedDuringMainMenuState(e.getKeyCode());
				break;
			case SELECT_AVATAR:
				avatarMenuInputs.keyReleasedDuringSelectAvatarState(e.getKeyCode());
				break;
			case OPTIONS:
				optionMenuInputs.keyReleasedDuringOptionState(e.getKeyCode());
				break;
			case PLAYING:
				handleKeyReleasedPlayState(e);
				break; 
			default:
				break;
		}	
	}

	private void handleKeyReleasedPlayState(KeyEvent e) {
		/*switch(e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				view.getController().getPlay().stopPlayerAttacking();
				break;
			case KeyEvent.VK_SPACE:
				view.getController().stopPlayerParring();
				break;
			case KeyEvent.VK_P:
				view.getController().stopPlayerThrowing();
				break;
			case KeyEvent.VK_E:
				view.getController().stopPlayerInteracting();
				break;	
			default:
				view.getController().resetPlayerDirection(getActionAssociatedToKey(e));
				break;
		}*/
	}
		
	public int getActionAssociatedToKey(KeyEvent e) {
		
	/*  	if(e.getKeyCode() == KeyEvent.VK_ENTER)
				return EntityView.ATTACK;
			
			else if(e.getKeyCode() == KeyEvent.VK_SPACE)
				return EntityView.PARRY;
			
			else if(e.getKeyCode() == KeyEvent.VK_P)
				return EntityView.THROW;
			
			else if(e.getKeyCode() == KeyEvent.VK_A)
				return EntityView.LEFT;
			
			else if(e.getKeyCode() == KeyEvent.VK_D)
				return EntityView.RIGHT;	
			
			else if(e.getKeyCode() == KeyEvent.VK_W)
				return EntityView.UP;	
			
			else if(e.getKeyCode() == KeyEvent.VK_S)
				return EntityView.DOWN;
			
			else return -1;
			
		*/
        return 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	

	private void handleKeypressedDuringPlayState(KeyEvent e) {
		/*switch(e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				view.getController().startPlayerAttack();
				break;
			case KeyEvent.VK_SPACE:
				view.getController().startPlayerParry();
				break;
			case KeyEvent.VK_ESCAPE:
				view.getController().goToPauseState();
				break;
			case KeyEvent.VK_P:
				view.getController().startPlayerThrow();
				break;
			case KeyEvent.VK_E:
				view.getController().startPlayerInteract();
				break;	
			default:
				view.getController().changePlayerDirection(getActionAssociatedToKey(e));
				break;
		}*/
		
	}
}




