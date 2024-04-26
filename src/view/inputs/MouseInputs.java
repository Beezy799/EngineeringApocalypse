package src.view.inputs;


import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import src.model.GameState;
//import controller.main.Gamestate;
import src.model.Rooms;
import src.view.IView;
import src.view.entityView.BossView;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private IView view;
	
	public MouseInputs(IView v) {
		this.view = v;
	}

	//lui sente solo che il mouse ha fatto qualcosa, poi delega la gestione dell'evento ai vari menu a seconda del gameState
	@Override
	public void mouseDragged(MouseEvent e) {   //per soundbar
 		switch (GameState.actualState) {
		case OPTIONS:
			view.getOptions().mouseDraggedInOption(e);
			break;
		case PAUSE:
			view.getPause().mouseDraggedInPause(e);
			break;
		default:
			break;
		}

	}

	//funzionamento come sopra
	@Override
	public void mouseMoved(MouseEvent e) {
 	 	switch (GameState.actualState) {
		case MAIN_MENU:
			view.getMainMenu().mouseMoved(e);
			break;
		case SELECT_AVATAR:
			view.getAvatarMenu().mouseMoved(e);
			break;
		case OPTIONS:
			view.getOptions().mouseMoved(e);
			break;
		case PAUSE:
			view.getPause().mouseMoved(e);
			break;
			case COMMAND_EXPLAINATION:
				view.getCommandsExplaination().mouseMoved(e);
				break;
			case GAME_OVER:
				view.getGameOverMenu().mouseMoved(e);
				break;
		default:
			break;

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	 	switch (GameState.actualState) {
		case START_TITLE:
			GameState.actualState = GameState.MAIN_MENU;
			break;
		default:
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (GameState.actualState) {
		case MAIN_MENU:
			view.getMainMenu().mousePressed(e);
			break;
		case SELECT_AVATAR:
			view.getAvatarMenu().mousePressed(e);
			break;
		case OPTIONS:
			view.getOptions().mousePressed(e);
			break;
		case PLAYING:
			handleMousePressedDuringPlayState(e);
			break;
		case PAUSE:
			view.getPause().mousePressed(e);
			break;
			case COMMAND_EXPLAINATION:
				view.getCommandsExplaination().mousePressed(e);
				break;
			case GAME_OVER:
				view.getGameOverMenu().mousePressed(e);
				break;
		default:
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		 switch (GameState.actualState) {
		case MAIN_MENU:
			view.getMainMenu().mouseReleased(e);
			break;
		case SELECT_AVATAR:
			view.getAvatarMenu().mouseReleased(e);
			break;
		case OPTIONS:
			view.getOptions().mouseReleased(e);
			break;
		case PLAYING:
			handleMouseReleasedDuringPlayState(e);
			break;
		case PAUSE:
			view.getPause().mouseReleased(e);
			break;
			 case COMMAND_EXPLAINATION:
				 view.getCommandsExplaination().mouseReleased(e);
				 break;
		case DIALOGUE:
			int index = view.getController().getIndexEntityInteraction();
			view.getModel().setEntityNextDialogue(index);
			break;
		 case GAME_OVER:
			 view.getGameOverMenu().mouseReleased(e);
			 break;
		 case BOSS_CUTSCENE:
			 ((BossView)(Rooms.STUDIO_PROF.getEnemy().get(0).getEnemyView())).setNextDialogueLine();
			 break;
		default:
			break;
		}
	}

	private void handleMouseReleasedDuringPlayState(MouseEvent e) {
		 if(SwingUtilities.isLeftMouseButton(e))
			InputState.LEFT_CLICK.setPressed(false);
		
		else if(SwingUtilities.isRightMouseButton(e))
			InputState.RIGHT_CLICK.setPressed(false);
		
		else if(SwingUtilities.isMiddleMouseButton(e))		
			InputState.MIDDLE_CLICK.setPressed(false);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private void handleMousePressedDuringPlayState(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e))
			InputState.LEFT_CLICK.setPressed(true);

		else if(SwingUtilities.isRightMouseButton(e))
			InputState.RIGHT_CLICK.setPressed(true);

		else if(SwingUtilities.isMiddleMouseButton(e))
			InputState.MIDDLE_CLICK.setPressed(true);

	}


}
