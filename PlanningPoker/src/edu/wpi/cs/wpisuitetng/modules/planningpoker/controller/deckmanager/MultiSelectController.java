package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager.DeckControlsPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class MultiSelectController implements ActionListener{
	
	private DeckControlsPanel view;
	
	public MultiSelectController(DeckControlsPanel view){
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JRadioButton button = (JRadioButton) e.getSource();
		
		if(button.getText().equals(view.getSingleSelectText())){
			view.setDeckMultiSelectStatus(false);
		} else{
			view.setDeckMultiSelectStatus(true);
		}
		
		final Request request = Network.getInstance().makeRequest(
					"planningpoker/deck", HttpMethod.POST);
		request.setBody(view.getDeck().toJSON());
		request.addObserver(new RequestObserver(){

			@Override
			public void responseSuccess(IRequest iReq) {
				successfulStatusUpdate();
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
	
	public void successfulStatusUpdate(){
		view.getCardView().updateView(view.getDeck());
	}

}
