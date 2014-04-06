/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;

/**
 * This class is the view for the toolbar. It has button groups
 * 
 * @author Team Code On Bleu
 * @version 1.0
 *
 */
@SuppressWarnings({"serial"})
public class ToolbarView extends DefaultToolbarView{
	
	private final CreateButtonsPanel createButton = new CreateButtonsPanel(this);
	private final MainView mainview;
	
	/**
	 * Sets up and positions the toolbar buttons
	 * @param mainView 
	 */
	public ToolbarView(MainView mainView)
	{
		this.addGroup(createButton);
		mainview = mainView;
	}
	
	/**
	 * Method getCreateButton.
	
	 * @return CreateButtonsPanel */
	public CreateButtonsPanel getCreateButton(){
		return createButton;
	}

	/**
	 * Creates a CreateNewGame tab in the main view
	 */
	public void crtGamePressed() {
		mainview.createNewGameTab();
	}
}
