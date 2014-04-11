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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer is called when a response is received from a request
 * to the server to add a message.
 * 
 * @author Code On Bleu
 * @version 1.0
 */
public class AddGameRequestObserver implements RequestObserver {
	
	private final AddGameController controller;
	
	/**
	 * Sets the controller as the one passed in
	 * @param controller to be set as the class controller
	 */
	public AddGameRequestObserver(AddGameController controller) {
		this.controller = controller;
	}
	
	/**
	 * Parse the message that was received from the server then pass them to
	 * the controller.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver
	 * 		#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Pass the messages back to the controller
		controller.addGameToView();
		
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
