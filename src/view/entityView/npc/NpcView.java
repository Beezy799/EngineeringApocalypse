package src.view.entityView.npc;

import src.model.GameState;
import src.view.IView;
import src.view.entityView.EntityView;

public abstract class NpcView extends EntityView {
    protected String[][] dialogues;
    protected int dialogueLine, dialogueIndex;

    public NpcView(IView v, int i) {
        super(v, i);
        loadDialogues();
    }

    public void setNextDialogueLine(){
        dialogueLine++;

        if(dialogueLine >= dialogues[dialogueIndex].length) {
            dialogueLine = dialogues[dialogueIndex].length - 1;
            GameState.actualState = GameState.PLAYING;
        }
    }

    public void setNextDialogue(){
        dialogueIndex++;
        dialogueLine = 0;
        if(dialogueIndex >= dialogues.length){
            dialogueIndex = dialogues.length -1;
        }
    }

    public String getDialogueLine(){
        return dialogues[dialogueIndex][dialogueLine];
    }

    @Override
    protected abstract int getAnimationLenght();
    protected abstract void loadDialogues();
    public void reset(){
        dialogueLine = 0;
    }
}