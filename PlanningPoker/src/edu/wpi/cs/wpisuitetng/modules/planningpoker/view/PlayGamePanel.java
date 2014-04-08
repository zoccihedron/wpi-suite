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


/**
 * TODO - add proper commenting for this class!
 * 
 * @author Codon Bleu
 * @version 1.0
 *
 */
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.GameInfoPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.ListRequirementsPanel;


@SuppressWarnings("serial")
public class PlayGamePanel extends JPanel{

	GameInfoPanel GameInfoPanel;
	ListRequirementsPanel listRequirements;
	
	/**
	 * TODO - create comment
	 * @param GameInfoPanel The game info panel being used
	 * @param listRequirements The list of requirements for this game
	 */
	public PlayGamePanel(GameInfoPanel GameInfoPanel, ListRequirementsPanel listRequirements ) {
		this.GameInfoPanel = GameInfoPanel;
		this.listRequirements = listRequirements;
	}
}
