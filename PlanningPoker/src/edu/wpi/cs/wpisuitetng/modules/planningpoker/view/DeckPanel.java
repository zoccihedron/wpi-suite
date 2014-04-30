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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.ManageDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame.ViewSumController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ScrollablePanel;

/**
 * This class creates the field for entering an estimation for a given
 * requirement.
 * 
 * @author Codon Bleu
 * @version Apr 9, 2014
 */
@SuppressWarnings("serial")
public class DeckPanel extends JScrollPane {
	private JTextField estimateField = new JTextField();
	private ImageIcon img = null;
	private String currentEstimate;
	private final List<JToggleButton> listOfButtons = new ArrayList<JToggleButton>();
	private boolean isDeckView;
	private boolean isMultipleSelection = true;
	private final ViewSumController controller;
	private Timer getDeckTimer = null;
	
	/**
	 * Constructs the DeckPanel
	 * Right now the default deck is constructed here, this should move when
	 * decks are fully implemented
	 * @param controller the view sum controller
	 * @param deck name of the deck
	 */

	public DeckPanel(final int deck, ViewSumController controller) {
		this.controller = controller;
		if(deck == -2){


			final ArrayList<Integer>defaultDeckCards = new ArrayList<Integer>();
			defaultDeckCards.add(0);
			defaultDeckCards.add(1);
			defaultDeckCards.add(1);
			defaultDeckCards.add(2);
			defaultDeckCards.add(3);
			defaultDeckCards.add(5);
			defaultDeckCards.add(8);
			defaultDeckCards.add(13);
			
			final Deck defaultDeck = new Deck("default", true, defaultDeckCards);
			this.setViewportView(deckVersion(defaultDeck));
		}
		else if(deck == -1){
			this.setViewportView(textVersion());
		}
		else
		{
			Deck tempDeck;
			tempDeck = ManageDeckController.getInstance().getDeckWithId(deck);
			if(tempDeck == null){
				getDeckTimer = new Timer(1000, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
					Deck tempDeck;
					tempDeck = ManageDeckController.getInstance().getDeckWithId(deck);
						if(tempDeck != null)
						{
							stopTimer();
							setViewportView(deckVersion(tempDeck));
							isMultipleSelection = tempDeck.canSelectMultipleCards();
						}
						
					}
				});
				getDeckTimer.start();
			}
			else{
			this.setViewportView(deckVersion(tempDeck));
			isMultipleSelection = tempDeck.canSelectMultipleCards();
			}
			
		}
		disableVoting();
	}
	
	/**
	 * stop timer that checks for deck
	 * @param editable
	 */
	private void stopTimer(){
		getDeckTimer.stop();
	}
	
	public String getEstimateField() {
		return estimateField.getText();
	}
	
	/**
	 * changes whether jTextField for estimate is enabled
	 * @param editable
	 */
	public void setEstimateFieldEditable(boolean editable){
		estimateField.setEditable(editable);
	}

	/**
	 * This function returns true if a deck is selected, or is in text version
	 * @return true if a deck is selected
	 */
	public boolean hasDeckSelected(){
		boolean result = true;
		
		if(isDeckView){
			result = false;
			for( JToggleButton button : listOfButtons){
				if(button.isSelected()){
					result = true;
				}
			}
		}
		return result;
	}
	
	private JPanel textVersion() {
		isDeckView = false;
		final ScrollablePanel textPanel = new ScrollablePanel();
		textPanel.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();

		// create JLabel and JTextField within scrollPane:
		final JLabel estimateLabel = new JLabel();
		estimateLabel.setText("Type your estimate here!");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		textPanel.add(estimateLabel, constraints);

		// create Textfield
	    estimateField = new JTextField();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		estimateField.setColumns(10);
		textPanel.add(estimateField, constraints);
		estimateField.setEditable(false);

		return textPanel;
	}

	
	/**
	 * This is a proof of concept Temp GUI there is no functionality programmed in it
	 * However it may be useful as a basis.
	 * @param test 
	 * @return The panel with clickable cards
	 */
	private JPanel deckVersion(final Deck deck) {
		isDeckView = true;
		final JPanel deckPanel = new JPanel();
		deckPanel.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();

		//Populate the deck with the cards it contains.
		final List<Integer> cards = deck.getCards();

		try {
			img = new ImageIcon(ImageIO.read(getClass().getResource(
					"cardtemplate.png")));
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

		for (int i = 0; i < cards.size(); i++) {
			final JToggleButton cardToAdd = new JToggleButton(Integer.toString(cards
					.get(i)), img);
			
			final Border unselectedBorder = BorderFactory.createLineBorder(Color.WHITE, 3);
			final Border selectedBorder = BorderFactory.createLineBorder(Color.GREEN, 3);
			
			cardToAdd.setHorizontalTextPosition(SwingConstants.CENTER);
			cardToAdd.setVerticalTextPosition(SwingConstants.CENTER);
			constraints.fill = GridBagConstraints.NONE;
			constraints.gridx = i;
			constraints.gridy = 2;
			constraints.gridwidth = 1;
			constraints.insets = new Insets(0, 3, 0, 3);
			
			cardToAdd.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					if(!deck.canSelectMultipleCards()){
						clearPrevious(cardToAdd);
						cardToAdd.setBorder(null);
					}
					if(cardToAdd.isSelected()){
						Border border = BorderFactory.createLineBorder(Color.GREEN, 3);
						cardToAdd.setBorder(border);
					}else{
						cardToAdd.setBorder(unselectedBorder);
					}
					calculateSum();
					
				}
			});
			
			cardToAdd.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseExited(MouseEvent e) {
					if(cardToAdd.isSelected()){
						cardToAdd.setBorder(selectedBorder);
					}else{
						cardToAdd.setBorder(unselectedBorder);
					}
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					if(cardToAdd.isSelected()){
					}else{
						cardToAdd.setBorder(selectedBorder);
					}
				}
			});

			cardToAdd.setBorder(unselectedBorder);

			listOfButtons.add(cardToAdd);
			deckPanel.add(cardToAdd, constraints);
		}
		
		return deckPanel;
	}

	private void clearPrevious(JToggleButton cardToAdd) {
		for( JToggleButton button : listOfButtons){
			if(!button.equals(cardToAdd)) {
				button.setSelected(false);
			}
		}
	}
	
	private void calculateSum() {
		int result = 0;
		
		for( JToggleButton button : listOfButtons){
			if(button.isSelected()){
				result += Integer.valueOf(button.getText());
			}
		}
		estimateField.setText(Integer.toString(result));
		controller.updateSum(result);
	}
	
	/**
	 * Displays the old estimate made by the user in the voting text field.
	 * 
	 * @param game
	 *            the game
	 * @param reqid
	 *            the requirement ID
	 */
	public void displayOldEstimate(Game game, int reqid) {

		ConfigManager.getInstance();
		final String name = ConfigManager.getConfig()
				.getUserName();
		final int oldEstimate = game.findEstimate(reqid).getEstimate(name);
		if (oldEstimate >= 0) {
			if(isDeckView){
				final List<Boolean> selected = game.findEstimate(reqid).getUserCardSelection(name);
				if(selected != null){
					for(int i = 0; i < selected.size(); i++)
					{
						listOfButtons.get(i).setSelected(selected.get(i));
					}
				}
				
			}
			estimateField.setText(Integer.toString(oldEstimate));
			currentEstimate = Integer.toString(oldEstimate);

		} else {
			estimateField.setText("");
			currentEstimate = "";
		}

	}
	
	public boolean isOldEstimate(){
		return currentEstimate.equals(estimateField.getText());
	}
	
	public void setCurrentEstimate(String estimate){
		currentEstimate = estimate;
	}
	
	public String getCurrentEstimate(){
		return currentEstimate;
	}

	/**
	 * This function is used to rest the deck selection to a blank state 
	 */
	public void clearCardsSelected() {
		for( JToggleButton button : listOfButtons){
			button.setSelected(false);
		}
		
	}
	
	public JTextField getEstimateFieldComponent() {
		return estimateField;
	}

	public boolean isDeckView() {
		return isDeckView;
	}

	/**
	 * This function gets a list of the selected cards
	 * @return The cards that are selected as boolean values
	 */
	public List<Boolean> getCardSelection() {
		final List<Boolean> selection = new ArrayList<Boolean>();
		for(JToggleButton button: listOfButtons){
			selection.add(button.isSelected());
		}
		return selection;
	}
	
	/**
	 * Disables voting by making deck not visible
	 */
	public void disableVoting(){
		if(isDeckView){
			this.getViewport().setVisible(false);
		}
	}
	
	/**
	 * Enables voting by making deck visible
	 */
	public void enableVoting(){
		if(isDeckView){
			this.getViewport().setVisible(true);
		}
	}
	
	public boolean isMultipleSelection() {
		return isMultipleSelection;
	}

}
