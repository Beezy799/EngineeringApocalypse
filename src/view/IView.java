package src.view;

import java.awt.Graphics2D;

import src.logic.ILogic;
import src.view.inputs.MouseInputs;
import src.view.main.GamePanel;
import src.view.main.GameWindow;

public class IView {

private ILogic logic;
private GameWindow gameWindow;
private GamePanel gamePanel;
private MouseInputs mouseInputs;

    public IView(ILogic log) {

        this.logic = log;
        mouseInputs = new MouseInputs(this);
        gamePanel = new GamePanel(this, mouseInputs);
        gameWindow = new GameWindow(gamePanel, this);
        gameWindow.setVisible(true);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
    }

    public void draw() {
    }

    public void prepareNewFrame(Graphics2D g2) {
    
    }
    
}
