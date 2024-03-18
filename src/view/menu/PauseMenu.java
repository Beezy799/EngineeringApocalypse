package src.view.menu;

import src.model.GameState;
import src.view.IView;
import src.view.ViewUtils;
import src.view.main.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import static src.view.main.GamePanel.*;


//Se durante il gioco il player preme esc, viene mostrato a schermo il menù di pausa
public class PauseMenu extends AbstractMenu {

    private String homeIconPath = "/res/pauseMenu/bottoneHome.png";
    private String resumeIconPath = "/res/pauseMenu/bottoneRiprendi.png";
    private String titlePausaIconPath =  "/res/pauseMenu/pausa.png";

    private BufferedImage titleImg;
    private BufferedImage musicVolumImg, soundEffectImg;

    private SoundBar musicBar, effectsBar;
    private PauseMenuButton home, resume;

    private int titleHeight = GAME_HEIGHT/4;
    private int musicHeight, seHeight;
    private int centeredXTitle, centeredXmusic, centeredXse;
    public final int maxBarWidth = GAME_WIDTH/4, barHeight = (int)(10*SCALE);
    private int soundbarsX = GAME_WIDTH/2 - maxBarWidth/2;

    private IView view;

    public PauseMenu(IView v) {
        view = v;
        loadImages();
        centeredXTitle = ViewUtils.getCenteredXPos(titleImg.getWidth());
        createSoundBars(v);
        createHomeButtons(v);

        buttons = new AbstractMenuButton[4];
        buttons[0] = musicBar;
        buttons[1] = effectsBar;
        buttons[2] = home;
        buttons[3] = resume;
    }

    private void createHomeButtons(IView v) {
        int homeY = effectsBar.getBounds().y + effectsBar.getBounds().height + (int)(20* GamePanel.SCALE);
        home = new PauseMenuButton(v, homeIconPath, soundbarsX + (int)(30*GamePanel.SCALE), homeY, GameState.TRANSITION_STATE, true);

        int resumeY = effectsBar.getBounds().y + effectsBar.getBounds().height + (int)(20*GamePanel.SCALE);
        resume = new PauseMenuButton(v, resumeIconPath, soundbarsX + (int)(90*GamePanel.SCALE) , resumeY, GameState.PLAYING, false);

    }

    private void createSoundBars(IView v) {
        musicHeight = titleHeight + titleImg.getHeight() + (int)(20*GamePanel.SCALE);
        centeredXmusic = ViewUtils.getCenteredXPos(musicVolumImg.getWidth());

        Rectangle r1 = new Rectangle(soundbarsX, musicHeight + musicVolumImg.getHeight() + (int)(20*GamePanel.SCALE), maxBarWidth, barHeight);
        Rectangle r2 = new Rectangle(soundbarsX, musicHeight + musicVolumImg.getHeight() + (int)(20*GamePanel.SCALE), maxBarWidth, barHeight);
        musicBar = new SoundBar(r1, r2, view, true);

        seHeight = r1.y + r1.height + (int)(20*GamePanel.SCALE);
        centeredXse = ViewUtils.getCenteredXPos(soundEffectImg.getWidth());

        Rectangle r3 = new Rectangle(soundbarsX, seHeight + soundEffectImg.getHeight() + (int)(20*GamePanel.SCALE), maxBarWidth, barHeight);
        Rectangle r4 = new Rectangle(soundbarsX, seHeight + soundEffectImg.getHeight() + (int)(20*GamePanel.SCALE), maxBarWidth, barHeight);
        effectsBar = new SoundBar(r3, r4, view, false);

    }

    private void loadImages() {
        try {
            titleImg = ImageIO.read(getClass().getResourceAsStream(titlePausaIconPath));
            int widht = titleImg.getWidth();
            int height = titleImg.getHeight();
            titleImg = ViewUtils.scaleImage(titleImg, widht*GamePanel.SCALE/2, height*GamePanel.SCALE/2);

            musicVolumImg = ImageIO.read(getClass().getResourceAsStream("/res/opzioni/volumemusica.png"));
            musicVolumImg = ViewUtils.scaleImage(musicVolumImg, musicVolumImg.getWidth()/4 * SCALE, musicVolumImg.getHeight()/4 * SCALE);

            soundEffectImg = ImageIO.read(getClass().getResourceAsStream("/res/opzioni/volumeeffetti.png"));
            soundEffectImg = ViewUtils.scaleImage(soundEffectImg, soundEffectImg.getWidth()/4 * SCALE, soundEffectImg.getHeight()/4 * SCALE);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        drawOpaqueRect(g2);
        drawText(g2);
        drawButtons(g2);
    }


    private void drawText(Graphics2D g2) {
        g2.drawImage(titleImg, centeredXTitle, titleHeight, null);
        g2.drawImage(musicVolumImg, centeredXmusic, musicHeight, null);
        g2.drawImage(soundEffectImg, centeredXse, seHeight, null);

    }

    //quando abbaimo la pausa , sotto abbiamo il gioco, sopra un quadrato nero , un po' trasparente in modo che si
    //possa vedere quello che c'è sotto
    private void drawOpaqueRect(Graphics2D g2) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void mouseDraggedInPause(MouseEvent e) {
    }
}
