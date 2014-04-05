package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class VoteActionObserver implements RequestObserver{
	
	private final VoteActionController controller;
	
	public VoteActionObserver(VoteActionController controller){
		this.controller= controller;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		// TODO Auto-generated method stub
		
		controller.reportSuccess();		
	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub
		System.err.println("The request to update a vote failed.");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub
		System.err.println("The request to update a vote failed.");
		
	}

}
