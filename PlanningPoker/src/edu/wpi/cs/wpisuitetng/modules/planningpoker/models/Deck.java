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
	private List<Integer> cardValues = new ArrayList<Integer>();
	
	/**
	 * Constructor for Deck
	 * @param name The name of the deck
	 * @param canSelectMultipleCards A falg that allows or disallows selection of multiple cards
	 * @param cardValues A list of each card value
	 */
	public Deck (String name, List<Integer> cardValues){
		this.name = name;
		this.cardValues = cardValues;
		Collections.sort(this.cardValues);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
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
		if(o instanceof String){
			result = (name.equals((String)(o)));
		} else if (o instanceof Deck){
			result = name.equals(((Deck)(o)).getName());
		} 
		return result;
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
	 * Getter for the name field
	 * @return The name of the deck
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Getter for list of cards 
	 * @return the cards in the deck
	 */
	public List<Integer> getCards(){
		return cardValues;
	}
}
