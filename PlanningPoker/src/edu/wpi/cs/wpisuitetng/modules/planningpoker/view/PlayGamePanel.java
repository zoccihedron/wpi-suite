/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
<<<<<<< HEAD
 * Creator:
 *    Team Code On Bleu
=======
 * Contributors:
 *    Team Codon Bleu
>>>>>>> be6644e43b6e67d32f64f429e235a6c6ea431614
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.PlayGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.GameInfoPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.ListRequirementsPanel;

/**
 * TODO - add commenting
 * @author Codon Bleu
 * @version 1.0
 *
 */

@SuppressWarnings("serial")
public class PlayGamePanel extends JPanel{

	private GameInfoPanel gameInfoPanel;
	private ListRequirementsPanel listRequirementsPanel;
	private EstimationPane estimationPane;
	private JSplitPane splitPane;

	public PlayGamePanel(Game game)
	{
		setLayout(new BorderLayout());
		
		//gameInfoPanel = new GameInfoPanel();
		listRequirementsPanel = new ListRequirementsPanel(game);
		estimationPane = new EstimationPane();
		splitPane = new JSplitPane();
		splitPane.setLeftComponent(listRequirementsPanel);
		splitPane.setRightComponent(estimationPane);
		PlayGameController.getInstance().setEstimationPane(estimationPane);
		//PlayGameController.getInstance().setGameInfoPanel(gameInfoPanel);
		
		//add(gameInfoPanel);
		add(splitPane, BorderLayout.CENTER);

	}
}
