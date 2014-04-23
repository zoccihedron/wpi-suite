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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results.ResultsPanel;

/**
 * Controller for resultsPanel 
 * @author Code On Bleu
 *@version 1.00
 */
public class ViewResultsController {
	
	private ResultsPanel resultsPanel;
	
	/**
	 * This is the constructor for the view results controller
	 */
	public ViewResultsController() {
		resultsPanel = null;
	}
	
	/**
	 * Thie sets the resultsPanel the controller will update
	 * @param resultsPanel The view
	 */
	public void setResultsPanel(ResultsPanel resultsPanel){
		this.resultsPanel = resultsPanel;
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
	 * @param reqid The req to display
	 */
	public void refreshResultsInfo(){
		resultsPanel.refreshDisplay();
	}
}
