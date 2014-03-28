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
import java.util.HashSet;
import java.util.List;

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

/**
 * Entity Manager testing class for Planning Poker
 *
 * @author Chris
 * @version Mar 22, 2014
 */
public class PlanningPokerEntityManagerTest {
	
	MockData db;
	Game game1;
	Game game2;
	List<String> userList1 = new ArrayList<String>();
	List<String> userList2 = new ArrayList<String>();
	PlanningPokerEntityManager manager;
	User dummyUser;
	Session s1;
	String mockSsid = "abc123";
	Project testProject;
	Project otherProject;
	
	
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
		
		User dummyUser = new User("Bob", "bob", "abc123", 1);
		dummyUser.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		s1 = new Session(dummyUser, testProject, mockSsid);
		
		db = new MockData(new HashSet<Object>());
		db.save(game1, testProject);
		db.save(dummyUser);
		db.save(game2, otherProject);
		manager = new PlanningPokerEntityManager(db);
	}
	
	@Test
	public void AddGameEntityToDatabaseTest() 
			throws WPISuiteException{
		Game newGame = manager.makeEntity(s1, game1.toJSON());
		assertEquals(1, newGame.getID());
		assertEquals("Planning Poker 1", newGame.getName());
		assertSame(db.retrieve(Game.class,  "id", 1).get(0), newGame);
	}
	
	@Test
	public void GetGameEntityFromDatabase() throws NotFoundException, WPISuiteException{
		Game[] retrived = manager.getEntity(s1, "1");
		assertSame(game1, retrived[0]);
	}
}
