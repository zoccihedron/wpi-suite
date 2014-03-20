package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import java.util.List;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;

public class PlanningPoker implements IJanewayModule {
	
	private List<JanewayTabModel> tabs;
	
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "PlanningPoker";
	}

	@Override
	public List<JanewayTabModel> getTabs() {
		// TODO Auto-generated method stub
		return tabs;
	}

}
