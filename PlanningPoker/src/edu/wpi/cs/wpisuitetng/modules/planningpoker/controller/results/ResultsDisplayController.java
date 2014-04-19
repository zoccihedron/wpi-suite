package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.results;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame.VoteActionObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results.ResultsDisplayPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class ResultsDisplayController implements ActionListener{
	private ResultsDisplayPanel view;
	private Game game;
	
	public ResultsDisplayController(ResultsDisplayPanel view, Game game){
		this.view = view;
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(view.checkField()){
			int finalEstimate = view.getFinalEstimate();
			int reqid = view.getReqid();
			
			Estimate estimate = game.findEstimate(reqid);
			estimate.setFinalEstimate(finalEstimate);
			
			//TODO make it so the tree refreshes itself
		}
	}

}
