/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Creator:
 *    Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controls the functionality for updating a deck.
 * @author Code On Bleu
 * @version 1.0
 */
public class UpdateDeckController implements ActionListener{
	private Deck updatedDeck;
	private final Deck originalDeck;
	private final DeckControlsPanel deckControlsPanel;
	
	/**
	 * Constructor for UpdateDeckController
	 * @param deckControlsPanel the deck controls panel
	 */
	public UpdateDeckController(DeckControlsPanel deckControlsPanel){
		this.deckControlsPanel = deckControlsPanel;
		originalDeck = deckControlsPanel.getDeck();
		updatedDeck = deckControlsPanel.getDeck();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		updatedDeck  = deckControlsPanel.getDeck();
		updatedDeck.setName(deckControlsPanel.getFieldDeckNameText());
		deckControlsPanel.saveMessage("<html>Saving changes...</html>");
		// Send a request to the core to save this game
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/deck", HttpMethod.POST);
		request.setBody(updatedDeck.toJSON()); // put the new message in the body of the request
		// add an observer to process the response
		request.addObserver(new UpdateDeckRequestObserver(this));
		request.send(); // send the request
		
	}
	
	/**
	 * Update all views and actionlisteners and lists with the saved deck
	 * @param deck the deck whose name was changed
	 */
	public void successfulNameChange(Deck deck){
		deckControlsPanel.setActionListeners(deck);
		deckControlsPanel.getCardView().updateView(deck);
		ManageDeckController.getInstance().updateName(deck);
		deckControlsPanel.getListDecksPanel().refresh();
		deckControlsPanel.saveMessage("<html>Changes saved.</html>");
	}

}
