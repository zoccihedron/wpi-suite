package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame.AddGameRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.CreateDeckPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddDeckController implements ActionListener {
	private CreateDeckPanel createDeckPanel;
	
	public AddDeckController(CreateDeckPanel createDeckPanel){
		this.createDeckPanel = createDeckPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(!createDeckPanel.getDeck().getName().equals("")){
			// Send a request to the core to save this deck
			final Request request = Network.getInstance().makeRequest(
					"planningpoker/deck", HttpMethod.PUT);
			request.setBody(createDeckPanel.getDeck().toJSON()); // put the new message in the body of the request
			// add an observer to process the response
			request.addObserver(new AddDeckRequestObserver(this));
			request.send(); // send the request
		}
	}

}
