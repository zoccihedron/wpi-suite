package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Responsible for receiving responses from database for requests to update.
 * @author Code On Bleu
 *
 */
public class UpdateDeckRequestObserver implements RequestObserver {
	private UpdateDeckController updateDeckController;
	public UpdateDeckRequestObserver(UpdateDeckController updateDeckController){
		this.updateDeckController = updateDeckController;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		ResponseModel response = iReq.getResponse();
		Deck deck = Deck.fromJson(response.getBody());
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
