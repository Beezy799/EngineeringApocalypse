package src.controller;

import src.controller.pathFinding.PathFinder;
import src.model.*;
import src.model.entity.EnemyComplete;
import src.model.entity.NpcComplete;
import src.model.Rooms;
import src.view.IView;
import src.view.gameWindow.GamePanel;
import src.view.inputs.InputState;

import java.util.concurrent.locks.ReentrantLock;

public class IController {

    private IView view;
    private IModel model;
    private PlayerController playerController;
    private PlayStateController playStateController;
    private PathFinder pathFinder;

    private int indexEntityInteraction;

    //serve per bloccare le variabili dell'inputstate mentre il controller le legge, in modo da evitare inconsistenze
    ReentrantLock lock;

    public IController(){
        lock = new ReentrantLock();
        playStateController = new PlayStateController(this);
        playerController = new PlayerController(this, playStateController);
        pathFinder = new PathFinder(this);
        pathFinder.createGraph();
    }

    public void setView(IView v) {
        this.view = v;
    }
    public void setModel(IModel m) { this.model = m; }
    public IView getView() {
        return view;
    }
    public IModel getModel(){
        return model;
    }
    public PlayerController getPlayerController(){
        return playerController;
    }

    public void updateGame() {
        switch (GameState.actualState){
            //si aggiorna solo quando stiamo nello stato di gioco
            case PLAYING:
                updateInputs(); // guarda lo stato della tastiera
                playerController.update();
                for(NpcComplete npc : Rooms.actualRoom.getNpc()){
                    npc.getEntityController().update();
                }
                for(EnemyComplete enemy : Rooms.actualRoom.getEnemy()){
                    if(enemy.isAlive()) {
                        enemy.getEnemyController().update();
                    }
                }
                break;

            case QUIT:
                System.exit(0);
                break;

            default:
                break;
        }
    }

    private void updateInputs() {
        playerController.resetDirectionVector();

        //il lock blocca le risorse usate dalla funzione finchè essa non ha finito
        //anche se premo un tasto, lo stato della tatiera non cambia--> rende sincronizzate le cose
        lock.lock();

        //movement inputs, setta la direzione del player e fa muovere il player
        if (InputState.W.getPressed()) {
            playerController.setDirectionUp();
        }

        if (InputState.S.getPressed()) {
            playerController.setDirectionDown();
        }

        if (InputState.D.getPressed()) {
            playerController.setDirectionRight();
        }

        if (InputState.A.getPressed()) {
            playerController.setDirectionLeft();
        }

        float xRisultante = playerController.getMovementVector().getX();
        float yRisultante = playerController.getMovementVector().getY();

        if(xRisultante != 0 || yRisultante != 0) {
            playerController.changeActualState(EntityStates.MOVE);

            //se si muove in diagonale, divido la velocità per radice di due
            float oldModuleSpeed = playerController.getSpeed();
            if(xRisultante != 0 && yRisultante != 0){
                float newModule = oldModuleSpeed * 0.71f;
                playerController.getMovementVector().setModule(newModule);
            }
            //altrimenti lascio la velocità iniziale
            else {
                playerController.getMovementVector().setModule(oldModuleSpeed);
            }
        }
        else {
            playerController.changeActualState(EntityStates.IDLE);
        }



        //azioni
        if (InputState.ENTER.getPressed() || InputState.LEFT_CLICK.getPressed()) {
            playerController.changeActualState(EntityStates.ATTACKING);
            playerController.lockState();
        }

        //se il giocatore preme spazio, il player resta in parring finché non rilascia il tasto
        if (InputState.SPACE.getPressed() || InputState.RIGHT_CLICK.getPressed()) {
            playerController.changeActualState(EntityStates.PARRING);
            playerController.lockState();
        }
        //quando smette di premere spazio, smette di pararsi
        else if(!InputState.SPACE.getPressed() && playerController.getCurrentState() == EntityStates.PARRING){
            playerController.unlockState();
        }


        if (InputState.E.getPressed()) {
            if(playerController.isNearEntity()){
                InputState.resetBooleans();
                playerController.speak();
                Rooms.actualRoom.getNpc().get(indexEntityInteraction).getEntityController().speak();
                GameState.actualState = GameState.DIALOGUE;
            }
        }

        if (InputState.P.getPressed() || InputState.MIDDLE_CLICK.getPressed()) {
            //playerController.changeActualState(EntityStates.THROWING);

//            playerController.changeActualState(EntityStates.CFU_FOUND);
//            playerController.getMovementVector().resetDirections();
//            playerController.getMovementVector().setY(1);
//            playerController.lockState();
            view.getSoundManager().setMusicVolume(0.01f);
            System.out.println("p " +playerController.getyPosPlayer()/ GamePanel.TILES_SIZE + ", " + playerController.getxPosPlayer()/GamePanel.TILES_SIZE);
            //System.out.println();         8, 18

            InputState.P.setPressed(false);
        }

        if (InputState.ESCAPE.getPressed()) {
            GameState.actualState = GameState.PAUSE;
            InputState.ESCAPE.setPressed(false);
        }

        lock.unlock();
    }

    public PathFinder getPathFinder(){
        return pathFinder;
    }

    public int getIndexEntityInteraction() {
        return indexEntityInteraction;
    }

    public void setIndexEntityInteraction(int indexEntityInteraction) {
        this.indexEntityInteraction = indexEntityInteraction;
    }

    public PlayStateController getPlayStateController(){
        return playStateController;
    }
}