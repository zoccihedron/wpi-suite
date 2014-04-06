package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.Component;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.NewGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.PlayGamePanel;


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
		mainView.insertTab("New Game", newGamePanel, mainView.getTabCount());
		mainView.invalidate();
		mainView.repaint();
		mainView.setSelectedComponent(newGamePanel);
	}
	
	
	public void createGameTab(Game game) {
		NewGamePanel newGamePanel = new NewGamePanel(game);
		mainView.insertTab(game.getName(), newGamePanel, mainView.getTabCount());
		mainView.invalidate();
		mainView.repaint();
		mainView.setSelectedComponent(newGamePanel);
	}
	
	
	public void playGameTab(Game game)
	{
		PlayGamePanel playGamePanel = new PlayGamePanel(game);
		mainView.insertTab(game.getName(), playGamePanel, mainView.getTabCount());
		mainView.invalidate();
		mainView.repaint();
		mainView.setSelectedComponent(playGamePanel);
	}
	
	public void closeTab(Component tabToClose) {
		mainView.remove(tabToClose);
	}
	
	public void closeTab(int index){
		mainView.remove(index);
	}
}
