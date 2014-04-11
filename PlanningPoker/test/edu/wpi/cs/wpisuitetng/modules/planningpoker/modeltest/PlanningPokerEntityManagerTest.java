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
import org.junit.BeforeClass;
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
	
	static MockData db;
	static Game game;
	static Game game2;
	static Game game3;
	static PlanningPokerEntityManager manager;
	static User dummyUser, dummyUser2;
	static Session s1, s2;
	static String mockSsid = "abc123";
	static Project testProject;
	static Project otherProject;
	
	
	@BeforeClass
	public static void setUp() throws WPISuiteException{
		
		User dummyUser = new User("Bob", "bob", "abc123", 1);
		Date start = new Date();
		Calendar endTime = new GregorianCalendar();
		endTime.set(2015, 1,1);
		Date end = endTime.getTime();
		
		dummyUser.setRole(Role.ADMIN);
		
		game = new Game("game", start, end);
		game2 = new Game("game2", start, end);
		game3 = new Game("game3", start, end);
		
		System.out.println(game.getId());
		System.out.println(game2.getId());
		System.out.println(game3.getId());
		
		game3.setStatus(Game.GameStatus.IN_PROGRESS);
		
		testProject = new Project("test", "1");
		
		s1 = new Session(dummyUser, testProject, mockSsid);

		dummyUser2 = new User("Bill", "bill", "abc123", 1);
		s2 = new Session(dummyUser2, testProject, mockSsid);
		
		db = new MockData(new HashSet<Object>());
		manager = new PlanningPokerEntityManager(db);
		
		game.setGameCreator(dummyUser.getUsername());
		game2.setGameCreator(dummyUser.getUsername());
		game3.setGameCreator(dummyUser.getUsername());
		
		manager.save(s1, game);
		manager.save(s1, game2);
		manager.save(s1, game3);

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
		assertEquals("Permission denied.", exceptionMessage);
		
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
			Game[] retrievedGames = manager.getEntity(s1,"0");
		}
		catch(NotFoundException e){
			successfulRetrievalByOwner = false;
		}
		
		try{
			Game[] retrievedGames = manager.getEntity(s2,"0");
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
		Game newGameRetrieved = (Game) db.retrieve(Game.class, "id", 2).get(0);
		assertTrue(game3.getName().equals(newGameRetrieved.getName()));
	}
	
	
	@Test
	public void GetGameEntityFromDatabase() throws NotFoundException, WPISuiteException{
		Game[] retrieved = manager.getEntity(s1, "1");
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
	
	@Test
	public void getHighestIdTest() throws WPISuiteException{
		Game[] retrievedGames = (Game[])manager.getAll(s1);
		for(Game g : retrievedGames)
		{
			System.out.println(manager.getIdCount());
			System.out.println(g.getName());
			System.out.println(g.getId());
		}
		int largestId = manager.getGameWithLargestId(retrievedGames);
		assertEquals(largestId, retrievedGames.length);
	}
	
	@Test
	public void autoIncrementGameIdTest() throws WPISuiteException{
		Game[] retrievedGames = (Game[])manager.getAll(s1);
		
		
	}
}
