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

import javax.swing.JPanel;

/**
 * Panel on the left hand side of the deck manager. Holds
 * the list of decks panel and the create deck button panel.
 * @author Code On Bleu
 *
 */
public class DeckDataPanel extends JPanel{
	private ListDecksPanel listDecksPanel;
	private CreateDeckPanel createDeckPanel;
	
	public DeckDataPanel(){
		listDecksPanel = new ListDecksPanel();
		createDeckPanel = new CreateDeckPanel();
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weighty = 0.8;
		constraints.anchor = constraints.NORTH;
		this.add(listDecksPanel, constraints);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.weighty = 0.0;
		constraints.anchor = constraints.SOUTH;
		this.add(createDeckPanel, constraints);
	}
	public ListDecksPanel getListDecksPanel() {
		return listDecksPanel;
	}
	public CreateDeckPanel getCreateDeckPanel(){
		return createDeckPanel;
	}

}
