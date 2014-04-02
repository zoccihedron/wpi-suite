/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.modeltest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerEntityManager;

import java.util.Date;

/**
 * Entity Manager testing class for Planning Poker
 *
 * @author  Code On Bleu
 * @version Mar 22, 2014
 */
public class PlanningPokerEntityManagerTest {
	
	MockData db;
	Game game;
	Game game2;
	Game game3;
	PlanningPokerEntityManager manager;
	User dummyUser, dummyUser2;
	Session s1, s2;
	String mockSsid = "abc123";
	Project testProject;
	Project otherProject;
	
	
	@Before
	public void setUp(){
		
		User dummyUser = new User("Bob", "bob", "abc123", 1);
		Date start = new Date();
		Calendar endTime = new GregorianCalendar();
		endTime.set(2015, 1,1);
		Date end = endTime.getTime();
		
		dummyUser.setRole(Role.ADMIN);
		
		game = new Game("game", start, end);
		game2 = new Game("game2", start, end);
		game3 = new Game("game3", start, end);
		
		game3.setId(10);
		game3.setStatus(Game.GameStatus.IN_PROGRESS);
		
		testProject = new Project("test", "1");
		
		s1 = new Session(dummyUser, testProject, mockSsid);

		dummyUser2 = new User("Bill", "bill", "abc123", 1);
		s2 = new Session(dummyUser2, testProject, mockSsid);
		
		db = new MockData(new HashSet<Object>());
		
		game.setGameCreator(dummyUser.getUsername());
		game2.setGameCreator(dummyUser.getUsername());
		game3.setGameCreator(dummyUser.getUsername());
		
		db.save(game, testProject);
		db.save(game2, testProject);
		db.save(game3, testProject);
		

		db.save(dummyUser);
		
		manager = new PlanningPokerEntityManager(db);
	}
	
	@Test
	public void retrieveGameDraftTest() throws NotFoundException{
		
		boolean PermissionDenied = false;
		String exceptionMessage = "";
		Game[] retrievedGames;
		
		try{
			retrievedGames = manager.getEntity(s2, "1");
			System.out.println("Game Creator: "+retrievedGames[0].getGameCreator());
			System.out.println("Session user: "+s2.getUser().getUsername());
		}
		catch(NotFoundException e){
			exceptionMessage=e.getMessage();
			PermissionDenied = true;
		}
		assertTrue(PermissionDenied);
		assertEquals(exceptionMessage,"Permission denied.");
		
		boolean PermissionAllowed = true;
		try{
			retrievedGames = manager.getEntity(s1, "1");
		}
		catch(NotFoundException e){
			PermissionAllowed = false;
		}
		assertTrue(PermissionAllowed);
	}
	
	@Test
	public void retrieveNonDraftGameTest() throws NotFoundException{
		
		boolean successfulRetrievalByOwner = true;
		boolean successfulRetrievalByNotOwner = true;
		
		try{
			Game[] retrievedGames = manager.getEntity(s1,"10");
		}
		catch(NotFoundException e){
			successfulRetrievalByOwner = false;
		}
		
		try{
			Game[] retrievedGames = manager.getEntity(s2,"10");
		}
		catch(NotFoundException e){
			successfulRetrievalByNotOwner = false;
		}
		
		assertTrue(successfulRetrievalByOwner);
		assertTrue(successfulRetrievalByNotOwner);
	}
	
	@Test
	public void AddGameEntityToDatabaseTest() throws WPISuiteException{
		Game newGame = manager.makeEntity(s1, game3.toJSON());
		assertEquals("game3", newGame.getName());
		Game newGameRetrieved = (Game) db.retrieve(Game.class, "id", 10).get(0);
		assertTrue(game3.getName().equals(newGameRetrieved.getName()));
	}
	
	
	@Test
	public void GetGameEntityFromDatabase() throws NotFoundException, WPISuiteException{
		Game[] retrieved = manager.getEntity(s1, "10");
		assertSame(game3, retrieved[0]);
	}
	
	@Test
	public void retrieveAllExcludingDraftsYouDidntCreateTest() throws WPISuiteException{
		Game[] retrievedGames  = (Game[])manager.getAll(s2);
		
		boolean containedDraftNotOwnedByUser = false;
		boolean containedGames = false;
		
		for(Game g: retrievedGames){
			if(g.getName().equals("game")||g.getName().equals("game2")){
				containedDraftNotOwnedByUser = true;
			}
			if((g.getStatus()==Game.GameStatus.DRAFT)&&(!g.getGameCreator().equals(s2.getUser().getName()))){
				containedDraftNotOwnedByUser = true;
			}
			containedGames = true;
		}
		
		assertFalse(containedDraftNotOwnedByUser);
		assertTrue(containedGames);
	}
	
	@Test
	public void retrieveAllIncludingDraftsYouDidCreateTest() throws WPISuiteException{
		Game[] retrievedGames  = (Game[])manager.getAll(s1);
		
		boolean containedDraftsOwnedByUser = false;
		boolean containedGames = false;
		
		for(Game g: retrievedGames){
			if(g.getName().equals("game")||g.getName().equals("game2")){
				containedDraftsOwnedByUser = true;
			}
			if((g.getStatus()==Game.GameStatus.DRAFT)&&(g.getGameCreator().equals(s2.getUser().getName()))){
				containedDraftsOwnedByUser = true;
			}
			containedGames = true;
		}
		
		assertTrue(containedDraftsOwnedByUser);
		assertTrue(containedGames);
	}
}
