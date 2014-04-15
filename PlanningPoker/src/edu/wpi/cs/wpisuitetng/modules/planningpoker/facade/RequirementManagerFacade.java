/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Code On Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.facade;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This class is a singleton which gets the requirements
 * from the requirement manager
 * 
 * @author Code On Bleu
 * @version 1.0
 */
public class RequirementManagerFacade {

	static RequirementManagerFacade instance;
	private List<Requirement> requirements = new ArrayList<Requirement>();

	/**
	 * Get the single instance of this class
	 * @return instance the single instance of the class
	 */
	public static RequirementManagerFacade getInstance() {

		if (instance == null) {
			instance = new RequirementManagerFacade();
		}

		return instance;
	}

	/**
	 * Constructor for the RequirementManagerFacade
	 */
	private RequirementManagerFacade() {
		
		// Creates an ActionListener to be used by the timer 
		// to update requirements every few seconds
		final ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if(Network.getInstance().getDefaultNetworkConfiguration() != null){
						updateRequirements();
					}
				}

				catch(RuntimeException exception){
					System.err.println(exception.getMessage());
				}
			}
		};

		// Timer will update the requirements every 2 seconds
		final Timer timer = new Timer(2000, actionListener);
		timer.start();
	}


	public void updateRequirements() {
		GetRequirementsControllerFacade.getInstance().retrieveRequirements();

	}
	
	/**
	 * Gets locally stored requirements
	 * @return requirements
	 */
	public List<Requirement> getPreStoredRequirements()
	{
		return requirements;
	}

	public void setRequirements(Requirement[] requirements) {
		// TODO Auto-generated method stub
		this.requirements = new ArrayList<Requirement>(Arrays.asList(requirements));
	}
	
	/**
	 * Sends average of estimations to requirement manager 
	 * Updates estimates in those requirements
	 * @param estimates to send
	 */
	public void sendEstimates(List<Estimate> estimates){
		for(Estimate estimate : estimates){
			Requirement req = requirements.get(estimate.getReqID());
			System.out.println("Req name: "+req.getName()+" Req Mean: "+estimate.getMean());
			
			req.setEstimate((int)estimate.getMean());
			
			Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.POST); 
			request.setBody(req.toJSON()); 
			request.addObserver(new RequestObserver(){

				@Override
				public void responseSuccess(IRequest iReq) {
					// TODO Auto-generated method stub
					GetRequirementsController.getInstance().retrieveRequirements();
				}

				@Override
				public void responseError(IRequest iReq) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void fail(IRequest iReq, Exception exception) {
					// TODO Auto-generated method stub
					
				}
				
			});
			request.send(); 
			
			
			System.out.println("Check: requ name: "+ req.getName() + " est " + req.getEstimate());
		}
	}

}
