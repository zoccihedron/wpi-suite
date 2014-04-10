/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Code On Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

/**
 * This class is a JPanel. 
 * It contains both a summary of fields from a game and 
 * a table of requirements in a game.
 * 
 * @author Code On Bleu
 * @version 1.0
 */
@SuppressWarnings({ "serial" })
public class GameSummaryPanel extends JPanel {

	private final GameSummaryInfoPanel infoPanel;
	private final GameSummaryReqPanel reqPanel;

	/**
	 * This constructor is to be used when starting from a new game
	 * 
	 * @param parent
	 *            the parent panel for this panel
	 */
	public GameSummaryPanel() {
		
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();

		infoPanel = new GameSummaryInfoPanel();
		reqPanel = new GameSummaryReqPanel();
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(infoPanel, constraints);
		
		JPanel emptyPanel = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(emptyPanel, constraints);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(reqPanel, constraints);
	}
	
	public void updateSummary(Game game){
		infoPanel.updateInfoSummary(game);
		reqPanel.updateReqSummary(game);
	}

	
}

	
