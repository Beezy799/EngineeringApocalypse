package src.view;

import java.awt.Graphics2D;

import src.controller.IController;
import src.model.GameState;
import src.model.IModel;
import src.view.gameBegin.StartTitle;
import src.view.inputs.MouseInputs;
import src.view.main.GamePanel;
import src.view.main.GameWindow;
import src.view.menu.MainMenu;

public class IView {

private IModel model;
private IController controller;

private GameWindow gameWindow;
private GamePanel gamePanel;
private MouseInputs mouseInputs;
private StartTitle startTitle;
public MainMenu getMainMenu;
private MainMenu mainMenu;

    public IView(IController cont, IModel mod) {

        this.controller = cont;
        this.model = mod;
        startTitle = new StartTitle(this);
        mainMenu = new MainMenu();



        mouseInputs = new MouseInputs(this);
        gamePanel = new GamePanel(this, mouseInputs);
        gameWindow = new GameWindow(gamePanel, this);
        gameWindow.setVisible(true);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
    }

    //chiede al pannello di creare il suo ambiente grafico, g, che poi userà per disegnare il frame successivo
	public void draw() {	
		gamePanel.repaint();	
	}

    public void prepareNewFrame(Graphics2D g2) {
        
        switch (GameState.actualState) {
            case START_TITLE:
                startTitle.draw(g2);
                break;
            case MAIN_MENU:
                mainMenu.draw(g2);
            break;
            default:
                break;
        }
    }

    public void changeGameStateToMainMenu() {
       model.changeGameState(GameState.MAIN_MENU);
    }
 
    
    public MainMenu getMainMenu(){

        return this.mainMenu;
    }

    public StartTitle getStartTitle() {
       return startTitle;
    }
}
