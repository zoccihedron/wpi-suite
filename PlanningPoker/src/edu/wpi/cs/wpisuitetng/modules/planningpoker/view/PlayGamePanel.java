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

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame.PlayGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.GameInfoPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.ListRequirementsPanel;

/**
 * This class constructs the PlayGamePanel, which displays information about the game,
 * its requirements, and creates the estimation pane.
 * @author Codon Bleu
 * @version 1.0
 *
 */

@SuppressWarnings("serial")
public class PlayGamePanel extends JPanel{

	private GameInfoPanel gameInfoPanel;
	private final ListRequirementsPanel listRequirementsPanel;
	private final EstimationPane estimationPane;
	private final JSplitPane splitPane;
	private final Game game;
	/**
	 * Constructs the PlayGamePanel with the given game
	 *
	 * @param game the game to construct the panel
	 */
	public PlayGamePanel(Game game)
	{
		this.game = game;
		setLayout(new BorderLayout());

		final PlayGameController playGameController = new PlayGameController();
		listRequirementsPanel = new ListRequirementsPanel(game, playGameController);
		final Dimension minimumSize = new Dimension(250, 300);
		listRequirementsPanel.setMinimumSize(minimumSize);
		splitPane = new JSplitPane();
		splitPane.setLeftComponent(listRequirementsPanel);
		estimationPane = new EstimationPane(listRequirementsPanel, game);
		playGameController.setEstimationPane(estimationPane);
		splitPane.setRightComponent(estimationPane);

		add(splitPane, BorderLayout.CENTER);

	}
	/**
	 * getter for game
	 * @return game used for game
	 */
	public Game getGame(){
		return game;
	}

	/**
	 * determines if the estimation pane is ready 
	 * to close and throws a notification if it may not be
	 * @return A true if it is ready to close and there are no changes
	 */
	public boolean isReadyToClose() {
		if(estimationPane.isNothingHappened()) {
			return true;
		} else if(estimationPane.getDeckPanel().getCurrentEstimate() != null) {
			if(estimationPane.getDeckPanel().isOldEstimate()){
				return true;
			}
			else{
				final Object options[] = {
						"Yes", "No"
						};
				final int i = JOptionPane.showOptionDialog(this, 
						"Any unsaved changes will be lost, would you like to exit anyways?",
						"Exit?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null, options, options[1]);

				return (i == 0);
			}
		}
		else return true;
	}
}
