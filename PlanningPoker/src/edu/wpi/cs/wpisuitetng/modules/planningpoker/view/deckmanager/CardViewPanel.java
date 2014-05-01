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
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;

/**
 * The panel that shows all the cards in the deck
 * in a grid format
 * 
 * @author Code On Bleu
 * @version 1.0
 */
public class CardViewPanel extends JScrollPane{
	private JPanel currentView;
	private List<JToggleButton> toggleBtns;
	private ImageIcon img;
	private final int NUM_OF_COLUMNS = 4;  
	
	/**
	 * Constructs all objects in the panel to prepare for updating
	 */
	public CardViewPanel(){
		
		toggleBtns = new ArrayList<JToggleButton>();
		
		try {
			img = new ImageIcon(ImageIO.read(getClass().getResource(
					"cardtemplate.png")));
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		
		currentView = new JPanel();
		currentView.setLayout(new GridBagLayout());
		this.setViewportView(currentView);
	}
	
	/**
	 * Fill the card view of the deck with the cards held in the deck
	 * @param deck the deck to get cards from 
	 */
	public void cardView(Deck deck){
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = -1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(2, 2, 2, 2);
		constraints.weighty = 1.0;
		
		currentView.removeAll();
		toggleBtns = new ArrayList<JToggleButton>();
		

		final Border unselectedBorder = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), BorderFactory.createLineBorder(Color.WHITE, 2));
		final Border selectedBorder = BorderFactory.createLineBorder(Color.GREEN, 3);
		
		for(Integer card: deck.getCards()){
			
			
			final JToggleButton cardToAdd = new JToggleButton(Integer.toString(card), img);
			cardToAdd.setHorizontalTextPosition(SwingConstants.CENTER);
			cardToAdd.setVerticalTextPosition(SwingConstants.CENTER);
			cardToAdd.setBorder(unselectedBorder);

			
			if(constraints.gridx % (NUM_OF_COLUMNS - 1) == 0 && constraints.gridx != 0){
				constraints.gridx = 0;
				constraints.gridy++;
			}else{
				constraints.gridx++;
			}
			
			//Set the anchor to be the last row
			if(constraints.gridy == deck.getCards().size() / NUM_OF_COLUMNS){
				constraints.anchor = GridBagConstraints.PAGE_END;
			}

			cardToAdd.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					if(cardToAdd.isSelected()){
						cardToAdd.setBorder(selectedBorder);
					}else{
						cardToAdd.setBorder(unselectedBorder);
					}
									
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


			currentView.add(cardToAdd, constraints); 
			toggleBtns.add(cardToAdd);
		}
		
	}
	
	/**
	 * update and refresh the card view panel with the passed deck
	 * @param deck the deck to update the cardview with
	 */
	public void updateView(Deck deck){
		cardView(deck);
		this.setViewportView(currentView);
		this.revalidate();
	}
	
	/**
	 * set the card view to blank. Used when deleting decks
	 */
	public void updateView() {
		currentView = new JPanel();
		this.setViewportView(currentView);
	}

	/**
	 * Find and return the list of selected cards in the card view
	 * @return the list of selected cards
	 */
	public List<Integer> getSelected() {
		final List<Integer> toBeRemoved = new ArrayList<Integer>();
		
		for(JToggleButton button : toggleBtns){
			if(button.isSelected()){
				toBeRemoved.add(Integer.valueOf(button.getText()));
			}
		}
		
		return toBeRemoved;
	}

	

}
