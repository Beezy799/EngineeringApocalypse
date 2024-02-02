package src.logic.main;

import src.logic.ILogic;
import src.view.IView;
import src.view.gameBegin.SplashScreenGame;

public class Starter {
	
private  SplashScreenGame caricamento;
private  IView view;
private  ILogic logic;

public Starter() {
    //inizializizza le interfacce View, logic
    initClasses();
}

	public static void main(String[] args) {
        Starter s = new Starter();
		new GameLoop(s.view, s.logic);
	}
  
    private  void initClasses() {
		
		caricamento = new SplashScreenGame();	
		logic = new ILogic();
		caricamento.showProgress(30);
		
		view = new IView(logic);
		caricamento.showProgress(99);
		logic.setView(view);
        System.out.println("fine caricamento");
        caricamento.setVisible(false);
		caricamento.dispose();
	}
}
