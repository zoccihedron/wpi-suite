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

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.ManageDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.UpdateDeckViewController;

/**
 * Overraching tab for the Deck Manager
 * @author Code On Bleu
 *
 */
@SuppressWarnings("serial")
public class DeckManagerPanel extends JPanel{
	private final JSplitPane splitPane;
	
	/**
	 * Instantiate the two halfs of the panel.
	 */
	public DeckManagerPanel(){
		setLayout(new BorderLayout());
		DeckDataPanel deckData = new DeckDataPanel();
		DeckManager deckManager = new DeckManager(deckData.getListDecksPanel());
		UpdateDeckViewController updateDeckViewController = new UpdateDeckViewController(deckManager);
		deckData.setListDecksController(updateDeckViewController);
		//TODO Needs arguements
		ManageDeckController controller = ManageDeckController.getInstance();
		splitPane = new JSplitPane();
		splitPane.setLeftComponent(deckData);
		splitPane.setRightComponent(deckManager);
		add(splitPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(300);
	}
	
	public boolean isReadyToClose() {
		return true;
	}
}

