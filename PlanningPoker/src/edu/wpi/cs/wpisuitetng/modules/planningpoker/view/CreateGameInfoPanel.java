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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import javax.swing.JFormattedTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;


/**
 * This class is a JPanel. It contains all the fields needed to 
 * create a new game
 * 
 * @author Joshua Allard
 *
 */
@SuppressWarnings({"serial"})
public class CreateGameInfoPanel extends JPanel {
	
	JPlaceholderTextField gameNameText;
	JButton inviteUserButton;
	
	JRadioButton realTime;
	JRadioButton distributed;
	ButtonGroup gameTypeSelection;
	
	JPlaceholderTextField dateText;
	JButton createGameButton;

	
	public CreateGameInfoPanel(PlanningPokerModel gamesModel) {
		gameNameText = new JPlaceholderTextField("Name");
		gameNameText.setBounds(13, 9, 86, 20);
		gameTypeSelection = new ButtonGroup();
		
		
		setBorder(BorderFactory.createLineBorder(Color.orange, 2));
		setLayout(null);
		
		
		//Adds the fields and button to the main panel.
		add(gameNameText);
		inviteUserButton = new JButton("Invite User");
		inviteUserButton.setBounds(13, 36, 85, 23);
		
				inviteUserButton.setEnabled(false);
				add(inviteUserButton);
		realTime = new  JRadioButton("Real-time");
		realTime.setBounds(13, 65, 71, 23);
		realTime.setEnabled(false);
		add(realTime);
		
		//Groups the JRadioButtons together so they act like actual radio buttons
		gameTypeSelection.add(realTime);
		distributed = new  JRadioButton("Distributed");
		distributed.setBounds(13, 94, 77, 23);
		distributed.setEnabled(false);
		add(distributed);
		gameTypeSelection.add(distributed);
		dateText = new JPlaceholderTextField("MM/DD/YY");
		dateText.setBounds(13, 123, 86, 20);
		dateText.setEnabled(false);
		add(dateText);
		createGameButton = new JButton("Create Game");
		createGameButton.setBounds(13, 149, 86, 23);
		add(createGameButton);
		
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
		//newGame.setId(Integer.parseInt(gameIdText.getText()));
		return newGame;
	}
	
	public void setResultName(String newName) {
		//resultName.setText(newName);
	}

	public void setResultId(String newId) {
		//resultId.setText(newId);	
	}

	public void setResultNumReqs(String newNumReqs) {
	//	resultNumReqs.setText(newNumReqs);
	}
}
