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
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;




/**
 * This class is a JPanel. It contains all the fields needed to 
 * create a new game
 * @author Code On Bleu
 */
@SuppressWarnings({"serial"})
public class NewGamePanel extends JSplitPane {
	public CreateGameInfoPanel createGameInfoPanel;
	public JTabbedPane tabPane;
	private SelectRequirementsPanel selectRequirementsPanel;
	
	public NewGamePanel(PlanningPokerModel model, MainView parentWindow) {
		createGameInfoPanel = new CreateGameInfoPanel(this);
		createGameInfoPanel.setMinimumSize(new Dimension(50, 300));
		selectRequirementsPanel = new SelectRequirementsPanel();
		
		tabPane = new JTabbedPane();
		tabPane.addTab("Requirements", selectRequirementsPanel);

		this.setLeftComponent(createGameInfoPanel);
		this.setRightComponent(tabPane);
		this.setDividerLocation(300);
	}
	
	public NewGamePanel() {
		createGameInfoPanel = new CreateGameInfoPanel(this);
		createGameInfoPanel.setMinimumSize(new Dimension(50, 300));
		selectRequirementsPanel = new SelectRequirementsPanel();
		
		tabPane = new JTabbedPane();
		tabPane.addTab("Requirements", selectRequirementsPanel);
		
		this.setLeftComponent(createGameInfoPanel);
		this.setRightComponent(tabPane);
		this.setDividerLocation(300);
	}

	public ArrayList<Integer> getGameRequirements() {
		return selectRequirementsPanel.getSelectedRequirementIds();
	}
}
