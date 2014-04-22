package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.CardViewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPanel;

public class AddCardController implements ActionListener{
	private DeckControlsPanel view;
	private CardViewPanel cardView;
	
	public AddCardController(DeckControlsPanel view, CardViewPanel cardView){
		this.view = view;
		this.cardView = cardView;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int cardValue = view.getCardValue();
		
		view.addToDeck(cardValue);
		
		cardView.updateView(view.getDeck());
	}

}
