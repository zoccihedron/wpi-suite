package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class AddDeckRequestObserver implements RequestObserver {
	private AddDeckController addDeckController;
	
	public AddDeckRequestObserver(AddDeckController addDeckController){
		this.addDeckController = addDeckController;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		
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
