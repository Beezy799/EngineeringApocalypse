package src.view.gameBegin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JProgressBar;

// schermata di caricamento del gioco
public class SplashScreenGame extends JFrame implements Runnable {
	   
	private int frameWidth = 600;
	private int frameHeight = 400;
	private JProgressBar progressBar;
	private static final long serialVersionUID = 1L;
	
	public SplashScreenGame() {
		createProgressBar();
		
		Thread loadingThread = new Thread(this);
		loadingThread.start();
	}

	private void createProgressBar() {
		   progressBar = new JProgressBar();
		   progressBar.setBounds(frameWidth/2 - 400/2, frameHeight/2 + 50, 400,20);
	       progressBar.setBorderPainted(true);
	       progressBar.setStringPainted(true);
	       progressBar.setBackground(Color.black);
	       progressBar.setForeground(Color.red);
	       progressBar.setValue(0);
		
	}

	@Override
	public void run() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {
	   //Jlabel permette di unsere delle gif come sfondo
	   JLabel background = new JLabel();
	   background.setOpaque(true);
	   ImageIcon imageBackground = new ImageIcon(getClass().getResource("/res/loading/tech.gif"));
	   imageBackground.setImage(imageBackground.getImage().getScaledInstance(frameWidth, frameHeight, Image.SCALE_DEFAULT));
	   background.setIcon(imageBackground);	
	   background.setBounds(0,0,frameWidth,frameHeight);
		
	   JLabel LoadingLabel = new JLabel();
	   LoadingLabel.setOpaque(true);
	   LoadingLabel.setBackground(new Color(0,0,0,0));
	   ImageIcon titleImage = new ImageIcon(getClass().getResource("/res/loading/caricamento.png"));
	   titleImage.setImage(titleImage.getImage().getScaledInstance(300, 30, Image.SCALE_DEFAULT));
	   LoadingLabel.setIcon(titleImage);
	   LoadingLabel.setBounds(frameWidth/2 - titleImage.getIconWidth()/2, frameHeight/2, titleImage.getIconWidth(), titleImage.getIconHeight());	   
	   
       //è un jpanel con più strati, ci permette di sovrappore le due jlabels create sopra
	   JLayeredPane layeredPane = new JLayeredPane();
	   layeredPane.setBounds(0,0, frameWidth, frameHeight);
	   layeredPane.add(background, Integer.valueOf(1));
	   layeredPane.add(LoadingLabel, Integer.valueOf(2));
	   layeredPane.add(progressBar, Integer.valueOf(3));
	    
        add(layeredPane);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(frameWidth, frameHeight));
        setLayout(null);
        setLocationRelativeTo(null);
        requestFocus();
        setVisible(true);
      	
	}
	
	public void showProgress(int i) {
	    progressBar.setValue(i);
	}
}