/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Team Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.HelpTopicsPanel;

/**
 * @author Corey
 *
 */
public class HelpPanelController {

	private static HelpPanelController instance = null;
	private HelpTopicsPanel topicsPanel = null;
	
	private HelpPanelController() {
	}
	
	/**
	 * Finds an instance of the OverviewPanelController, or
	 * makes one if there is not one.
	 *
	 * @return an instance of the OverviewPanelController
	 */
	public static HelpPanelController getInstance(){
		if(instance == null){
			instance = new HelpPanelController();
		}
		return instance;
	}
	
	public void setHelpTopics(HelpTopicsPanel htp) {
		topicsPanel = htp;
	}


}
