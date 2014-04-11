/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.CreateGameInfoPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * TODO - comment this!
 * @author Codon Bleu
 *
 */
public class EndGameManuallyController implements ActionListener {
	
	private final PlanningPokerModel model;
	private final CreateGameInfoPanel view;
	private Game updatedGame;
	private final boolean endingGame;

	
	public EndGameManuallyController(CreateGameInfoPanel createGameInfoPanel, Game updatedGame, boolean endingGame) {
		model = PlanningPokerModel.getInstance();
		view = createGameInfoPanel;
		this.updatedGame = updatedGame;
		this.endingGame = endingGame;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// 		if (view.checkFields())
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
					("planningpoker/game", HttpMethod.POST);
			// put the updated game in the body of the request
			request.setBody(currentGame.toJSON());
			// add an observer to process the response
			request.addObserver(new UpdateGameRequestObserver(this));
			request.send(); // send the request
		}

	}

}
