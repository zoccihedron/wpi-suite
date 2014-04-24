package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.CardViewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class AddCardController implements ActionListener{
	private DeckControlsPanel view;
	private CardViewPanel cardView;
	
	public AddCardController(DeckControlsPanel view){
		this.view = view;
		this.cardView = view.getCardView();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int cardValue = view.getCardValue();
		
		view.addToDeck(cardValue);
		
		if(view.checkFields())
		{
			final Request request = Network.getInstance().makeRequest(
					"planningpoker/deck", HttpMethod.POST);
			request.setBody(view.getDeck().toJSON());
			request.addObserver(new RequestObserver(){

				@Override
				public void responseSuccess(IRequest iReq) {
					successfulAdditon();
				}

				@Override
				public void responseError(IRequest iReq) {
					System.err.println("The request to update the deck failed");
				}

				@Override
				public void fail(IRequest iReq, Exception exception) {
					System.err.println("The request to update the deck failed");
					
				}
				
			});
			request.send();
			
			
		}
	}
	
	public void successfulAdditon(){
		cardView.updateView(view.getDeck());
	}

}
