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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.CardViewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.ListDecksPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * The controller for removing decks form the database
 * @author Code on Bleu
 * @version 1.0
 */
public class RemoveDeckController implements ActionListener{
	private final DeckControlsPanel view;
	private final CardViewPanel cardView;
	private final ListDecksPanel listDecksPanel;
	private Deck toDelete;
	
	/**
	 * Constructor for RemoveDeckController
	 * @param view the deck controls panel
	 */
	public RemoveDeckController(DeckControlsPanel view){
		this.view = view;
		cardView = view.getCardView();
		listDecksPanel = view.getListDecksPanel();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		toDelete = view.getDeck();
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/deck/" + toDelete.getId(), HttpMethod.DELETE);
		request.addObserver(new RequestObserver(){

			@Override
			public void responseSuccess(IRequest iReq) {
				successfulRemoval();
			}

			@Override
			public void responseError(IRequest iReq) {
				System.err.println("The request to delete the deck failed");
				
			}

			@Override
			public void fail(IRequest iReq, Exception exception) {
				System.err.println("The request to delete the deck failed" + exception.toString());
				
			}
			
		});
		request.send();
		
	}

	/**
	 * Remove the deck from the list of decks, update the right hand side
	 * of Deck Manager to be empty, and display a message that the
	 * deck was successfully deleted
	 */
	public void successfulRemoval(){
		ManageDeckController.getInstance().removeDeck(toDelete);
		cardView.updateView();
		view.disableControls();
		view.updateDeckRemovalMessage("Deck Successfully Removed");
		listDecksPanel.refresh();
	}
}
