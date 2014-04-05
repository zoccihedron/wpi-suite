package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.Component;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.NewGamePanel;

/**
 * This is a controller created for the purpose of interfacing with the main view.
 * There is only one instance of MainViewTabController.
 * @author Codon Bleu
 *
 */
public class MainViewTabController {
	

	private static MainViewTabController instance = null;
	private MainView mainView = null;

	
	private MainViewTabController() {
		
	}
	

	/**
	 * 
	 * @return reference to MainViewTabController
	 */
	public static MainViewTabController getInstance() {
		if (instance == null) {
			instance = new MainViewTabController();
		}
		return instance;
	}

	/**
	 * 
	 * @param mainView 
	 */
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
	
	/**
	 * Adds a new instance of a newGamePanel to tab bar of mainView
	 */
	public void createGameTab() {
		NewGamePanel newGamePanel = new NewGamePanel();
		mainView.addTab("New Game", null, newGamePanel, null);
		mainView.invalidate();
		mainView.repaint();
		mainView.setSelectedComponent(newGamePanel);
	}
	
	/**
	 * 
	 * @param tabToClose
	 */
	public void closeTab(Component tabToClose) {
		mainView.remove(tabToClose);
	}
}
