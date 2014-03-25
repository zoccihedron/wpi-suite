/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;


/**
 * This is the model for the planning poker module.
 * The game will keep track of any estimates for a session,
 * which will be used in the module.
 *
 * @author Robert
 * @version Mar 22, 2014
 */
public class Game extends AbstractModel{
	private int id;
	private String name;


	private List<User> participants;
	private User gameCreator;
	
	
	private List<Estimate> estimates;
	
	
	//TODO: timestamp, countdown or time for deadline, estimate

	
	/**
	 * The real constructor of a game instantiation, the game creator field
	 * of this game will be filled when the game is created
	 * 
	 * @param s is the session of the user creating the game is in
	 * 
	 */
	public Game(Session s) {
		//TODO: whether a session could be add to the parameter of game's constructor
		id = 0;
		name = "";
		estimates = new ArrayList<Estimate>();
		participants = new ArrayList<User>();
		gameCreator = s.getUser();
		
	}
	
	/**
	 * this constructor is only for Count() method in PlanningPokerEntityManager,
	 * and should not be called when a real game is created
	 */
	public Game(){
		id = 0;
		name = "";
		estimates = new ArrayList<Estimate>();
		participants = new ArrayList<User>();
		gameCreator = null;
	}
	
	/**
	 * Returns a game from JSON encoded string
	 *
	 * @param json JSON-encoded string
	 * @return Game
	 */
	public static Game fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Game.class);
	}

	/**
	 * Saves the game
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub
	}

	/**
	 * Deletes the game
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub
	}

	/**
	 * Converts the game to JSON-encoded string
	 * 
	 * @return JSON-encoded string
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	public String toJSON() {
		return new Gson().toJson(this, Game.class);
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Copies all of the values from the given game to this game.
	 * 
	 * @param updatedGame
	 *            the game to copy from.
	 */
	public void copyFrom(Game updatedGame) {
		id = updatedGame.getID();
		name = updatedGame.getName();
	}
	
	
	
	/**
	 * Make the given user to become the creator of the game.
	 * If this user is already in the participants list, it would
	 * be removed from the list.
	 * 
	 * After this method is called, the old creator becomes a 
	 * normal participants who has no control over the game
	 * 
	 * @param user the given user who should become the creator
	 * @return new creator of the game
	 * 
	 */
	public User changeCreator(User user){
		if(gameCreator.equals(user)) return user;
		if(hasUser(user)){
			participants.remove(user);
		}
		User oldCreator = gameCreator;
		participants.add(oldCreator);
		gameCreator = user;
		
		return gameCreator;
	}
	
	
	
	/**
	 * Check if a given user is the creator of this game
	 *  (has authorization to change participants/users)
	 * @param user given
	 * @return true if this user is the creator
	 */
	public boolean isCreator(User user){
		if(gameCreator.equals(user)) return true;
		return false;
	}
	

	
	/**
	 * Check if the given user is a participant of this game (not the creator)
	 * 
	 * @param user given for checking
	 * @return true if the given user is a participant of this game
	 */
	public boolean isParticipant(User user){
		if(participants==null) return false;
		
		for(User temp: participants){
			if(user.equals(temp))
				return true;
		}
		
		return false;
	}
	/**
	 * Check if the given user is in this game (either creator or participant)
	 * 
	 * @param user given for checking
	 * @return true if the given user is in this game
	 */
	public boolean hasUser(User user){
		return isParticipant(user) || isCreator(user);
		
	}
	
	/**
	 * Add a new user to this game 
	 * (by adding this user to the user list and also every estimate in estimate list)
	 * @param user the new user to be added to this game
	 * @return true if the user is successfully added, false if not or the user is already in the list
	 * @throws exception if this user has no 
	 */
	public boolean addUser(User user){
		
		if(hasUser(user)) return false;
		participants.add(user);
		
		for(Estimate e: estimates){
			if(!e.addUser(user))	return false;
		}
		return true;
		
	}
	
	/**
	 * Add a new estimate to this game
	 * before adding to the list of estimates, all users present in this game will
	 * be added to this estimate
	 * @param estimate
	 */
	public void addEstimate(Estimate estimate){
		for(User u: participants){
			estimate.addUser(u);
		}
		//TODO: make sure that creator of the game also participate in game; 
		//if not, changeCreator will have more things to do
		estimate.addUser(gameCreator);
		
		estimates.add(estimate);
		
	}

	
	public void setId(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumRequirements() {
		return estimates.size();
	}
}
