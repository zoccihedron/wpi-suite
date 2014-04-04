package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.EstimationPane;

public class VoteActionController implements ActionListener {
	
	private final EstimationPane view;
	
	
	public VoteActionController(EstimationPane view){
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(!view.checkField()) return;
		
/*		
		  final Request request = Network.getInstance().makeRequest("planningpoker/???, HttpMethod.PUT);
		  request.setBody(???.toJSON());
		  request.addObserver(???
		  request.send();
*/
	}

}
