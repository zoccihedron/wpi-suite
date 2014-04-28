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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Responsible for receiving responses from database for requests to update.
 * @author Code On Bleu
 * @version 1.0
 *
 */
public class UpdateDeckRequestObserver implements RequestObserver {
	private final UpdateDeckController updateDeckController;
	
	/**
	 * Constructor for Request Obersver
	 * @param updateDeckController the updated deck controller
	 */
	public UpdateDeckRequestObserver(UpdateDeckController updateDeckController){
		this.updateDeckController = updateDeckController;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		final ResponseModel response = iReq.getResponse();
		final Deck deck = Deck.fromJson(response.getBody());
		updateDeckController.successfulNameChange(deck);
	}

	@Override
	public void responseError(IRequest iReq) {
		System.err.println("Failure to save deck");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("Failure to save deck");

	}

}
