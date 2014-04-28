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
 * Request Observer for the add deck controller
 * @author Code on Bleu
 * @version 1.0
 */
public class AddDeckRequestObserver implements RequestObserver {
	private final AddDeckController addDeckController;
	
	/**
	 * Constructor for AddDeckRequestObserver
	 * @param addDeckController the controller for the request observer
	 */
	public AddDeckRequestObserver(AddDeckController addDeckController){
		this.addDeckController = addDeckController;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		final ResponseModel response = iReq.getResponse();
		final Deck deck = Deck.fromJson(response.getBody());
		addDeckController.deckCreationSuccessful(deck);
	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub

	}

}
