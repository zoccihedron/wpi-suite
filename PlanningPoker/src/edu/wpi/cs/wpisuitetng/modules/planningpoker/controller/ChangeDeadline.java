/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Team Code On Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.CreateGameInfoPanel;

/**
 *Changes the fields on the CreateGameInfoPanel to have deadline input enabled
 * 
 * @author Code On Bleu
 * @version 1.0
 */
public class ChangeDeadline implements ActionListener {
	private final CreateGameInfoPanel view;
	
	/**
	 * Construct an ChangeDeadline for the given model, view pair
	 * @param createGameInfoPanel the view where the user selects the Deadline? Button
	 */
	public ChangeDeadline(CreateGameInfoPanel createGameInfoPanel) {
		view = createGameInfoPanel;
	}

	/** 
	 * This method is called when the user clicks the Submit button
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		view.EnableOrDisableDeadline();
	}


}
