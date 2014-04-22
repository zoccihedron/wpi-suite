/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.ManageDeckController;

/**
 * Button Panel for the deck manager panel.
 * 
 * @author Code On Bleu
 *
 */
public class DeckControlsPane extends JPanel {
	JButton btnCancel;
	JButton btnSave;
	JButton btnRemoveDeck;
	JButton btnRemoveCard;
	JButton btnAddCard;
	private JTextField fieldAddCard;
	
	/**
	 * Construct all the buttons and their action listeners
	 */
	public DeckControlsPane() {
		GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		
		//CANCEL BUTTON
		 btnCancel = new JButton("Cancel");
		//TODO
		//btnCancel.addActionListener(DeckManagerController.cancel());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.add(btnCancel, constraints);
		
		//SAVE BUTTON
		 btnSave = new JButton("Save");
		//TODO
		//btnCancel.addActionListener(DeckManagerController.cancel());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.add(btnSave, constraints);
		
		//REMOVE DECK BUTTON
		 btnRemoveDeck = new JButton("Remove Deck");
		//TODO
		//btnCancel.addActionListener(DeckManagerController.cancel());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.add(btnRemoveDeck, constraints);
		
		//REMOVE CARD BUTTON
		 btnRemoveCard = new JButton("Remove Card");
		//TODO
		//btnCancel.addActionListener(DeckManagerController.cancel());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		this.add(btnRemoveCard, constraints);
		
		//ADD CARD BUTTON
		 btnAddCard = new JButton("Add Card");
		//TODO
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		this.add(btnAddCard, constraints);
		
		//ADD CARD TEXT FIELD
		 fieldAddCard = new JTextField();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		this.add(fieldAddCard, constraints);
	}
	
	public int sendCard() {
		return Integer.valueOf(fieldAddCard.getText());
	}
}
