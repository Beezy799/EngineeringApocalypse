package src.controller;

import src.model.GameState;
import src.model.IModel;
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
            break;

            case QUIT:
                System.exit(0);
                break;

            default:
                break;
        }
    }


    public void setModel(IModel m) {
        this.model = m;
    }

    //prova per modifica 

    
}
