package src.view;

import java.awt.*;

import src.controller.IController;
import src.model.GameState;
import src.model.IModel;
import src.view.gameBegin.StartTitle;
import src.view.inputs.MouseInputs;
import src.view.main.GamePanel;
import src.view.main.GameWindow;
import src.view.menu.AvatarMenu;
import src.view.menu.MainMenu;
import src.view.menu.OptionMenu;
import src.view.menu.PauseMenu;

public class IView {

private IModel model;
private IController controller;

private GameWindow gameWindow;
private GamePanel gamePanel;
private MouseInputs mouseInputs;
private StartTitle startTitle;
private MainMenu mainMenu;
private OptionMenu optionMenu;
private PauseMenu pauseMenu;
private AvatarMenu avatarMenu;

    public IView(IController cont, IModel mod) {

        this.controller = cont;
        this.model = mod;
        startTitle = new StartTitle(this);
        mainMenu = new MainMenu();
        optionMenu = new OptionMenu(this);
        pauseMenu = new PauseMenu();
        avatarMenu = new AvatarMenu(this);


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
            case SELECT_AVATAR:
                avatarMenu.draw(g2);
                break;
            case OPTIONS:
                optionMenu.draw(g2);
                break;
            default:
                break;
        }
    }

    public void changeGameStateToMainMenu() {
       model.changeGameState(GameState.MAIN_MENU);
    }

    public void changeGameState(GameState newState){
        GameState.actualState = newState;
    }
 
    public void setCursorPosition(int x, int y){    //richiama e basta il metodo che sta in GamePanel
        gamePanel.setCursorPosition(x,y);               
    }

    public Point getCursorPosition(){
        return gamePanel.getMousePosition();
    }
    
    public MainMenu getMainMenu(){
        return this.mainMenu;
    }

    public StartTitle getStartTitle() {
       return startTitle;
    }

    public OptionMenu getOptions() {
        return optionMenu;
    }

    public PauseMenu getPause() {
        return pauseMenu;
    }

    public AvatarMenu getAvatarMenu() {
        return avatarMenu;
    }
}
