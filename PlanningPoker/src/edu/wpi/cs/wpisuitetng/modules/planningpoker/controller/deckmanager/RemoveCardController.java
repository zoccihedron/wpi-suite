package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.CardViewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPanel;

public class RemoveCardController implements ActionListener{
	private DeckControlsPanel view;
	private CardViewPanel cardView;
	
	public RemoveCardController(DeckControlsPanel view, CardViewPanel cardView){
		this.view = view;
		this.cardView = cardView;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<Integer> toRemove = cardView.getSelected();
		
		for(Integer cardValue: toRemove){
			view.getDeck().removeCard(cardValue);
		}
		
		cardView.updateView(view.getDeck());;
	}

}
