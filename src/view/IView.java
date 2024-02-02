package src.view;

import src.logic.ILogic;

public class IView {

private ILogic logic;
private GameWindow gameWindow;

    public IView(ILogic log) {
        this.logic = log;
        gameWindow = new GameWindow();
    }

    public void draw() {
    }
    
}
