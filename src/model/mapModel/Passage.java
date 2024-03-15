package src.model.mapModel;

import src.model.Hitbox;
import src.model.IModel;
import src.view.main.GamePanel;

public class Passage {

    private Hitbox borders;
    private Rooms nextRoom;

    //posizione nella nuova stanza
    private int xNext, yNext;
    private int cfuRequired;
    private IModel model;
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
        setNextRoom(nextRoomName);
        message = m;
    }

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
            //cambia x, y del player
            try{
                Rooms.getModel().getController().getPlayerController().setxPosPlayer(xNext);
                Rooms.getModel().getController().getPlayerController().setyPosPlayer(yNext);
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
            Rooms.actualRoom = nextRoom;
        }
        else{
            //mostra il messaggio
        }
    }

    public Hitbox getBorders(){
        return borders;
    }

}
