package src.controller.entitycontroller;

import src.controller.IController;
import src.model.Constants;
import src.model.EntityStates;

public class CatController extends EntityController {

    private final int hitboxWidth = 32;
    private final int hitboxHeight = 32;

    private boolean firstInteraction = true;
    private String questCompletata = "hai ritrovato l'hard disk del nerd \n quest completata!";

    public CatController(int x, int y, IController c, int index) {
        super(x, y, c, index);
        setHitbox(hitboxWidth, hitboxHeight);
    }

    @Override
    public void update() {
        switch(currentState){
            case IDLE:
                randomMove();
                break;
            case SPEAKING:
                if(firstInteraction){
                    controller.getPlayerController().addCFU(30);
                    controller.getView().getPlayStateView().getPlayUI().setMessageToShow(questCompletata);

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
            case MOVE:
                updatePosition();
                break;
            default:
                currentState = EntityStates.IDLE;
                break;
        }
    }
}
