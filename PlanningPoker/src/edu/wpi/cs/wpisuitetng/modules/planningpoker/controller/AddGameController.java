/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.CreateGameInfoPanel;
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
public class AddGameController implements ActionListener {
	
	private final PlanningPokerModel model;
	private final CreateGameInfoPanel view;
	
	/**
	 * Construct an AddMessageController for the given model, view pair
	 * @param model the model containing the messages
	 * @param createGameInfoPanel the view where the user enters new messages
	 */
	public AddGameController(PlanningPokerModel model, CreateGameInfoPanel createGameInfoPanel) {
		this.model = model;
		this.view = createGameInfoPanel;
	}

	/* 
	 * This method is called when the user clicks the Submit button
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// Get the text that was entered
		if (!view.checkFields()) return;
		
		Game currentGame = view.getGameObject();
		
		// Make sure there is text
		if (currentGame.getName() != "") {
			// Clear the text field
			// TODO - Reset default fields
			
			// Send a request to the core to save this game
			final Request request = Network.getInstance().makeRequest("planningpoker/game", HttpMethod.PUT); // PUT == create
			request.setBody(currentGame.toJSON()); // put the new message in the body of the request
			request.addObserver(new AddGameRequestObserver(this)); // add an observer to process the response
			request.send(); // send the request
		}
	}

	/**
	 * When the new message is received back from the server, add it to the local model.
	 * @param message
	 */
	public void addGameToModel(Game currentGame) {
		model.AddGame(currentGame);
	}

	public void addGameToView(Game returnGame) {
		view.reportMessage("<html>Success: Game Saved!</html>");
		//view.setResultName(returnGame.getName());
		//view.setResultId(Integer.toString(returnGame.getId()));
		//view.setResultNumReqs(Integer.toString(returnGame.getEstimates().size()));
	}
}
