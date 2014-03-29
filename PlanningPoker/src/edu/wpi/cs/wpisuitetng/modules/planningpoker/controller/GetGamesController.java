/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.NewGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.OverviewPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Submit button by
 * sending the contents of the fields to the server as a New Game.
 * 
 * @author Josh and Corey
 *
 */
public class GetGamesController implements ActionListener {
	
	private final PlanningPokerModel model;
	private final OverviewPanel view;
	
	/**
	 * Construct an AddMessageController for the given model, view pair
	 * @param model the model containing the messages
	 * @param view the view where the user enters new messages
	 */
	public GetGamesController(PlanningPokerModel model, OverviewPanel view) {
		this.model = model;
		this.view = view;
	}

	/* 
	 * This method is called when the user clicks the Submit button
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// Get the text that was entered
					
			// Send a request to the core to save this game
			final Request request = Network.getInstance().makeRequest("planningpoker/game", HttpMethod.GET); // PUT == create
			request.addObserver(new GetGamesRequestObserver(this)); // add an observer to process the response
			request.send(); // send the request
	}
	
	
	public void initializeTable() {
		// Get the text that was entered
					
			// Send a request to the core to save this game
			final Request request = Network.getInstance().makeRequest("planningpoker/game", HttpMethod.GET); // PUT == create
			request.addObserver(new GetGamesRequestObserver(this)); // add an observer to process the response
			request.send(); // send the request
	}
	
	
	
	/**
	 * Removes all data from the PlanningPokerModel and then adds the games from the database to the model. It then 
	 * refreshes the OverviewTable
	 * @param games is the array of games currently in the database
	 */
	public void receivedGames(Game[] games)
	{
		PlanningPokerModel.getInstance().emptyModel();
		if(games != null)
		{
			PlanningPokerModel.getInstance().addAllGames(games);
		}
		view.updateTable();
	}
	


}