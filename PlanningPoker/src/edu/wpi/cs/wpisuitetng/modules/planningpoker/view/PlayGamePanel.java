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

	/**
	 * Constructs the PlayGamePanel with the given game
	 *
	 * @param game the game to construct the panel
	 */
	public PlayGamePanel(Game game)
	{
		setLayout(new BorderLayout());
		
		//gameInfoPanel = new GameInfoPanel();
		listRequirementsPanel = new ListRequirementsPanel(game);
		Dimension minimumSize = new Dimension(250, 300);
		listRequirementsPanel.setMinimumSize(minimumSize);
		estimationPane = new EstimationPane();
		splitPane = new JSplitPane();
		splitPane.setLeftComponent(listRequirementsPanel);
		splitPane.setRightComponent(estimationPane);
		PlayGameController.getInstance().setEstimationPane(estimationPane);
		//PlayGameController.getInstance().setGameInfoPanel(gameInfoPanel);
		
		//add(gameInfoPanel);
		add(splitPane, BorderLayout.CENTER);

	}

	public boolean isReadyToClose() {
		Object options[] = {"Yes", "No"};
		int i = JOptionPane.showOptionDialog(this, 
				"There are unsaved changes would you like to exit anyways?",
				"Unsaved Changes!",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, options, options[1]);
		
		if(i == 0) {
			return true;
		} else {
			return false;
		}
	}
}
