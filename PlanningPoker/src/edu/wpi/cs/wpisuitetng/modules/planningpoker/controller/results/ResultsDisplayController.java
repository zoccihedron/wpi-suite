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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.results;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.OverviewPanelController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results.ResultsDisplayPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Controller for ResultsDisplayPanel, used for
 * sending final estimates to requirement manager
 * @author Team Code On Bleu
 * @version 1.0
 */
public class ResultsDisplayController implements ActionListener{
	private final ResultsDisplayPanel view;
	private final Game game;
	
	/**
	 * Constructor for ResultDisplayController
	 * @param view ResultsDisplayPanel
	 * @param game
	 */
	public ResultsDisplayController(ResultsDisplayPanel view, Game game){
		this.view = view;
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(view.canMakeEstimate()){
			final int finalEstimate = view.getFinalEstimate();
			final int reqid = view.getReqid();
			
			final Estimate estimate = game.findEstimate(reqid);
			estimate.setFinalEstimate(finalEstimate);
			estimate.setGameID(game.getId());
			estimate.setNote(view.getNote());
			estimate.estimationSent(false);
			
			// Send a request to the core to mark this estimate as being set
			final Request request = Network.getInstance().makeRequest(
					"Advanced/planningpoker/game/sendFinalEstimate", 
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
			view.refresh();
			
		}
	}

}
