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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.JPlaceholderTextField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.PlanningPokerModel;


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
	JPlaceholderTextField gameIdText;
	JPlaceholderTextField usernameText;
	JButton inviteUserButton;
	JRadioButton realTime;
	JRadioButton distributed;
	ButtonGroup gameTypeSelection;
	JPlaceholderTextField dateText;
	JPlaceholderTextField numReqsText;
	JButton createGameButton;
	
	JTextField resultName;
	JTextField resultId;
	JTextField resultNumReqs;

	
	public NewGamePanel(PlanningPokerModel gamesModel) {
		gameNameText = new JPlaceholderTextField("Name");
		gameIdText = new JPlaceholderTextField("ID");
		usernameText = new JPlaceholderTextField("Username");
		inviteUserButton = new JButton("Invite User");
		realTime = new  JRadioButton("Real-time");
		distributed = new  JRadioButton("Distributed");
		gameTypeSelection = new ButtonGroup();
		dateText = new JPlaceholderTextField("MM/DD/YY");
		numReqsText = new JPlaceholderTextField("Number of Requirements");
		createGameButton = new JButton("Create Game");
		
		resultName = new JTextField("Name goes here");
		resultId = new JTextField("ID goes here");
		resultNumReqs = new JTextField("Num Reqs go here");
		
		
		setBorder(BorderFactory.createLineBorder(Color.green, 2));
		
		
		//Adds the fields and button to the main panel.
		add(gameNameText);
		add(gameIdText);
		add(usernameText);
		add(inviteUserButton);
		add(realTime);
		add(distributed);
		add(dateText);
		add(numReqsText);
		add(createGameButton);
		
		add(resultName);
		add(resultId);
		add(resultNumReqs);
		
		//Groups the JRadioButtons together so they act like actual radio buttons
		gameTypeSelection.add(realTime);
		gameTypeSelection.add(distributed);
		
		// Maps Create Game button to AddGameController class
		createGameButton.addActionListener(new AddGameController(gamesModel, this));

	}
	
	/**
	 * @return the txtNewMessage JTextField
	 */

	// creates a new game object to be sent to the database
	public Game getGameObject() {
		Game newGame = new Game();
		newGame.setName(gameNameText.getText());
		newGame.setId(Integer.parseInt(gameIdText.getText()));
		newGame.setNumRequirements(Integer.parseInt(numReqsText.getText()));
		return newGame;
	}
	
	public void setResultName(String newName) {
		resultName.setText(newName);
	}

	public void setResultId(String newId) {
		resultId.setText(newId);	
	}

	public void setResultNumReqs(String newNumReqs) {
		resultNumReqs.setText(newNumReqs);
	}
}
