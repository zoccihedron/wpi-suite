/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.utils;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerEntityManager;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

/**
 * Game testing class for Planning Poker
 *
 * @author  Code On Bleu
 * @version Mar 23, 2014
 */
public class MailerTest {
	static MockData db;
	static Game game;
	static Game game2;
	static Game game3;
	static PlanningPokerEntityManager manager;
	static User dummyUser, dummyUser2;
	static Session s1, s2;
	static String mockSsid = "abc123";
	static Project testProject;
	
	
	@BeforeClass
	public static void setUp() throws WPISuiteException{
		
		User dummyUser = new User("Bob", "bob", "abc123", 1);
		dummyUser.setAllowEmail(true);
		dummyUser.setEmail("planningpoker@yahoo.com");

		dummyUser2 = new User("Bill", "bill", "abc123", 1);
		dummyUser2.setAllowEmail(true);
		dummyUser2.setEmail("planningpoker@yahoo.com");
		
		Date start = new Date();
		Calendar endTime = new GregorianCalendar();
		endTime.set(2015, 1,1);
		Date end = endTime.getTime();
		
		dummyUser.setRole(Role.ADMIN);
		
		game = new Game("game", start, end);
		
		
		testProject = new Project("test", "1");
		//testProject.addTeamMember(dummyUser);
		//testProject.addTeamMember(dummyUser2);
		
		
		s1 = new Session(dummyUser, testProject, mockSsid);

		

		
		db = new MockData(new HashSet<Object>());
		manager = new PlanningPokerEntityManager(db);
		
		game.setGameCreator(dummyUser.getUsername());
		
		manager.save(s1, game);

		db.save(dummyUser);
		
		
		manager = new PlanningPokerEntityManager(db);
	}
	

	@Test
	public void testEmail()
	{

		assertNull(testProject.getTeam());
		/*
		Mailer mailer = new Mailer(game);
		mailer.notifyStart();
		*/
	}
	
	

	

}
