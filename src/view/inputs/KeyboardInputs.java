package src.view.inputs;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import src.model.GameState;

import src.model.InputState;
import src.view.IView;

import static src.view.main.GamePanel.SCALE;

public class KeyboardInputs implements KeyListener {

    private int cursorSpeed = (int)(SCALE*10);
    private IView view;

    public KeyboardInputs(IView v) {
        this.view = v;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.actualState) {
            case START_TITLE:
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    GameState.actualState = GameState.MAIN_MENU;
                }
                break;
            case PLAYING:
                handleKeypressedDuringPlayState(e);
                break;
            case DIALOGUE:
                break;
            case BOSS_CUTSCENE:
                break;
            default: //nei casi main manu , opzioni e  avatar selection
                moveCursor(e);
                break;

/*          case DIALOGUE:
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE ||
                   e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A ||
                   e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_W) {

                    view.changeGameState(Gamestate.PLAYING);
                    view.getController().resetPlayerBooleans();
                }
                else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int index = view.getController().getIndexOfInteractingEntity();
                    view.getPlay().getRoom(view.getCurrentRoomIndex()).getNPC(index).nextDialogueLine();
                }
                break;

            case BOSS_CUTSCENE:
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    view.getController().resetPlayerBooleans();
                    view.changeGameState(Gamestate.PLAYING);
                }
                else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //l'indice è sempre zero, perchè il boss è l'unico NPC della stanza
                    view.getPlay().getRoom(view.getCurrentRoomIndex()).getBossView().nextDialogueLine();
                }
                break;

            default:
                break;
        }  */
        }

    }

    private void moveCursor(KeyEvent e) {

        Point p = view.getCursorPosition();
        int x = p.x;
        int y = p.y;

        if(e.getKeyCode() == KeyEvent.VK_UP ||e.getKeyCode() == KeyEvent.VK_W){
            view.setCursorPosition(x, y - cursorSpeed);
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN ||e.getKeyCode() == KeyEvent.VK_S){
            view.setCursorPosition(x, y + cursorSpeed);
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT ||e.getKeyCode() == KeyEvent.VK_D){
            view.setCursorPosition(x + cursorSpeed, y);
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT ||e.getKeyCode() == KeyEvent.VK_A){
            view.setCursorPosition(x - cursorSpeed, y);
        }



    }


    //lui sente solo che un tasto è stato rilasciato, poi delega la gestione del fatto ai vari gamestate
    @Override
    public void keyReleased(KeyEvent e) {

        switch (GameState.actualState) {
            case MAIN_MENU:
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    view.getMainMenu().enterReleased(e);
                }
                break;
            case SELECT_AVATAR:
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    view.getAvatarMenu().enterReleased(e);
                }
                break;
            case OPTIONS:
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    view.getOptions().enterReleased(e);
                }
                break;
            case PLAYING:
                handleKeyReleasedPlayState(e);
                break;
            case COMMAND_EXPLAINATION:
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    view.getCommandsExplaination().enterReleased(e);
                }
                break;
            case PAUSE:
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    view.getPause().enterReleased(e);
                }
                break;
            default:
                break;
        }

    }

    private void handleKeypressedDuringPlayState(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                InputState.ENTER.setPressed(true);
                break;
            case KeyEvent.VK_SPACE:
                InputState.SPACE.setPressed(true);
                break;
            case KeyEvent.VK_ESCAPE:
                InputState.ESCAPE.setPressed(true);
                break;
            case KeyEvent.VK_P:
                InputState.P.setPressed(true);
                break;
            case KeyEvent.VK_E:
                InputState.E.setPressed(true);
                break;

            //movement
            case KeyEvent.VK_W:
                InputState.W.setPressed(true);
                break;
            case KeyEvent.VK_S:
                InputState.S.setPressed(true);
                break;
            case KeyEvent.VK_D:
                InputState.D.setPressed(true);
                break;
            case KeyEvent.VK_A:
                InputState.A.setPressed(true);
                break;
            default:
                break;
        }

    }

    private void handleKeyReleasedPlayState(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    InputState.ENTER.setPressed(false);
                    break;
                case KeyEvent.VK_SPACE:
                    InputState.SPACE.setPressed(false);
                    break;
                case KeyEvent.VK_ESCAPE:
                    InputState.ESCAPE.setPressed(false);
                    break;
                case KeyEvent.VK_P:
                    InputState.P.setPressed(false);
                    break;
                case KeyEvent.VK_E:
                    InputState.E.setPressed(false);
                    break;

                //movement
                case KeyEvent.VK_W:
                    InputState.W.setPressed(false);
                    break;
                case KeyEvent.VK_S:
                    InputState.S.setPressed(false);
                    break;
                case KeyEvent.VK_D:
                    InputState.D.setPressed(false);
                    break;
                case KeyEvent.VK_A:
                    InputState.A.setPressed(false);
                    break;

                default:
                    break;
            }
        }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
