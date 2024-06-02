package src.view;

import src.model.GameState;
import src.view.gameWindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.view.gameWindow.GamePanel.GAME_HEIGHT;
import static src.view.gameWindow.GamePanel.SCALE;

public class TitoliDiCoda {

    private IView view;
    private String ringraziamenti = "GNgame ringrazia per la collaborazione:";
    private String votoSperato = "30";
    private BufferedImage title;
    private int titleX, titleY = GAME_HEIGHT/4;
    private String Francesco = "Francesco: sound designer";
    private String Bea = "Bea: artistic sprite editing";
    private String Lorenzo = "Lorenzo: creative feedback";
    private String Salvatore = "Salvatore: code reviewer";
    private String Armando = "Armando: mental health helper";
    private String Umberto = "Umberto: game designer";
    private String betaTesters = "BETA TESTERS: \n \n Flavio, Amin, Aiman, Matteo \n Filippo, Edoardo, Valentino, Valeria" +
                                    " \n Alice, Elena, Alessia";
    private Font fontTitolo, votoFont, nomiFont;
    private int counter, durataScritta = 400;

    public TitoliDiCoda(IView v){
        view = v;
        nomiFont = new Font("Arial", Font.PLAIN, (int)(25* GamePanel.SCALE));
        fontTitolo = new Font("Arial", Font.PLAIN, (int)(20* GamePanel.SCALE));
        votoFont = new Font("Arial", Font.PLAIN, (int)(300*GamePanel.SCALE));
        loadTitle();
        titleX = ViewUtils.getCenteredXPos(title.getWidth());
    }

    public void draw(Graphics2D g2){
        drawBackGround(g2);
        drawTitle(g2);

        g2.setColor(Color.red);

        g2.setFont(nomiFont);
        counter++;

        drawTizio(g2, Francesco, 200);
        drawTizio(g2, Bea, 200 + 2*durataScritta);
        drawTizio(g2, Lorenzo, 200 + 4*durataScritta);
        drawTizio(g2,Salvatore, 200 +6*durataScritta);
        drawTizio(g2,Armando, 200 +8*durataScritta);
        drawTizio(g2,Umberto, 200 +10*durataScritta);
        drawBetaTesters(g2, 200+12*durataScritta);
        drawTizio(g2, "Grazie ♥", 200 + 14*durataScritta);

        if(counter >= 200 + 15*durataScritta){
            GameState.actualState = GameState.QUIT;
        }

    }

    private void drawBetaTesters(Graphics2D g2, int quandoIniziare) {

        if(counter < quandoIniziare || counter > quandoIniziare + 2*durataScritta)
            return;

        if(counter > quandoIniziare && counter <= (quandoIniziare+durataScritta)) {
            float alpha = (float) (counter - quandoIniziare) / (durataScritta);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }

        else if(quandoIniziare+durataScritta < counter && counter <= quandoIniziare+2*durataScritta) {
            float alpha = 1f - ((float) (counter) - (quandoIniziare + durataScritta)) / (durataScritta);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }

        String[] lines = betaTesters.split("\n ");
        for (int i = 0; i < lines.length; i++) {
            g2.drawString(lines[i], ViewUtils.getXforCenterText(lines[i], g2), GAME_HEIGHT/2 + (int)(40* SCALE) + i*40);
        }

    }

    public void drawTizio(Graphics2D g2, String nome, int quandoIniziare){

        if(counter > quandoIniziare+2*durataScritta)
            return;

        if(counter > quandoIniziare && counter <= (quandoIniziare+durataScritta)){
            float alpha = (float)(counter - quandoIniziare) / (durataScritta) ;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            int x = ViewUtils.getXforCenterText(nome,g2);
            g2.drawString(nome, x, GAME_HEIGHT/2 + (int)(5*SCALE));
        }

        else if(quandoIniziare+durataScritta < counter && counter <= quandoIniziare+2*durataScritta){
            float alpha = 1f - ((float)(counter) - (quandoIniziare+durataScritta))/ (durataScritta);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            int x = ViewUtils.getXforCenterText(nome,g2);
            g2.drawString(nome, x, GAME_HEIGHT/2 + (int)(5*SCALE));
        }
    }

    private void drawBackGround(Graphics2D g2) {
        view.getMainMenu().drawBackground(g2);
        g2.setColor(Color.red);

        view.getMainMenu().drawCredits(g2);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.03f));
        g2.setFont(votoFont);
        int xVoto = ViewUtils.getXforCenterText(votoSperato, g2);
        int yVoto = GamePanel.GAME_HEIGHT/2 + ViewUtils.getStringHeight(votoSperato, g2)/4;
        g2.drawString(votoSperato, xVoto, yVoto);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void drawTitle(Graphics2D g2) {

        g2.drawImage(title, titleX, titleY, null);

        g2.setColor(Color.CYAN);
        g2.setFont(fontTitolo);
        int titolox = ViewUtils.getXforCenterText(ringraziamenti, g2);
        int tiyoloy = titleY + title.getHeight() + (int)(18*SCALE);
        g2.drawString(ringraziamenti, titolox, tiyoloy);
    }

    public void loadTitle(){
        try {
            title = ImageIO.read(getClass().getResourceAsStream("/res/mainMenu/logo.png"));
            title = ViewUtils.scaleImage(title, title.getWidth() * 0.8f * SCALE, title.getHeight() * SCALE * 0.8f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
