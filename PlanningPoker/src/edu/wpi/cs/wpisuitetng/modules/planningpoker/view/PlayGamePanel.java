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

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.GameInfoPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.ListRequirementsPanel;


public class PlayGamePanel extends JPanel{

	private GameInfoPanel gameInfoPanel;
	private ListRequirementsPanel listRequirementsPanel;
	private EstimationPane estimationPane;
	private JSplitPane splitPane;

	public PlayGamePanel(Game game)
	{
		gameInfoPanel = new GameInfoPanel();
		gameInfoPanel.updatePanel(game);
		listRequirementsPanel = new ListRequirementsPanel(game);
		estimationPane = new EstimationPane();
		splitPane = new JSplitPane();
		splitPane.setLeftComponent(listRequirementsPanel);
		splitPane.setRightComponent(estimationPane);
		setLayout(new BorderLayout());
		add(gameInfoPanel, BorderLayout.NORTH);
		add(splitPane, BorderLayout.CENTER);
	}

	
	
	
	
}
