/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Code On Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.Component;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.PlayGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.UserPreferencesPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGamePanel;



/**
 * This is a controller created for the purpose of interfacing with the main view.
 * There is only one instance of MainViewTabController.
 * @author Codon Bleu
 * @version 1.0
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
		final NewGamePanel newGamePanel = new NewGamePanel();
		mainView.insertTab("New Game", newGamePanel, mainView.getTabCount());
		mainView.invalidate();
		mainView.repaint();
		mainView.setSelectedComponent(newGamePanel);
	}
	
	/**
	 * Creates a createGameTab given a game
	 * @param game the info with which to populate the tab
	 */
	public void createGameTab(Game game, boolean isInProgress) {
		final NewGamePanel newGamePanel = new NewGamePanel(game, isInProgress);
		mainView.insertTab(game.getName(), newGamePanel, mainView.getTabCount());
		mainView.invalidate();
		mainView.repaint();
		mainView.setSelectedComponent(newGamePanel);
	}
	
	/**
	 * Creates a playGameTab given a game
	 * @param game the game to be played
	 */
	public void playGameTab(Game game)
	{
		final PlayGamePanel playGamePanel = new PlayGamePanel(game);
		mainView.insertTab(game.getName(), playGamePanel, mainView.getTabCount());
		mainView.invalidate();
		mainView.repaint();
		mainView.setSelectedComponent(playGamePanel);
	}
	
	/**
	 * Creates a user preferences tab, but only if
	 * there are no other preference tabs open.
	 */
	public void userPreferencesTab() {
		for(int i = 0; i < mainView.getTabCount(); i++){
			if(mainView.getComponentAt(i).getClass() == UserPreferencesPanel.class){
				mainView.setSelectedComponent(mainView.getComponentAt(i));
				return;
			}
		}
		final UserPreferencesPanel userPrefPanel = new UserPreferencesPanel();
		mainView.insertTab("Preferences", userPrefPanel, mainView.getTabCount());
		mainView.invalidate();
		mainView.repaint();
		mainView.setSelectedComponent(userPrefPanel);
	}
	
	/**
	 * Closes a given tab
	 * @param tabToClose the tab to close
	 */
	public void closeTab(Component tabToClose) {
		if(tabToClose instanceof NewGamePanel) {
			if(((NewGamePanel)tabToClose).isReadyToClose()) {
				mainView.remove(tabToClose);
			}
		} else if(tabToClose instanceof PlayGamePanel) {
			if(((PlayGamePanel)tabToClose).isReadyToClose()) {
				mainView.remove(tabToClose);
			}
		} else if(tabToClose instanceof UserPreferencesPanel) {
			if(((UserPreferencesPanel)tabToClose).isReadyToClose()) {
				mainView.remove(tabToClose);
			}
		}
	}
}
