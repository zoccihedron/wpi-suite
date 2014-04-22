package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.CardViewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.ListDecksPanel;

public class RemoveDeckController implements ActionListener{
	private DeckControlsPanel view;
	private CardViewPanel cardView;
	private ListDecksPanel listDecksPanel;
	
	public RemoveDeckController(DeckControlsPanel view){
		this.view = view;
		cardView = view.getCardView();
		listDecksPanel = view.getListDecksPanel();
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		// Remove the deck from the database
		
	}

	public void successfulRemoval(){
		listDecksPanel.refresh();
		cardView.updateView();
		view.disableControls();
		view.updateDeckRemovalMessage("Deck Successfully Removed");
	}
}
