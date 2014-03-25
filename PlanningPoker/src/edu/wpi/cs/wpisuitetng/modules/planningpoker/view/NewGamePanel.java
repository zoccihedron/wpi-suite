/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.JPlaceholderTextField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Game;


/**
 * This class is a JPanel. It contains all the fields needed to 
 * create a new game
 * 
 * @author Joshua Allard
 *
 */
@SuppressWarnings({"serial"})
public class NewGamePanel extends JPanel {
	
	JPlaceholderTextField gameNameText;
	JPlaceholderTextField usernameText;
	JButton inviteUserButton;
	JRadioButton realTime;
	JRadioButton distributed;
	ButtonGroup gameTypeSelection;
	JPlaceholderTextField DateText;
	JButton createGameButton;

	
	public NewGamePanel(Game gameModel) {
		gameNameText = new JPlaceholderTextField("Name");
		usernameText = new JPlaceholderTextField("Username");
		inviteUserButton = new JButton("Invite User");
		realTime = new  JRadioButton("Real-time");
		distributed = new  JRadioButton("Distributed");
		gameTypeSelection = new ButtonGroup();
		DateText = new JPlaceholderTextField("MM/DD/YY");
		createGameButton = new JButton("Create Game");
		
		setBorder(BorderFactory.createLineBorder(Color.green, 2));
		
		
		//Adds the fields and button to the main panel.
		add(gameNameText);
		add(usernameText);
		add(inviteUserButton);
		add(realTime);
		add(distributed);
		add(DateText);
		add(createGameButton);
		
		//Groups the JRadioButtons together so they act like actual radio buttons
		gameTypeSelection.add(realTime);
		gameTypeSelection.add(distributed);

	}
	
	/**
	 * @return the txtNewMessage JTextField
	 */

}
