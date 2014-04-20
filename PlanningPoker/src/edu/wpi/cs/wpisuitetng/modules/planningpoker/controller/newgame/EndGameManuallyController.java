/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.OverviewPanelController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.GameSummaryPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the End Game button by
 * sending the contents of the fields to the server as an Ended Game.
 * @author Codon Bleu
 * @version 1.0
 */
public class EndGameManuallyController implements ActionListener {
	
	private final PlanningPokerModel model;
	private final GameSummaryPanel view;
	private Game endedGame;
	private final boolean endingGame;

	
	public EndGameManuallyController(GameSummaryPanel gameSummaryPanel,
										Game endedGame,
										boolean endingGame) {
		model = PlanningPokerModel.getInstance();
		view = gameSummaryPanel;
		this.endedGame = endedGame;
		this.endingGame = endingGame;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		{

			final Game currentGame = view.getGameObject();
			
			if(endingGame) {
				currentGame.setStatus(Game.GameStatus.ENDED);
			}
			else{
				currentGame.setStatus(Game.GameStatus.DRAFT);
			}

			// Send a request to the core to save this game
			final Request request = Network.getInstance().makeRequest
					("Advanced/planningpoker/game/end", HttpMethod.POST);
			// put the updated game in the body of the request
			request.setBody(currentGame.toJSON());
			// add an observer to process the response
			request.addObserver(new EndGameManuallyRequestObserver(this));
			request.send(); // send the request
		}

	}

	/**
	 * Updates the ended game to the game passed in
	 * @param returnGame
	 */
	public void returnGame(Game returnGame) {
		endedGame = returnGame;
		OverviewPanelController.getInstance().refreshListGames();
		OverviewPanelController.getInstance().updateGameSummary(endedGame);
		view.reportSuccess("Game ended successfully!");
	}

	/**
	 * When the new message is received back from the server, add it to the local model.
	 * @param currentGame the game which will be updated
	 */
	public static void addGameToModel(Game currentGame) {
		PlanningPokerModel.UpdateGame(currentGame);
	}
	
	/**
	 * Reports when there has been an error ending the game
	 */
	public void returnErrorGame() {
		view.reportError("Error: Game not ended.");
	}
	
	/**
	 * Getter for the updatedGame
	 */
	public Game getUpdatedGame()
	{
		return endedGame;
	}
	
	public boolean isEndingGame() {
		return endingGame;
	}

	
}
