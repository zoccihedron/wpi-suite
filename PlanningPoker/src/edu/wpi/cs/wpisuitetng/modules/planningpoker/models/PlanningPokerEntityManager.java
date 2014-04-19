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

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;


/**
 * This is the entity manager for the game sessions in the PlanningPoker module
 * 
 * @author Code On Bleu
 * @version Mar 24, 2014
 */
public class PlanningPokerEntityManager implements EntityManager<Game> {

	/** The database */
	Data db;

	private int id_count = 0;

	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in the
	 * ManagerLayer file.
	 * 
	 * @param db
	 *            a reference to the persistent database
	 */
	public PlanningPokerEntityManager(Data db) {
		this.db = db;
	}

	/**
	 * Save a game session when it is received from the client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager
	 *      #makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Game makeEntity(Session s, String content) throws WPISuiteException {
		final Game newGame = Game.fromJson(content);
		newGame.setGameCreator(s.getUsername());
		newGame.setUsers(db.retrieveAll(new User()));
		newGame.setHasBeenEstimated(false);
		save(s, newGame);
		return db.retrieve(Game.class, "id", newGame.getId(), s.getProject())
				.toArray(new Game[0])[0];
	}

	/**
	 * Retrieves a game session from the database
	 * 
	 * @param s
	 *            the session
	 * @param id
	 *            the ID number of the game
	 * 
	 * @return the game matching the given ID
	 * @throws NotFoundException
	 *             * @throws NotFoundException
	 * @throws NotFoundException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager
	 *      #getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Game[] getEntity(Session s, String id) throws NotFoundException {
		final int intId = Integer.parseInt(id);
		if (intId < 1) {
			throw new NotFoundException("ID is not valid");
		}
		Game[] games = null;
		try {
			games = db.retrieve(Game.class, "id", intId, s.getProject())
					.toArray(new Game[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if (games.length < 1 || games[0] == null) {
			throw new NotFoundException("There are no games in the list");
		}
		if ((games[0].getStatus() == Game.GameStatus.DRAFT)
				&& ! games[0].getGameCreator().equals(s.getUsername())) {
			throw new NotFoundException("Permission denied.");
		}

		return games;
	}

	/**
	 * Returns all game sessions of a project, besides other users drafts
	 * 
	 * @param s
	 *            the session
	 * 
	 * @return the list of games this user participates in
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public Game[] getAll(Session s) {
		final Game[] allGames = db.retrieveAll(new Game(), s.getProject())
				.toArray(new Game[0]);
		final ArrayList<Game> gamesViewableByUser = new ArrayList<Game>();
		for (Game game : allGames) {
			if (game.getStatus() == Game.GameStatus.DRAFT) {
				if (game.getGameCreator().equals(s.getUsername())) {
					gamesViewableByUser.add(game);
				}
			} else {
				gamesViewableByUser.add(game);
			}
		}

		return gamesViewableByUser
				.toArray(new Game[gamesViewableByUser.size()]);

	}

	/**
	 * Returns all game sessions of a project
	 * 
	 * @param s
	 *            the session
	 * @return the list of games this user participates in
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	public Game[] getAllForEveryone(Session s) {
		return db.retrieveAll(new Game(), s.getProject()).toArray(new Game[0]);
	}

	/**
	 * Updates the current game in the project
	 * 
	 * @param s
	 *            the session
	 * @param content
	 *            JSON-encoded content
	 * 
	 * @return the updated game * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager
	 *      #update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Game update(Session s, String content) throws WPISuiteException {
		final Game updatedGame = Game.fromJson(content);
		/*
		 * Because of the disconnected objects problem in db4o, we can't just
		 * save games. We have to get the original game from db4o, copy
		 * properties from updatedGame, then save the original Game again.
		 */
		final List<Model> oldGames = db.retrieve(Game.class, "id",
				updatedGame.getId(), s.getProject());
		if (oldGames.size() < 1 || oldGames.get(0) == null) {
			throw new BadRequestException("Game with ID does not exist.");
		}

		final Game existingGame = (Game) oldGames.get(0);
		updatedGame.setGameCreator(s.getUsername());
		// copy values to old Game and fill in our changeset appropriately
		existingGame.copyFrom(updatedGame);
		
		existingGame.setUsers(db.retrieveAll(new User()));
		existingGame.setHasBeenEstimated(false);
		
		if (!db.save(existingGame, s.getProject())) {
			throw new WPISuiteException("Save was not successful");
		}

		return existingGame;
	}

	/**
	 * 
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager
	 *      #save(edu.wpi.cs.wpisuitetng.Session,
	 *      edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	public void save(Session s, Game model) throws WPISuiteException {
		if (id_count == 0) {
			final Game[] retrieved = db.retrieveAll(new Game(), s.getProject())
					.toArray(new Game[0]);
			if (retrieved.length == 0) {
				id_count = 1;
			} else {
				id_count = getGameWithLargestId(retrieved) + 1;
			}
		}

		model.setId(id_count);
		id_count++;
		if (!db.save(model, s.getProject())) {
			throw new WPISuiteException("Save was not successful");
		}
	}

	/**
	 * Ensures that a user is of the specified role
	 * 
	 * @param session
	 *            the session
	 * @param role
	 *            the role being verified
	 * @throws WPISuiteException
	 *             user isn't authorized for the given role
	 */
	private void ensureRole(Session session, Role role)
			throws WPISuiteException {
		final User user = (User) db.retrieve(User.class, "username",
				session.getUsername()).get(0);
		if (!user.getRole().equals(role)) {
			throw new UnauthorizedException(
					"You are not authorized for this process");
		}
	}

	@Override
	public boolean deleteEntity(Session s, String id) throws NotFoundException {
		// TODO Implement role check for authorization of delete
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws NotImplementedException {
		throw new NotImplementedException();
	}

	@Override
	public void deleteAll(Session s) {
		// TODO Implement role check for authorization of deleteAll
		db.deleteAll(new Game(), s.getProject());
	}

	@Override
	public int Count() {
		return db.retrieveAll(new Game()).size();
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws NotImplementedException {
		throw new NotImplementedException();
	}
	
	/**
	 * If the content string is "vote", the game will try to update the database with the vote.
	 * 
	 * If the content string is "end", the game will be set to ENDED in the database.
	 * 
	 * If the content string is "edit", the game will be set to DRAFT if it has not been voted on.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(edu.wpi.cs.wpisuitetng.Session, java.lang.String, java.lang.String)
	 */
	@Override
	public String advancedPost(Session s, String string, String content) 
			throws NotFoundException, WPISuiteException{
		
			String returnString = "false";
			if( string.equals("vote") ){
				
				final Estimate estimate = Estimate.fromJson(content);
				final Game game = getEntity(s, Integer.toString(estimate.getGameID()))[0];
				if(game.getStatus().equals(GameStatus.IN_PROGRESS)){
					final Estimate gameEst = game.findEstimate(estimate.getReqID());
					gameEst.makeEstimate(s.getUsername(), estimate.getEstimate(s.getUsername()));
					gameEst.setUserCardSelection(s.getUsername(), estimate.getUserCardSelection(s.getUsername()));
					
					final List<Estimate> newEstimates = new ArrayList<Estimate>();
					for(Estimate e: game.getEstimates()){
						Estimate tempEst = e.getCopy();
						newEstimates.add(tempEst);
					}
					game.setEstimates(newEstimates);
					game.endIfAllEstimated();
					
					game.setHasBeenEstimated(true);
					
					if(!db.save(game, s.getProject())) {
						throw new WPISuiteException("Save was not successful");
					}
					returnString = "true";
				}
				else if(game.getStatus().equals(GameStatus.DRAFT)){
					returnString = "*Error: This game is currently being edited!";
				}
				else if(game.getStatus().equals(GameStatus.ENDED)){
					returnString = "*Error: This game has ended!";
				}
				else {
					returnString = "*Error: Vote was not saved!";
				}
			}
			else if( string.equals("end") ){
				
				final Game endedGame = Game.fromJson(content);
				final Game game = getEntity(s, Integer.toString(endedGame.getId()))[0];
				
				game.setStatus(GameStatus.ENDED);
				
				if(!db.save(game, s.getProject())) {
					throw new WPISuiteException("Save was not successful");
				}
				returnString = game.toJSON();
			}
			else if( string.equals("edit") ){
				
				final Game editedGame = Game.fromJson(content);
				final Game game = getEntity(s, Integer.toString(editedGame.getId()))[0];
				if(!game.isHasBeenEstimated()){
					game.setStatus(GameStatus.DRAFT);
					
					if(!db.save(game, s.getProject())) {
						throw new WPISuiteException("Save was not successful");
					}
					returnString = "true";
				}
				else{
					returnString = "*Error: This game has been voted on.";
				}
			}
			return returnString;
	}

	/**
	 * Finds the game with the largest ID in order to increment it
	 * 
	 * @param retrieved
	 *            Games already in the database
	 * @return value of largest ID
	 */
	public int getGameWithLargestId(Game[] retrieved) {
		int largestId = 0;
		for (int i = 0; i < retrieved.length; i++) {
			if (retrieved[i].getId() > largestId) {
				largestId = retrieved[i].getId();
			}
		}
		return largestId;
	}

	/**
	 * Returns the id_count - a counter saying how many entities were added upon
	 * start up of PlanningPoker
	 * 
	 * @return id_count
	 */
	public int getIdCount() {
		return id_count;
	}
	
	/**
	 * Returns all users that are on the same project
	 * 
	 * @return List<User> a list of all users
	 */
	public List<Class<User>> getAllUsers()
	{
		return db.retrieveAll(User.class);
	}
}
