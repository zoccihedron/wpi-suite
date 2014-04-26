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
import javax.swing.ButtonModel;
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
 *
 */
public class DeckControlsPanel extends JPanel {
	private JButton btnRemoveDeck;
	private JButton btnRemoveCard;
	private JButton btnAddCard;
	private JTextField fieldAddCard;
	private Deck deck;
	private CardViewPanel cardView;
	private ListDecksPanel listDecksPanel;
	private JLabel deckRemovedMessage;
	private JLabel message;
	private ButtonGroup cardSelectGroup;
	private JRadioButton singleSelectBtn;
	private JRadioButton multiSelectBtn;
	
	/**
	 * Construct all the buttons and their action listeners
	 */
	public DeckControlsPanel(CardViewPanel cardView, ListDecksPanel listDecksPanel) {
		this.cardView = cardView;
		this.listDecksPanel = listDecksPanel;
		
		
		GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
				
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
		
		deck = null;
		
		
	}
	
	public void setActionListeners(Deck newDeck){
		this.deck = newDeck;
		
		if(deck.getDeckCreator().equals(ConfigManager.getInstance().getConfig().getUserName()))
		{
		deckRemovedMessage.setVisible(false);
		
		btnAddCard.setVisible(true);
		btnRemoveCard.setVisible(true);
		btnRemoveDeck.setVisible(true);
		fieldAddCard.setVisible(true);
		singleSelectBtn.setVisible(true);
		multiSelectBtn.setVisible(true);
		
		if(!(btnAddCard.getActionListeners().length == 0)){
			btnAddCard.removeActionListener(btnAddCard.getActionListeners()[0]);
		}
		if(!(btnRemoveCard.getActionListeners().length == 0)){
			btnRemoveCard.removeActionListener(btnRemoveCard.getActionListeners()[0]);
		}
		if(!(btnRemoveDeck.getActionListeners().length == 0)){
			btnRemoveDeck.removeActionListener(btnRemoveDeck.getActionListeners()[0]);
		}
		if(!(singleSelectBtn.getActionListeners().length == 0)){
			singleSelectBtn.removeActionListener(singleSelectBtn.getActionListeners()[0]);
		}
		if(!(multiSelectBtn.getActionListeners().length == 0)){
			multiSelectBtn.removeActionListener(multiSelectBtn.getActionListeners()[0]);
		}
		
		btnAddCard.addActionListener(new AddCardController(this));
		btnRemoveCard.addActionListener(new RemoveCardController(this));
		btnRemoveDeck.addActionListener(new RemoveDeckController(this));
		
		MultiSelectController multiSelectController = new MultiSelectController(this);
		singleSelectBtn.addActionListener(multiSelectController);
		multiSelectBtn.addActionListener(multiSelectController);

		if(deck.canSelectMultipleCards()){
			singleSelectBtn.setSelected(false);
			multiSelectBtn.setSelected(true);
		} else {
			singleSelectBtn.setSelected(true);
			multiSelectBtn.setSelected(false);
			}
		
		
		} else{
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
	
	public void disableControls(){
		btnAddCard.setVisible(false);
		btnRemoveCard.setVisible(false);
		btnRemoveDeck.setVisible(false);
		fieldAddCard.setVisible(false);
		singleSelectBtn.setVisible(false);
		multiSelectBtn.setVisible(false);
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
	
	public String getSingleSelectText(){
		return singleSelectBtn.getText();
	}

	public void setDeckMultiSelectStatus(boolean multiSelectStatus) {
		deck.setCanSelectMultipleCards(multiSelectStatus);
	}
}
