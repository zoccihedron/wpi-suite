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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.ManageDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;

/**
 * The right hand side of the deck manager tab. This
 * holds the view of the cards in a deck as well as the
 * button panel for the deck
 * @author Code On Bleu
 *
 */
public class DeckManager extends JPanel{
	private CardViewPanel cardViewPanel;
	private DeckControlsPanel deckControlsPanel;
	private ManageDeckController controller;
	
	/**
	 * Constructs the two sub panels and places them in their correct positions
	 */
	public DeckManager(ListDecksPanel listDecksPanel){
		
		cardViewPanel = new CardViewPanel();
		deckControlsPanel = new DeckControlsPanel(cardViewPanel, listDecksPanel);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weighty = 0.8;
		this.add(cardViewPanel, constraints);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.weighty = 0.2;
		this.add(deckControlsPanel, constraints);
	}
	
	/**
	 * updates the cards shown in the top panel to correspond with
	 * what is in the deck.
	 * @param deck the deck to update the card view with
	 */
	public void updateDeck(Deck deck){
		cardViewPanel.cardView(deck);
		deckControlsPanel.setActionListeners(deck);
	}

}
