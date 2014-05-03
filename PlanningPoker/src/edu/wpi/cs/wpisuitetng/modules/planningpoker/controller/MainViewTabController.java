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
import java.awt.Dimension;

import javax.swing.JFrame;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.help.HelpPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.OverviewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.PlayGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.UserPreferencesPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewResultsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckManagerPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGamePanel;



/**
 * This is a controller created for the purpose of interfacing with the main view.
 * There is only one instance of MainViewTabController.
 * @author Team Code On Bleu
 * @version 1.0
 */
public class MainViewTabController {


	private static MainViewTabController instance = null;
	private static OverviewPanel overviewPanel;
	private MainView mainView = null;
	private JFrame helpWindow = null;


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
	 * @param game The game that is being modified
	 * @param isInProgress check if game has been started
	 */
	public void createGameTab(Game game, boolean isInProgress) {
		boolean makePanel = true;
		for(int i = 0; i < mainView.getTabCount(); i++){
			if(mainView.getComponentAt(i).getClass() == NewGamePanel.class){
				if(((NewGamePanel)mainView.getComponentAt(i)).getGame().getId()== game.getId()){
					mainView.setSelectedComponent(mainView.getComponentAt(i));
					makePanel = false;
				}
			}
			else if(mainView.getComponentAt(i).getClass() == PlayGamePanel.class){
				if(((PlayGamePanel)mainView.getComponentAt(i)).getGame().getId()== game.getId()){
					mainView.setSelectedComponent(mainView.getComponentAt(i));
					makePanel = instance.closeTab(mainView.getComponentAt(i));
				}
			}
			else if(mainView.getComponentAt(i).getClass() == ViewResultsPanel.class){
				if(((ViewResultsPanel)mainView.getComponentAt(i)).getGame().getId()== game.getId()){
					mainView.setSelectedComponent(mainView.getComponentAt(i));
					makePanel = instance.closeTab(mainView.getComponentAt(i));
				}
			}
			
		}
		if(makePanel){
			final NewGamePanel newGamePanel = new NewGamePanel(game, isInProgress);
			mainView.insertTab(game.getName(), newGamePanel, mainView.getTabCount());
			mainView.invalidate();
			mainView.repaint();
			mainView.setSelectedComponent(newGamePanel);
		}
	}

	/**
	 * Creates a playGameTab given a game
	 * @param game the game to be played
	 */
	public void playGameTab(Game game)
	{
		boolean makePanel = true;
		for(int i = 0; i < mainView.getTabCount(); i++){
			if(mainView.getComponentAt(i).getClass() == NewGamePanel.class){
				if(((NewGamePanel)mainView.getComponentAt(i)).getGame().getId()== game.getId()){
					mainView.setSelectedComponent(mainView.getComponentAt(i));
					makePanel = instance.closeTab(mainView.getComponentAt(i));
				}
			}
			else if(mainView.getComponentAt(i).getClass() == PlayGamePanel.class){
				if(((PlayGamePanel)mainView.getComponentAt(i)).getGame().getId()== game.getId()){
					mainView.setSelectedComponent(mainView.getComponentAt(i));
					makePanel = false;
				}
			}
			else if(mainView.getComponentAt(i).getClass() == ViewResultsPanel.class){
				if(((ViewResultsPanel)mainView.getComponentAt(i)).getGame().getId()== game.getId()){
					mainView.setSelectedComponent(mainView.getComponentAt(i));
					makePanel = instance.closeTab(mainView.getComponentAt(i));
				}
			}
			
		}
		if(makePanel){
			final PlayGamePanel playGamePanel = new PlayGamePanel(game);
			mainView.insertTab(game.getName(), playGamePanel, mainView.getTabCount());
			mainView.invalidate();
			mainView.repaint();
			mainView.setSelectedComponent(playGamePanel);
		}
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
	 * Creates a view results tab given a game
	 * @param game the game to be viewed
	 */
	public void viewResultsTab(Game game) {
		boolean makePanel = true;
		for(int i = 0; i < mainView.getTabCount(); i++){
			if(mainView.getComponentAt(i).getClass() == NewGamePanel.class){
				if(((NewGamePanel)mainView.getComponentAt(i)).getGame().getId()== game.getId()){
					mainView.setSelectedComponent(mainView.getComponentAt(i));
					makePanel = instance.closeTab(mainView.getComponentAt(i));
				}
			}
			else if(mainView.getComponentAt(i).getClass() == PlayGamePanel.class){
				if(((PlayGamePanel)mainView.getComponentAt(i)).getGame().getId()== game.getId()){
					mainView.setSelectedComponent(mainView.getComponentAt(i));
					makePanel = instance.closeTab(mainView.getComponentAt(i));
				}
			}
			else if(mainView.getComponentAt(i).getClass() == ViewResultsPanel.class){
				if(((ViewResultsPanel)mainView.getComponentAt(i)).getGame().getId()== game.getId()){
					mainView.setSelectedComponent(mainView.getComponentAt(i));
					makePanel = false;
				}
			}
			
		}
		if(makePanel){
			final ViewResultsPanel resultsPanel = new ViewResultsPanel(game);
			mainView.insertTab(game.getName(), resultsPanel, mainView.getTabCount());
			mainView.invalidate();
			mainView.repaint();
			mainView.setSelectedComponent(resultsPanel);
		}
	}

	/**
	 * Creates a help tab, but only if
	 * there are no other help tabs open.
	 */
	public void helpTab() {
		if(helpWindow == null){
			helpWindow = new JFrame("Planning Poker Help");
			helpWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			HelpPanel helpPanel = new HelpPanel();
			helpPanel.setMinimumSize(new Dimension(800, 500));
			helpWindow.setContentPane(helpPanel);
			helpWindow.pack();   
			helpWindow.setLocationByPlatform(true);
			helpWindow.setVisible(true);
			helpWindow.setMinimumSize(new Dimension(800, 500));
		}
		else{
			helpWindow.setExtendedState(JFrame.NORMAL);
			helpWindow.setVisible(true);
		}
	}

	/**
	 * Creates an instance of the deck manager tab. There can
	 * only be one open at a time. If the button is pressed
	 * a second time, it simply switches back to that tab.
	 */
	public void DeckManagerTab() {
		for (int i = 0; i < mainView.getTabCount(); i++) {
			if (mainView.getComponentAt(i).getClass() == DeckManagerPanel.class) {
				mainView.setSelectedComponent(mainView.getComponentAt(i));
				return;
			}
		}
		final DeckManagerPanel deckManagerPanel = new DeckManagerPanel();
		mainView.insertTab("Deck Manager", deckManagerPanel, mainView.getTabCount());
		mainView.invalidate();
		mainView.repaint();
		mainView.setSelectedComponent(deckManagerPanel);
	}

	/**
	 * Closes a given tab
	 * @param tabToClose the tab to close
	 * @return Boolean if the tab was closed
	 */
	public Boolean closeTab(Component tabToClose) {
		Boolean tabClosed = false;
		if(tabToClose instanceof NewGamePanel) {
			if(((NewGamePanel)tabToClose).isReadyToClose()) {
				mainView.remove(tabToClose);
				tabClosed = true;
			}
		} else if(tabToClose instanceof PlayGamePanel) {
			if(((PlayGamePanel)tabToClose).isReadyToClose()) {
				mainView.remove(tabToClose);
				tabClosed = true;
			}
		} else if(tabToClose instanceof UserPreferencesPanel) {
			if(((UserPreferencesPanel)tabToClose).isReadyToClose()) {
				mainView.remove(tabToClose);
				tabClosed = true;
			}
		} else if(tabToClose instanceof ViewResultsPanel){
			if(((ViewResultsPanel)tabToClose).isReadyToClose()) {
				mainView.remove(tabToClose);
				tabClosed = true;
			}
		} else if (tabToClose instanceof DeckManagerPanel) {
			if(((DeckManagerPanel) tabToClose).isReadyToClose()){
				mainView.remove(tabToClose);
				tabClosed = true;
			}
		} else if(tabToClose instanceof HelpPanel){
			if(((HelpPanel)tabToClose).isReadyToClose()) {
				mainView.remove(tabToClose);
				tabClosed = true;
			}
		}
		mainView.setSelectedComponent(overviewPanel);
		return tabClosed;

	}


	/**
	 * @param overview the overview panel in mainview
	 */
	public static void setOverviewPanel(OverviewPanel overview) {
		overviewPanel = overview;
	}
}
