package src.view;

import src.model.Constants;
import src.model.GameState;
import src.model.mapModel.Rooms;
import src.view.gameWindow.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;


//per non passare bruscamente da uno stato all'altro, disegna sfumandolo il vecchio stato
//per tre secondi e sfuma il valore della musica
public class TransitionState {

    private float counter;
    private final float transitionDuration = 180; //120 fps quindi sono 1,5 secondi

    private float opacity;
    private GameState prev;
    private GameState next;
    private IView view;

    private float volume;
    private float volumeBeforeTransition;
    private boolean volumeSaved = false;

    public TransitionState(IView v) {
        prev = GameState.SELECT_AVATAR;
        next = GameState.PLAYING;
        view = v;
        volume = view.getMusicVolume();
    }

    public void draw(Graphics2D g2) {
        counter++;
        saveOldVolume();

        if (counter < transitionDuration) {

            //disegna il vecchio stato
            view.changeGameState(prev);
            view.prepareNewFrame(g2);

            //disegna un rect nero sempre più visibile che copre il vecchio stato
            opacity = counter/transitionDuration;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2.setColor(Color.black);
            g2.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);

            //sfuma il volume della musica
            volume = view.getMusicVolume() - opacity + 0.01f;
            view.setMusicVolume(volume);

            view.changeGameState(GameState.TRANSITION_STATE);

        }
        else
            resetTransition();

    }

    //ripristina lo stato iniziale della classe
    private void resetTransition() {
        counter = 0;
        volumeSaved = false;

        view.getSoundManager().stopMusic();
        view.setMusicVolume(volumeBeforeTransition);
        volume = view.getMusicVolume();

        //se sta andando dal menu al play
        if(next == GameState.PLAYING && prev == GameState.SELECT_AVATAR) {
            view.getSoundManager().loopMusic(Rooms.actualRoom.getMusicIndex());
            view.changeGameState(next);
        }

        //se sta cambiando stanza
        else if(next == GameState.PLAYING && prev == GameState.PLAYING) {
            view.getModel().resumeGameAfterTransition();
            view.getSoundManager().loopMusic(Rooms.actualRoom.getMusicIndex());
            view.changeGameState(next);
        }

        //se sta tornando al menu iniziale
        else if(next == GameState.MAIN_MENU) {
            view.getSoundManager().loopMusic(Constants.SoundConstants.MENU_MUSIC);
            view.changeGameState(next);
            resetNextPrev();
        }

    }

    //per far ripartire la musica al volume prima della transizione
    private void saveOldVolume() {
        if (!volumeSaved) {
            volumeBeforeTransition = view.getMusicVolume();
            volumeSaved = true;
        }
    }

    //	//da cambiare
    private void resetNextPrev() {
        next = GameState.PLAYING;
        prev = GameState.SELECT_AVATAR;
    }

    public GameState getPrev() {
        return prev;
    }

    public void setPrev(GameState prev) {
        this.prev = prev;
    }

    public GameState getNext() {
        return next;
    }

    public void setNext(GameState next) {
        this.next = next;
    }

}
