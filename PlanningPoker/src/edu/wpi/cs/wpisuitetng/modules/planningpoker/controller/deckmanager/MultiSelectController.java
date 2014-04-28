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

import javax.swing.JRadioButton;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Controller for selecting single select or multi select for decks
 * @author Code on Bleu
 * @version 1.0
 *
 */
public class MultiSelectController implements ActionListener{
	
	private final DeckControlsPanel view;
	
	/**
	 * Constructor for MultiSelectController
	 * @param view the deck controls panel
	 */
	public MultiSelectController(DeckControlsPanel view){
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final JRadioButton button = (JRadioButton) e.getSource();
		
		view.saveMessage("<html>Saving changes...</html>");

		
		if(button.getText().equals(view.getSingleSelectText())){
			view.setDeckMultiSelectStatus(false);
		} else{
			view.setDeckMultiSelectStatus(true);
		}
		
		final Request request = Network.getInstance().makeRequest(
					"planningpoker/deck", HttpMethod.POST);
		request.setBody(view.getDeck().toJSON());
		request.addObserver(new RequestObserver(){

			@Override
			public void responseSuccess(IRequest iReq) {
				successfulStatusUpdate();
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
	public void successfulStatusUpdate(){
		view.getCardView().updateView(view.getDeck());
		view.saveMessage("<html>Changes saved.</html>");
	}

}
