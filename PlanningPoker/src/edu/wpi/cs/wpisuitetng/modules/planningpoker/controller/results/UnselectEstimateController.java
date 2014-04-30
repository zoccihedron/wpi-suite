/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.results;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.OverviewPanelController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class UnselectEstimateController implements ActionListener {
	Estimate estimate;
	
	UnselectEstimateController(Estimate estimate){
		this.estimate = estimate;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Send a request to the core to mark this estimate as being sent
		final Request request = Network.getInstance().makeRequest(
				"Advanced/planningpoker/game/unselectEstimate", 
				HttpMethod.POST); // POST is update
		request.setBody(estimate.toJSON()); 
		request.addObserver(new RequestObserver(){
			@Override
			public void responseSuccess(IRequest iReq) {
				
				OverviewPanelController.getInstance().updateGameSummary(game);
			}

			@Override
			public void responseError(IRequest iReq) {
			}

			@Override
			public void fail(IRequest iReq, Exception exception) {
			}
			
		}); 
		request.send();


	}

}
