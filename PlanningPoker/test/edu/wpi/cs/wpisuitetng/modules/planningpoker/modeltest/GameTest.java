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

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
	
	
	@BeforeClass
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
		
		
		Estimate est1 = new Estimate(1);		
		game1.addEstimate(est1);
		
		est1.addUser(dummyUser.getUsername());
		est1.addUser(dummyUser2.getUsername());
		
	}
	
	@Test
	public void autoIncrementingIDAndUpdateEstimateTest()
	{
		assertEquals(game1.getName(),"Game1");
		assertEquals(game1.getId(), 1);
		
		assertEquals(game2.getId(), 2);
		
		
		int estimateValue = 3;
		assertEquals(game1.findEstimate(1).getEstimate(dummyUser.getUsername()),0);
		Estimate estimate = game1.findEstimate(1);
		estimate.makeEstimate(dummyUser.getUsername(),estimateValue);
		assertEquals(game1.findEstimate(1).getEstimate(dummyUser.getUsername()),3);

	}
	
	

	

}
