/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.ManageDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame.PlayGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.GameInfoPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.ListRequirementsPanel;

/**
 * Overraching tab for the Deck Manager
 * @author Code On Bleu
 *
 */
public class DeckManagerPanel extends JPanel{
	private final JSplitPane splitPane;
	
	/**
	 * Instantiate the two halfs of the panel.
	 */
	public DeckManagerPanel(){
		setLayout(new BorderLayout());
		DeckDataPanel deckData = new DeckDataPanel();
		DeckManager deckManager = new DeckManager();
		//TODO Needs arguements
		ManageDeckController controller = new ManageDeckController();
		splitPane = new JSplitPane();
		splitPane.setLeftComponent(deckData);
		splitPane.setRightComponent(deckManager);
		add(splitPane, BorderLayout.CENTER);
	}
}

