package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.EstimationPane;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.GameInfoPanel;

public class PlayGameController {
	
	private static PlayGameController instance = null;
	 private EstimationPane estimationPane = null;
	 private GameInfoPanel infoPanel = null;
	
	private PlayGameController() {
	}
	
	public static PlayGameController getInstance(){
		if(instance == null){
			instance = new PlayGameController();
		}
		return instance;
	}

	public void setEstimationPane(EstimationPane ep){
		estimationPane = ep;
	}
	
	public void setGameInfoPanel(GameInfoPanel gio){
		infoPanel = gio;
	}
	
	public void updateGameInfo(Game game){
		infoPanel.updatePanel(game);
	}
	
	public void updateEstimationPane(int id, Game game) {
		 estimationPane.setGameAndRequirement(id, game);
		 //estimationPane.setGame(game);
		 //estimationPane.setRequirement(id);
	}
	
	
	

}
