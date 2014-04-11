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
 * @author  Code On Bleu
 * @version 1.0
 */
public class Game extends AbstractModel{
	private int id;
	private String name = "";
	private String description = "";
	private List<String> participants = new ArrayList<String>();
	private String gameCreator = "";
	private boolean hasDeadline = false;
	private Date start = new Date();
	private Date end = new Date();
	private List<Estimate> estimates = new ArrayList<Estimate>();
	private List<Integer> requirements = new ArrayList<Integer>();
	
	public enum GameStatus{
		DRAFT("Draft"),
		IN_PROGRESS("In Progress"),
		ENDED("Ended");
		
		private final String text; 
        private GameStatus(String text) { 
            this.text = text; 
        } 
        
        @Override 
        public String toString(){ 
            return text; 
        }
	}
	private GameStatus status = GameStatus.DRAFT;
	
	//TODO: timestamp, countdown or time for deadline, estimate, boolean for termination

	
	/**
	 * The real constructor of a game instantiation, the game creator field
	 * of this game will be filled when the game is created
	 * 
	 * @param name the name of the game
	 * @param startTime start time of the game
	 * @param endTime end time of the game
	 * 
	 */
	public Game(String name, Date startTime, Date endTime) {
		//TODO: whether a session could be add to the parameter of game's constructor
		this.name = name;
		start = startTime;
		end = endTime;
	}
	
	/**
	 * this constructor is only for Count() and retrieve method in PlanningPokerEntityManager,
	 * and should not be called when a real game is created
	 */
	public Game(){
		id = 0;
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
	 * Returns a game array from JSON encoded string
	 * 
	 * @param json JSON-encoded string
	 * @return GameArray
	 */
	public static Game[] fromJsonArray(String json)
	{
		final Gson parser = new Gson();
		return parser.fromJson(json, Game[].class);
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
		boolean result = false;
		if(o instanceof Integer){
			result = (id == (Integer)(o));
		} else if (o instanceof Game){
			result = (id == ((Game)(o)).getId());
		} else if (o instanceof String){
			result = (Integer.toString(id).equals((String)o));
		}
		return result;
	}
	
	/**
	 * Copies all of the values from the given game to this game.
	 * 
	 * @param updatedGame
	 *            the game to copy from.
	 */
	public void copyFrom(Game updatedGame) {
		name = updatedGame.getName();
		participants = updatedGame.getParticipants();
		gameCreator = updatedGame.getGameCreator();
		description = updatedGame.getDescription();
		requirements = updatedGame.getRequirements();
		start = updatedGame.getStart();
		end = updatedGame.getEnd();
		status = updatedGame.getStatus();
		hasDeadline = updatedGame.isHasDeadline();
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
	public String changeCreator(String user)
	{ // TODO need test
		String newCreator = "";
		if(isCreator(user)) return user;
		else if(isParticipant(user)) {
			for(String u: participants) {
				if(u.equals(user)) {
					newCreator = u;
					participants.remove(u);
				}
			}
			
		} else {
			newCreator = user;
		}
		final String oldCreator = gameCreator;
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
	public boolean isCreator(String user){
		return (gameCreator.equals(user)) ? true : false;
	}
	
	/**
	 * Check if the given user is a participant of this game (not the creator)
	 * 
	 * @param user given for checking
	 * @return true if the given user is a participant of this game
	 */
	public boolean isParticipant(String user) {
		boolean result = false;

		if (participants == null) {
			result = false;
		} else {
			for (String temp : participants) {
				if (temp.equals(user)) {
					result = true;
				}
			}
		}

		return result;
	}
		
	/**
	 * Check if the given user is in this game (either creator or participant)
	 * 
	 * @param user given for checking
	 * @return true if the given user is in this game
	 */
	public boolean hasUser(String user){
		return isParticipant(user) || isCreator(user);
		
	}
	
	/**
	 * Add a new user to this game 
	 * (by adding this user to the user list and also every estimate in estimate list)
	 * @param user the new user to be added to this game
	 * @return true if the user is successfully added, 
	 * 	false if not or the user is already in the list
	 */
	public boolean addUser(String user){
		boolean result = true;
		
		if(hasUser(user)) result = false;
		participants.add(user);
		
		for(Estimate e: estimates){
			if(!e.canAddUser(user)) {
				result = false;
			}
		}
		return result;
		
	}
	
	/**
	 * Add a new estimate to this game
	 * before adding to the list of estimates, all users present in this game will
	 * be added to this estimate
	 * @param estimate
	 */
	public void addEstimate(Estimate estimate){
		for(String u: participants){
			estimate.canAddUser(u);
		}
		//TODO: make sure that creator of the game also participate in game; 
		//if not, changeCreator will have more things to do
		estimate.canAddUser(gameCreator);
		
		estimates.add(estimate);
		
	}

	
	/**
	 * @return the gameCreator
	 */
	public String getGameCreator() {
		return gameCreator;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n){
		name = n;
	}
	
	
	/**
	 * Checks if the game end time has been reached
	 * @return status of the game (DRAFT,IN_PROGRESS,ENDED)
	 */
	public GameStatus getStatus(){
		final Date now = Calendar.getInstance().getTime();
		if(now.compareTo(end) >= 0){
			status = GameStatus.ENDED;
		}
		return status;
	}
	
	/**
	 * @param newStatus GameStatus set Game.status to
	 */
	public void setStatus(GameStatus newStatus){
		status = newStatus;
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
	public boolean setStart(Date start) {
		boolean result = true;
		if(start.compareTo(end) >= 0)
		{
			result = false;
		}
		this.start = start;
		return result;
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
	public boolean setEnd(Date end) {
		boolean result = true;
		final Date now = Calendar.getInstance().getTime();
		if(end.compareTo(now) <= 0 || end.compareTo(start) <= 0) {
			result = false;
		}
		this.end = end;
		return result;
	}
	
	public List<String> getParticipants() {
		return participants;
	}

	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}

	public List<Estimate> getEstimates() {
		return estimates;
	}

	public void setEstimates(List<Estimate> estimates) {
		this.estimates = estimates;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setGameCreator(String gameCreator) {
		this.gameCreator = gameCreator;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Integer> getRequirements(){
		return requirements;
	}
	public void setRequirements(List<Integer> requirements){
		this.requirements = requirements;
	}

	/**
	 * @return the hasDeadline
	 */
	public boolean isHasDeadline() {
		return hasDeadline;
	}

	/**
	 * @param hasDeadline the hasDeadline to set
	 */
	public void setHasDeadline(boolean hasDeadline) {
		this.hasDeadline = hasDeadline;
	}
	
	@Override
	public String toString(){
		return getName();
	}

	/**
	 * Finds an estimate for a game based on the requirement id
	 *
	 * @param reqid
	 * @return the estimate for the given requirement ID
	 */
	public Estimate findEstimate(int reqid){
		for(Estimate e: estimates){
			if(e.getReqID() == reqid) return e;
		}
		return null;
	}

	/**
	 * Sets all of the given users in the list to each of the estimates
	 * in the game, after creating an estimate for each req
	 *
	 * @param list the list of users to add
	 */
	public void setUsers(List<User> list) {
		for(Integer req: requirements){
			this.addEstimate(new Estimate(req, id));
		}
		for(Estimate e: estimates){
			for(User u: list){
				e.addUser(u.getUsername());
			}
		}
	}
	
	/**
	 * Check if all users have estimated on all requirements. If this is true,
	 * end the game by changing the game status enum. Otherwise do nothing. This
	 * function should not do anything if the game is not currently being played.
	 */
	public void endIfAllEstimated(){
		boolean shouldEnd = true;
		
		if(status == GameStatus.DRAFT){
			return;
		}
		
		for(Estimate estimate: estimates){
			shouldEnd &= estimate.areAllEstimationsMade();
		}
		
		if(shouldEnd){
			status = GameStatus.ENDED;
		}
				
	}

	
	
	
}
