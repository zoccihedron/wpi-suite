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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Game testing class for Planning Poker
 *
 * @author Ryan
 * @version Mar 23, 2014
 */
public class GameTest {
	
	Game game1;
	Game game2;
	List<String> userList1 = new ArrayList<String>();
	List<String> userList2 = new ArrayList<String>();
	
	@Before
	public void setUp(){
		game1 = new Game();
		game2 = new Game();
		
		userList1.add("Bob");
		userList1.add("Bill");
		userList1.add("Jack");
		userList1.add("Jill");
		
		userList2.add("Will");
		userList2.add("Mark");
		
		game1.setId(1);
		game1.setName("Planning Poker 1");
		game1.setGameCreator("Susan");
		game1.setParticipants(userList1);
		
		game2.setId(2);
		game2.setName("Planning Poker 2");
		game2.setGameCreator("Jane");
		game2.setParticipants(userList2);
	}
	
	@Test
	public void identifyGameBasedOnDifferentObjectsTest()
	{
		assertTrue(game1.identify(1));
		assertTrue(game1.identify("1"));
		assertTrue(game2.identify(2));
		assertTrue(game2.identify("2"));
		assertFalse(game2.identify("32"));
	}
	
	@Test
	public void checkIfParticipantTest()
	{
		assertTrue(game1.isParticipant("Bob"));
		assertTrue(game2.isParticipant("Will"));
		assertFalse(game1.isParticipant("Daniel"));
	}
}
