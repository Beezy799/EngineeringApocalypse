package src.model;

import src.controller.IController;
import src.controller.Vector;
import src.controller.pathFinding.Node;
import src.model.mapModel.Rooms;
import src.model.mapModel.Tileset;
import src.view.IView;

public class IModel {

    private IController controller;
    private IView view;
    private Tileset tileset;
    private int xPlayerBeforeTransition, yPlayerBeforeTransition;
    private Rooms roomBeforeTransition;
    private Rooms rooms;

    public IModel(IController contr){
        this.controller = contr;

        tileset = new Tileset();
        tileset.loadTileset("/res/map/tilesetChat.json");

        //una volta finito di crearsi, passa il suo riferimento alle stanze
        Rooms.setModel(this);

        //controller.getPathFinder().existPath(new Node(9,12), new Node(9, 13));
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
        xPlayerBeforeTransition = xPlayer;
        yPlayerBeforeTransition = yPlayer;
        roomBeforeTransition = nextRoom;
    }

    //finita la transizione, il player si troverà finalmente nella nuova posizione
    public void resumeGameAfterTransition() {
        controller.getPlayerController().setxPosPlayer(xPlayerBeforeTransition);
        controller.getPlayerController().setyPosPlayer(yPlayerBeforeTransition);
        Rooms.actualRoom = roomBeforeTransition;
    }

    public int getEntityXpos(int index){
        return Rooms.actualRoom.getEntities().get(index).getEntityController().getxPos();
    }
    public int getEntityYpos(int index){
        return Rooms.actualRoom.getEntities().get(index).getEntityController().getyPos();
    }

    public EntityStates getCurrentStateOfEntity(int entityIndex){
        return Rooms.actualRoom.getEntities().get(entityIndex).getEntityController().getCurrentState();
    }

    public Vector getCurrentDirectionOfEntity(int entityIndex){
        return Rooms.actualRoom.getEntities().get(entityIndex).getEntityController().getMovementVector();
    }


}
