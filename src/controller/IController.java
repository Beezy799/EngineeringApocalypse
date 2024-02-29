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


    public void updateGame() {
        switch (GameState.actualState){
            //si aggiorna solo quando stiamo nello stato di gioco
            case PLAYING:
               updateInputs();
            break;

            case QUIT:
                System.exit(0);
                break;

            default:
                break;
        }
    }

    private void updateInputs() {

        if (InputState.W.getPressed())
            System.out.println("vai su");

            if (InputState.S.getPressed())
                System.out.println("vai giu");

            if (InputState.D.getPressed())
                System.out.println("vai destra");

            if (InputState.A.getPressed())
                System.out.println("vai sinistra");

    }

    public void setModel(IModel m) {
        this.model = m;
    }

    //prova per modifica 

    
}
