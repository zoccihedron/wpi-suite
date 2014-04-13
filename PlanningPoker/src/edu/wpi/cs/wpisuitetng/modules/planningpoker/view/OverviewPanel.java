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

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.OverviewPanelController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.GameSummaryPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.ListGamePanel;



/**
 * Creates the overview panel, which includes
 * a button for refreshing and the table that
 * displays the game data.
 *
 * @author Team Code On Bleu
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OverviewPanel extends JSplitPane {
	private final ListGamePanel listGamePanel;
	private final GameSummaryPanel summaryPanel;
	
	public OverviewPanel()

	{
		summaryPanel = new GameSummaryPanel();
		listGamePanel = new ListGamePanel();
		
		Dimension minimumSize = new Dimension(250, 300);
		listGamePanel.setMinimumSize(minimumSize);
	
		OverviewPanelController.getInstance().setGameSummary(summaryPanel);
		OverviewPanelController.getInstance().setListGames(listGamePanel);
		
		setLeftComponent(listGamePanel);

		setRightComponent(summaryPanel);
		setDividerLocation(300);
		
		
		
	}
	
}
