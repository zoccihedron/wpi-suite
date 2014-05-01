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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.JPlaceholderTextField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.AddCardController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.MultiSelectController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.RemoveCardController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.RemoveDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.UpdateDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;

/**
 * Button Panel for the deck manager panel.
 * 
 * @author Code On Bleu
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class DeckControlsPanel extends JPanel {

	private final JButton btnRemoveDeck;
	private final JButton btnRemoveCard;
	private final JButton btnAddCard;
	private final JPlaceholderTextField fieldAddCard;
	private final String PLACEHOLDER_TEXT = "Enter Card Value Here";

	private Deck deck;
	private final CardViewPanel cardView;
	private final ListDecksPanel listDecksPanel;
	private final DeckManager deckManager;

	private final JTextField fieldDeckName;
	private final JButton btnUpdateDeckName;

	private final JLabel deckRemovedMessage;
	private final JLabel message;
	private final JLabel helpTitle;
	private final JLabel helpText;
	private final JLabel invalidCardValueMessage;
	private final ButtonGroup cardSelectGroup;
	private final JRadioButton singleSelectBtn;
	private final JRadioButton multiSelectBtn;

	/**
	 * Construct all the buttons and their action listeners.
	 * 
	 * @param cardView
	 *            the card view panel
	 * @param listDecksPanel
	 *            the list deck panel
	 * @param deckManager
	 */
	public DeckControlsPanel(final CardViewPanel cardView,
			ListDecksPanel listDecksPanel, DeckManager deckManager) {
		this.cardView = cardView;
		this.listDecksPanel = listDecksPanel;
		this.deckManager = deckManager;

		final GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(new GridBagLayout());

		
		helpTitle = new JLabel();
		helpText = new JLabel();

		helpTitle.setText("Deck Overview");
		helpTitle.setFont(new Font("Tahoma", Font.BOLD, 17));

		helpText.setText("To begin, please select a deck from the tree on the left.");
		helpText.setFont(new Font("Tahoma", Font.PLAIN, 15));

		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(helpTitle, constraints);

		constraints.gridy = 1;
		constraints.gridx = 0;
		this.add(helpText, constraints);
		
		invalidCardValueMessage = new JLabel("Enter a positive integer (less than 1,000,000).");
		invalidCardValueMessage.setForeground(Color.RED);
		
		invalidCardValueMessage.setVisible(false);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(2, 0, 2, 0);
		constraints.anchor = GridBagConstraints.SOUTH;


		this.add(invalidCardValueMessage, constraints);


		// DECK NAME TEXT FIELD
		fieldDeckName = new JTextField("");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(2, 0, 0, 2);
		constraints.anchor = GridBagConstraints.SOUTH;
		this.add(fieldDeckName, constraints);

		// DECK NAME BUTTON
		btnUpdateDeckName = new JButton("Rename Deck");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0.5;
		constraints.insets = new Insets(2, 2, 0, 0);
		constraints.anchor = GridBagConstraints.SOUTH;
		this.add(btnUpdateDeckName, constraints);
		btnUpdateDeckName.setToolTipText("Click here to change the deck's name.");

		// REMOVE DECK BUTTON
		btnRemoveDeck = new JButton("Remove Deck");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(2, 2, 0, 0);
		this.add(btnRemoveDeck, constraints);
		btnRemoveDeck.setToolTipText("Click here to permanently remove the deck.");

		// REMOVE CARD BUTTON
		btnRemoveCard = new JButton("Remove Card");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 2, 2, 0);
		this.add(btnRemoveCard, constraints);

		// ADD CARD BUTTON
		btnAddCard = new JButton("Add Card");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 2, 2, 2);
		this.add(btnAddCard, constraints);

		// ADD CARD TEXT FIELD
		fieldAddCard = new JPlaceholderTextField(PLACEHOLDER_TEXT);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 0, 2, 2);
		this.add(fieldAddCard, constraints);
		fieldAddCard.setEnabled(false);

		try {
			Image img = ImageIO.read(getClass().getResource("editIcon.png"));
			btnUpdateDeckName.setIcon(new ImageIcon(img));

			img = ImageIO.read(getClass().getResource("removeDeck.png"));
			btnRemoveDeck.setIcon(new ImageIcon(img));

			img = ImageIO.read(getClass().getResource("removeCard.png"));
			btnRemoveCard.setIcon(new ImageIcon(img));

			img = ImageIO.read(getClass().getResource("addCard.png"));
			btnAddCard.setIcon(new ImageIcon(img));

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

		fieldAddCard.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(!fieldAddCard.getText().trim().equals("") &&
						fieldAddCard.getText().trim().matches("[0-9]+")){
					try{
						if((Integer.parseInt(fieldAddCard.getText().trim()) >= 0) &&
								(Integer.parseInt(fieldAddCard.getText().trim()) <= 999999)) {
								btnAddCard.setEnabled(true);
								invalidCardValueMessage.setVisible(false);
								addRemoveCardToolTip();
						}
						else{

							btnAddCard.setEnabled(false);
							invalidCardValueMessage.setVisible(btnAddCard.isVisible());
							addRemoveCardToolTip();
						}
					}

					catch (Exception exception) {
						btnAddCard.setEnabled(false);
						invalidCardValueMessage.setVisible(btnAddCard.isVisible());
						addRemoveCardToolTip();
					}
				} else {
					btnAddCard.setEnabled(false);
					invalidCardValueMessage.setVisible(btnAddCard.isVisible());
					addRemoveCardToolTip();
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if(!fieldAddCard.getText().trim().equals("") &&
						fieldAddCard.getText().trim().matches("[0-9]+")){
					try{
						if((Integer.parseInt(fieldAddCard.getText().trim()) >= 0) &&
								(Integer.parseInt(fieldAddCard.getText().trim()) <= 999999)){
								btnAddCard.setEnabled(true);
								invalidCardValueMessage.setVisible(false);
								addRemoveCardToolTip();
						}
						else{

							btnAddCard.setEnabled(false);
							invalidCardValueMessage.setVisible(btnAddCard.isVisible());
							addRemoveCardToolTip();
						}
					}

					catch (NumberFormatException exception) {
						btnAddCard.setEnabled(false);
						invalidCardValueMessage.setVisible(btnAddCard.isVisible());
						addRemoveCardToolTip();
					}
				} else {
					btnAddCard.setEnabled(false);
					invalidCardValueMessage.setVisible(btnAddCard.isVisible());
					addRemoveCardToolTip();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// Intentionally Left Blank

			}

		});

		// MULTI SELECT RADIO BUTTONS
		singleSelectBtn = new JRadioButton("Single Selection");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(8, 2, 8, 2);
		this.add(singleSelectBtn, constraints);

		multiSelectBtn = new JRadioButton("Multiple Selection");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(8, 0, 8, 2);
		this.add(multiSelectBtn, constraints);

		cardSelectGroup = new ButtonGroup();
		cardSelectGroup.add(singleSelectBtn);
		cardSelectGroup.add(multiSelectBtn);

		deckRemovedMessage = new JLabel();
		this.add(deckRemovedMessage);

		message = new JLabel();

		deck = null;

		btnAddCard.setEnabled(false);
		
		final ActionListener changeRemoveButtonText = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(cardView.getSelected().size() > 1){
					btnRemoveCard.setText("Remove Cards");
				} else {
					btnRemoveCard.setText("Remove Card");
				}
			}
		};
			
		final ActionListener renameDeckListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(fieldDeckName.getText().equals("")){
					btnUpdateDeckName.setEnabled(false);
				}else{
					btnUpdateDeckName.setEnabled(true);
				}
			}
		};
		
		final Timer removeButtonTimer = new Timer(250, changeRemoveButtonText);
		final Timer renameDeckTimer = new Timer(250, renameDeckListener);
		renameDeckTimer.start();
		removeButtonTimer.start();
		
		final ActionListener removeCardListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final List<Integer> selected = cardView.getSelected();
				if (selected.size() == 0) {
					btnRemoveCard.setEnabled(false);
				} else {
					btnRemoveCard.setEnabled(true);
					addRemoveCardToolTip();
				}
			}
		};

		final Timer timer = new Timer(250, removeCardListener);

		timer.start();

		btnAddCard.setVisible(false);
		btnRemoveCard.setVisible(false);
		btnRemoveDeck.setVisible(false);
		fieldAddCard.setVisible(false);
		singleSelectBtn.setVisible(false);
		multiSelectBtn.setVisible(false);
		fieldDeckName.setVisible(false);
		deckRemovedMessage.setVisible(false);
		btnUpdateDeckName.setVisible(false);
		deckManager.setSaveVisible(false);

	}

	/**
	 * Configures the action listeners for the buttons on the control panel.
	 * 
	 * @param newDeck
	 *            the deck that is being edited
	 */
	public void setActionListeners(Deck newDeck) {
		
		invalidCardValueMessage.setVisible(false);
		
		deck = newDeck;

		helpText.setVisible(false);
		helpTitle.setVisible(false);

		deckManager.setSaveText("");
		ConfigManager.getInstance();
		if (deck.getDeckCreator().equals(
				ConfigManager.getConfig().getUserName())) {

			deckRemovedMessage.setVisible(false);

			btnAddCard.setVisible(true);
			btnRemoveCard.setVisible(true);
			btnRemoveDeck.setVisible(true);
			fieldAddCard.setVisible(true);
			fieldAddCard.setEnabled(true);
			singleSelectBtn.setVisible(true);
			multiSelectBtn.setVisible(true);
			fieldDeckName.setVisible(true);
			btnUpdateDeckName.setVisible(true);
			deckManager.setSaveVisible(!deck.isInUse());
			deckManager.setSaveText("<html>No changes made.</html>");

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
			if (!(btnUpdateDeckName.getActionListeners().length == 0)) {
				btnUpdateDeckName.removeActionListener(btnUpdateDeckName
						.getActionListeners()[0]);
			}

			btnAddCard.addActionListener(new AddCardController(this));
			btnRemoveCard.addActionListener(new RemoveCardController(this));
			btnRemoveDeck.addActionListener(new RemoveDeckController(this));
			btnUpdateDeckName.addActionListener(new UpdateDeckController(this));

			final MultiSelectController multiSelectController = new MultiSelectController(
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

			fieldDeckName.setText(deck.getName());

		} else {
			btnAddCard.setVisible(false);
			btnRemoveCard.setVisible(false);
			btnRemoveDeck.setVisible(false);
			fieldAddCard.setVisible(false);
			singleSelectBtn.setVisible(false);
			multiSelectBtn.setVisible(false);
			fieldDeckName.setVisible(false);
			btnUpdateDeckName.setVisible(false);
			fieldDeckName.setVisible(false);
			deckManager.setSaveVisible(false);
			invalidCardValueMessage.setVisible(false);
			
			deckRemovedMessage.setVisible(true);
			deckRemovedMessage.setForeground(Color.BLUE);
			deckRemovedMessage
					.setText("Deck: " + deck.getName()
							+ " was not created by you. "
							+ "You cannot edit this deck");
		}
	}

	/**
	 * Disables the visibility and controls for the buttons.
	 * 
	 */
	public void disableControls() {
		btnAddCard.setVisible(false);
		btnRemoveCard.setVisible(false);
		btnRemoveDeck.setVisible(false);
		fieldAddCard.setVisible(false);
		singleSelectBtn.setVisible(false);
		multiSelectBtn.setVisible(false);
		btnUpdateDeckName.setVisible(false);
		fieldDeckName.setVisible(false);
		deckManager.setSaveVisible(false);
		invalidCardValueMessage.setVisible(false);
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
	 * @param cardValue
	 *            the value of the card
	 */
	public void addToDeck(int cardValue) {
		deck.addCard(cardValue);
	}

	/**
	 * Clears card value and returns field to hint text
	 */
	public void clearCardValue() {
		fieldAddCard.setText("");
		// remove focus from textfield
		try {
			fieldAddCard.grabFocus();
		} finally {
			fieldDeckName.grabFocus();
		}

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
	public CardViewPanel getCardView() {
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
	 * @param text
	 *            the deck removal message.
	 */
	public void updateDeckRemovalMessage(String text) {
		deckRemovedMessage.setVisible(true);
		deckRemovedMessage.setText(text);
	}



	/**
	 * Used to report errors in a status message.
	 * 
	 * @param string
	 *            the error message
	 */
	public void reportError(String string) {
		message.setText(string);
		message.setForeground(Color.RED);
	}

	/**
	 * Used to report info in a status message
	 * 
	 * @param string
	 *            the info message
	 */
	public void reportInfo(String string) {
		message.setText(string);
		message.setForeground(Color.BLUE);
	}

	/**
	 * Obtains the test for the single selection
	 * 
	 * @return the single selection text.
	 */
	public String getSingleSelectText() {
		return singleSelectBtn.getText();
	}

	/**
	 * Sets the deck to single or multi selection status.
	 * 
	 * @param multiSelectStatus
	 *            True if multi-selection, false if single-selection
	 */
	public void setDeckMultiSelectStatus(boolean multiSelectStatus) {
		deck.setCanSelectMultipleCards(multiSelectStatus);
	}

	/**
	 * Reports the status of changes being saved to the deck
	 * 
	 * @param message
	 *            the save message
	 */
	public void saveMessage(String message) {
		deckManager.setSaveText(message);
	}

	public String getFieldDeckNameText() {
		return fieldDeckName.getText();
	}
	
	private void addRemoveCardToolTip() {
		if(btnRemoveCard.isEnabled()) {
			btnRemoveCard.setToolTipText("Click here to remove the selected cards.");
		}
		else {
			btnRemoveCard.setToolTipText("Please select a card to be able to remove it.");
		}
		if(btnAddCard.isEnabled()) {
			btnAddCard.setToolTipText("Click here to add a card of the chosen value.");
		}
		else {
			btnAddCard.setToolTipText("Please insert a value to be able to add a card of that value.");
		}
	}

}
