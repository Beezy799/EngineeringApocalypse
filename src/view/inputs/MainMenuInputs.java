package src.view.inputs;

import src.view.menu.MainMenu;

//dice al gioco cosa deve fare dopo l'input (tastira) durante il menu
public class MainMenuInputs {

    private MainMenu mainMenu;

    public MainMenuInputs(MainMenu m){
        this.mainMenu = m; 
    }

    public void keyReleasedDuringMainMenuState(int keyCode) {
 
    
        
        
    }






    public void keyPressedDuringMainMenuState(int keyCode){
        System.out.println("hai premuto " + keyCode + "nel main menu");
    }
}
