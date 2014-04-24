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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.AddCardController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.CancelDeckChangesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.RemoveCardController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.RemoveDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;

/**
 * Button Panel for the deck manager panel.
 * 
 * @author Code On Bleu
 *
 */
public class DeckControlsPanel extends JPanel {
	//private JButton btnCancel;
	//private JButton btnSave;
	private JButton btnRemoveDeck;
	private JButton btnRemoveCard;
	private JButton btnAddCard;
	private JTextField fieldAddCard;
	private Deck deck;
	private CardViewPanel cardView;
	private ListDecksPanel listDecksPanel;
	private JLabel deckRemovedMessage;
	private JLabel message;
	
	/**
	 * Construct all the buttons and their action listeners
	 */
	public DeckControlsPanel(CardViewPanel cardView, ListDecksPanel listDecksPanel) {
		this.cardView = cardView;
		this.listDecksPanel = listDecksPanel;
		
		GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		
		/*//CANCEL BUTTON
		btnCancel = new JButton("Cancel");
		//TODO
		//btnCancel.addActionListener(DeckManagerController.cancel());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.add(btnCancel, constraints);*/
		
		/*//SAVE BUTTON
		btnSave = new JButton("Save");
		//TODO
		//btnCancel.addActionListener(DeckManagerController.cancel());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		this.add(btnSave, constraints); */
		
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
		
		deckRemovedMessage = new JLabel();
		deckRemovedMessage.setVisible(false);
		this.add(deckRemovedMessage);
		
		message = new JLabel();
		
		deck = null;
		
		
	}
	
	public void setActionListeners(Deck newDeck){
		this.deck = newDeck;
		
		if(deck.getDeckCreator().equals(ConfigManager.getInstance().getConfig().getUserName()))
		{
		deckRemovedMessage.setVisible(false);
		
		btnAddCard.setVisible(true);
		btnRemoveCard.setVisible(true);
		//btnCancel.setVisible(true);
		btnRemoveDeck.setVisible(true);
		//btnSave.setVisible(true);
		fieldAddCard.setVisible(true);
		
		btnAddCard.addActionListener(new AddCardController(this));
		btnRemoveCard.addActionListener(new RemoveCardController(this));
		//btnCancel.addActionListener(new CancelDeckChangesController(this));
		btnRemoveDeck.addActionListener(new RemoveDeckController(this));
		} else{
			btnAddCard.setVisible(false);
			btnRemoveCard.setVisible(false);
			//btnCancel.setVisible(false);
			btnRemoveDeck.setVisible(false);
			//btnSave.setVisible(false);
			fieldAddCard.setVisible(false);
			
			deckRemovedMessage.setVisible(true);
			deckRemovedMessage.setForeground(Color.BLUE);
			deckRemovedMessage.setText("You cannot edit this deck");
		}
	}
	
	public void disableControls(){
		btnAddCard.setVisible(false);
		btnRemoveCard.setVisible(false);
		//btnCancel.setVisible(false);
		btnRemoveDeck.setVisible(false);
		//btnSave.setVisible(false);
		fieldAddCard.setVisible(false);
	}
	
	public int getCardValue() {
		return Integer.valueOf(fieldAddCard.getText());
	}

	public void addToDeck(int cardValue) {
		this.deck.addCard(cardValue);
	}

	public Deck getDeck() {
		return deck;
	}
	
	public CardViewPanel getCardView(){
		return cardView;
	}

	public ListDecksPanel getListDecksPanel() {
		return listDecksPanel;
	}

	public void updateDeckRemovalMessage(String text) {
		deckRemovedMessage.setVisible(true);
		deckRemovedMessage.setText(text);
	}

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
			reportInfo("<html>0 indicates that you are unable to estimate this requirement. </html>");
			btnAddCard.setEnabled(true);
			return true;
		}
		
		
		btnAddCard.setEnabled(true);
		return true;
	}
	
	public void reportError(String string) {
		message.setText(string);
		message.setForeground(Color.RED);
	}
	
	public void reportInfo(String string){
		message.setText(string);
		message.setForeground(Color.BLUE);
	}
}
