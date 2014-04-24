package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.EstimationPane;

public class ViewSumController {
	
	private EstimationPane estimationPane;
	
	public ViewSumController(EstimationPane pane){
		estimationPane = pane;
	}
	
	public void updateSum(int sum){
		estimationPane.updateSum(sum);
		
	}
	

}
