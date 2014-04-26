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
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.HelpPanelController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.OverviewPanelController;

/**
 * @author Codon Bleu
 *
 */
@SuppressWarnings("serial")
public class HelpPanel extends JSplitPane {
	private final HelpTopicsPanel topicsPanel;
	private final JPanel helpPanel;
	private final JLabel titleText;
	
	public HelpPanel() {
		topicsPanel = new HelpTopicsPanel();
		helpPanel = new JPanel();
		
		setLayout(new BorderLayout(0, 0));

		
		final Dimension minimumSize = new Dimension(250, 300);
		topicsPanel.setMinimumSize(minimumSize);
		

		//HelpPanelController.getInstance().setGameSummary(topicsPanel);
		//HelpPanelController.getInstance().setListGames(helpPanel);
		
		setLeftComponent(topicsPanel);
		setRightComponent(helpPanel);
		
		setDividerLocation(300);
		
		titleText = new JLabel("How to Use Planning Poker");
		helpPanel.add(titleText);
	}

	/**
	 * Says that the help tab should always be ready to close
	 * @return always true
	 */
	public boolean isReadyToClose() {
		return true;
	}
}
