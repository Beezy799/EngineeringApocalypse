package src.controller.entitycontroller.npc;

import src.controller.IController;
import src.controller.entitycontroller.EntityController;
import src.controller.pathFinding.Node;
import src.model.Constants;
import src.model.EntityStates;
import src.model.Rooms;
import src.model.entity.EntityComplete;
import src.model.entity.NpcComplete;
import src.view.entityView.npc.NerdView;
import src.view.gameWindow.GamePanel;

import java.util.Random;

public class CatController extends EntityController {

    private final int hitboxWidth = 32;
    private final int hitboxHeight = 32;

    private boolean firstInteraction = true;
    private String questCompletata = "hai ritrovato l'hard disk del nerd \n quest completata!";

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
                if(interactionHitbox.intersects(controller.getPlayerController().getHitbox())) {
                    currentState = EntityStates.RUNAWAY;
                }
                break;
            case MOVE:
                updatePosition();
                if(interactionHitbox.intersects(controller.getPlayerController().getHitbox())) {
                    currentState = EntityStates.RUNAWAY;
                }
                break;
            case SPEAKING:
                if(firstInteraction){
                    controller.getPlayerController().addCFU(30);
                    controller.getView().getPlayStateView().getPlayUI().setMessageToShow(questCompletata);

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

            case RUNAWAY:
                speed = GamePanel.SCALE*1.2f;
                movementVector.resetDirections();
                int pX = (int)controller.getPlayerController().getxPosPlayer();
                int pY = (int)controller.getPlayerController().getyPosPlayer();

                //sceglie se andare su o giu
                if(pX > hitbox.getX() && pX < hitbox.getX()+hitbox.getWidth()){
                    //se gli sta sotto, prova a salire
                    if(pY > yPos)
                        movementVector.setY(-1);
                    //se non può salire, sceglie un percorso verso uno dei due angoli in alto
                    else
                        movementVector.setY(1);
                }
                //sceglie se andare a dx o a sx
                else {
                    if(pX > xPos)
                        movementVector.setX(-1);

                    else
                        movementVector.setX(1);
                }
                if(canMove(tempHitbox)) {
                    tempHitbox.setY(hitbox.getY());
                    tempHitbox.setX(hitbox.getX());
                    updatePosition();
                }
                else
                    currentState = EntityStates.HITTED;
                break;

            default:
                currentState = EntityStates.IDLE;
                break;
        }
    }

    public void reset(){
        super.reset();
        firstInteraction = true;
    }

}
