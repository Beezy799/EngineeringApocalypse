package src.controller.main;

import src.controller.IController;
import src.view.IView;
import src.model.IModel;
import src.view.gameBegin.SplashScreenGame;

public class Starter {
private  SplashScreenGame caricamento;
private  IView view;
private  IController controller;
private IModel model;

public Starter() {
    //inizializizza le interfacce View, logic
    initClasses();
}

	public static void main(String[] args) {
        Starter s = new Starter();
		new GameLoop(s.view, s.controller, s.model);
	}
  
    private  void initClasses() {
		
		caricamento = new SplashScreenGame();	
		controller = new IController();
		caricamento.showProgress(30);
		
		model = new IModel(controller);
		controller.setModel(model);
		caricamento.showProgress(60);

		view = new IView(controller, model);
		caricamento.showProgress(80);
		controller.setView(view);
		model.setView(view);
        caricamento.setVisible(false);
		caricamento.dispose();
	}
}
