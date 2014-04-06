package edu.wpi.cs.wpisuitetng.modules.planningpoker.facade;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.network.Network;

public class RequirementManagerFacade {

	static RequirementManagerFacade instance;
	private List<Requirement> requirements = new ArrayList<Requirement>();

	public static RequirementManagerFacade getInstance() {

		if (instance == null) {
			instance = new RequirementManagerFacade();
		}

		return instance;
	}

	private RequirementManagerFacade() {
		
		// Creates an ActionListener to be used by the timer to update requirements every few seconds
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if(Network.getInstance().getDefaultNetworkConfiguration() != null){
						requirements = getRequirments();
					}
				}

				catch(RuntimeException exception){
				}
			}
		};

		// Timer will update the requirements every 2 seconds
		Timer timer = new Timer(2000, actionListener);
		timer.start();
	}

	public List<Requirement> getRequirments() {
		GetRequirementsController.getInstance().retrieveRequirements();
		return RequirementModel.getInstance().getRequirements();
	}
	
	/**
	 * Gets locally stored requirements
	 * @return requirements
	 */
	public List<Requirement> getPreStoredRequirements()
	{
		return requirements;
	}

}
