package src.controller;

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
        playerController = new PlayerController();
    }

    public void setView(IView v) {
        this.view = v;
    }
    public void setModel(IModel m) { this.model = m; }

    public void updateGame() {
        switch (GameState.actualState){
            //si aggiorna solo quando stiamo nello stato di gioco
            case PLAYING:
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
        lock.lock();
        //movement inputs
        if (InputState.W.getPressed())
            playerController.setDirectionUp();

        if (InputState.S.getPressed())
            playerController.setDirectionDown();

        if (InputState.D.getPressed())
            playerController.setDirectionRight();

        if (InputState.A.getPressed())
            playerController.setDirectionLeft();


        //azioni
        if (InputState.ENTER.getPressed())
            System.out.println("attacca");

        if (InputState.SPACE.getPressed())
            System.out.println("para");

        if (InputState.E.getPressed())
            System.out.println("interagisci");

        if (InputState.P.getPressed())
            System.out.println("spara");

        if (InputState.ESCAPE.getPressed())
            GameState.actualState = GameState.PAUSE;

        lock.unlock();
    }

    
}
