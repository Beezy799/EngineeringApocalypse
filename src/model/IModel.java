package src.model;

import src.controller.IController;
import src.controller.Vector;
import src.model.mapModel.Rooms;
import src.model.mapModel.tileset.Tileset;
import src.view.IView;
import src.view.entityView.EntityView;
import src.view.entityView.npc.NpcView;

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

    public int getEntityXpos(EntityView ev){
        if(ev instanceof NpcView)
            return Rooms.actualRoom.getNpc().get(ev.getIndexInEntityArray()).getEntityController().getxPos();

        return Rooms.actualRoom.getEnemy().get(ev.getIndexInEntityArray()).getEnemyController().getxPos();
    }
    public int getEntityYpos(EntityView ev){
        if(ev instanceof NpcView)
            return Rooms.actualRoom.getNpc().get(ev.getIndexInEntityArray()).getEntityController().getyPos();

        return Rooms.actualRoom.getEnemy().get(ev.getIndexInEntityArray()).getEnemyController().getyPos();
    }

    public EntityStates getCurrentStateOfEntity(EntityView ev){
        if(ev instanceof NpcView)
            return Rooms.actualRoom.getNpc().get(ev.getIndexInEntityArray()).getEntityController().getCurrentState();

        return Rooms.actualRoom.getEnemy().get(ev.getIndexInEntityArray()).getEnemyController().getCurrentState();
    }

    public Vector getCurrentDirectionOfEntity(EntityView ev){
        if(ev instanceof NpcView)
            return Rooms.actualRoom.getNpc().get(ev.getIndexInEntityArray()).getEntityController().getMovementVector();

        return Rooms.actualRoom.getEnemy().get(ev.getIndexInEntityArray()).getEnemyController().getMovementVector();
    }

    public void loadEntitiesInRooms(IView iView) {
        for(Rooms room : Rooms.values()) {
            room.loadNPCs(iView);
            room.loadEnemies(iView);
        }
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

    public void removeEnemy(int index){
        Rooms.actualRoom.getEnemy().remove(index);
    }


}
