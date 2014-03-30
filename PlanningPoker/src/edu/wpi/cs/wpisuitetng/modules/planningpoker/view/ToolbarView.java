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
 * @author Ryan Baker
 *
 */
@SuppressWarnings({"serial"})
public class ToolbarView extends DefaultToolbarView{
	
	private CreateButtonsPanel createButton = new CreateButtonsPanel();
	
	/**
	 * Sets up and positions the toolbar buttons
	 */
	public ToolbarView()
	{
		this.addGroup(createButton);
	}
	
	/**
	 * Method getCreateButton.
	
	 * @return CreateButtonsPanel */
	public CreateButtonsPanel getEditButton(){
		return createButton;
	}
}
