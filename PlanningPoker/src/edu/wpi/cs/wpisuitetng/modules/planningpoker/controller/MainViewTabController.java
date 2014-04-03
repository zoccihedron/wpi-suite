package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.NewGamePanel;


public class MainViewTabController {
	
	private static MainViewTabController instance = null;
	private MainView mainView = null;

	private MainViewTabController() {
		
	}
	
	public static MainViewTabController getInstance() {
		if (instance == null) {
			instance = new MainViewTabController();
		}
		return instance;
	}
	
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
	
	public void createGameTab() {
		NewGamePanel newGamePanel = new NewGamePanel();
		mainView.addTab("New Game", null, newGamePanel, null);
		mainView.invalidate();
		mainView.repaint();
		mainView.setSelectedComponent(newGamePanel);
	}
}
