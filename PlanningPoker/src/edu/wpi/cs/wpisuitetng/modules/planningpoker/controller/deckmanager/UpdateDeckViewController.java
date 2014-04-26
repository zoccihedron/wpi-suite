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

	private DeckManager deckManager;
	
	public UpdateDeckViewController(DeckManager deckManager) {
		
		this.deckManager = deckManager;
	}

	public void updateDeckManager(Deck deck) {
		deckManager.updateDeck(deck);
	}
	
}
