package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.EstimationPane;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class VoteActionController implements ActionListener {
	
	private final EstimationPane view;
	private final Game game;
	private int estimateValue;
	
	
	
	public VoteActionController(EstimationPane view, Game game){
		this.view = view;
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(!view.checkField()) return;
		
		String name = ConfigManager.getInstance().getConfig().getUserName();
		estimateValue = view.getEstimate();
		
		Estimate estimate = game.findEstimate(view.getReqID());
		estimate.makeEstimate(name,estimateValue);
		// Send a request to the core to update this game
		final Request request = Network.getInstance().makeRequest("planningpoker/game", HttpMethod.POST); // POST == update
		request.setBody(game.toJSON()); // put the new message in the body of the request
		request.addObserver(new VoteActionObserver(this)); // add an observer to process the response
		request.send(); // send the request
		
		
		

	}

	public void reportSuccess() {
		view.reportSuccess();
		// TODO Auto-generated method stub
		
	}

}
