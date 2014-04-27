package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

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
		// TODO Auto-generated method stub

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
