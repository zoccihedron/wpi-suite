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

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;

/**
 * The right hand side of the deck manager tab. This
 * holds the view of the cards in a deck as well as the
 * button panel for the deck
 * @author Code On Bleu
 * @version 1.0
 *
 */
public class DeckManager extends JPanel{
	
	private final CardViewPanel cardViewPanel;
	private final DeckControlsPanel deckControlsPanel;
	
	
	/**
	 * Constructs the two sub panels and places them in their correct positions
	 * @param listDecksPanel is the list decks panel to be passed to deck controls panel
	 */
	public DeckManager(ListDecksPanel listDecksPanel){
		
		cardViewPanel = new CardViewPanel();
		deckControlsPanel = new DeckControlsPanel(cardViewPanel, listDecksPanel);
		
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.8;
		this.add(cardViewPanel, constraints);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.2;
		this.add(deckControlsPanel, constraints);
	}
	
	/**
	 * updates the cards shown in the top panel to correspond with
	 * what is in the deck.
	 * @param deck the deck to update the card view with
	 */
	public void updateDeck(Deck deck){
		deckControlsPanel.setActionListeners(deck);

		cardViewPanel.updateView(deck);

	}

	/**
	 * Update the card view and deck controls panel with the uneditable deck
	 * (as it is in use)
	 * @param deck the deck to update cardview with
	 */
	public void updateInUse(Deck deck) {
		deckControlsPanel.disableControls();
		deckControlsPanel.updateDeckRemovalMessage("The deck is currently in use "
				+ "and unable to be edited");
		
		cardViewPanel.updateView(deck);

	}

}
