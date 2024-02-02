package src.view.main;

import javax.swing.JFrame;

import src.view.IView;
//import src.view.ViewUtils;

//classe che implementa lafinestra di gioco, la cornice dentro alla quale si visualizza il gioco
public class GameWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private IView view;

	public GameWindow(GamePanel gp, IView v) {
		view = v;
		setTitle("ENGINEERING ADVENTURE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().add(gp);
		pack();
		setLocationRelativeTo(null);
		//handleLostFocus();
	}

	//questo metodo serve per quando la finestra di gioco perde il focus
	//resetta i boolean della direzione del personaggio
	/*private void handleLostFocus() {
		addWindowFocusListener(new WindowFocusListener() { 
			@Override
			public void windowLostFocus(WindowEvent e) {
				view.getController().getPlay().getPlayer().resetBooleans(); 	
			}
			@Override
			public void windowGainedFocus(WindowEvent e) {
				
			}
		});
	}*/


}
