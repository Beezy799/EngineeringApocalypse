package src.controller;

import src.model.EntityStates;
import src.model.GameState;
import src.model.IModel;
import src.model.InputState;
import src.view.IView;

import java.util.concurrent.locks.ReentrantLock;

public class IController {

    private IView view;
    private IModel model;
    private PlayerController playerController;
    private PlayStateController playStateController;

    //serve per bloccare le variabili dell'inputstate mentre il controller le legge, in modo da evitare inconsistenze
    ReentrantLock lock;

    public IController(){
        lock = new ReentrantLock();
        playStateController = new PlayStateController(this);
        playerController = new PlayerController(this, playStateController);

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
               playerController.resetDirectionVector();
               updateInputs(); // guarda lo stato della tastiera
               playerController.update();
            break;

            case QUIT:
                System.exit(0);
                break;

            default:
                break;
        }
    }

    private void updateInputs() {
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
            playerController.changeActualState(EntityStates.DYING);
        }

        if (InputState.P.getPressed() || InputState.MIDDLE_CLICK.getPressed()) {
            playerController.changeActualState(EntityStates.THROWING);
        }

        if (InputState.ESCAPE.getPressed())
            GameState.actualState = GameState.PAUSE;

        lock.unlock();
    }


}
