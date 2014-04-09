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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.core.models.Project;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This request observer returns the project from 
 *
 * @author Team Code On Bleu
 * @version Apr 9, 2014
 */
public class GetUserRequestObserver implements RequestObserver {
	
	private final AddGameController gameController;
	
	/**
	 * Constructs the GetUserRequestObserver
	 * @param gameController the AddGameController to send the project data to
	 */
	public GetUserRequestObserver(AddGameController gameController)
	{
		this.gameController = gameController;
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
		final Project project = Project.fromJSON(response.getBody());
		
		// Pass the messages back to the controller
		AddGameController.receivedProject(project);
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
