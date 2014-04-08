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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
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
 * @author Team Code On Bleu
 * @version 1.0
 *
 */
public class AddGameController implements ActionListener {
	
	private final PlanningPokerModel model;
	private final CreateGameInfoPanel view;
	private static User[] users = {};
	
	/**
	 * Construct an AddMessageController for the given model, view pair
	 * @param createGameInfoPanel the view where the user enters new messages
	 */
	public AddGameController(CreateGameInfoPanel createGameInfoPanel) {
		model = PlanningPokerModel.getInstance();
		view = createGameInfoPanel;
	}

	/** 
	 * This method is called when the user clicks the Submit button
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// Get the text that was entered
		if (view.checkFields())
		{
			final Request userRequest = Network.getInstance().makeRequest("core/project", HttpMethod.GET);
			userRequest.addObserver(new GetUserRequestObserver(this));
			userRequest.send();
			final Game currentGame = view.getGameObject(users);
			// Send a request to the core to save this game
			final Request request = Network.getInstance().makeRequest(
					"planningpoker/game", HttpMethod.PUT);
			request.setBody(currentGame.toJSON()); // put the new message in the body of the request
			// add an observer to process the response
			request.addObserver(new AddGameRequestObserver(this));
			request.send(); // send the request
		}
	
	}

	/**
	 * When the new message is received back from the server, add it to the local model.
	 * @param currentGame 
	 */
	public void addGameToModel(Game currentGame) {
		model.AddGame(currentGame);
	}

	/**
	 * Shows the used that the game has been saved
	 */
	public void addGameToView() {
		view.reportMessage("<html>Success: Game Saved!</html>");
		view.closeNewGameTab();
	}

	public static void receivedProject(Project project) {
		users = project.getTeam();
	}
}
