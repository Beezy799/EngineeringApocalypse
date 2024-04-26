package src.view.playStateView;

import src.model.Rooms;
import src.view.ViewUtils;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenOverlay {
    //buio, attacchi prof, altro che non saprei

    private BufferedImage buio;
    private boolean isDark = true, figthBoss = true, bossSpecialAttack;
    private int initialBossLifeRectWidth = GamePanel.GAME_WIDTH/2;
    private int completeBossLife = 400;
    private String bossName = "Prof Luke Crickets";
    private Font bossNameFont = new Font("Dialog", Font.ITALIC, (int)(10*GamePanel.SCALE));


    public ScreenOverlay(PlayStateView p){
        try {
            buio = ImageIO.read(getClass().getResourceAsStream("/res/ui/effettoBuioFinale.png"));
            buio = ViewUtils.scaleImage(buio, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        }
        catch (Exception e){

        }
    }

    public void draw(Graphics2D g2){
        if(Rooms.actualRoom == Rooms.DORMITORIO && isDark) {
            g2.drawImage(buio, 0, 0, null);
        }
        if(bossSpecialAttack){
            g2.setColor(Color.black);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g2.fillRect(0,0, GamePanel.GAME_WIDTH,GamePanel.GAME_HEIGHT);
        }

        if(Rooms.actualRoom == Rooms.STUDIO_PROF && figthBoss){
            drawBossLife(g2);
        }
    }

    private void drawBossLife(Graphics2D g2) {
        g2.setColor(Color.red);
        int bossLife = Rooms.actualRoom.getEnemy().get(0).getEnemyController().getLife();
        int lifeRectWidth = bossLife*initialBossLifeRectWidth/completeBossLife;

        g2.fillRect(GamePanel.CENTER_X_GAME_PANEL - initialBossLifeRectWidth/2, (int)(GamePanel.GAME_HEIGHT*0.9),
                        lifeRectWidth, (int)(4*GamePanel.SCALE));

        g2.setColor(Color.black);
        g2.drawRect(GamePanel.CENTER_X_GAME_PANEL - initialBossLifeRectWidth/2, (int)(GamePanel.GAME_HEIGHT*0.9),
                initialBossLifeRectWidth, (int)(4*GamePanel.SCALE));

        g2.setFont(bossNameFont);
        int yBossNameString = (int)(GamePanel.GAME_HEIGHT*0.9) - ViewUtils.getStringHeight(bossName, g2);
        g2.drawString(bossName, GamePanel.CENTER_X_GAME_PANEL - initialBossLifeRectWidth/2, yBossNameString);

    }

    public void setDark(boolean b){
        isDark = b;
    }

    public void setBossSpecialAttack(boolean b){
        bossSpecialAttack = b;
    }
}
