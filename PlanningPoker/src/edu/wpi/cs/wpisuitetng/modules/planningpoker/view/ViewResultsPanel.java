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

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.results.ViewResultsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results.EstimateTreePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results.ResultsPanel;

/**
 * This class constructs the ViewResultsPanel, which displays results of the requirement,
 * and information about the requirement
 * @author Codon Bleu
 * @version 1.0
 *
 */

@SuppressWarnings("serial")
public class ViewResultsPanel extends JPanel{

	private final EstimateTreePanel estimateTreePanel;
	private final ResultsPanel resultsPanel;
	private final JSplitPane splitPane;
	private final Game game;
	private final ViewResultsController controller;

	/**
	 * Constructs the ViewResultsPanel with the given game
	 *
	 * @param game the game to construct the panel
	 */
	public ViewResultsPanel(Game game)
	{
		this.game = game;
		setLayout(new BorderLayout());
		
		controller = new ViewResultsController();
		estimateTreePanel = new EstimateTreePanel(game, controller);
		final Dimension minimumSize = new Dimension(250, 300);
		estimateTreePanel.setMinimumSize(minimumSize);
		splitPane = new JSplitPane();
		splitPane.setLeftComponent(estimateTreePanel);
		resultsPanel = new ResultsPanel(estimateTreePanel, game);
		splitPane.setRightComponent(resultsPanel);
		controller.setResultsPanel(resultsPanel);
		
		add(splitPane, BorderLayout.CENTER);

	}
	/**
	 * Check to see if the tab is ready to be closed.
	 * @return that the tab is always ready to close as data is never saved
	 */
	public boolean isReadyToClose() {
		return true;
	}
	/**
	 * get Game
	 * @return the game for this tab
	 */
	public Game getGame() {
		return game;
	}
	
}
