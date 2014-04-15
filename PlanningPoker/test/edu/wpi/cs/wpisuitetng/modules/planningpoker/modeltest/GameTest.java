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
		endTime.set(2015, 1,1);
		Date end = endTime.getTime();
		game1 = new Game(game1name,start,end);
		
		String game2name = "Game2";
		game2 = new Game(game2name,start,end);

		dummyUser = new User("Bob", "bob", "abc123", 1);

		dummyUser2 = new User("Bill", "bill", "abc123", 1);
		
		
		Estimate est1 = new Estimate(1, game1.getId());		
		game1.addEstimate(est1);
		
		est1.addUser(dummyUser.getUsername());
		est1.addUser(dummyUser2.getUsername());
		
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

}
