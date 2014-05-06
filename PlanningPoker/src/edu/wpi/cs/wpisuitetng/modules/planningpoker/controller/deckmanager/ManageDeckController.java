/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Code On Bleu
 ******************************************************************************/


package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This class is a singleton which gets the desks
 * from the server
 * 
 * @author Code On Bleu
 * @version 1.0
 */
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

	/**
	 *  Send a request to the server to retrieve all decks
	 */
	public void updateDecks() {

		final Request request = Network.getInstance().makeRequest(
				"planningpoker/deck",
						HttpMethod.GET);
		request.addObserver(new RequestObserver() {
			
			@Override
			public void responseSuccess(IRequest iReq) {
				final Deck[] deckArray = Deck.fromJsonArray(iReq.getResponse().getBody());
				setDecks(new ArrayList<Deck>(Arrays.asList(deckArray)));
				setDecksInUse();
			}
			
			@Override
			public void responseError(IRequest iReq) {
				System.err.println("Request to get decks failed.");
			}
			
			@Override
			public void fail(IRequest iReq, Exception exception) {
				System.err.println("Request to get decks failed.");
			}
		}); // add an observer to process the response
		request.send(); // send the request
	}

	/**
	 * Returns the singleton object of the ManageDeckController
	 *  (creating a new one if needed)
	 * @return the instance of the controller
	 */
	public static ManageDeckController getInstance(){
		if(instance == null){
			instance = new ManageDeckController();
		}
			
		return instance;
		
	}
	
	/**
	 * Sets the decks variable to be all decks that are currently
	 * being used in a game
	 */
	public void setDecksInUse(){
	
		final List<Game> games = 
				PlanningPokerModel.getInstance().getAllGames();
		final List<Integer> deckIds = new ArrayList<Integer>();
		for(Game g: games){
			if(!g.getStatus().equals(GameStatus.ENDED) &&
					!g.getStatus().equals(GameStatus.CLOSED))
			deckIds.add(g.getDeck());
		}
		
		for(Deck d: decks){
			d.setInUse(deckIds.contains(d.getId()));
		}
	}

	/**
	 * @return the decks
	 */
	public List<Deck> getDecks() {
		return decks;
	}

	/**
	 * @param decks - sets the decks in the class
	 */
	public void setDecks(List<Deck> decks) {
		this.decks = decks;
	}

	/**
	 * Remove the passed deck from the list of decks
	 * @param toDelete the deck to be removed from the list
	 */
	public void removeDeck(Deck toDelete) {
		Deck d = null;
		for(Deck deck : decks){
			if(deck.getId() == toDelete.getId()){
				d = deck;
			}
		}
		
		decks.remove(d);
	}

	/**
	 * Get the deck with the passed id
	 * @param id the id of the deck
	 * @return the deck with the passed id
	 */
	public Deck getDeckWithId(Integer id){
		Deck tempDeck = null;
		for(Deck d: decks){
			if(d.getId() == id){
				tempDeck = d;
			}
		}
		return tempDeck;
	}

	
	/**
	 * Add the passed deck to the list of decks
	 * @param deck the deck to add
	 */
	public void addDeck(Deck deck) {
		decks.add(deck);
	}

	/**
	 * Updated the name of the deck in the list of decks
	 * @param toChange the deck to change
	 */
	public void updateName(Deck toChange) {
		int i = 0;
		int index = -1;
		for(Deck deck : decks){
			if(deck.getId() == toChange.getId()){
				index = i;
			}
			i++;
		}
		
		if(index != -1){
			decks.set(index, toChange);
		}
	}
	

	
}
