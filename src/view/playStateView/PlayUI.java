package src.view.playStateView;

import src.view.main.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import src.view.ViewUtils;

// classe che mostra a video le informazioni come il punteggio, la vita, le munizioni...
public class PlayUI {

    //per i messaggi tipo "hai preso appnti" etc
    private Font fontDisplay = new Font("Arial", Font.PLAIN, (int)(20*GamePanel.SCALE));
    private String message = "";

    private BufferedImage noteIcon, cfuIcon;
    private BufferedImage[] lifeIcons;

    //per disegnare il numero corrispondente alla vita, alle munizioni...
    private String dataToShow = "";
    private int yPosDataUI = (int)(5*GamePanel.SCALE);
    private int xPosDataUI = (int)(20*GamePanel.SCALE);

    private PlayStateView play;
    private int counterMessage = 0;
    private boolean showMessage;


    public PlayUI(PlayStateView p) {
        play = p;
        loadImages();
    }

    private void loadImages() {
        try {
            cfuIcon = ImageIO.read(getClass().getResourceAsStream("/res/ui/punteggioPiccolo.png"));
            cfuIcon = ViewUtils.scaleImage(cfuIcon, cfuIcon.getWidth()*GamePanel.SCALE, cfuIcon.getHeight()*GamePanel.SCALE);

            lifeIcons = new BufferedImage[4];
            BufferedImage temp = ImageIO.read(getClass().getResourceAsStream("/res/ui/vitaPiccola.png"));

            for(int i = 0; i < 4; i++)
                lifeIcons[i] = temp.getSubimage(45*i, 0, 45, 32);

            for(int i = 0; i < 4; i++) {
                lifeIcons[i] = ViewUtils.scaleImage(lifeIcons[i], lifeIcons[i].getWidth()*GamePanel.SCALE, lifeIcons[i].getHeight()*GamePanel.SCALE);
            }

            noteIcon = ImageIO.read(getClass().getResourceAsStream("/res/ui/appuntiPiccoli.png"));
            noteIcon = ViewUtils.scaleImage(noteIcon, noteIcon.getWidth()*GamePanel.SCALE, noteIcon.getHeight()*GamePanel.SCALE);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        drawPlayerData(g2);
        drawMessage(g2);

    }

    private void drawPlayerData(Graphics2D g2) {
        drawBackGround(g2);
        drawLife(g2, dataToShow);
        drawNotes(g2, dataToShow);
        drawPoints(g2, dataToShow);
        //resetta il valore della x
        xPosDataUI = (int)(20*GamePanel.SCALE);
    }

    private void drawBackGround(Graphics2D g2) {
        g2.getColor().getAlpha();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2.setColor(Color.yellow);
        g2.fillRoundRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT/12, 40, 40);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.setColor(Color.black);
    }

    private void drawLife(Graphics2D g2, String s) {
        int life = play.getView().getController().getPlayerController().getLife();
        s = life + " %";

        if(life > 75)
            g2.drawImage(lifeIcons[0], xPosDataUI, yPosDataUI, null);
        else if(life <=75 && life > 50)
            g2.drawImage(lifeIcons[1], xPosDataUI, yPosDataUI, null);
        else if(life <=50 && life > 25)
            g2.drawImage(lifeIcons[2], xPosDataUI, yPosDataUI, null);
        else if(life <= 25)
            g2.drawImage(lifeIcons[3], xPosDataUI, yPosDataUI, null);

        g2.setFont(fontDisplay);
        xPosDataUI += lifeIcons[0].getWidth() + (int)(10*GamePanel.SCALE);
        g2.drawString(s, xPosDataUI, yPosDataUI + (int)(25*GamePanel.SCALE));

    }

    private void drawPoints(Graphics2D g2, String s) {
        int cfu = play.getView().getController().getPlayerController().getCfu();
        s = "" + cfu;

        xPosDataUI = GamePanel.GAME_WIDTH - cfuIcon.getWidth() - (int)(60*GamePanel.SCALE);
        g2.drawImage(cfuIcon, xPosDataUI, yPosDataUI, null);

        xPosDataUI += cfuIcon.getWidth() + (int)(10*GamePanel.SCALE);
        g2.drawString(s, xPosDataUI, yPosDataUI + (int)(25*GamePanel.SCALE));
    }

    private void drawNotes(Graphics2D g2, String s) {
        int notes = play.getView().getController().getPlayerController().getNotes();
        s = "" + notes;

        xPosDataUI = ViewUtils.getCenteredXPos(noteIcon.getWidth());
        g2.drawImage(noteIcon, xPosDataUI, yPosDataUI, null);

        xPosDataUI += noteIcon.getWidth() + (int)(10*GamePanel.SCALE);
        g2.drawString(s, xPosDataUI, yPosDataUI + (int)(25*GamePanel.SCALE));

    }

    private void drawMessage(Graphics2D g2) {
        if(showMessage) {

            counterMessage++;
            if(counterMessage < 240) {

                g2.setFont(fontDisplay);

                //per disegnare il messaggio al centro dello schermo, circa
                int x = ViewUtils.getXforCenterText(message, g2);
                int y = GamePanel.GAME_HEIGHT/2 + (int)(30*GamePanel.SCALE);
                int width = ViewUtils.getStringLenght(message, g2);
                int height = ViewUtils.getStringHeight(message, g2);

                //per il rettangolo giallo dietro la scritta
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                g2.setColor(Color.yellow);
                g2.fillRoundRect(x, y, width + (int)(3*GamePanel.SCALE), height + (int)(4*GamePanel.SCALE), 30, 30);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                //disegna la scritta
                g2.setColor(Color.red);
                g2.drawString(message, x, y + height);
                g2.setColor(Color.black);
            }

            else {
                counterMessage = 0;
                showMessage = false;
            }
        }

    }

    public void setMessageToShow(String s) {
        message = s;
        setThereIsAMessageToShow(true);
    }

    public void setThereIsAMessageToShow(boolean thereIsAMessageToShow) {
        this.showMessage = thereIsAMessageToShow;
    }

}
