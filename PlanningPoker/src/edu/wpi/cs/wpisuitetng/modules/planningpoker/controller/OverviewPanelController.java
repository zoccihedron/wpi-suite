package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.GameSummaryPanel;

public class OverviewPanelController {
	
	private static OverviewPanelController instance = null;
	private GameSummaryPanel gameSummaryPanel = null;
	
	private OverviewPanelController() {
	}
	
	public static OverviewPanelController getInstance(){
		if(instance == null){
			instance = new OverviewPanelController();
		}
		return instance;
	}

	public void setGameSummary(GameSummaryPanel gsp){
		 gameSummaryPanel = gsp;
	}
	
	public void updateGameSummary(Game game) {
		gameSummaryPanel.updateSummary(game);
	}
	
	
	

}
