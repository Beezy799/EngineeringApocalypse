package src.view.inputs;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import src.model.GameState;
//import controller.main.Gamestate;
import src.view.IView;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private IView view;
	
	public MouseInputs(IView v) {
		this.view = v;
	}

	//lui sente solo che il mouse ha fatto qualcosa, poi delega la gestione dell'evento ai vari menu a seconda del gameState
	@Override
	public void mouseDragged(MouseEvent e) {   //per soundbar
/* 		switch (GameState.actualState) {
		case OPTIONS:
			view.getOptions().mouseDraggedInOption(e);
			break;
		case PAUSE:
			view.getPause().mouseDraggedInPause(e);
			break;
		default:
			break;
		} */

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
		default:
			break;

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	 	switch (GameState.actualState) {
		case START_TITLE:
			view.changeGameStateToMainMenu();
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
/*		case DIALOGUE:
			int index = view.getController().getPlay().getPlayer().getIndexOfEntityInteract();
			view.getPlay().getRoom(view.getCurrentRoomIndex()).getNPC(index).nextDialogueLine();
			break;*/
		default:
			break;
		}
	}

	private void handleMouseReleasedDuringPlayState(MouseEvent e) {
		/* if(SwingUtilities.isLeftMouseButton(e))
			view.getController().getPlay().stopPlayerAttacking();
		
		else if(SwingUtilities.isRightMouseButton(e))
			view.getController().getPlay().stopPlayerParring();
		
		else if(SwingUtilities.isMiddleMouseButton(e))		
				view.getController().getPlay().stopPlayerThrowing(); */
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private void handleMousePressedDuringPlayState(MouseEvent e) {
		/* if(SwingUtilities.isLeftMouseButton(e))
			view.getController().getPlay().startPlayerAttacking();

		else if(SwingUtilities.isRightMouseButton(e))
			view.getController().getPlay().startPlayerParring();

		else if(SwingUtilities.isMiddleMouseButton(e))
				view.getController().getPlay().startPlayerThrowing(); */

	}


}
