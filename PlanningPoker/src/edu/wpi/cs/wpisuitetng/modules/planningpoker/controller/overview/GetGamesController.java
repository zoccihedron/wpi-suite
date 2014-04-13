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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Submit button by
 * sending the contents of the fields to the server as a New Game.
 * 
 * @author Code On Bleu
 * @version 1.0
 */
public class GetGamesController implements ActionListener {

	/* 
	 * This method is called when the user clicks the Submit button
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// Get the text that was entered
		
			// Send a request to the core to save this game
			final Request request = Network.getInstance().makeRequest
					("planningpoker/game", HttpMethod.GET);
			// add an observer to process the response
			request.addObserver(new GetGamesRequestObserver(this));
			request.send(); // send the request
	}
	
	/**
	 * Gets the game data from the games request observer
	 */
	public void initializeTable() {
		// Send a request to the core to save this game
		final Request request = Network.getInstance().makeRequest
				("planningpoker/game", HttpMethod.GET);
		// add an observer to process the response
		request.addObserver(new GetGamesRequestObserver(this));
		request.send(); // send the request
	}
	
	
	
	/**
	 * Removes all data from the PlanningPokerModel and then adds the 
	 * games from the database to the model. It then 
	 * refreshes the OverviewTable
	 * @param games is the array of games currently in the database
	 */
	public static void receivedGames(Game[] games)
	{
		PlanningPokerModel.getInstance().emptyModel();
		if(games != null)
		{
			PlanningPokerModel.getInstance().addAllGames(games);
		}
	}
}