package src.model.events;

import src.model.Constants;
import src.controller.Hitbox;
import src.model.IModel;
import src.view.inputs.InputState;

public class Piano extends Event{
    public Piano(Hitbox r, IModel m, int ind) {
        super(r, m, ind);
        message = "la musica non fa per te...";
    }

    @Override
    public void interact() {

        if(!endInteraction) {
            model.getView().getPlayStateView().getPlayUI().setMessageToShow("premi E per interagire");

            if (InputState.E.getPressed()) {
                model.getView().getPlayStateView().getPlayUI().setMessageToShow(message);
                model.getView().getSoundManager().playSE(Constants.SoundConstants.PIANO_SE);

                endInteraction = true;
            }
        }
    }

}
