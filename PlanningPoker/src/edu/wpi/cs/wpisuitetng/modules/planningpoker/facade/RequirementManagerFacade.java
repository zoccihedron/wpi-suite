package edu.wpi.cs.wpisuitetng.modules.planningpoker.facade;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

public class RequirementManagerFacade {

	static RequirementManagerFacade instance;

	public static RequirementManagerFacade getInstance() {

		if (instance == null) {
			instance = new RequirementManagerFacade();
		}

		return instance;
	}

	private RequirementManagerFacade() {

	}

	public List<Requirement> getRequirments() {
		GetRequirementsController.getInstance().retrieveRequirements();
		return RequirementModel.getInstance().getRequirements();
	}

}
