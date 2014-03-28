package edu.wpi.cs.wpisuitetng.modules.planningpoker.controllers;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Update button by
 * adding the contents of the requirement text fields to the model as a new
 * requirement.
 * @version $Revision: 1.0 $
 * @author justinhess
 */
public class AddGameController{
	
	private static AddGameController instance;
	private AddGameRequestObserver observer;
	
	/**
	 * Construct an AddGameController for the given model, view pair
	
	
	 */
	private AddGameController() {
		observer = new AddGameRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the AddGameController or creates one if it does not
	 * exist. */
	public static AddGameController getInstance()
	{
		if(instance == null)
		{
			instance = new AddGameController();
		}
		
		return instance;
	}

	/**
	 * This method adds a Game to the server.
	 * @param newGame is the Game to be added to the server.
	 */
	public void addGame(Game newGame) 
	{
		final Request request = Network.getInstance().makeRequest("planningpokerentitymanager/Game", HttpMethod.PUT); // PUT == create
		request.setBody(newGame.toJSON()); // put the new Game in the body of the request
		request.addObserver(observer); // add an observer to process the response
		request.send(); 
	}
}