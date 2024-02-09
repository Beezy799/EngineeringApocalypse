package src.view.inputs;

import java.awt.event.KeyEvent;

import src.model.GameState;
import src.view.IView;
import src.view.menu.MainMenu;




//dice al mainMenu cosa deve fare dopo aver ascoltato l'input (tastira) durante il menu
public class MainMenuInputs {

    private MainMenu mainMenu;
    private IView view;

    private final int PLAY = 0, OPTION = 1, RESUME = 2, QUIT = 3;
    private int buttonPointedByCursor = PLAY;


    public MainMenuInputs(IView v, MainMenu m){
        this.view = v;
        this.mainMenu = m; 
    }


    //METODI TASTIERA
    public void keyReleasedDuringMainMenuState(int keyCode) {
        switch (buttonPointedByCursor) {
            case PLAY:
                keyboardInputsPlayButton(keyCode);
                break;
            case OPTION:
                keyboardInputsOptionButton(keyCode);
            break;
            case RESUME:
                keyboardInputsResumeButton(keyCode);
            break;
            case QUIT:
                keyboardInputsQuitButton(keyCode);
            break;

            default:
                break;
        }
    } 

    //metodo che dice cosa fare quando puntiamo il tasto play
    private void keyboardInputsPlayButton(int key) {
       if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
            view.setCursorPosition(mainMenu.getPlayButton().getBounds().x, mainMenu.getPlayButton().getBounds().y);
       }
        
        else if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            view.setCursorPosition(mainMenu.getOptionButton().getBounds().x, mainMenu.getOptionButton().getBounds().y);
            buttonPointedByCursor = OPTION;
        }

        else if(key == KeyEvent.VK_ENTER) {
            mainMenu.resetButtons();
            view.changeGameState(mainMenu.getPlayButton().getPointedState());
        }
    }

    
    //metodo che dice cosa fare quando puntiamo il tasto option
    private void keyboardInputsOptionButton(int key) {
         if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            view.setCursorPosition(mainMenu.getResumeButton().getBounds().x, mainMenu.getResumeButton().getBounds().y);
            buttonPointedByCursor = RESUME;
        }
        else if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            view.setCursorPosition(mainMenu.getPlayButton().getBounds().x, mainMenu.getPlayButton().getBounds().y);
            buttonPointedByCursor = PLAY;
            }
        else if(key == KeyEvent.VK_ENTER) {
            mainMenu.resetButtons();
            view.changeGameState(mainMenu.getOptionButton().getPointedState());
        }
    }


    //metodo che dice cosa fare quando puntiamo il tasto resume
    private void keyboardInputsResumeButton(int key) {
        if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            view.setCursorPosition(mainMenu.getQuitButton().getBounds().x, mainMenu.getQuitButton().getBounds().y);
            buttonPointedByCursor = QUIT;
        }
        else if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            view.setCursorPosition(mainMenu.getOptionButton().getBounds().x, mainMenu.getOptionButton().getBounds().y);
            buttonPointedByCursor = OPTION;
        }
        else if(key == KeyEvent.VK_ENTER) {
            mainMenu.resetButtons();
            view.changeGameState(mainMenu.getResumeButton().getPointedState());
        } 
    }

    //metodo che dice cosa fare quando puntiamo il tasto quit
    private void keyboardInputsQuitButton(int key) {
        if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            view.setCursorPosition(mainMenu.getResumeButton().getBounds().x, mainMenu.getResumeButton().getBounds().y);
            buttonPointedByCursor = RESUME;
            }
        else if(key == KeyEvent.VK_ENTER) {
            mainMenu.resetButtons();
            view.changeGameState(mainMenu.getQuitButton().getPointedState());   
        }
    }
    

    public void keyPressedDuringMainMenuState(int keyCode){

    } 

    
}