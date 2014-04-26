package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.CardViewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.ListDecksPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

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
		Deck toDelete = view.getDeck();
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/deck/" + toDelete.getId(), HttpMethod.DELETE);
		request.addObserver(new RequestObserver(){

			@Override
			public void responseSuccess(IRequest iReq) {
				successfulRemoval();
			}

			@Override
			public void responseError(IRequest iReq) {
				System.err.println("The request to delete the deck failed");
				
			}

			@Override
			public void fail(IRequest iReq, Exception exception) {
				System.err.println("The request to delete the deck failed");
				
			}
			
		});
		request.send();
		
	}

	public void successfulRemoval(){
		ManageDeckController.getInstance().updateDecks();
		cardView.updateView();
		view.disableControls();
		view.updateDeckRemovalMessage("Deck Successfully Removed");
		listDecksPanel.refresh();
	}
}
