package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.CardViewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.ListDecksPanel;

public class CancelDeckChangesController implements ActionListener{
	private DeckControlsPanel view;
	private CardViewPanel cardView;
	private ListDecksPanel listDecksPanel;
	
	public CancelDeckChangesController(DeckControlsPanel view){
		this.view = view;
		this.cardView = view.getCardView();
		this.listDecksPanel = view.getListDecksPanel();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Deck deck = listDecksPanel.getSelected();
		listDecksPanel.refresh();
		listDecksPanel.setSelected(deck);
		cardView.updateView(deck);
		view.setActionListeners(deck);
	}

}
