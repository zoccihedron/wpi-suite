/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Creator:
 *    Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * This is the model for the planning poker module.
 * The deck will make any estimates for a session,
 * which will be used in the module.
 *
 * @author  Code On Bleu
 * @version 1.0
 */
public class Deck extends AbstractModel{
	private String name = "";
	private String deckCreator = "";
	private boolean canSelectMultipleCards = false;
	private List<Integer> cardValues = new ArrayList<Integer>();
	private int id;
	private boolean inUse = false;
	private boolean myDeck = false;
	
	/**
	 * Constructor for Deck
	 * @param name The name of the deck
	 * @param canSelectMultipleCards A falg that allows or disallows selection of multiple cards
	 * @param cardValues A list of each card value
	 */
	public Deck (String name, boolean canSelectMultipleCards, List<Integer> cardValues){
		this.name = name;
		this.canSelectMultipleCards = canSelectMultipleCards;
		this.cardValues = cardValues;
		this.deckCreator = ConfigManager.getInstance().getConfig().getUserName();
		Collections.sort(this.cardValues);
	}
	
	public Deck() {
		id = 0;
	}
	
	public Deck (String name){
		this(name, false, new ArrayList<Integer>());
	}

	/**
	 * Returns a deck from JSON encoded string
	 *
	 * @param json JSON-encoded string
	 * @return Deck
	 */
	public static Deck fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Deck.class);
	}
	
	/**
	 * Returns a deck array from JSON encoded string
	 * 
	 * @param json JSON-encoded string
	 * @return Deck
	 */
	public static Deck[] fromJsonArray(String json)
	{
		final Gson parser = new Gson();
		return parser.fromJson(json, Deck[].class);
	}
	
	
	@Override
	public void save() {
		// Left Empty on purpose	
	}

	@Override
	public void delete() {
		// Left Empty on purpose	
	}

	@Override
	public String toJSON() {
		return new Gson().toJson(this, Deck.class);
	}

	/**
	 * Check if Object o provided is equal to this deck by checking its name
	 * (name should be unique)
	 * 
	 * @param o given Object for checking
	 * @return true if same
	 * 
	 */
	@Override
	public Boolean identify(Object o) {
		boolean result = false;
		if(o instanceof Integer){
			result = (id == (Integer)o);
		} else if (o instanceof Deck){
			result = (id == ((Deck)o).getId());
		} 
		return result;
	}

	public int getId() {
		return id;
	}

	/**
	 * Add a new Card to this deck
	 * @param cardValue the value of the card to add
	 */
	public void addCard(Integer cardValue){
		cardValues.add(cardValue);
		Collections.sort(cardValues);
	}
	
	/**
	 * Remove the card with the specified card value
	 * @param cardValue the value of the card to add
	 */
	public void removeCard(Integer cardValue){
		cardValues.remove(cardValue);
		Collections.sort(cardValues);
	}
	
	/**
	 * return a boolean that tell you if is usable for the new game tab
	 * @return if the deck is usable in new game tab
	 */
	public boolean isUsable(){
		int nonZeroCards = 0;
		for(Integer card: this.getCards()){
			if(card > 0){
				nonZeroCards++;
			}
		}
		
		return nonZeroCards >= 2;
	}
	
	/**
	 * Getter for the name field
	 * @return The name of the deck
	 */
	public String getName(){
		return name;
	}
	/**
	 * Getter for the name field
	 * @return The name of the deck
	 */
	public String ToString(){
		return name;
	}
	
	/**
	 * Setter for the name field
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Getter for list of cards 
	 * @return the cards in the deck
	 */
	public List<Integer> getCards(){
		return cardValues;
	}
	
	/**
	 * Getter for canSelectMultipleCards 
	 * @return canSelectMultipleCards 
	 */
	public boolean canSelectMultipleCards(){
		return canSelectMultipleCards;
	}
	
	/**
	 * Setter for canSelectMultipleCards
	 * @param newValue value to set boolean to 
	 */
	public void setCanSelectMultipleCards(boolean newValue){
		canSelectMultipleCards = newValue;
	}

	public void copyFrom(Deck updatedDeck) {
		this.name = updatedDeck.getName();
		this.canSelectMultipleCards = updatedDeck.canSelectMultipleCards();
		this.cardValues = updatedDeck.getCards();
		this.id = updatedDeck.getId();
		this.deckCreator = updatedDeck.getDeckCreator();
		myDeck = false;
	}

	public String getDeckCreator() {
		return deckCreator;
	}

	public void setId(int id_count) {
		id = id_count;
	}
	
	/**
	 * A boolean that keeps track if the deck
	 * is being used by a game
	 * @return the inUse
	 */
	public boolean isInUse() {
		return inUse;
	}

	/**
	 * set the deck if it's in use or not
	 * @param inUse the inUse to set
	 */
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

	public String toString() {
		String returnString = name;
		
		if(myDeck){
			returnString = name + " (Owner)";
		}
		
		return returnString;
	}

	/**
	 * A boolean to set before to claim a deck
	 * @return the myDeck
	 */
	public boolean isMyDeck() {
		return myDeck;
	}

	/**
	 * use this to claim the deck as the current users
	 * @param myDeck the myDeck to set
	 */
	public void setMyDeck(boolean myDeck) {
		this.myDeck = myDeck;
	}
	
	public boolean equals(Object deck){
		if(deck instanceof Deck) {
			return this.id == ((Deck) deck).getId();
		}
		else {
			return false;
		}
	}
}
