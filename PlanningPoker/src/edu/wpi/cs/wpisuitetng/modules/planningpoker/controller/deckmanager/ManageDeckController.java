package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.CardViewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.CreateGameInfoPanel;

public class ManageDeckController {

	private DeckControlsPanel deckControl = null;
	private CardViewPanel cardView = null;

	public ManageDeckController() {
		//deckControl = deckControlsPane;
		//cardView = cardViewPane;
	}

	/**
	 * Updates the Card View panel with the given game
	 */
	public void updateCardView(Deck deck){
		cardView.updateView(deck);
	}
}
