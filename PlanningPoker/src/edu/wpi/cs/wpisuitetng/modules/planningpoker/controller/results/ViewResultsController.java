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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.results;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results.EstimateTreePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results.ResultsPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Controller for resultsPanel 
 * @author Code On Bleu
 *@version 1.00
 */
public class ViewResultsController implements ActionListener {
	private Estimate currentSelectedEstimate;
	private ResultsPanel resultsPanel;
	private EstimateTreePanel estimateTreePanel;
	
	/**
	 * This is the constructor for the view results controller
	 */
	public ViewResultsController() {
		resultsPanel = null;
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		currentSelectedEstimate.unSelectFinalEstimate();
	
		// Send a request to the core to mark this estimate as being unselected
		final Request request = Network.getInstance().makeRequest(
				"Advanced/planningpoker/game/unselectEstimate", 
				HttpMethod.POST); // POST is update
		request.setBody(currentSelectedEstimate.toJSON()); 
		request.addObserver(new RequestObserver(){
			@Override
			public void responseSuccess(IRequest iReq) {
			}
	
			@Override
			public void responseError(IRequest iReq) {
			}
	
			@Override
			public void fail(IRequest iReq, Exception exception) {	
			}
			
		}); 
	
		request.send();
		resultsPanel.refresh();
	
	}
	
	/**
	 * Update estimate value stored in this class
	 * @param estimate the estimate passed in
	 */
	public void updateEstimate(Estimate estimate){
		currentSelectedEstimate = estimate;
	}
	

	
	/**
	 * The sets the resultsPanel the controller will update
	 * @param resultsPanel The view
	 */
	public void setResultsPanel(ResultsPanel resultsPanel){
		this.resultsPanel = resultsPanel;
	}
	
	/**
	 * This sets the estimateTreePanel the controller will update
	 * @param estimateTreePanel the estimateTreePanel view
	 */
	public void setEstimateTreePanel(EstimateTreePanel estimateTreePanel){
		this.estimateTreePanel = estimateTreePanel;
	}
	
	/**
	 * Updates the GUI to display the results of the req specified by the req IID
	 * @param reqid The req to display
	 */
	public void updateResultsInfo(int reqid){
		resultsPanel.updateDisplay(reqid);
	}
	
	/**
	 * Updates the GUI to display the results of the req last selected
	 */
	public void refreshResultsInfo(){
		resultsPanel.refreshDisplay();
	}
	
	/**
	 * Reset the unselect estimate button in estimate tree 
	 * panel to be enabled or not based on the given boolean
	 * @param valid if the button is valid
	 */
	public void setUnselectButtonEnabled(boolean valid){
		estimateTreePanel.setUnselectButtonEnabled(valid);
	}
}
