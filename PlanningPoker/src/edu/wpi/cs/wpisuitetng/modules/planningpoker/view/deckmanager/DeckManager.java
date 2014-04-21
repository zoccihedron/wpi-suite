package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.ManageDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;

public class DeckManager extends JPanel{
	private CardViewPane cardViewPane;
	private DeckControlsPane deckControlsPane;
	private ManageDeckController controller;
	
	public DeckManager(){
		
		cardViewPane = new CardViewPane();
		deckControlsPane = new DeckControlsPane();
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weighty = 0.8;
		this.add(cardViewPane, constraints);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.weighty = 0.2;
		this.add(deckControlsPane, constraints);
	}
	
	public void updateDeck(Deck deck){
		cardViewPane.cardView(deck);
	}

}
