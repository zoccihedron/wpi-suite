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
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * This is the entity manager for the game sessions 
 * in the PlanningPoker module
 *
 * @author Robert Edwards
 * @version Mar 22, 2014
 */
public class PlanningPokerEntityManager implements EntityManager<Game> {
	
	/** The database */
	Data db;
	
	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */
	public PlanningPokerEntityManager(Data db) {
		this.db = db;
	}
	
	/**
	 * Save a game session when it is received from the client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Game makeEntity(Session s, String content)
			throws WPISuiteException {
		final Game newGame = Game.fromJson(content);
		if(!db.save(newGame, s.getProject())) {
			throw new WPISuiteException("Save was no successful");
		}
		return newGame;
	}

	/**
	 * Retrieves a game session from the database
	 * @param s the session
	 * @param id the ID number of the game
	 * 
	 * @return the game matching the given ID 
	 * @throws NotFoundException * @throws NotFoundException 
	 * @throws NotFoundException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Game[] getEntity(Session s, String id) throws NotFoundException {
		final int intId = Integer.parseInt(id);
		if(intId < 1) {
			throw new NotFoundException("ID is not valid");
		}
		Game[] games = null;
		try {
			games = db.retrieve(Game.class, "id", intId, s.getProject()).toArray(new Game[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(games.length < 1 || games[0] == null) {
			throw new NotFoundException("There are no games in the list");
		}
		return games;
	}

	/**
	 * Returns all game sessions of a project
	 * 
	 * @param s the session
	 * 
	 * @return the list of games this user participates in
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public Game[] getAll(Session s) throws WPISuiteException {
		Game[] allGames =  db.retrieveAll(new Game(s), s.getProject()).toArray(new Game[0]);
		ArrayList<Game> gameForThisUser = new ArrayList<Game>();
		User thisUser = s.getUser();
		
		for(Game temp: allGames){
			if(temp.hasUser(thisUser)){
				gameForThisUser.add(temp);
			}
		}
		return gameForThisUser.toArray(new Game[0]);
		
	}

	/**
	 * Updates the current game in the project
	 * @param s the session
	 * @param content JSON-encoded content
	 * 
	 * @return the updated game * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Game update(Session s, String content) throws WPISuiteException {
		final Game updatedGame = Game.fromJson(content);
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save games.
		 * We have to get the original game from db4o, copy properties from updatedGame,
		 * then save the original Game again.
		 */
		final List<Model> oldGames = db.retrieve(Game.class, "id", updatedGame.getID(), s.getProject());
		if(oldGames.size() < 1 || oldGames.get(0) == null) {
			throw new BadRequestException("Game with ID does not exist.");
		}
		
		Game existingGame = (Game)oldGames.get(0);	
		
		// copy values to old Game and fill in our changeset appropriately
		existingGame.copyFrom(updatedGame);
		
		if(!db.save(existingGame, s.getProject())) {
			throw new WPISuiteException("Save was no successful");
		}
		
		return existingGame;
	}

	/**
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, Game model) throws WPISuiteException {
		db.save(model, s.getProject());
	}
	
	/**
	 * Ensures that a user is of the specified role
	 * @param session the session
	 * @param role the role being verified
	
	 * @throws WPISuiteException user isn't authorized for the given role */
	private void ensureRole(Session session, Role role) throws WPISuiteException {
		final User user = (User) db.retrieve(User.class, "username", session.getUsername()).get(0);
		if(!user.getRole().equals(role)) {
			throw new UnauthorizedException("You are not authorized for this process");
		}
	}
	
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// TODO Implement role check for authorization of delete
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		throw new NotImplementedException();
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		// TODO Implement role check for authorization of deleteAll
		db.deleteAll(new Game(s), s.getProject());
	}

	@Override
	public int Count() throws WPISuiteException {
		return db.retrieveAll(new Game()).size();
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		throw new NotImplementedException();
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		throw new NotImplementedException();
	}

}
