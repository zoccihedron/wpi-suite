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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

/**
 * This class is a JPanel.
 * It contains a summary of fields in a game.
 * 
 * @author Code On Bleu
 * @version 1.0
 */
@SuppressWarnings({ "serial" })
public class GameSummaryInfoPanel extends JPanel {

	private final JLabel lblName;
	private final JTextField gameNameText;
	private final JTextArea description;
	private final JTextField deadline;
	private final JLabel lblDeadline;
	private final JLabel lblTitle;
	private final JLabel lblDescription;
	private final JScrollPane descriptionScroll;

	/**
	 * This constructor is to be used when starting from a new game
	 * 
	 * @param parent
	 *            the parent panel for this panel
	 */
	public GameSummaryInfoPanel() {
		
		setBounds(5, 5, 307, 393);
		this.setLayout(new GridBagLayout());

		// Adds the fields and button to the main panel.
		gameNameText = new JTextField();
		gameNameText.setEnabled(false);

		final Border jtextFieldBorder = gameNameText.getBorder();
		
		description = new JTextArea();
		description.setBorder(jtextFieldBorder);
		description.setEnabled(false);
		description.setAutoscrolls(true);
		
		descriptionScroll = new JScrollPane();
		descriptionScroll.add(description);

		deadline = new JTextField();
		deadline.setEnabled(false);

		lblTitle = new JLabel("Game Information");

		lblName = new JLabel("Name:       ");

		lblDeadline = new JLabel("Deadline:");

		lblDescription = new JLabel("Description:");
		infoPanelSetup();
	}

	
	/**
	 * Sets all the grid components for either constructor
	 */
	public void infoPanelSetup() {
		// DEFINE CONSTAINTS
		final GridBagConstraints constraints = new GridBagConstraints();

		final JPanel fakePanel1 = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(fakePanel1, constraints);
		
		final JPanel fakePanel2 = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(fakePanel2, constraints);

		// GAME INFORMATION LABEL
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(lblTitle, constraints);

		// NAME LABEL
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 1;
		add(lblName, constraints);
		lblName.setBorder(new EmptyBorder(5, 5, 5, 5));

		// NAME FIELD
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 1;
		add(gameNameText, constraints);

		// DESCRIPTION LABEL
		lblDescription.setFont(new Font("Dialog", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 10;
		add(lblDescription, constraints);

		
		JScrollPane scrollPane = new JScrollPane(description); 
		description.setEditable(false);
		
		// DESCRIPTION
		description.setLineWrap(true);
		constraints.fill = GridBagConstraints.BOTH;
		
		// DESCRIPTION SCROLL
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 1;
		constraints.gridy = 11;
		add(scrollPane, constraints);

		// DEADLINE LABEL
		lblDeadline.setFont(new Font("Tahoma", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 6;
		constraints.anchor = GridBagConstraints.WEST;
		add(lblDeadline, constraints);
		lblDeadline.setBorder(new EmptyBorder(5, 5, 5, 5));

		// DEADLINE FIELD
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 6;
		add(deadline, constraints);

		
		
	}
	
	/**
	 * fills fields on this panel with info specific to game 
	 * @param game to get info from
	 * 
	 */
	public void updateInfoSummary(Game game){
		gameNameText.setText(game.getName());
		description.setText(game.getDescription());
		if(game.isHasDeadline()){
			deadline.setText(game.getEnd().toString());
		}
		else{
			deadline.setText("This game has no deadline.");
		}
	}

	
}

	
