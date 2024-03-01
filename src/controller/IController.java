package src.controller;

import src.model.GameState;
import src.model.IModel;
import src.model.InputState;
import src.view.IView;

public class IController {

    private IView view;
    private IModel model;

    public void setView(IView v) {
        this.view = v;
    }
    public void setModel(IModel m) { this.model = m; }

    public void updateGame() {
        switch (GameState.actualState){
            //si aggiorna solo quando stiamo nello stato di gioco
            case PLAYING:
               updateInputs(); // guarda lo stato della tastiera
            break;

            case QUIT:
                System.exit(0);
                break;

            default:
                break;
        }
    }

    private synchronized void updateInputs() {

        //movement inputs
        if (InputState.W.getPressed())
            System.out.println("vai su");

        if (InputState.S.getPressed())
            System.out.println("vai giu");

        if (InputState.D.getPressed())
            System.out.println("vai destra");

        if (InputState.A.getPressed())
            System.out.println("vai sinistra");

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
    }

    
}
