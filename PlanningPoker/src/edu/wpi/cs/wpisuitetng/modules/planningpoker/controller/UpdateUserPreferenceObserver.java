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


import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.UserPreferencesPanel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to update a user preference.
 * 
 * @author Code On Bleu
 * @version 1.0
 */
public class UpdateUserPreferenceObserver implements RequestObserver {
	
	private UserPreferencesPanel userPreferencePanel;
	
	public UpdateUserPreferenceObserver(UserPreferencesPanel userPreferencePanel){
		this.userPreferencePanel = userPreferencePanel;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		
		final ResponseModel response = iReq.getResponse();
		final User[] user = User.fromJsonArray(response.getBody());
		userPreferencePanel.setCurrentEmailAndIM(user[0].getEmail(), user[0].getIM(),
				user[0].isAllowEmail(), user[0].isAllowIM());
		
		userPreferencePanel.btnSubmit.setEnabled(false);

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
