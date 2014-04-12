/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * @author Corey
 *
 */
public class EndGameManuallyRequestObserver implements RequestObserver {
	
	private final EndGameManuallyController controller;
	
	/**
	 * Constructor for UpdateGameRequestObserver
	 * @param controller the controller for the class
	 */
	public EndGameManuallyRequestObserver(EndGameManuallyController controller) {
		this.controller = controller;
	}

	/**
	 * Parse the game that was received from the server then pass them to
	 * the controller.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver
	 * #responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();
		
	
		final Game returnGame = Game.fromJson(response.getBody());
		
		// Pass the game back to the controller
		controller.returnGame(returnGame);
		
	}

	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to end a game failed.");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to end a game failed.");
	}

}
