package src.view.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import src.view.IView;
import src.view.ViewUtils;
import src.view.inputs.KeyboardInputs;
import src.view.inputs.MouseInputs;

//questa classe definisce le dimensioni del gioco. Non sulla finestra per evitare i problemi con la barra della finestra
//(chiudi,riduci,allarga)  

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static int TILES_IN_WIDTH = 20;
	public final static int TILES_IN_HEIGHT = 15;
	
	//viene tutto scalato in modo da far entrare la finestra di gioco anche in schermi più piccoli
	static float computerScreenHeight = (float) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public final static float SCALE = computerScreenHeight/576;	
	
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	private IView view;

	public GamePanel(IView view, MouseInputs mi) {
		this.view = view;
		this.setDoubleBuffered(true);   	//improve rendering performance
		setPanelSize();
		addMouseListener(mi);
		addMouseMotionListener(mi);
		addKeyListener(new KeyboardInputs(view));
        setGameCursor(this);

	}

	private void setPanelSize() {
		setBackground(Color.black);
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setSize(size);
		setPreferredSize(size);
	}
	
	//iview usa l'ambiente grafico del pannello, g, per disegnare il frame successivo, poi g si chiude per risparmiare risorse
	public void paintComponent(Graphics g) {	
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);			//migliora le prestazioni
		view.prepareNewFrame(g2);
		g2.dispose();
	}
	
    //per avere quella adorabile freccetta blu
    private void setGameCursor(GamePanel gp) {	
        BufferedImage mouseIcon = null;
        try {
            mouseIcon = ImageIO.read(getClass().getResourceAsStream("/res/cursor.png"));
            mouseIcon = ViewUtils.scaleImage(mouseIcon, GamePanel.SCALE*15, GamePanel.SCALE*20);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(mouseIcon,new Point(gp.getX(),gp.getY()),"custom cursor"));
        
    } 
}
