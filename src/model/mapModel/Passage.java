package src.model.mapModel;

import src.model.Hitbox;
import src.model.IModel;

public class Passage {

    private Hitbox borders;
    private Rooms nextRoom;

    //posizione nella nuova stanza
    private int xNext, yNext;
    private int cfuRequired;
    private IModel model;

    public Passage(Hitbox h, String nextRoomName, int xN, int yN, int cfu){
        borders = h;
        xNext = xN;
        yNext = yN;
        cfuRequired = cfu;
        setNextRoom(nextRoomName);
        System.out.println("passaggio creato, cfu " + cfuRequired);
    }

    //
    private void setNextRoom(String nextRoomName) {
        if(nextRoomName.equals("TENDA")){
            nextRoom = Rooms.TENDA;
        }
        else if(nextRoomName.equals("DORMITORIO")){
            nextRoom = Rooms.DORMITORIO;
        }
        else if(nextRoomName.equals("LABORATORIO")){
            nextRoom = Rooms.LABORATORIO;
        }
        else if(nextRoomName.equals("AULA_STUDIO")){
            nextRoom = Rooms.AULA_STUDIO;
        }
        else if(nextRoomName.equals("BIBLIOTECA")){
            nextRoom = Rooms.BIBLIOTECA;
        }
        else if(nextRoomName.equals("STUDIO_PROF")){
            nextRoom = Rooms.STUDIO_PROF;
        }
    }

    public void changeRoom(int cfuPlayer){
        if(cfuPlayer >= cfuRequired) {
            Rooms.actualRoom = nextRoom;
            //cambia x, y del player
            model.getController().getPlayerController().setxPosPlayer(xNext);
            model.getController().getPlayerController().setyPosPlayer(yNext);
        }
        else{
            //metti un messaggio
        }
    }

    public Hitbox getBorders(){
        return borders;
    }

}
