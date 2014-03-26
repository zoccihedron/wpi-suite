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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;


/**
 * This is the model for the planning poker module.
 * The game will keep track of any estimates for a session,
 * which will be used in the module.
 *
 * @author Robert Edwards, yyan
 * @version Mar 25, 2014
 */
/**
 * @author Bootlegger
 *
 */
public class Game extends AbstractModel{
	private int id;
	private String name = "";
	private List<UserInfo> participants = new ArrayList<UserInfo>();
	private UserInfo gameCreator;
	
	private Date start;
	private Date end;
	private Boolean isTerminated = false;
	
	private List<Estimate> estimates = new ArrayList<Estimate>();;
	
	
	//TODO: timestamp, countdown or time for deadline, estimate, boolean for termination

	
	/**
	 * The real constructor of a game instantiation, the game creator field
	 * of this game will be filled when the game is created
	 * 
	 * @param user is the user that created the session
	 * @param startTime start time of the game
	 * @param endTime end time of the game
	 * 
	 */
	public Game(UserInfo user, String n, Date startTime, Date endTime) {
		//TODO: whether a session could be add to the parameter of game's constructor
		id = 0;
		name = n;
		gameCreator = user;
		start = startTime;
		end = endTime;
		
	}
	
	/**
	 * this constructor is only for Count() and retrieve method in PlanningPokerEntityManager,
	 * and should not be called when a real game is created
	 */
	public Game(){
		id = 0;
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

	
	/**
	 * Check if Object o provided is equal to this game by checking id
	 * number of the game (id number should be unique)
	 * 
	 * @param o given Object for checking
	 * @return true if same
	 * 
	 */
	@Override
	public Boolean identify(Object o) {
		if(o instanceof Integer){		
			return (getID() == (Integer)(o));	
			
		} else if (o instanceof Game){
			return (getID() == ((Game)(o)).getID());
			
		} else if (o instanceof String){			
			return (Integer.toString(getID()).equals((String)o));
			
		}
		return false;
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
		//TODO: not finished
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
	public UserInfo changeCreator(User user){ //need test
		UserInfo newCreator = null;
		
		if(isCreator(user)) return getGameCreator();
		else if(isParticipant(user)){
			for(UserInfo u: participants){
				if(u.getUser().equals(user)){
					newCreator = u;
					participants.remove(u);
				}
			}
			
		} else {
			newCreator = new UserInfo(user);
		}
		UserInfo oldCreator = gameCreator;
		participants.add(oldCreator);
		gameCreator = newCreator;
		
		return gameCreator;
	}
	
	public UserInfo changeCreator(UserInfo user){
		UserInfo newCreator = null;
		if(isCreator(user)) return user;
		else if (isParticipant(user)){
			for(UserInfo u: participants){
				if(u.equals(user)){
					newCreator = u;
					participants.remove(u);
				}
			}
			
		} else {
			newCreator = user;
		}
		UserInfo oldCreator = gameCreator;
		participants.add(oldCreator);
		gameCreator = newCreator;
		
		return gameCreator;
		
	}
	
	
	
	

	/**
	 * Check if a given user is the creator of this game
	 *  (has authorization to change participants/users)
	 * @param user given
	 * @return true if this user is the creator
	 */
	public boolean isCreator(User user){
		if(gameCreator.getUser().equals(user)) return true;
		return false;
	}
	public boolean isCreator(UserInfo user){
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
		
		for(UserInfo temp: participants){
			if(temp.getUser().equals(temp))
				return true;
		}
		
		return false;
	}
	
	public boolean isParticipant(UserInfo user){
		if(participants==null) return false;
		
		for(UserInfo temp: participants){
			if(temp.equals(temp))
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
	
	public boolean hasUser(UserInfo user){
		return isParticipant(user) || isCreator(user);
	}
	
	/**
	 * Add a new user to this game 
	 * (by adding this user to the user list and also every estimate in estimate list)
	 * @param user the new user to be added to this game
	 * @return true if the user is successfully added, false if not or the user is already in the list
	 * @throws exception if this user has no 
	 */
	public boolean addUser(UserInfo user){
		
		if(hasUser(user.getUser())) return false;
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
		for(UserInfo u: participants){
			estimate.addUser(u);
		}
		//TODO: make sure that creator of the game also participate in game; 
		//if not, changeCreator will have more things to do
		estimate.addUser(gameCreator);
		
		estimates.add(estimate);
		
	}

	
	/**
	 * @return the gameCreator
	 */
	public UserInfo getGameCreator() {
		return gameCreator;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n){
		name = n;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	/**
	 * Check if this game is terminated. Game can be terminated 
	 * automatically (end time has been reached) or manually
	 * @return true if game is terminated
	 */
	public Boolean getIsTerminated() {
		if(isTerminated==true) return true;
		Date now = Calendar.getInstance().getTime();
		if(now.compareTo(end) >= 0){
			isTerminated = true;
			return isTerminated;
		}
		return false;
	}

	/**
	 * @param isTerminated the isTerminated to set
	 */
	public void setIsTerminated(Boolean isTerminated) {
		this.isTerminated = isTerminated;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * reset start time of the game
	 * @param start the start to set
	 * @return false if the start time is later than end time
	 */
	public boolean setStart(Date s) {
		if(s.compareTo(end) >= 0) return false;
		this.start = s;
		return true;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * Reset end time of the game
	 * @param end the end to set
	 * @return false if the end time is earlier than current time or the start time
	 */
	public boolean setEnd(Date e) {
		Date now = Calendar.getInstance().getTime();
		if(e.compareTo(now) <= 0 || e.compareTo(start) <= 0) return false;
		end = e;
		return true;
	}
	
	
}
