package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.UserPreferencesPanel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class UpdateUserPreferenceObserver implements RequestObserver {
	
	private UserPreferencesPanel userPreferencePanel;
	
	public UpdateUserPreferenceObserver(UserPreferencesPanel userPreferencePanel){
		this.userPreferencePanel = userPreferencePanel;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		final ResponseModel response = iReq.getResponse();
		final User user = User.fromJSON(response.getBody());
		userPreferencePanel.setCurrentEmailAndIM(user.getEmail(), user.getIM());

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
