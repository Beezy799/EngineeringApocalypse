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

    //serve per bloccare le variabili dell'inputstate mentre il controller le legge, in modo da evitare inconsistenze
    ReentrantLock lock;

    public IController(){
        lock = new ReentrantLock();
        playerController = new PlayerController(this);
    }

    public void setView(IView v) {
        this.view = v;
    }
    public void setModel(IModel m) { this.model = m; }

    public IView getView() {
        return view;
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

        //serve per riportare il player allo stato iniziale quando il tasto è rilasciato
        boolean setMoving = false;
        //movement inputs, setta la direzione del player e fa muovere il player
        if (InputState.W.getPressed()) {
            playerController.setDirectionUp();
            setMoving = true;
        }

        if (InputState.S.getPressed()) {
            playerController.setDirectionDown();
            setMoving = true;
        }

        if (InputState.D.getPressed()) {
            playerController.setDirectionRight();
            setMoving = true;
        }

        if (InputState.A.getPressed()) {
            playerController.setDirectionLeft();
            setMoving = true;
        }

        if(setMoving == true) {
            playerController.changeActualState(EntityStates.MOVE);
        }
        else {
            playerController.changeActualState(EntityStates.IDLE);
        }



        //azioni
        if (InputState.ENTER.getPressed()) {
            playerController.changeActualState(EntityStates.ATTACKING);
        }

        if (InputState.SPACE.getPressed()) {
            playerController.changeActualState(EntityStates.PARRING);
        }

        if (InputState.E.getPressed()) {
            playerController.changeActualState(EntityStates.SPEAKING);
        }

        if (InputState.P.getPressed()) {
            playerController.changeActualState(EntityStates.THROWING);
        }

        if (InputState.ESCAPE.getPressed())
            GameState.actualState = GameState.PAUSE;

        lock.unlock();
    }

}
