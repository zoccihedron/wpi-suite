package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.CardViewPane;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPane;

public class DeckManager extends JPanel{
	CardViewPane cardViewPane;
	DeckControlsPane deckControlsPane;
	
	public DeckManager(){
		
		cardViewPane = new CardViewPane();
		deckControlsPane = new DeckControlsPane();
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		this.add(cardViewPane, constraints);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		this.add(deckControlsPane, constraints);
	}
	
	public void updateDeck(Deck deck){
		cardViewPane.cardView(deck);
	}

}
