/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Josh and Corey
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to add a message.
 * 
 * @author Josh and Corey
 *
 */
public class GetGamesRequestObserver implements RequestObserver {
	
	private final GetGamesController controller;
	
	public GetGamesRequestObserver(GetGamesController controller) {
		this.controller = controller;
	}
	
	/*
	 * Parse the message that was received from the server then pass them to
	 * the controller.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();
		
		// Parse the message out of the response body
		// TODO - force list update after getting response
		// final PostBoardMessage message = PostBoardMessage.fromJson(response.getBody());
		final Game[] returnGames = Game.fromJsonArray(response.getBody());
		
		// Pass the messages back to the controller
		controller.receivedGames(returnGames);
	}

	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a game failed.");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a game failed.");
	}

}
