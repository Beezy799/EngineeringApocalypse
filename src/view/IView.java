package src.view;

import java.awt.*;

import src.controller.IController;
import src.model.GameState;
import src.model.IModel;
import src.model.mapModel.Rooms;
import src.view.entityView.PlayerView;
import src.view.gameBegin.StartTitle;
import src.view.inputs.MouseInputs;
import src.view.main.GamePanel;
import src.view.main.GameWindow;
import src.view.menu.*;
import src.view.playStateView.PlayStateView;

import static src.model.Constants.SoundConstants.MENU_MUSIC;

public class IView {

private IModel model;
private IController controller;
private GameWindow gameWindow;
private GamePanel gamePanel;
private MouseInputs mouseInputs;
private CommandsExplaination commandsExplaination;
private StartTitle startTitle;
private MainMenu mainMenu;
private OptionMenu optionMenu;
private PauseMenu pauseMenu;
private AvatarMenu avatarMenu;
private SoundManager soundManager;

private TransitionState transitionState;
PlayStateView playStateView;



    public IView(IController cont, IModel mod) {

        this.controller = cont;
        this.model = mod;

        startTitle = new StartTitle(this);
        mainMenu = new MainMenu();
        optionMenu = new OptionMenu(this);
        pauseMenu = new PauseMenu(this);
        avatarMenu = new AvatarMenu(this);
        commandsExplaination = new CommandsExplaination(this);

        mouseInputs = new MouseInputs(this);
        gamePanel = new GamePanel(this, mouseInputs);

        gameWindow = new GameWindow(gamePanel, this);
        gameWindow.setVisible(true);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        playStateView = new PlayStateView(this);

        soundManager = new SoundManager();
        soundManager.playMusic(MENU_MUSIC);

        transitionState = new TransitionState(this);

        //quando la view ha finito di crearsi, può dire al model di inserire le entità nelle stanze
        //perchè ora la parte view delle entità esiste
        model.loadEntitiesInRooms(this);

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
            case PLAYING:
                drawPlayState(g2);
                break;
            case COMMAND_EXPLAINATION:
                commandsExplaination.draw(g2);
                break;
            case TRANSITION_STATE:
                transitionState.draw(g2);
            break;
            case PAUSE:
                playStateView.draw(g2);
                pauseMenu.draw(g2);
                break;
            case DIALOGUE:
                playStateView.draw(g2);
                playStateView.getPlayUI().drawDialogue(g2);
                break;
            default:
                break;
        }
    }

    private void drawPlayState(Graphics2D g2) {
        playStateView.draw(g2);
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

    public IController getController(){
        return controller;
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

    public float getMusicVolume() {
        return soundManager.getMusicVolume();
    }

    public void setMusicVolume(float musicVolume) {
        this.soundManager.setMusicVolume(musicVolume);
    }

    public float getSeVolume(){
        return soundManager.getSEVolume();
    }

    public void setSeVolume (float v) {
        soundManager.setSEVolume(v);
    }

    public PlayerView getPlayerWiew() {
        return playStateView.getPlayerView();
    }

    public IModel getModel(){
        return model;
    }

    public CommandsExplaination getCommandsExplaination(){
        return commandsExplaination;
    }

    public SoundManager getSoundManager(){
        return soundManager;
    }

    public TransitionState getTransitionState(){
        return transitionState;
    }

    public PlayStateView getPlayStateView(){
        return playStateView;
    }
}
