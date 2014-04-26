package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class AddDeckRequestObserver implements RequestObserver {
	private AddDeckController addDeckController;
	
	public AddDeckRequestObserver(AddDeckController addDeckController){
		this.addDeckController = addDeckController;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		ResponseModel response = iReq.getResponse();
		Deck deck = Deck.fromJson(response.getBody());
		addDeckController.deckCreationSuccessful();
	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub

	}

}
