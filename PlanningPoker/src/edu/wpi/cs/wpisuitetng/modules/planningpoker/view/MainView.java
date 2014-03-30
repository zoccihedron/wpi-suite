/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Team Codon Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;

/**
 * This panel fills the main content area of the tab for this module. It
 * contains one inner JPanel, the BoardPanel.
 * 
 * @author Joshua Allard
 *
 */
@SuppressWarnings("serial")
public class MainView extends JTabbedPane {

	/** The panel containing the new game creator */
	private final NewGamePanel newGamePanel;
	
	/** The overview panel */
	private final OverviewPanel overviewPanel;
	
	/**
	 * Construct the panel.
	 * @param boardModel 
	 */
	public MainView(PlanningPokerModel gamesModel) {
		
		newGamePanel = new NewGamePanel(gamesModel);
		newGamePanel.setBounds(0,0,500,500);
		overviewPanel = new OverviewPanel(gamesModel);
		overviewPanel.setBounds(0, 0, 500, 500);
		addTab("Games Overview", overviewPanel);
		addTab("New Game",newGamePanel);
	}
}
