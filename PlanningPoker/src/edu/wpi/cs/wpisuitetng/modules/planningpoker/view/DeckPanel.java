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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
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
public class DeckPanel extends JScrollPane {
	private JTextField estimateField = new JTextField();
	private ImageIcon img = null;
	private final int CARD_WIDTH = 100;
	private final int CARD_HEIGHT = 128;
	private String currentEstimate;
	private final List<JToggleButton> listOfButtons = new ArrayList<JToggleButton>();
	private boolean isDeckView;
	
	/**
	 * Constructs the DeckPanel
	 * Right now the default deck is constructed here, this should move when
	 * decks are fully implemented
	 */
	public DeckPanel(String deck) {
		if(deck.equals("default")){

			ArrayList<Integer>defaultDeckCards = new ArrayList<Integer>();
			defaultDeckCards.add(0);
			defaultDeckCards.add(1);
			defaultDeckCards.add(1);
			defaultDeckCards.add(2);
			defaultDeckCards.add(3);
			defaultDeckCards.add(5);
			defaultDeckCards.add(8);
			defaultDeckCards.add(13);
			
			Deck defaultDeck = new Deck("default", true, defaultDeckCards);
			this.setViewportView(deckVersion(defaultDeck));
		}
		else {
			this.setViewportView(textVersion());
		}
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
			cardToAdd.setHorizontalTextPosition(SwingConstants.CENTER);
			cardToAdd.setVerticalTextPosition(SwingConstants.CENTER);
			constraints.fill = GridBagConstraints.NONE;
			constraints.gridx = i;
			constraints.gridy = 2;
			constraints.gridwidth = 1;
			constraints.insets = new Insets(0, 5, 0, 5);
			cardToAdd.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					if(!deck.canSelectMultipleCards()){
						clearPrevious(cardToAdd);
					}
					calculateSum();
				}
			});
			
				
			

			listOfButtons.add(cardToAdd);
			deckPanel.add(cardToAdd, constraints);
		}

		return deckPanel;
	}

	private void clearPrevious(JToggleButton cardToAdd) {
		for( JToggleButton button : listOfButtons){
			if(!button.equals(cardToAdd)){
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
		System.out.println("--------text set for old estiamte");

		final String name = ConfigManager.getInstance().getConfig()
				.getUserName();
		final int oldEstimate = game.findEstimate(reqid).getEstimate(name);
		System.out.println("--------old estimate value: " + oldEstimate);
		if (oldEstimate > 0) {
			if(isDeckView){
				ArrayList<Boolean> selected =
						(ArrayList<Boolean>) game.findEstimate(reqid).getUserCardSelection(name);
				if(selected != null){
					for(int i = 0; i < selected.size(); i++)
					{
						listOfButtons.get(i).setSelected(selected.get(i));
					}
				}
				
			}
			estimateField.setText(Integer.toString(oldEstimate));
			currentEstimate = Integer.toString(oldEstimate);
			System.out.println("--------reached ");

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

	public ArrayList<Boolean> getCardSelection() {
		ArrayList<Boolean> selection = new ArrayList<Boolean>();
		for(JToggleButton button: listOfButtons){
			selection.add(button.isSelected());
		}
		return selection;
	}

}
