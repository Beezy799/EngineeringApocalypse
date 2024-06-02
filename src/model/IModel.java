package src.model;

import src.controller.Hitbox;
import src.controller.IController;
import src.model.entity.EnemyComplete;
import src.model.entity.NpcComplete;
import src.model.mapModel.tileset.Tileset;
import src.view.IView;
import src.view.entityView.BossView;

import java.util.ArrayList;

public class IModel {

    private IController controller;
    private IView view;
    private Tileset tileset;
    private int xPlayerAfterTransition, yPlayerAfterTransition;
    private Rooms roomAfterTransition;

    public IModel(IController contr){
        this.controller = contr;

        tileset = new Tileset();
        tileset.loadTileset("/res/map/tilesetChat.json");

        //una volta finito di crearsi, passa il suo riferimento alle stanze
        //questo perchè le Rooms potrebbero crearsi prima del model
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

        //per evitare che il player vada ad incastrarsi sopra un'altra entità
        for(NpcComplete npc : Rooms.actualRoom.getNpc()){
            if(npc.getEntityController().getHitbox().intersects(controller.getPlayerController().getHitbox())){
                npc.getEntityController().resetPosition();
            }
        }
        for(EnemyComplete enemy : Rooms.actualRoom.getEnemy()){
            if(enemy.getEnemyController().getHitbox().intersects(controller.getPlayerController().getHitbox())){
                enemy.getEnemyController().resetPosition();
            }
        }
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
        if(Rooms.actualRoom != Rooms.STUDIO_PROF) {
            view.getSoundManager().playSE(Constants.SoundConstants.DIALOGUE_SE);
            Rooms.actualRoom.getNpc().get(entityIndex).getNpcView().setNextDialogueLine();
        }
        else {
            ((BossView)(Rooms.actualRoom.getEnemy().get(0).getEnemyView())).setNextDialogueLine();
        }
    }

    public String getEntityDialogue(int entityIndex){
        //se non siamo nello studio del prof
        if(Rooms.actualRoom != Rooms.STUDIO_PROF)
            return Rooms.actualRoom.getNpc().get(entityIndex).getNpcView().getDialogueLine();
        //se siamo nello studio del prof, l'unico nemico presente è lui
        return ((BossView)(Rooms.STUDIO_PROF.getEnemy().get(0).getEnemyView())).getDialogueLine();
    }

    public void unlockState(int enemyIndex){
        Rooms.actualRoom.getEnemy().get(enemyIndex).getEnemyController().setStateLocked(false);
    }

    public void checkHittedEnemy(Hitbox playerAttackHitbox, int playerAttack) {
        ArrayList<EnemyComplete> enemyes = Rooms.actualRoom.getEnemy();
        for (EnemyComplete e : enemyes){
            if(e.getEnemyController().getHitbox().intersects(playerAttackHitbox)){
                e.getEnemyController().hitted(playerAttack);
            }
        }
    }

    public  void resetGame(){
        for (Rooms room : Rooms.values()){
            room.resetEventsAndEntities();
        }
        Rooms.actualRoom = Rooms.BIBLIOTECA;
        controller.getPlayerController().reset();
    }

    public void revomeBullet(int indexInList) {
        Rooms.actualRoom.deleteBullet(indexInList);
    }
}