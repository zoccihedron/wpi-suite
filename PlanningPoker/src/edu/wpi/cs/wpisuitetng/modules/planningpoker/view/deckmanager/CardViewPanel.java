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
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;

/**
 * The panel that shows all the cards in the deck
 * in a grid format
 * 
 * @author Code On Bleu
 *
 */
public class CardViewPanel extends JScrollPane{
	private JPanel currentView;
	private ArrayList<JToggleButton> toggleBtns;
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
	
	public JPanel cardView(Deck deck){
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = -1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weighty = 1.0;
		
		currentView.removeAll();

		for(Integer card: deck.getCards()){
			
			final JToggleButton cardToAdd = new JToggleButton(Integer.toString(card), img);
			cardToAdd.setHorizontalTextPosition(SwingConstants.CENTER);
			cardToAdd.setVerticalTextPosition(SwingConstants.CENTER);
			
			
			if(constraints.gridx % (NUM_OF_COLUMNS-1) == 0 && constraints.gridx != 0){
				constraints.gridx = 0;
				constraints.gridy++;
			}else{
				constraints.gridx++;
			}
			
			//Set the anchor to be the last row
			if(constraints.gridy == deck.getCards().size() / NUM_OF_COLUMNS){
				constraints.anchor = GridBagConstraints.PAGE_END;
			}
			currentView.add(cardToAdd, constraints); 
		}
		
		return currentView;
		
		
	}
	
	public void updateView(Deck deck){
		cardView(deck);
		this.setViewportView(currentView);
	}
	
	public void updateView() {
		currentView = new JPanel();
		this.setViewportView(currentView);
	}

	public ArrayList<Integer> getSelected() {
		ArrayList<Integer> toBeRemoved = new ArrayList<Integer>();
		
		for(JToggleButton button : toggleBtns){
			if(button.isSelected()){
				toBeRemoved.add(Integer.valueOf(button.getText()));
			}
		}
		
		return toBeRemoved;
	}

	

}
