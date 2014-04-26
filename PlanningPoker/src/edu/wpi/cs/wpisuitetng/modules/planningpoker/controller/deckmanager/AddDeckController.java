package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.CreateDeckPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.ListDecksPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddDeckController implements ActionListener {
	private CreateDeckPanel createDeckPanel;
	private String defaultDeckText;
	private ListDecksPanel listDecksPanel;
	private Deck deck;
	
	public AddDeckController(CreateDeckPanel createDeckPanel, String defaultDeckText, ListDecksPanel listDecksPanel){
		this.createDeckPanel = createDeckPanel;
		this.defaultDeckText = defaultDeckText;
		this.listDecksPanel = listDecksPanel;
		this.deck = null;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		deck  = new Deck(createDeckPanel.getDeckName(), false, new ArrayList<Integer>());
		if(!deck.getName().equals("") && !deck.getName().equals(defaultDeckText)){
			// Send a request to the core to save this game
			final Request request = Network.getInstance().makeRequest(
					"planningpoker/deck", HttpMethod.PUT);
			request.setBody(deck.toJSON()); // put the new message in the body of the request
			// add an observer to process the response
			request.addObserver(new AddDeckRequestObserver(this));
			request.send(); // send the request
		}
	}
	
	public void deckCreationSuccessful() {
		ManageDeckController.getInstance().addDeck(deck);
		listDecksPanel.refresh();
	}

}
