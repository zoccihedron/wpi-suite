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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * The VoteActionObserver tells the controller to update the 
 * Estimation pane if the estimation is successfully made for
 * a requirement.
 *
 * @author Robert
 * @version Apr 9, 2014
 */
public class VoteActionObserver implements RequestObserver{

	private final VoteActionController controller;

	/**
	 * Constructs the VoteActionObserver
	 *
	 * @param controller
	 */
	public VoteActionObserver(VoteActionController controller){
		this.controller= controller;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		// TODO Auto-generated method stub
		final ResponseModel response = iReq.getResponse();
		final String message = response.getBody();
		System.out.println("Message:" + message + "<<<");
		if(message.trim().equals("true"))
		{
			controller.reportSuccess();
		}
		else
		{
			controller.reportError(message);
		}
	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub
		System.err.println("The request to update a vote failed.");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub
		System.err.println("The request to update a vote failed.");

	}

}
