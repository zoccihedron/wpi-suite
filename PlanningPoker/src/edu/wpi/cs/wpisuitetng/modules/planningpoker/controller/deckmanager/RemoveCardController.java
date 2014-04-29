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
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.CardViewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Controller for removing cards from the database
 * @author Code on Bleu
 * @version 1.0
 */
public class RemoveCardController implements ActionListener{
	private final DeckControlsPanel view;
	private final CardViewPanel cardView;
	
	/**
	 * Constructor for RemoveCardController
	 * @param view the deck controls panel
	 */
	public RemoveCardController(DeckControlsPanel view){
		this.view = view;
		cardView = view.getCardView();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		final List<Integer> toRemove = cardView.getSelected();
		view.saveMessage("<html>Saving changes...</html>");
		
		for(Integer cardValue: toRemove){
			view.getDeck().removeCard(cardValue);
		}
		
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/deck", HttpMethod.POST);
		request.setBody(view.getDeck().toJSON());
		request.addObserver(new RequestObserver(){

			@Override
			public void responseSuccess(IRequest iReq) {
				successfulRemoval();
			}

			@Override
			public void responseError(IRequest iReq) {
				System.err.println("The request to update the deck failed");
			}

			@Override
			public void fail(IRequest iReq, Exception exception) {
				System.err.println("The request to update the deck failed");
				
			}
			
		});
		request.send();
		
	}
	
	/**
	 * Update the cardview and the save message when changes are 
	 * successfully saved to the database
	 */
	public void successfulRemoval(){
		cardView.updateView(view.getDeck());
		view.saveMessage("<html>Changes saved.</html>");
	}

}
