package src.controller.main;

import src.controller.IController;
import src.view.IView;
import src.model.IModel;
import src.view.gameBegin.SplashScreenGame;


public class Starter {
private static SplashScreenGame caricamento;
private static IView view;
private static IController controller;
private static IModel model;


	public static void main(String[] args) {
		initClasses();
		new GameLoop(view, controller);
	}
  
    private static void initClasses() {
		
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
