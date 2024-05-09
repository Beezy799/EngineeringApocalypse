package src.model.mapModel;

import src.controller.Hitbox;
import src.model.*;
import src.view.gameWindow.GamePanel;
import src.view.inputs.InputState;

public class Passage {

    private Hitbox borders;
    private String nextRoomName;

    //posizione nella nuova stanza
    private int xNext, yNext;
    private int cfuRequired;
    private String message;

    public Passage(Hitbox h, String nextRoomName, int xN, int yN, int cfu, String m){
        borders = h;

        borders.setX(borders.getX()* GamePanel.TILES_SIZE);
        borders.setY(borders.getY()* GamePanel.TILES_SIZE);
        borders.setWidth((int)(borders.getWidth()* GamePanel.SCALE));
        borders.setHeight((int)(borders.getHeight()* GamePanel.SCALE));

        xNext = xN * GamePanel.TILES_SIZE;
        yNext = yN * GamePanel.TILES_SIZE;
        cfuRequired = cfu;

        this.nextRoomName = nextRoomName;

        message = m;
    }

    private Rooms getNextRoom(String nextRoomName) {
        if(nextRoomName.equals("TENDA")){
            return Rooms.TENDA;
        }
        else if(nextRoomName.equals("DORMITORIO")){
            return Rooms.DORMITORIO;
        }
        else if(nextRoomName.equals("LABORATORIO")){
            return Rooms.LABORATORIO;
        }
        else if(nextRoomName.equals("AULA_STUDIO")){
            return Rooms.AULA_STUDIO;
        }
        else if(nextRoomName.equals("BIBLIOTECA")){
            return Rooms.BIBLIOTECA;
        }
        else if(nextRoomName.equals("STUDIO_PROF")){
            return Rooms.STUDIO_PROF;
        }
        return null;
    }

    public void changeRoom(int cfuPlayer){
        if(cfuPlayer >= cfuRequired) {

            InputState.resetBooleans();
            //il personaggio si ferma
            Rooms.getModel().getController().getPlayerController().changeActualState(EntityStates.IDLE);

            //salva nel model i dati di dove si troverà il giocatore
            Rooms.getModel().savePassageData(getxNext(), getyNext(), getNextRoom(nextRoomName));

            //settiamo il gamestate nella stransizione
            Rooms.getModel().getView().getTransitionState().setNext(GameState.PLAYING);
            Rooms.getModel().getView().getTransitionState().setPrev(GameState.PLAYING);
            GameState.actualState = GameState.TRANSITION_STATE;

        }
        else{
            Rooms.getModel().getView().getPlayStateView().getPlayUI().setMessageToShow(message);
        }
    }

    public Hitbox getBorders(){
        return borders;
    }

    public String getNextRoomName() {
        return nextRoomName;
    }

    public int getxNext() {
        return xNext;
    }

    public int getyNext() {
        return yNext;
    }
}
