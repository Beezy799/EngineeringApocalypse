package src.controller.entitycontroller.npc;

import src.controller.IController;
import src.controller.entitycontroller.EntityController;
import src.model.Constants;
import src.model.EntityStates;
import src.model.Rooms;
import src.model.entity.NpcComplete;
import src.view.entityView.npc.NerdView;
import src.view.gameWindow.GamePanel;

import java.util.Random;

public class CatController extends EntityController {

    private final int hitboxWidth = 32;
    private final int hitboxHeight = 32;

    private boolean firstInteraction = true, questActivated;
    private String questCompletata = "hai ritrovato il mouse del nerd \n quest completata!";

    public CatController(int x, int y, IController c, int index) {
        super(x, y, c, index);
        setHitbox(hitboxWidth, hitboxHeight, 2, 2);
    }

    @Override
    public void update() {
        speed = GamePanel.SCALE;

        switch(currentState){
            case IDLE:
                randomMove();
                break;
            case MOVE:
                updatePosition();
                break;
            case SPEAKING:
                if(firstInteraction && questActivated){
                    controller.getPlayerController().addCFU(30);
                    controller.getView().getPlayStateView().getPlayUI().setMessageToShow(questCompletata);
                    //cambia il dialogo del nerd nella tenda
                    for(NpcComplete nerd : Rooms.TENDA.getNpc()){
                        if(nerd.getEntityView() instanceof NerdView){
                            nerd.getNpcView().setNextDialogue();
                        }
                    }
                    controller.getView().getSoundManager().playSE(Constants.SoundConstants.CFU_SE);
                    controller.getPlayerController().changeActualState(EntityStates.CFU_FOUND);
                    controller.getPlayerController().getMovementVector().resetDirections();
                    controller.getPlayerController().getMovementVector().setY(1);
                    controller.getPlayerController().lockState();
                    firstInteraction = false;
                }
                turnToPlayer();
                currentState = EntityStates.IDLE;
                break;

            default:
                currentState = EntityStates.IDLE;
                break;
        }
    }

    public void setQuestActivated(boolean b){
        questActivated = b;
    }

    public void reset(){
        super.reset();
        questActivated = false;
        firstInteraction = true;
    }

}
