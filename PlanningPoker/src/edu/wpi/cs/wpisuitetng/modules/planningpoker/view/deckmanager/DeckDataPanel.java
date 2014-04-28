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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.UpdateDeckViewController;

/**
 * Panel on the left hand side of the deck manager. Holds
 * the list of decks panel and the create deck button panel.
 * @author Code On Bleu
 * @version 1.0
 *
 */
public class DeckDataPanel extends JPanel{
	private final ListDecksPanel listDecksPanel;
	private final CreateDeckPanel createDeckPanel;
	
	private final JLabel currentlySelectedDeckName;
	
	public DeckDataPanel(){
		listDecksPanel = new ListDecksPanel();

		createDeckPanel = new CreateDeckPanel(listDecksPanel);
		
		currentlySelectedDeckName = new JLabel();
		
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.8;
		constraints.anchor = GridBagConstraints.NORTH;
		this.add(listDecksPanel, constraints);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 1.0;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridwidth = 1;
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.SOUTH;
		this.add(createDeckPanel, constraints);
	}
	public ListDecksPanel getListDecksPanel() {
		return listDecksPanel;
	}
	public CreateDeckPanel getCreateDeckPanel(){
		return createDeckPanel;
	}

	/**
	 * Sets the controller for list decks panel. This controller takes in 
	 * Deck Manager which is why it needs to be set
	 * @param updateDeckViewController the controller for list decks panel
	 */
	public void setListDecksController(UpdateDeckViewController updateDeckViewController) {
		listDecksPanel.setDeckViewController(updateDeckViewController);
	}
}
