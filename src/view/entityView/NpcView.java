package src.view.entityView;

import src.view.IView;

public abstract class NpcView extends EntityView{
    protected String[] dialogues;
    protected int dialogueIndex;

    public NpcView(IView v, int i) {
        super(v, i);
        loadDialogues();
    }


    public void setNextDialogueLine(){
        dialogueIndex++;

        if(dialogueIndex >= dialogues.length)
            dialogueIndex = dialogues.length - 1;

    }

    public String getDialogue(){
        return dialogues[dialogueIndex];
    }
    @Override
    protected abstract int getAnimationLenght();
    protected abstract void loadDialogues();
}
