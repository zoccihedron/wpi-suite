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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckManager;

/**
 * This class is used to update the Deck view on the right side of the
 * deck manager tab
 * 
 * @author Codon Bleu
 * @version 1.0
 */
public class UpdateDeckViewController {

	private final DeckManager deckManager;
	
	/**
	 * Constructor for UpdateDeckViewController
	 * @param deckManager the deck manager
	 */
	public UpdateDeckViewController(DeckManager deckManager) {
		
		this.deckManager = deckManager;
	}

	/**
	 * Update the right hand views with the passed deck
	 * @param deck the deck to update with
	 */
	public void updateDeckManager(Deck deck) {
		deckManager.updateDeck(deck);
	}

	/**
	 * Updates the right hand views with the passed, uneditable deck.
	 * @param deck the deck to update with
	 */
	public void updateInUse(Deck deck) {
		deckManager.updateInUse(deck);
	}
	
}
