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
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.CreateDeckPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.ListDecksPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller for adding a deck to the database
 * @author Code on Bleu
 * @version 1.0
 */
public class AddDeckController implements ActionListener {
	private final CreateDeckPanel createDeckPanel;
	private final String defaultDeckText;
	private final ListDecksPanel listDecksPanel;
	private Deck deck;
	
	/**
	 * Constructor for AddDeckController
	 * @param createDeckPanel the create deck panel
	 * @param defaultDeckText the hint in the text field
	 * @param listDecksPanel the list decks panel
	 */
	public AddDeckController(CreateDeckPanel createDeckPanel, 
			String defaultDeckText, ListDecksPanel listDecksPanel){
		this.createDeckPanel = createDeckPanel;
		this.defaultDeckText = defaultDeckText;
		this.listDecksPanel = listDecksPanel;
		deck = null;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		deck  = new Deck(createDeckPanel.getDeckName(), false, new ArrayList<Integer>());
		if(!deck.getName().equals("") && !deck.getName().equals(defaultDeckText)){
			// Send a request to the core to save this game
			final Request request = Network.getInstance().makeRequest(
					"planningpoker/deck", HttpMethod.PUT);
			request.setBody(deck.toJSON()); // put the new message in the body of the request
			// add an observer to process the response
			request.addObserver(new AddDeckRequestObserver(this));
			request.send(); // send the request
			
			createDeckPanel.setDeckNameJTextField("");
		}
	}
	
	/**
	 * Refresh the list of decks when a deck is successfully added to the database
	 * @param deckAdded the deck added to the database
	 */
	public void deckCreationSuccessful(Deck deckAdded) {
		ManageDeckController.getInstance().addDeck(deckAdded);
		ManageDeckController.getInstance().updateDecks();
		listDecksPanel.refresh();
	}

}
