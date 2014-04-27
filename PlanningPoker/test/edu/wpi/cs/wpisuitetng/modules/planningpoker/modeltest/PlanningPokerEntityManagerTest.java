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
import java.util.List;

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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
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
	Game draftGame;
	Game draftGameYouCreated;
	Game inProgressGame;
	PlanningPokerEntityManager manager;
	User dummyUser, dummyUser2;
	Session s1, s2;
	String mockSsid = "abc123";
	Project testProject;
	Project otherProject;
	Date start;
	Date end;
	
	
	@Before
	public void setUp() throws WPISuiteException{
		
		dummyUser = new User("Bob", "bob", "abc123", 1);
		start = new Date();
		Calendar endTime = new GregorianCalendar();
		endTime.set(2015, 1,1);
		end = endTime.getTime();
		
		dummyUser.setRole(Role.ADMIN);
		
		draftGame = new Game("game", start, end, "default");
		draftGameYouCreated = new Game("game2", start, end, "default");
		inProgressGame = new Game("game3", start, end, "default");
		
		inProgressGame.setStatus(Game.GameStatus.IN_PROGRESS);
		
		testProject = new Project("test", "1");
		
		s1 = new Session(dummyUser, testProject, mockSsid);

		dummyUser2 = new User("Bill", "bill", "abc123", 1);
		s2 = new Session(dummyUser2, testProject, mockSsid);
		
		db = new MockData(new HashSet<Object>());
		db.save(dummyUser);
		manager = new PlanningPokerEntityManager(db);
		
		draftGame.setGameCreator(dummyUser.getUsername());
		draftGameYouCreated.setGameCreator(dummyUser.getUsername());
		inProgressGame.setGameCreator(dummyUser.getUsername());
		
		manager.save(s1, draftGame);
		manager.save(s1, draftGameYouCreated);
		manager.save(s1, inProgressGame);
	}
	
	@Test
	public void retrieveGameDraftTest() throws NotFoundException{
		
		boolean PermissionDenied = false;
		String exceptionMessage = "";
		Game[] retrievedGames;
		
		try{
			retrievedGames = manager.getEntity(s2, "" + draftGame.getId());
		}
		catch(NotFoundException e){
			exceptionMessage=e.getMessage();
			PermissionDenied = true;
		}
		assertTrue(PermissionDenied);
		assertEquals("Permission denied.", exceptionMessage);
		
		boolean PermissionAllowed = true;
		try{
			retrievedGames = manager.getEntity(s1, "" + draftGame.getId());
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
			Game[] retrievedGames = manager.getEntity(s1,"" + inProgressGame.getId());
		}
		catch(NotFoundException e){
			successfulRetrievalByOwner = false;
		}
		
		try{
			Game[] retrievedGames = manager.getEntity(s2,"" + inProgressGame.getId());
		}
		catch(NotFoundException e){
			successfulRetrievalByNotOwner = false;
		}
		
		assertTrue(successfulRetrievalByOwner);
		assertTrue(successfulRetrievalByNotOwner);
	}
	
	@Test
	public void AddGameEntityToDatabaseTest() throws WPISuiteException{
		Game newGame = manager.makeEntity(s1, inProgressGame.toJSON());
		assertEquals("game3", newGame.getName());
		Game newGameRetrieved = (Game) db.retrieve(Game.class, "id", inProgressGame.getId()).get(0);
		assertTrue(inProgressGame.getName().equals(newGameRetrieved.getName()));
	}
	
	
	@Test
	public void GetGameEntityFromDatabase() throws NotFoundException, WPISuiteException{
		Game[] retrieved = manager.getEntity(s1, "" + inProgressGame.getId());
		assertSame(inProgressGame, retrieved[0]);
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
	public void getAllForEveryoneTest() throws WPISuiteException{
		//test for session 1
		Game[] retrievedGames = (Game[])manager.getAllForEveryone(s1);

		boolean containedDraftsOwnedByUser = false;
		boolean containedGameInProgress = false;
		boolean containedGames = false;

		for(Game g: retrievedGames){
			if(g.getName().equals("game")||g.getName().equals("game2")){
				containedDraftsOwnedByUser = true;
			}
			if(g.getName().equals("game3")){
				containedGameInProgress = true;
			}
			if((g.getStatus()==Game.GameStatus.DRAFT)&&(g.getGameCreator().equals(s2.getUser().getName()))){
				containedDraftsOwnedByUser = true;
			}
			containedGames = true;
		}

		assertTrue(containedDraftsOwnedByUser);
		assertTrue(containedGames);
		assertTrue(containedGameInProgress);

		//test for session 2
		retrievedGames = (Game[])manager.getAllForEveryone(s2);

		containedDraftsOwnedByUser = false;
		containedGameInProgress = false;
		containedGames = false;


		for(Game g: retrievedGames){
			if(g.getName().equals("game")||g.getName().equals("game2")){
				containedDraftsOwnedByUser = true;
			}
			if(g.getName().equals("game3")){
				containedGameInProgress = true;
			}
			if((g.getStatus()==Game.GameStatus.DRAFT)&&(g.getGameCreator().equals(s2.getUser().getName()))){
				containedDraftsOwnedByUser = true;
			}
			containedGames = true;
		}

		assertTrue(containedDraftsOwnedByUser);
		assertTrue(containedGames);
		assertTrue(containedGameInProgress);

	}

	@Test 
	public void updateGameTest() throws WPISuiteException{
		draftGame.setName("gameWithNewName");
		draftGame.setDescription("new description");
		manager.update(s1,draftGame.toJSON());
		Game updatedGame = (Game)db.retrieve(Game.class, "id", draftGame.getId()).get(0);

		assertEquals(updatedGame, draftGame);
		assertEquals(updatedGame.getName(), "gameWithNewName");
		assertEquals(updatedGame.getDescription(),"new description");

	}

	@Test
	public void saveGameTest() throws WPISuiteException{
		Game newGame = new Game("newgame", start, end, "default");
		manager.save(s1, newGame);
		assertSame(newGame, db.retrieve(Game.class, "id", newGame.getId()).get(0));

	}

	@Test
	public void getCountTest() throws WPISuiteException{
		assertEquals(manager.Count(), 3);
	}

	@Test
	public void getHighestIdTest() throws WPISuiteException{
		Game[] retrievedGames = (Game[])manager.getAll(s1);
		int largestId = manager.getGameWithLargestId(retrievedGames);
		assertEquals(largestId, retrievedGames.length);
	}
	
	@Test
	public void autoIncrementGameIdTest() throws WPISuiteException{
		Game[] retrievedGames = (Game[])manager.getAll(s1);
		for(Game game : retrievedGames){
			if (game.getName().equals("game")){
				assertEquals(1, game.getId());
			} 
			else if (game.getName().equals("game2")){
				assertEquals(2, game.getId());
			} 
			else if (game.getName().equals("game3")){
				assertEquals(3, game.getId());
			} 
		}
	}
	


	@Test
	public void getAllUsersTest() throws WPISuiteException{
		List<User> userList = manager.getAllUsers();
		int size = userList.size();
		assertEquals(size,1);

	}


	@Test
	public void advancedPostCloseTest() throws WPISuiteException{
		Game closedGame = Game.fromJson(manager.advancedPost(s1,"close",draftGame.toJSON()));
		assertEquals(closedGame.getId(), draftGame.getId());
		assertEquals(closedGame.getName(), draftGame.getName());
		assertEquals(closedGame.getStatus(),Game.GameStatus.CLOSED);
	}


	@Test
	public void advancedPostEndTest() throws WPISuiteException{
		Game endedGame = Game.fromJson(manager.advancedPost(s1,"close",draftGame.toJSON()));
		assertEquals(endedGame.getId(), draftGame.getId());
		assertEquals(endedGame.getName(), draftGame.getName());
		assertEquals(endedGame.getStatus(),Game.GameStatus.CLOSED);

	}

	@Test
	public void advancedPostEditTest() throws WPISuiteException{
		String ableToEdit = manager.advancedPost(s1, "edit", draftGame.toJSON());
		assertEquals(ableToEdit, "true");

		draftGame.setHasBeenEstimated(true);
		String hasBeenVotedOn = manager.advancedPost(s1, "edit", draftGame.toJSON());
		Game edittedGame = (Game) db.retrieve(Game.class, "id", draftGame.getId()).get(0);
		assertEquals(hasBeenVotedOn, "*This game has been recently voted on. Editing is no longer available");
		assertTrue(edittedGame.isEditing());

	}

	@Test
	public void advancedPostEndEditTest() throws WPISuiteException{
		String ableToEdit = manager.advancedPost(s1, "endEdit", draftGame.toJSON());
		assertEquals(ableToEdit, "true");

		draftGame.setHasBeenEstimated(true);
		String hasBeenVotedOn = manager.advancedPost(s1, "endEdit", draftGame.toJSON());
		Game edittedGame = (Game) db.retrieve(Game.class, "id", draftGame.getId()).get(0);
		assertEquals(hasBeenVotedOn, "*This game has been recently voted on. Editing is no longer available");
		assertFalse(edittedGame.isEditing());

	}


	@Test
	public void advancedPostVoteTest() throws WPISuiteException{
		Estimate newEstimate = new Estimate(0, inProgressGame.getId());
		inProgressGame.addEstimate(newEstimate);
		newEstimate.makeEstimate(dummyUser.getUsername(),3);
		String makeNewEstimate = manager.advancedPost(s1, "vote", newEstimate.toJSON());
		assertEquals(makeNewEstimate, "true");
		Game votedGame = (Game) db.retrieve(Game.class, "id", inProgressGame.getId()).get(0);
		assertEquals(votedGame.findEstimate(newEstimate.getReqID()).getEstimate(dummyUser.getUsername()),3);

		inProgressGame.setStatus(Game.GameStatus.ENDED);
		manager.update(s1,inProgressGame.toJSON());

		String estimateOnEndedGame = manager.advancedPost(s1, "vote", newEstimate.toJSON());
		assertEquals(estimateOnEndedGame, "*Voting is not currently allowed: The game has ended.");

	}


	@Test
	public void advancedPostSendTest() throws WPISuiteException{
		Estimate newEstimate = new Estimate(0, inProgressGame.getId());
		inProgressGame.addEstimate(newEstimate);
		newEstimate.makeEstimate(dummyUser.getUsername(),3);
		String makeNewEstimate = manager.advancedPost(s1, "send", newEstimate.toJSON());
		assertEquals(makeNewEstimate, "true");
		Game votedGame = (Game) db.retrieve(Game.class, "id", inProgressGame.getId()).get(0);
		assertTrue(votedGame.findEstimate(newEstimate.getReqID()).estimationHasBeenSent());

	}

	@Test
	public void getIdCountTest() throws WPISuiteException{
		int count = manager.getIdCount();
		assertEquals(count, 4); 
		
	}
	
}
