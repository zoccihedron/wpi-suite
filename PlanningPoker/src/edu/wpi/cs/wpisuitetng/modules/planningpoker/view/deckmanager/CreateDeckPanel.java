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
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.JPlaceholderTextField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;


/**
 * This class is the panel that allows the user to create a deck with a given name
 * The user cannot create a blank name or a name that is the placeholder text
 * @author Code on Bleu
 * @version 1.0
 */
public class CreateDeckPanel extends JPanel{

	private final JPlaceholderTextField deckName;
	private final JButton btnSubmit;
	private final Deck deck;
	private final ListDecksPanel listDecksPanel;
	private final String PLACEHOLDER_TEXT = "Enter Deck Name Here";

	/**
	 * Constructor for the create deck panel that initializes the positions
	 * of objects in it and initializes action listeners
	 * @param listDecksPanel the list decks panel
	 */
	public CreateDeckPanel(ListDecksPanel listDecksPanel){

		this.listDecksPanel = listDecksPanel;
		deckName = new JPlaceholderTextField(PLACEHOLDER_TEXT);
		btnSubmit = new JButton("Create Deck");

		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0.75;
		constraints.anchor = GridBagConstraints.WEST;
		this.add(deckName, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 5, 0, 0);
		constraints.gridwidth = 1;
		constraints.weightx = 0.25;
		constraints.anchor = GridBagConstraints.EAST;
		this.add(btnSubmit, constraints);
		btnSubmit.setEnabled(false);

		try {
		    Image img = ImageIO.read(getClass().getResource("addDeck.png"));
		    btnSubmit.setIcon(new ImageIcon(img));   
		} 
		catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		
		deck = null;

		btnSubmit.addActionListener(new AddDeckController(this, PLACEHOLDER_TEXT, listDecksPanel));
		deckName.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(deckName.getText().trim().equals("") ||
						deckName.getText().trim().equals(PLACEHOLDER_TEXT)){
					btnSubmit.setEnabled(false);
				}
				else{
					btnSubmit.setEnabled(true);
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(deckName.getText().trim().equals("") ||
						deckName.getText().trim().equals(PLACEHOLDER_TEXT)){
					btnSubmit.setEnabled(false);
				}
				else{
					btnSubmit.setEnabled(true);
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				//Intentionally Left Blank
				
			}
		});
	}

	/**
	 * @return the deck
	 */
	public Deck getDeck() {
		return deck;
	}

	/**
	 * @return the name the user wants the text to be
	 */
	public String getDeckName() {
		return deckName.getText();
	}

	public JButton getBtnSubmit() {
		return btnSubmit;
	}
}
