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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.OverviewPanelController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.CreateGameInfoPanel;
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
	private boolean startingGame = false;
	private boolean endingGame = false;
	private boolean disregardingNewReq = true;

	/**
	 * Construct an AddMessageController for the given model, view pair
	 * @param createGameInfoPanel the view where the user enters new messages
	 * @param startingGame whether the game is being started or not
	 * @param endingGame whether or not the game is ending
	 */
	public AddGameController(CreateGameInfoPanel createGameInfoPanel, 
			boolean startingGame, boolean endingGame) {
		model = PlanningPokerModel.getInstance();
		view = createGameInfoPanel;
		this.startingGame = startingGame;
		this.endingGame = endingGame;
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
			if(view.getReqPanel().isCreatingNewReq()){
				final Object options[] = {
						"Yes", "No"
				};
				final int i = JOptionPane.showOptionDialog(view.getParent().getParent(), 
						"Your new requirement will not be saved, would you like to exit anyways?",
						"Exit?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null, options, options[1]);
				disregardingNewReq = (i == 0);
			}
			if(disregardingNewReq){
				view.disableOrEnableButtonsOnParent(false);
				view.initDefaults();
				final Game currentGame = view.getGameObject();

				if(startingGame){
					currentGame.setStatus(Game.GameStatus.IN_PROGRESS);
				}
				else if(endingGame){
					currentGame.setStatus(Game.GameStatus.ENDED);
				}
				else{
					currentGame.setStatus(Game.GameStatus.DRAFT);
				}

				// Send a request to the core to save this game
				final Request request = Network.getInstance().makeRequest(
						"planningpoker/game", HttpMethod.PUT);
				// put the new message in the body of the request
				request.setBody(currentGame.toJSON()); 
				// add an observer to process the response
				request.addObserver(new AddGameRequestObserver(this));
				request.send(); // send the request
			}
			disregardingNewReq = true;
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
	 * 
	 * @param game the game to update the summary with
	 */
	public void addGameToView(Game game) {
		view.reportMessage("<html>Game Saved.</html>");
		OverviewPanelController.getInstance().refreshListGames();
		OverviewPanelController.getInstance().updateGameSummary(game);
		view.closeNewGameTab(false);
	}

	/**
	 * Sets all of the users in a project to add to a game
	 * @param project The project
	 */
	public static void receivedProject(Project project) {
		users = project.getTeam();
	}

	public static User[] getUsers()
	{
		return users;
	}
}
