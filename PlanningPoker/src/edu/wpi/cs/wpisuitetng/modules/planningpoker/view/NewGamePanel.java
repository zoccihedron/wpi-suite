/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Panel;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;



/**
 * This class is a JPanel. It contains all the fields needed to 
 * create a new game
 * 
 * @author Joshua Allard
 *
 */
@SuppressWarnings({"serial"})
public class NewGamePanel extends JSplitPane {
	public CreateGameInfoPanel createGameInfoPanel;
	public JTabbedPane tabPane;
	
	public NewGamePanel(PlanningPokerModel model) {
		createGameInfoPanel = new CreateGameInfoPanel(model);
		tabPane = new JTabbedPane();
		tabPane.addTab("Requirements", new Panel());
		tabPane.addTab("Create new requirement", new Panel());
		tabPane.addTab("Create new deck", new Panel());

		
		
		
		
		
		this.setLeftComponent(createGameInfoPanel);
		this.setRightComponent(tabPane);
		this.setDividerLocation(200);
	}
}
