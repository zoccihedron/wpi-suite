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

import javax.swing.JSplitPane;

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
	
	private final ListGamePanel listPanel;
	private final GameSummaryPanel summaryPanel;
	
	public OverviewPanel()
	{
		summaryPanel = new GameSummaryPanel();
		listPanel = new ListGamePanel();
		
		setLeftComponent(listPanel);
		setRightComponent(summaryPanel);
		setDividerLocation(300);
	}
}

