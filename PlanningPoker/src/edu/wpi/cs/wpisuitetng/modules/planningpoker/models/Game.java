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
 * This is the model for the planning poker module. The game will keep track of
 * any estimates for a session, which will be used in the module.
 * 
 * @author Code On Bleu
 * @version 1.0
 */
public class Game extends AbstractModel {
	private int id;
	private String name = "";
	private String description = "";
	private List<String> participants = new ArrayList<String>();
	private String gameCreator = "";
	private boolean hasDeadline = false;
	private Date start = new Date();
	private int modifiedVersion = 0;

	private Date end = new Date();
	private List<Estimate> estimates = new ArrayList<Estimate>();
	private List<Integer> requirements = new ArrayList<Integer>();
	private boolean hasBeenEstimated = false;
	private String deck = "";
	
	public enum GameStatus {
		DRAFT("Draft"), IN_PROGRESS("In Progress"), ENDED("Ended"), CLOSED("Closed");

		private final String text;

		private GameStatus(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	private GameStatus status = GameStatus.DRAFT;
	private boolean editing = false;

	// TODO: timestamp, countdown or time for deadline, estimate, boolean for
	// termination

	/**
	 * The real constructor of a game instantiation, the game creator field of
	 * this game will be filled when the game is created
	 * 
	 * @param name
	 *            the name of the game
	 * @param startTime
	 *            start time of the game
	 * @param endTime
	 *            end time of the game
	 * @param deckName
	 * 			  name of the deck
	 * 
	 */
	public Game(String name, Date startTime, Date endTime, String deckName) {
		// TODO: whether a session could be add to the parameter of game's
		// constructor
		this.name = name;
		start = startTime;
		end = endTime;
		this.setDeck(deckName);
	}

	/**
	 * this constructor is only for Count() and retrieve method in
	 * PlanningPokerEntityManager, and should not be called when a real game is
	 * created
	 */
	public Game() {
		id = 0;
	}

	/**
	 * Returns a game from JSON encoded string
	 * 
	 * @param json
	 *            JSON-encoded string
	 * @return Game
	 */
	public static Game fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Game.class);
	}

	/**
	 * Returns a game array from JSON encoded string
	 * 
	 * @param json
	 *            JSON-encoded string
	 * @return GameArray
	 */
	public static Game[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Game[].class);
	}

	/**
	 * Saves the game
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// Left empty on purpose
	}

	/**
	 * Deletes the game
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// Left empty on purpose
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
	 * Check if Object o provided is equal to this game by checking id number of
	 * the game (id number should be unique)
	 * 
	 * @param o
	 *            given Object for checking
	 * @return true if same
	 * 
	 */
	@Override
	public Boolean identify(Object o) {
		boolean result = false;
		if (o instanceof Integer) {
			result = (id == (Integer) (o));
		} else if (o instanceof Game) {
			result = (id == ((Game) (o)).getId());
		} else if (o instanceof String) {
			result = (Integer.toString(id).equals((String) o));
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
	 * Make the given user to become the creator of the game. If this user is
	 * already in the participants list, it would be removed from the list.
	 * 
	 * After this method is called, the old creator becomes a normal
	 * participants who has no control over the game
	 * 
	 * @param user
	 *            the given user who should become the creator
	 * @return new creator of the game
	 * 
	 */
	public String changeCreator(String user) { // TODO need test
		String newCreator = "";

		if (isCreator(user)) {
			gameCreator = user;
		} else {

			if (isParticipant(user)) {
				for (String u : participants) {
					if (u.equals(user)) {
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
		}

		return gameCreator;
	}

	/**
	 * Check if a given user is the creator of this game (has authorization to
	 * change participants/users)
	 * 
	 * @param user
	 *            given
	 * @return true if this user is the creator
	 */
	public boolean isCreator(String user) {
		return (gameCreator.equals(user)) ? true : false;
	}

	/**
	 * Check if the given user is a participant of this game (not the creator)
	 * 
	 * @param user
	 *            given for checking
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
	 * @param user
	 *            given for checking
	 * @return true if the given user is in this game
	 */
	public boolean hasUser(String user) {
		return isParticipant(user) || isCreator(user);

	}

	/**
	 * Add a new user to this game (by adding this user to the user list and
	 * also every estimate in estimate list)
	 * 
	 * @param user
	 *            the new user to be added to this game
	 * @return true if the user is successfully added, false if not or the user
	 *         is already in the list
	 */
	public boolean addUser(String user) {
		boolean result = true;

		if (hasUser(user)){
			result = false;
		}
		participants.add(user);

		for (Estimate e : estimates) {
			if (!e.canAddUser(user)) {
				result &= false;
			}
		}
		return result;

	}

	/**
	 * Add a new estimate to this game before adding to the list of estimates,
	 * all users present in this game will be added to this estimate
	 * 
	 * @param estimate
	 */
	public void addEstimate(Estimate estimate) {
		for (String u : participants) {
			estimate.canAddUser(u);
		}
		// TODO: make sure that creator of the game also participate in game;
		// if not, changeCreator will have more things to do
		estimate.canAddUser(gameCreator);

		estimates.add(estimate);

	}

	/**
	 * Finds an estimate for a game based on the requirement id
	 * 
	 * @param reqid
	 * @return the estimate for the given requirement ID
	 */
	public Estimate findEstimate(int reqid) {
		Estimate tempEst = null;
		for (Estimate e : estimates) {
			if (e.getReqID() == reqid){
				tempEst = e;
			}
		}
		return tempEst;
	}

	/**
	 * Checks if the deadline has passed and updates the status
	 */
	public void updateStatus() {
		if(!status.equals(GameStatus.CLOSED) && !status.equals(GameStatus.ENDED)){
			if (hasDeadline) {
				final Date now = Calendar.getInstance().getTime();
				if (now.compareTo(end) >= 0) {
					status = GameStatus.ENDED;
				}
			}
			endIfAllEstimated();
		}
	}

	/**
	 * Check if all users have estimated on all requirements. If this is true,
	 * end the game by changing the game status enum. Otherwise do nothing. This
	 * function should not do anything if the game is not currently being
	 * played.
	 */
	public void endIfAllEstimated() {
		boolean shouldEnd = true;

		if (status != GameStatus.DRAFT && !estimates.isEmpty()) {

			for (Estimate estimate : estimates) {
				shouldEnd &= estimate.areAllEstimationsMade();
			}

			if (shouldEnd) {
				status = GameStatus.ENDED;
			}
		}
	}
	
	/**
	 * Returns the number of total votes needed for the game to be done.
	 * @return the number of total votes needed
	 */
	public int getMaxVotes(){
		int count  = 0;
		for(Estimate e: estimates){
			count += e.getMaxVoteCount();
		}
		return count;
	}
	
	/**
	 * Returns the number of total votes needed for the game to be done.
	 * @return the number of total votes needed
	 */
	public int getUserMaxVotes(){
		int count  = 0;
		for(Estimate e: estimates){
			count ++;
		}
		return count;
	}
	
	/**
	 * Returns the number of total votes needed for the game to be done.
	 * @return the number of total votes needed
	 */
	public int getVoteCount(){
		int count  = 0;
		for(Estimate e: estimates){
			count += e.getVoteCount();
		}
		return count;
	}
	
	/**
	 * Returns the number of total votes needed for the game to be done.
	 * @param user - the user to check for
	 * @return the number of total votes needed
	 */
	public int getUserVoteCount(String user){
		int count  = 0;
		for(Estimate e: estimates){
			count += e.hasMadeAnEstimation(user) ? 1 : 0;
		}
		return count;
	}
	
	/**
	 * Returns the number of total votes needed for the req to be done.
	 * @param reqid - the id of the requirement searching through
	 * @return the number of total votes needed
	 */
	public int getReqVoteCount(int reqid){
		return findEstimate(reqid).getVoteCount();
	}
	
	/**
	 * Returns the number of total votes needed for the req to be done.
	 * @param reqid - the id of the requirement searching through
	 * @return the number of total votes needed
	 */
	public int getReqMaxVotes(int reqid){
		return findEstimate(reqid).getMaxVoteCount();
	}
	
	/**
	 * Checks to see if the game has been changed
	 * @param returnedGame the game to compare against
	 * @param user the current user
	 * @return true if the game has been changed
	 */
	public boolean isChanged(Game returnedGame, String user) {
		boolean result = false;
		result |= !(equalUserVotes(returnedGame, user));
		result |= (getVoteCount() != returnedGame.getVoteCount());
		result |= !(equalFinalEstimates(returnedGame));
		return result;
	}

	private boolean equalFinalEstimates(Game returnedGame) {
		boolean result = true;
		for(Estimate e: estimates){
			int reqid = e.getReqID();
			result &= e.getFinalEstimate() == 
					returnedGame.findEstimate(reqid).getFinalEstimate();
		}
		return result;
	}

	/**
	 * Checks to see if all the users votes are the same
	 * @param returnedGame the game to compare against
	 * @param user the current user
	 * @return true if all the votes are the same
	 */
	private boolean equalUserVotes(Game returnedGame, String user) {
		boolean result = true;
		for(Estimate e: estimates){
			int reqid = e.getReqID();
			result &= e.getEstimate(user) == 
					returnedGame.findEstimate(reqid).getEstimate(user);
		}
		return result;
	}
	
	/**
	 * Checks to see if the game's version matches the other
	 * @param otherGameVersion int of the game to compare to.
	 * @return true if they match.
	 */
	public boolean isSameModifiedVersion(int otherGameVersion){
		return otherGameVersion == modifiedVersion;
	}

	/**
	 * @return the gameCreator
	 */
	public String getGameCreator() {
		return gameCreator;
	}

	/**
	 * return the name of the game
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the game
	 * 
	 * @param n
	 *            the name
	 */
	public void setName(String n) {
		name = n;
	}

	/**
	 * Returns the status of the game
	 * 
	 * @return status of the game (DRAFT,IN_PROGRESS,ENDED)
	 */
	public GameStatus getStatus() {
		return status;
	}

	/**
	 * @param newStatus
	 *            GameStatus set Game.status to
	 */
	public void setStatus(GameStatus newStatus) {
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
	 * 
	 * @param start
	 *            the start to set
	 * @return false if the start time is later than end time
	 */
	public boolean setStart(Date start) {
		boolean result = true;
		if (start.compareTo(end) >= 0) {
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
	 * 
	 * @param end
	 *            the end to set
	 * @return false if the end time is earlier than current time or the start
	 *         time
	 */
	public boolean setEnd(Date end) {
		boolean result = true;
		final Date now = Calendar.getInstance().getTime();
		if (end.compareTo(now) <= 0 || end.compareTo(start) <= 0) {
			result = false;
		}
		this.end = end;
		return result;
	}

	/**
	 * Returns the list of the particpants in the game
	 * 
	 * @return particpants
	 */
	public List<String> getParticipants() {
		return participants;
	}

	/**
	 * sets the participants of the game
	 * 
	 * @param participants
	 */
	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}

	/**
	 * returns the list of estimates inside the game
	 * 
	 * @return estimates
	 */
	public List<Estimate> getEstimates() {
		return estimates;
	}

	/**
	 * sets the list of estimates inside the game
	 * 
	 * @param estimates
	 */
	public void setEstimates(List<Estimate> estimates) {
		this.estimates = estimates;
	}

	/**
	 * returns the id of the game
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets the id of the game
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Sets the game creator of the game
	 * 
	 * @param gameCreator
	 */
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
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the list of requirements
	 * 
	 * @return requirements
	 */
	public List<Integer> getRequirements() {
		return requirements;
	}

	/**
	 * Sets the requirements in the game
	 * 
	 * @param the
	 *            requirement ids to be stored in the game
	 */
	public void setRequirements(List<Integer> requirements) {
		this.requirements = requirements;
	}

	/**
	 * @return the hasDeadline
	 */
	public boolean isHasDeadline() {
		return hasDeadline;
	}

	/**
	 * @param hasDeadline
	 *            the hasDeadline to set
	 */
	public void setHasDeadline(boolean hasDeadline) {
		this.hasDeadline = hasDeadline;
	}

	/**
	 * returns the name of the game
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Sets all of the given users in the list to each of the estimates in the
	 * game, after creating an estimate for each req
	 * 
	 * @param list
	 *            the list of users to add
	 */
	public void setUsers(List<User> list) {
		estimates = new ArrayList<Estimate>();
		for (Integer req : requirements) {
			this.addEstimate(new Estimate(req, id));
		}
		for (Estimate e : estimates) {
			for (User u : list) {
				e.addUser(u.getUsername());
			}
		}
	}

	/**
	 * Gets whether the game was voted on or not
	 * @return if the game was voted on or not
	 */
	public boolean isHasBeenEstimated() {
		return hasBeenEstimated;
	}

	/**
	 * Sets whether the game was voted on or not
	 * @param hasBeenEstimated if the game was voted on or not
	 */
	public void setHasBeenEstimated(boolean hasBeenEstimated) {
		this.hasBeenEstimated = hasBeenEstimated;
	}

	/**
	 * @return the deck
	 */
	public String getDeck() {
		return deck;
	}

	/**
	 * @param deck the deck to set
	 */
	public void setDeck(String deck) {
		this.deck = deck;
	}

	/**
	 * sets whether the games is being edited or not
	 * @param editing true if the game is being edited.
	 */
	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	/**
	 * @return true if the game is being edited
	 */
	public boolean isEditing() {
		return editing;
	}

	/**
	 * @return the modifiedVersion from the game
	 */
	public int getModifiedVersion() {
		return modifiedVersion;
	}

	/**
	 * @param modifiedVersion the modifiedVersion to set in the game
	 */
	public void setModifiedVersion(int modifiedVersion) {
		this.modifiedVersion = modifiedVersion;
	}

}
