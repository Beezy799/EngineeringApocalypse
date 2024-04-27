package src.view.playStateView;

import java.awt.Graphics2D;

import src.view.IView;
import src.view.gameWindow.GamePanel;

public class CutsceneView {

    private IView view;
    private static final int PHASE_0 = 0, PHASE_1 = 1;
    private int currentPhase = PHASE_0;

    //la camera salirà dal quadratino 24,22 verso il prof, indipendentemente da dove si trova il player
    private int cameraX = 25* GamePanel.TILES_SIZE, cameraY = 21*GamePanel.TILES_SIZE;

    public CutsceneView(IView v) {
        view = v;
    }

    public void drawCutscene(Graphics2D g2){
        switch(currentPhase) {
            //muovi la telecamera verso l'alto
            case PHASE_0:
                cameraY--;
                view.getPlayStateView().drawCutSceneBackground(g2, cameraX, cameraY);

                if(cameraY <= 15*GamePanel.TILES_SIZE)
                    currentPhase = PHASE_1;
                break;

            //la telecamera si ferma sul boss, che parla
            case PHASE_1:
                view.getPlayStateView().drawCutSceneBackground(g2, cameraX, cameraY);
                view.getPlayStateView().getPlayUI().drawDialogue(g2);
                break;
        }
    }

    public void reset(){
        cameraX = 25* GamePanel.TILES_SIZE;
        cameraY = 21*GamePanel.TILES_SIZE;
        currentPhase = PHASE_0;
    }
}
