package src.view;

import java.awt.*;

import src.controller.IController;
import src.model.GameState;
import src.model.IModel;
import src.view.gameBegin.StartTitle;
import src.view.inputs.MouseInputs;
import src.view.gameWindow.GamePanel;
import src.view.gameWindow.GameWindow;
import src.view.menu.*;
import src.view.playStateView.CutsceneView;
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
    private GameOverMenu gameOverMenu;
    private SoundManager soundManager;

    private TransitionState transitionState;
    private PlayStateView playStateView;
    private TitoliDiCoda titoliDiCoda;
    private CutsceneView cutsceneView;

    public IView(IController cont, IModel mod) {

        this.controller = cont;
        this.model = mod;

        startTitle = new StartTitle(this);
        mainMenu = new MainMenu();
        optionMenu = new OptionMenu(this);
        pauseMenu = new PauseMenu(this);
        avatarMenu = new AvatarMenu(this);
        commandsExplaination = new CommandsExplaination(this);
        cutsceneView = new CutsceneView(this);

        mouseInputs = new MouseInputs(this);
        gamePanel = new GamePanel(this, mouseInputs);

        playStateView = new PlayStateView(this);

        gameOverMenu = new GameOverMenu(this);

        titoliDiCoda = new TitoliDiCoda(this);

        soundManager = new SoundManager();
        soundManager.loopMusic(MENU_MUSIC);

        transitionState = new TransitionState(this);

        //quando la view ha finito di crearsi, può dire al model di inserire le entità nelle stanze
        //perchè ora la parte view delle entità esiste
        model.loadEntitiesInRooms(this);

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
            case GAME_OVER:
                playStateView.draw(g2);
                gameOverMenu.draw(g2);
                break;
            case TRANSITION_AFTER_GAME_OVER:
                transitionState.drawTransitionAfterGameOver(g2);
                break;
            case BOSS_CUTSCENE:
                cutsceneView.drawCutscene(g2);
                break;
            case END_GAME:
                titoliDiCoda.draw(g2);
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

    public GameOverMenu getGameOverMenu(){
        return gameOverMenu;
    }

    public CutsceneView getCutsceneView(){return cutsceneView; }
}