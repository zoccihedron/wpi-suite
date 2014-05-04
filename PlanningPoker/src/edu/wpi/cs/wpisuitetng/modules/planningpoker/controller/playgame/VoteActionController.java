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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.OverviewPanelController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.EstimationPane;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * The controller updates the estimate for a user, and updates
 * the game with the estimate in the database.
 *
 * @author Team Codon Bleu
 * @version Apr 9, 2014
 */
public class VoteActionController implements ActionListener {
	
	private final EstimationPane view;
	private final Game game;
	private int estimateValue;
	
	/**
	 * Constructs the VoteActionController
	 *
	 * @param view the Estimation Pane
	 * @param game the game to vote in
	 */
	public VoteActionController(EstimationPane view, Game game){
		this.view = view;
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(view.checkField()) {

			final String name = ConfigManager.getConfig().getUserName();
			estimateValue = view.getEstimate();
			final List<Boolean> selection = view.getCardSelection();
			view.getDeckPanel().setCurrentEstimate(Integer.toString(estimateValue));

			final Estimate oldEstimate = game.findEstimate(view.getReqID());
			oldEstimate.makeEstimate(name, estimateValue);
			oldEstimate.setUserCardSelection(name, selection);
			final Estimate estimate = new Estimate(view.getReqID(), game.getId());
			estimate.addUser(name);
			estimate.makeEstimate(name, estimateValue);
			estimate.setGameModifiedVersion(game.getModifiedVersion());
			estimate.setUserCardSelection(name, selection);
			// Send a request to the core to update this game
			final Request request = Network.getInstance().makeRequest(
					"Advanced/planningpoker/game/vote", 
					HttpMethod.POST); // POST is update
			request.setBody(estimate.toJSON()); // put the new message in the body of the request
			request.addObserver(new VoteActionObserver(this)); 
			request.send(); 
			
		}

	}

	/**
	 * Tells the Estimation Pane to update its view with a 
	 * success message if the estimation was made
	 */
	public void reportSuccess() {
		final boolean moved = view.hasRefreshAndMoved();
		OverviewPanelController.getInstance().refreshListGames();
		OverviewPanelController.getInstance().updateGameSummary(game);
		view.reportSuccess(estimateValue, moved);
	}
	
	/**
	 * Reports the error that was encountered in the Estimation Pane.
	 * @param errorText
	 * 					The error text to display.
	 */
	public void reportError(String errorText) {
		view.reportError(errorText);
	}

}
