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

@SuppressWarnings("serial")
/**
 * Creates the overview panel, which includes
 * a button for refreshing and the table that
 * displays the game data.
 *
 * @author Team Code On Bleu
 * @version Mar 31, 2014
 */
public class OverviewPanel extends JSplitPane {
	
	private ListGamePanel whateverthehellwewant;
	private GameSummaryPanel summaryPanel;
	
	public OverviewPanel()
	{
		this.summaryPanel = new GameSummaryPanel();
		this.whateverthehellwewant = new ListGamePanel();
		
		this.setLeftComponent(this.whateverthehellwewant);
		this.setRightComponent(this.summaryPanel);
		this.setDividerLocation(300);
	}
}

