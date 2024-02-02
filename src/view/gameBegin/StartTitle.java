package src.view.gameBegin;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.model.GameState;
import src.view.IView;
import src.view.ViewUtils;
import src.view.main.GamePanel;

// sarebbero le due scritte iniziali, "gngames" e "presenta"
public class StartTitle {
	
	private BufferedImage[] titles;
	private int timer = 120*10;
	private int firstTextTimer = 120*5;
	private int counter;
	private int text1X, text1Y, text2X, text2Y;
	private float alPhaValue; //per far apparire piano piano le scritte
	private IView view;
	
	public StartTitle(IView v) {
		view = v;
		titles = new BufferedImage[2];
		getImages();
		setImgDimensions();
		
	}

	private void setImgDimensions() {
		titles[0] = ViewUtils.scaleImage(titles[0], titles[0].getWidth()*GamePanel.SCALE, titles[0].getHeight()*GamePanel.SCALE);
		titles[1] = ViewUtils.scaleImage(titles[1], titles[1].getWidth()*GamePanel.SCALE, titles[0].getHeight()*GamePanel.SCALE/1.5f);
        
        //per posizionare le scritte
        text1X = ViewUtils.getCenteredXPos(titles[0].getWidth());
        text2X = ViewUtils.getCenteredXPos(titles[1].getWidth());

		text1Y = GamePanel.GAME_HEIGHT/2 - titles[0].getHeight()/2;
		text2Y = GamePanel.GAME_HEIGHT/2 - titles[1].getHeight()/2;
	}

	private void getImages() {
		try {
			titles[0] = ImageIO.read(getClass().getResourceAsStream("/res/startTitle/gngames.png"));
			titles[1] = ImageIO.read(getClass().getResourceAsStream("/res/startTitle/presenta.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void draw(Graphics2D g2) {
		counter++;

		if(counter > timer) {
			skipTitle();
			return;
		}
		
		if(counter < firstTextTimer) {
            // alpha = opacità
			alPhaValue = (float) counter/(firstTextTimer);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alPhaValue));
			g2.drawImage(titles[0], text1X, text1Y, null);
		}
		
		else if(counter >= firstTextTimer){
			alPhaValue = (float) (counter - firstTextTimer)/firstTextTimer;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alPhaValue));
			g2.drawImage(titles[1], text2X, text2Y, null);
		}	
	}
	
    //per evitare una import del model in una classe del view, usiamo questo metodo
	public void skipTitle() {
		view.changeGameStateToMainMenu();
	}
	
	

}

