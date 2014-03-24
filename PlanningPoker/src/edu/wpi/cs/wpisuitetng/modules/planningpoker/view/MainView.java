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

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Game;

/**
 * This panel fills the main content area of the tab for this module. It
 * contains one inner JPanel, the BoardPanel.
 * 
 * @author Joshua Allard
 *
 */
@SuppressWarnings("serial")
public class MainView extends JPanel {

	/** The panel containing the post board */
	private final NewGamePanel newGamePanel;
	
	/**
	 * Construct the panel.
	 * @param boardModel 
	 */
	public MainView(Game gameModel) {
		// Add the board panel to this view
		setBorder(BorderFactory.createLineBorder(Color.green, 2));
		
		newGamePanel = new NewGamePanel(gameModel);
		add(newGamePanel);
	}
}
