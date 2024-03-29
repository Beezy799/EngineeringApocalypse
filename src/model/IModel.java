package src.model;

import src.controller.IController;
import src.controller.Vector;
import src.model.mapModel.Rooms;
import src.model.mapModel.Tileset;
import src.view.IView;

public class IModel {

    private IController controller;
    private IView view;
    private Tileset tileset;
    private int xPlayerAfterTransition, yPlayerAfterTransition;
    private Rooms roomAfterTransition;
    private Rooms rooms;

    public IModel(IController contr){
        this.controller = contr;

        tileset = new Tileset();
        tileset.loadTileset("/res/map/tilesetChat.json");

        //una volta finito di crearsi, passa il suo riferimento alle stanze
        Rooms.setModel(this);
        loadEventsRooms();
    }

    public void setView(IView v) {
        this.view = v;
    }

    public void changeGameState(GameState nextGameState){
        GameState.actualState = nextGameState;
    }

    public Tileset getTileset(){
        return tileset;
    }

    public IController getController(){
        return controller;
    }

    public IView getView(){
        return view;
    }

    //siccome durante la transizione dobbiamo disegnare la vecchia posizione del player, ci salviamo qui
    //dove finirà il player finita la trasizione
    public void savePassageData(int xPlayer, int yPlayer, Rooms nextRoom) {
        xPlayerAfterTransition = xPlayer;
        yPlayerAfterTransition = yPlayer;
        roomAfterTransition = nextRoom;
    }

    //finita la transizione, il player si troverà finalmente nella nuova posizione
    public void resumeGameAfterTransition() {
        controller.getPlayerController().setxPosPlayer(xPlayerAfterTransition);
        controller.getPlayerController().setyPosPlayer(yPlayerAfterTransition);
        Rooms.actualRoom = roomAfterTransition;
        //il pathfinder si crea il grafo della nuova stanza
        controller.getPathFinder().createGraph();
    }

    public int getEntityXpos(int index){
        return Rooms.actualRoom.getNpc().get(index).getEntityController().getxPos();
    }
    public int getEntityYpos(int index){
        return Rooms.actualRoom.getNpc().get(index).getEntityController().getyPos();
    }

    public EntityStates getCurrentStateOfEntity(int entityIndex){
        return Rooms.actualRoom.getNpc().get(entityIndex).getEntityController().getCurrentState();
    }

    public Vector getCurrentDirectionOfEntity(int entityIndex){
        return Rooms.actualRoom.getNpc().get(entityIndex).getEntityController().getMovementVector();
    }

    public void loadEntitiesInRooms(IView iView) {
        for(Rooms room : Rooms.values())
            room.loadEntities(iView);
    }

    public void loadEventsRooms() {
        for(Rooms room : Rooms.values())
            room.loadEvents(this);
    }

    public void setEntityNextDialogue(int entityIndex){
        Rooms.actualRoom.getNpc().get(entityIndex).getNpcView().setNextDialogueLine();
    }

    public String getEntityDialogue(int entityIndex){
        return Rooms.actualRoom.getNpc().get(entityIndex).getNpcView().getDialogue();
    }
}
