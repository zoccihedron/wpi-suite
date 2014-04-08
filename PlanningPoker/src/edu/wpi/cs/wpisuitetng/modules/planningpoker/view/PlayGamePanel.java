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

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.GameInfoPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.ListRequirementsPanel;


@SuppressWarnings("serial")
public class PlayGamePanel extends JPanel{

	GameInfoPanel GameInfoPanel;
	ListRequirementsPanel listRequirements;
	
	public PlayGamePanel(GameInfoPanel GameInfoPanel, ListRequirementsPanel listRequirements ) {
		this.GameInfoPanel = GameInfoPanel;
		this.listRequirements = listRequirements;
	}
}
