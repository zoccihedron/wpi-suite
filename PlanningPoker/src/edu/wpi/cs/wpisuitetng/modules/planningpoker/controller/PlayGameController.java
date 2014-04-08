package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.EstimationPane;

public class PlayGameController {
	
	private static PlayGameController instance = null;
	 private EstimationPane estimationPane = null;
	
	private PlayGameController() {
		this.setEstimationPane();
	}
	
	public static PlayGameController getInstance(){
		if(instance == null){
			instance = new PlayGameController();
		}
		return instance;
	}

	public void setEstimationPane(){
		estimationPane = new EstimationPane();
	}
	
	public void updateEstimationPane(int id, Game game) {
		 estimationPane.setGame(game);
		 estimationPane.setRequirement(id);
	}
	
	
	

}
