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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.AddCardController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.MultiSelectController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.RemoveCardController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.RemoveDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;

/**
 * Button Panel for the deck manager panel.
 * 
 * @author Code On Bleu
 * @version 1.0
 *
 */
public class DeckControlsPanel extends JPanel {
<<<<<<< HEAD

	private JButton btnRemoveDeck;
	private JButton btnRemoveCard;
	private JButton btnAddCard;
	private JTextField fieldAddCard;
	private Deck deck;
	private CardViewPanel cardView;
	private ListDecksPanel listDecksPanel;
	
	public JTextField fieldDeckName;
	private JButton btnUpdateDeckName;
	
	private JLabel deckRemovedMessage;
	private JLabel message;
	private ButtonGroup cardSelectGroup;
	private JRadioButton singleSelectBtn;
	private JRadioButton multiSelectBtn;
	private final JLabel saveStatusMessage;

	/**
	 * Construct all the buttons and their action listeners.
	 * @param cardView the card view panel
	 * @param listDecksPanel the list deck panel
	 */
	public DeckControlsPanel(CardViewPanel cardView, ListDecksPanel listDecksPanel) {
		this.cardView = cardView;
		this.listDecksPanel = listDecksPanel;


		final GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		//DECK NAME TEXT FIELD
		fieldDeckName = new JTextField("");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weightx = 0.5;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.SOUTH;
		this.add(fieldDeckName, constraints);
		
		//DECK NAME TEXT FIELD
		btnUpdateDeckName = new JButton("Rename Deck");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.weightx = 0.5;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.SOUTH;
		this.add(btnUpdateDeckName, constraints);
				
		//REMOVE DECK BUTTON
		btnRemoveDeck = new JButton("Remove Deck");
		//TODO: btnCancel.addActionListener(DeckManagerController.cancel());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.add(btnRemoveDeck, constraints);

		//REMOVE CARD BUTTON
		btnRemoveCard = new JButton("Remove Card");
		//TODO: btnCancel.addActionListener(DeckManagerController.cancel());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		this.add(btnRemoveCard, constraints);

		//ADD CARD BUTTON
		btnAddCard = new JButton("Add Card");
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

		//MULTI SELECT RADIO BUTTONS
		singleSelectBtn = new JRadioButton("Single Selection");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.add(singleSelectBtn, constraints);

		multiSelectBtn = new JRadioButton("Multiple Selection");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.add(multiSelectBtn, constraints);

		cardSelectGroup = new ButtonGroup();
		cardSelectGroup.add(singleSelectBtn);
		cardSelectGroup.add(multiSelectBtn);


		deckRemovedMessage = new JLabel();
		deckRemovedMessage.setVisible(false);
		this.add(deckRemovedMessage);

		message = new JLabel();

		saveStatusMessage = new JLabel();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		constraints.anchor = GridBagConstraints.WEST;
		this.add(saveStatusMessage, constraints);

		deck = null;


	}

	/**
	 * Configures the action listeners for the buttons on the
	 * control panel.
	 *
	 * @param newDeck the deck that is being edited
	 */
	public void setActionListeners(Deck newDeck) {
		deck = newDeck;

		if (deck.getDeckCreator().equals(
				ConfigManager.getInstance().getConfig().getUserName())) {

			deckRemovedMessage.setVisible(false);

			btnAddCard.setVisible(true);
			btnRemoveCard.setVisible(true);
			btnRemoveDeck.setVisible(true);
			fieldAddCard.setVisible(true);
			singleSelectBtn.setVisible(true);
			multiSelectBtn.setVisible(true);

			if (!(btnAddCard.getActionListeners().length == 0)) {
				btnAddCard
						.removeActionListener(btnAddCard.getActionListeners()[0]);
			}
			if (!(btnRemoveCard.getActionListeners().length == 0)) {
				btnRemoveCard.removeActionListener(btnRemoveCard
						.getActionListeners()[0]);
			}
			if (!(btnRemoveDeck.getActionListeners().length == 0)) {
				btnRemoveDeck.removeActionListener(btnRemoveDeck
						.getActionListeners()[0]);
			}
			if (!(singleSelectBtn.getActionListeners().length == 0)) {
				singleSelectBtn.removeActionListener(singleSelectBtn
						.getActionListeners()[0]);
			}
			if (!(multiSelectBtn.getActionListeners().length == 0)) {
				multiSelectBtn.removeActionListener(multiSelectBtn
						.getActionListeners()[0]);
			}

			btnAddCard.addActionListener(new AddCardController(this));
			btnRemoveCard.addActionListener(new RemoveCardController(this));
			btnRemoveDeck.addActionListener(new RemoveDeckController(this));

			MultiSelectController multiSelectController = new MultiSelectController(
					this);
			singleSelectBtn.addActionListener(multiSelectController);
			multiSelectBtn.addActionListener(multiSelectController);

			if (deck.canSelectMultipleCards()) {
				singleSelectBtn.setSelected(false);
				multiSelectBtn.setSelected(true);
			} else {
				singleSelectBtn.setSelected(true);
				multiSelectBtn.setSelected(false);
			}

		} else {
			btnAddCard.setVisible(false);
			btnRemoveCard.setVisible(false);
			btnRemoveDeck.setVisible(false);
			fieldAddCard.setVisible(false);
			singleSelectBtn.setVisible(false);
			multiSelectBtn.setVisible(false);

			deckRemovedMessage.setVisible(true);
			deckRemovedMessage.setForeground(Color.BLUE);
			deckRemovedMessage.setText("You cannot edit this deck");
		}
	}

	/**
	 * Disables the visibility and controls for the buttons.
	 *
	 */
	public void disableControls(){
		btnAddCard.setVisible(false);
		btnRemoveCard.setVisible(false);
		btnRemoveDeck.setVisible(false);
		fieldAddCard.setVisible(false);
		singleSelectBtn.setVisible(false);
		multiSelectBtn.setVisible(false);
	}

	/**
	 * Returns the card value selected.
	 *
	 * @return card value
	 */
	public int getCardValue() {
		return Integer.valueOf(fieldAddCard.getText());
	}

	/**
	 * Adds the card value to the deck.
	 *
	 * @param cardValue the value of the card
	 */
	public void addToDeck(int cardValue) {
		deck.addCard(cardValue);
	}

	/**
	 * Returns the deck being constructed.
	 *
	 * @return the deck
	 */
	public Deck getDeck() {
		return deck;
	}

	/**
	 * Returns the card view panel.
	 *
	 * @return the card view panel
	 */
	public CardViewPanel getCardView(){
		return cardView;
	}

	/**
	 * Returns the list decks panel.
	 *
	 * @return the list decks panel
	 */
	public ListDecksPanel getListDecksPanel() {
		return listDecksPanel;
	}

	/**
	 * Updates the deck removal message to the string provided
	 *
	 * @param text the deck removal message.
	 */
	public void updateDeckRemovalMessage(String text) {
		deckRemovedMessage.setVisible(true);
		deckRemovedMessage.setText(text);
	}

	/**
	 * Checks if the current card value is valid or not.
	 *
	 * @return true if a valid card, false otherwise.
	 */
	public boolean checkFields() {
		final int estimate;
		try{
			reportError("<html></html>");
			estimate = Integer.parseInt(fieldAddCard.getText());

		} catch (NumberFormatException e){
			reportError("<html>Error: Estimate must be an integer.</html>");
			btnAddCard.setEnabled(false);
			return false;
		}

		if(estimate < 0) {
			reportError("<html>Error: Estimate must be an integer greater than 0.</html>");
			btnAddCard.setEnabled(false);
			return false;
		}

		if(estimate == 0){
			reportInfo("<html>0 indicates that you are unable to "
					+ "estimate this requirement. </html>");
			btnAddCard.setEnabled(true);
			return true;
		}


		btnAddCard.setEnabled(true);
		return true;
	}

	/**
	 * Used to report errors in a status message.
	 *
	 * @param string the error message
	 */
	public void reportError(String string) {
		message.setText(string);
		message.setForeground(Color.RED);
	}

	/**
	 * Used to report info in a status message
	 *
	 * @param string the info message
	 */
	public void reportInfo(String string){
		message.setText(string);
		message.setForeground(Color.BLUE);
	}

	/**
	 * Obtains the test for the single selection
	 *
	 * @return the single selection text.
	 */
	public String getSingleSelectText(){
		return singleSelectBtn.getText();
	}

	/**
	 * Sets the deck to single or multi selection status.
	 *
	 * @param multiSelectStatus True if multi-selection, false if single-selection
	 */
	public void setDeckMultiSelectStatus(boolean multiSelectStatus) {
		deck.setCanSelectMultipleCards(multiSelectStatus);
	}

	/**
	 * Reports the status of changes being saved to the deck
	 *
	 * @param message the save message
	 */
	public void saveMessage(String message) {
		saveStatusMessage.setText(message);
		saveStatusMessage.setForeground(Color.BLUE);
	}

}
