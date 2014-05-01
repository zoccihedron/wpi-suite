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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results.ResultsDisplayPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class UnselectEstimateController implements ActionListener {
	Game game;
	ResultsDisplayPanel view;
	
	/**
	 * The controller for marking an estimate as unselected
	 * @param game the game currently under playing
	 * @param view the view that contains this controller
	 */
	public UnselectEstimateController(Game game, ResultsDisplayPanel view){
		this.game = game;
		this.view = view;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final int reqid = view.getReqid();
		
		final Estimate estimate = game.findEstimate(reqid);

		estimate.setGameID(game.getId());
		estimate.unSelectFinalEstimate();
	
		// Send a request to the core to mark this estimate as being unselected
		final Request request = Network.getInstance().makeRequest(
				"Advanced/planningpoker/game/unselectEstimate", 
				HttpMethod.POST); // POST is update
		request.setBody(estimate.toJSON()); 
		request.addObserver(new RequestObserver(){
			@Override
			public void responseSuccess(IRequest iReq) {
				
			}
	
			@Override
			public void responseError(IRequest iReq) {
				
			}
	
			@Override
			public void fail(IRequest iReq, Exception exception) {
				
			}
			
		}); 
	
		request.send();
		view.refresh();
	
}


}

