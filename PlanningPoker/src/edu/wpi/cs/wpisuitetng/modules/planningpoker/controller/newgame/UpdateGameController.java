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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.OverviewPanelController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.CreateGameInfoPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Update button by
 * sending the contents of the fields to the server as an Updated Game.
 * 
 * @author Team Code On Bleu
 * @version 1.0
 */
public class UpdateGameController implements ActionListener {

	private final PlanningPokerModel model;
	private final CreateGameInfoPanel view;
	private Game updatedGame;
	private final boolean startingGame;
	private boolean disregardingNewReq = true;


	/**
	 * Construct an UpdateGameController for the given model, view pair
	 * @param updatedGame the updated game
	 * @param createGameInfoPanel the view where the user enters new messages
	 * @param startingGame whether the game will be started or not
	 */
	public UpdateGameController(CreateGameInfoPanel createGameInfoPanel, 
			Game updatedGame, boolean startingGame) {
		model = PlanningPokerModel.getInstance();
		view = createGameInfoPanel;
		this.updatedGame = updatedGame;
		this.startingGame = startingGame;
	}

	/**
	 * This method is called when the user clicks the Submit button
	 * 
	 * TODO add proper verification of view being a game object.
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
				else{
					currentGame.setStatus(Game.GameStatus.DRAFT);
				}

				// Send a request to the core to save this game
				final Request request = Network.getInstance().makeRequest
						("planningpoker/game", HttpMethod.POST);
				// put the updated game in the body of the request
				request.setBody(currentGame.toJSON());
				// add an observer to process the response
				request.addObserver(new UpdateGameRequestObserver(this));
				request.send(); // send the request
				OverviewPanelController.getInstance().refreshListGames();
			}
			disregardingNewReq = true;
		}
	}

	/**
	 * When the new message is received back from the server, add it to the local model.
	 * @param currentGame the game which will be updated
	 */
	public static void addGameToModel(Game currentGame) {
		PlanningPokerModel.UpdateGame(currentGame);
	}

	/**
	 * Reports a successful message
	 */
	public void addGameToView() {
		view.reportMessage("<html>Game Updated</html>");
	}

	/**
	 * Updates the updated game to the game passed in
	 * @param returnGame
	 */
	public void returnGame(Game returnGame) {
		updatedGame = returnGame;
		view.reportMessage("<html>Game Updated</html>");
		OverviewPanelController.getInstance().refreshListGames();
		OverviewPanelController.getInstance().updateGameSummary(returnGame);
		view.closeNewGameTab(false);
	}

	/**
	 * Getter for the updatedGame
	 */
	public Game getUpdatedGame()
	{
		return updatedGame;
	}
}
