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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to add a message.
 * 
 * @author Code On Bleu
 * @version 1.0
 */
public class GetGamesRequestObserver implements RequestObserver {
	
	private final GetGamesController controller;
	
	/**
	 * Constructor for the GetGamesRequestObserver class
	 * @param controller
	 */
	public GetGamesRequestObserver(GetGamesController controller) {
		this.controller = controller;
	}
	
	/**
	 * Parse the message that was received from the server then pass them to
	 * the controller.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver
	 * #responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();
		
		// Parse the message out of the response body
		// TODO - force list update after getting response
		final Game[] returnGames = Game.fromJsonArray(response.getBody());
		
		// Pass the messages back to the controller
		GetGamesController.receivedGames(returnGames);
	}

	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a game failed.");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a game failed.");
	}
	
	/**
	 * Gets the class controller
	 */
	public GetGamesController getGetGamesController()
	{
		return controller;
	}

}
