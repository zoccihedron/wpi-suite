package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class ManageDeckController {

	static ManageDeckController instance = null;
	private List<Deck> decks = new ArrayList<Deck>();

	private ManageDeckController() {
		// Creates an ActionListener to be used by the timer 
		// to update decks every few seconds
		final ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if(Network.getInstance().getDefaultNetworkConfiguration() != null){
						updateDecks();
					}
				}
				catch(RuntimeException exception){
					System.err.println(exception.getMessage());
				}
			}
		};

		// Timer will update the requirements every 2 seconds
		final Timer timer = new Timer(2000, actionListener);
		timer.start();
	}

	protected void updateDecks() {
		// Send a request to the server to retrieve all decks
		final Request request = Network.getInstance().makeRequest("planningpoker/deck",
																	HttpMethod.GET); // GET is read
		request.addObserver(new RequestObserver() {
			
			@Override
			public void responseSuccess(IRequest iReq) {
				final Deck[] deckArray = Deck.fromJsonArray(iReq.getResponse().getBody());
				setDecks(new ArrayList<Deck>(Arrays.asList(deckArray)));
			}
			
			@Override
			public void responseError(IRequest iReq) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void fail(IRequest iReq, Exception exception) {
				// TODO Auto-generated method stub
				
			}
		}); // add an observer to process the response
		request.send(); // send the request
	}

	public ManageDeckController getInstance(){
		if(instance == null){
			return new ManageDeckController();
		}
		else{
			return instance;
		}
	}

	/**
	 * @return the decks
	 */
	public List<Deck> getDecks() {
		return decks;
	}

	/**
	 * @param decks the decks to set
	 */
	public void setDecks(List<Deck> decks) {
		this.decks = decks;
	}
}
