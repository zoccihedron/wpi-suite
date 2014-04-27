package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controls the functionality for updating a deck.
 * @author Code On Bleu
 *
 */
public class UpdateDeckController implements ActionListener{
	private Deck updatedDeck;
	private Deck originalDeck;
	private DeckControlsPanel deckControlsPanel;
	
	public UpdateDeckController(DeckControlsPanel deckControlsPanel){
		this.deckControlsPanel = deckControlsPanel;
		originalDeck = deckControlsPanel.getDeck();
		updatedDeck = deckControlsPanel.getDeck();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		updatedDeck  = deckControlsPanel.getDeck();
		updatedDeck.setName(deckControlsPanel.getFieldDeckNameText());
		// Send a request to the core to save this game
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/deck", HttpMethod.POST);
		request.setBody(updatedDeck.toJSON()); // put the new message in the body of the request
		// add an observer to process the response
		System.out.println(updatedDeck.toJSON());
		request.addObserver(new UpdateDeckRequestObserver(this));
		request.send(); // send the request
		
	}
	
	public void successfulNameChange(Deck deck){
		deckControlsPanel.setActionListeners(deck);
		deckControlsPanel.getCardView().updateView(deck);
		ManageDeckController.getInstance().updateName(deck);
		deckControlsPanel.getListDecksPanel().refresh();
	}

}
