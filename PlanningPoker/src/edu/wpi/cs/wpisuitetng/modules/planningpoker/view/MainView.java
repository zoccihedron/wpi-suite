/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Team Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGamePanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ClosableTabComponent;


/**
 * This panel fills the main content area of the tab for this module. It
 * contains one inner JPanel, the BoardPanel.
 * 
 * @author Code On Bleu
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class MainView extends JTabbedPane {

	/** The overview panel */
	private final OverviewPanel overviewPanel;

	/**
	 * Construct the panel.
	 * @param gamesModel 
	 */
	public MainView() {
		MainViewTabController.getInstance().setMainView(this);
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		// Add the board panel to this view
		overviewPanel = new OverviewPanel();
		overviewPanel.setBounds(0, 0, 500, 500);
		this.addTab("Overview", overviewPanel);
	}

	/**
	 * Adds a new game panel if it is not
	 * already there
	 */
	public void createNewGameTab()
	{
		final NewGamePanel newGamePanel = new NewGamePanel();
		newGamePanel.setBounds(0, 0, 500, 500);
		this.addTab("New Game", newGamePanel);
		this.invalidate(); // force the tabbedpane to redraw
		this.repaint();
		newGamePanel.resetDividerLocation();
		this.setSelectedComponent(newGamePanel);
	}
	

	/**
	 * Get the overview panel from the mainView
	 * 
	 * @return overviewPanel
	 */
	public OverviewPanel getOverviewPanel(){
		return overviewPanel;
	}
	
	/**
	 * Overridden insertTab function to add the closable tab element.
	 * 
	 * @param title	Title of the tab
	 * @param component	The tab
	 * @param index	Location of the tab
	 */
	public void insertTab(String title, final Component component, int index) {
		super.insertTab(title, null, component, null, index);
		if (!(component instanceof OverviewPanel)) {
			setTabComponentAt(index, new ClosableTabComponent(this)
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					MainViewTabController.getInstance().closeTab(component);
				}
			});
		}
	}
}
