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

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;

/**
 * Game testing class for Planning Poker
 *
 * @author  Code On Bleu
 * @version Mar 23, 2014
 */
public class GameTest {
	Game game1;
	Game game2;
	User dummyUser;
	User dummyUser2;
	
	@Before
	public void setUp(){
		String game1name = "Game1";
		Date start = new Date();
		Calendar endTime = new GregorianCalendar();
		endTime.set(start.getYear(), 1,1);
		Date end = endTime.getTime();
		game1 = new Game(game1name,start,end, "default");
		
		String game2name = "Game2";
		game2 = new Game(game2name,start,end, "default");

		dummyUser = new User("Bob", "bob", "abc123", 1);

		dummyUser2 = new User("Bill", "bill", "abc123", 1);
		
		
		Estimate est1 = new Estimate(1, game1.getId());		
		game1.addEstimate(est1);
		game1.addUser(dummyUser.getUsername());
		est1.addUser(dummyUser.getUsername());
		est1.addUser(dummyUser2.getUsername());
		
	}

	@Test
	public void constructorTest(){
		Game testGameEmptyConstructor = new Game();
		
		Date start = new Date();
		Calendar endTime = new GregorianCalendar();
		endTime.set(start.getYear() + 1, 1,1);
		Date end = endTime.getTime();
		
		Game testGameConstructor = new Game("test",start,end, "default");
		
		assertEquals("test", testGameConstructor.getName());
		assertEquals(start, testGameConstructor.getStart());
		assertEquals(end, testGameConstructor.getEnd());
		assertEquals("default", testGameConstructor.getDeck());
		
		assertEquals(0, testGameEmptyConstructor.getId());
		
	}
	
	@Test
	public void updateEstimateTest()
	{			
		int estimateValue = 3;
		assertEquals(game1.findEstimate(1).getEstimate(dummyUser.getUsername()),-1);
		Estimate estimate = game1.findEstimate(1);
		estimate.makeEstimate(dummyUser.getUsername(),estimateValue);
		assertEquals(game1.findEstimate(1).getEstimate(dummyUser.getUsername()),3);

	}
	
	@Test
	public void dontEndDraftTest(){
		Game draftGame = new Game();
		draftGame.endIfAllEstimated();
		assertEquals(GameStatus.DRAFT, draftGame.getStatus());
	}
	
	@Test
	public void dontEndNotCompletedTest(){
		Game inProgressGame = new Game();
		inProgressGame.setStatus(GameStatus.IN_PROGRESS);
		User user1 = new User("user", "us", "password", 1);
		Estimate estimate1 = new Estimate(1, 1);
		Estimate estimate2 = new Estimate(2, 1);

		estimate1.addUser(user1.getName());
		estimate2.addUser(user1.getName());
		
		estimate1.makeEstimate(user1.getName(), 4);
		
		inProgressGame.addEstimate(estimate1);
		inProgressGame.addEstimate(estimate2);
		
		inProgressGame.endIfAllEstimated();
		
		assertEquals(GameStatus.IN_PROGRESS, inProgressGame.getStatus());
	}
	
	@Test
	public void gameEndsAfterAllUsersEstimateTest(){
		Game endedGame = new Game();
		endedGame.setStatus(GameStatus.IN_PROGRESS);
		User user1 = new User("user", "us", "password", 1);
		Estimate estimate1 = new Estimate(1, 1);
		Estimate estimate2 = new Estimate(2, 1);

		estimate1.addUser(user1.getName());
		estimate2.addUser(user1.getName());
		
		estimate1.makeEstimate(user1.getName(), 4);
		estimate2.makeEstimate(user1.getName(), 4);

		endedGame.addEstimate(estimate1);
		endedGame.addEstimate(estimate2);
		
		
		endedGame.endIfAllEstimated();
		
		assertEquals(GameStatus.IN_PROGRESS, endedGame.getStatus());
	}

	@Test
	public void gameStatusEnumTest(){
		Game testGame = new Game();
		testGame.setStatus(GameStatus.DRAFT);
		assertEquals(GameStatus.DRAFT, testGame.getStatus());
		
		testGame.setStatus(GameStatus.IN_PROGRESS);
		assertEquals(GameStatus.IN_PROGRESS, testGame.getStatus());
		
		testGame.setStatus(GameStatus.ENDED);
		assertEquals(GameStatus.ENDED, testGame.getStatus());
		
		testGame.setStatus(GameStatus.CLOSED);
		assertEquals(GameStatus.CLOSED, testGame.getStatus());
	}
	
	
	@Test
	public void JSONtest(){
		Date start = new Date();
		Calendar endTime = new GregorianCalendar();
		endTime.set(start.getYear(), 1,1);
		Date end = endTime.getTime();
		
		Game testGameConstructor = new Game("test",start,end, "default");
		String jsonMessage = testGameConstructor.toJSON();
		Game fromMessage = Game.fromJson(jsonMessage);
		
		assertEquals(testGameConstructor.getName(), fromMessage.getName());
		assertEquals(testGameConstructor.getDeck(), fromMessage.getDeck());
	}
	
	@Test 
	public void identityTest(){
		assertTrue(game1.identify(0));
		assertFalse(game1.identify(11));
		assertTrue(game1.identify("0"));
		assertFalse(game1.identify("11"));
		assertTrue(game1.identify(game1));
	}
	
	@Test
	public void copyTest(){
		Game copy = new Game();
		copy.copyFrom(game1);
		
		assertEquals(copy.getName(), game1.getName());
		assertEquals(copy.getParticipants(), game1.getParticipants());
		assertEquals(copy.getGameCreator(), game1.getGameCreator());
		assertEquals(copy.getDescription(), game1.getDescription());
		assertEquals(copy.getRequirements(), game1.getRequirements());
		assertEquals(copy.getStart(), game1.getStart());
		assertEquals(copy.getEnd(), game1.getEnd());
		assertEquals(copy.getStatus(), game1.getStatus());
		assertEquals(copy.isHasDeadline(), game1.isHasDeadline());
	}
	
	@Test
	public void changeCreatorTest(){
		game1.changeCreator("Testing123");
		assertEquals("Testing123", game1.getGameCreator());
	}
	
	@Test
	public void isParticipantTest(){
		assertTrue(game1.isParticipant(dummyUser.getUsername()));
		assertFalse(game1.isParticipant("Nope"));
	}
	
	@Test
	public void hasUserTest(){
		assertTrue(game1.hasUser(dummyUser.getUsername()));
		assertFalse(game1.hasUser("Nope"));
	}
	
	@Test
	public void addUserTest(){
		
		assertFalse(game1.hasUser(dummyUser2.getUsername()));
		game1.addUser(dummyUser2.getUsername());
		assertTrue(game1.hasUser(dummyUser2.getUsername()));

	}
	
	@Test
	public void addEstimateTest(){
		assertNull(game1.findEstimate(2));
		Estimate est2 = new Estimate(2, game1.getId());		
		game1.addEstimate(est2);
		assertNotNull(game1.findEstimate(2));
	}
}

