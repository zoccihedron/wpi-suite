package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.results;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results.ResultsPanel;

public class ViewResultsController {
	
	private ResultsPanel resultsPanel;
	
	public ViewResultsController() {
		resultsPanel = null;
	}
	
	public void setResultsPanel(ResultsPanel resultsPanel){
		this.resultsPanel = resultsPanel;
	}
	public void updateResultsInfo(int reqid){
		this.resultsPanel.updateDisplay(reqid);
	}
}
