package src.view;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.view.gameWindow.GamePanel;

// classe che contiene alcuni metodi comodi a un po' tutte le altre classi
//(scalare immagini,posizionare scritte al centro,lunghezza scritta...)
	
public class ViewUtils {

	public static BufferedImage scaleImage(BufferedImage original, float width, float height) {	
		int imageType = original.getType();
		if(imageType <= 0) 
			imageType = BufferedImage.TYPE_4BYTE_ABGR;
		BufferedImage scaledImage = new BufferedImage((int)width, (int)height, imageType);			//==0?5:original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, (int)width, (int)height, null);
		g2.dispose();	
		
		return scaledImage;	
	}
	

	// per posizionare le immagini al centro dello schermo
	public static int getCenteredXPos(int width){
		return GamePanel.GAME_WIDTH/2 - width/2;
	}
	
	// per posizionare le scringhe al centro
	public static int getXforCenterText(String text, Graphics2D g2) {
		int textLenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();	
		return  GamePanel.GAME_WIDTH/2 - textLenght/2;
	}
	
	public static int getStringLenght(String text, Graphics2D g2) {
		return (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
	}
	
	public static int getStringHeight(String text, Graphics2D g2) {
		return (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
	}
}
