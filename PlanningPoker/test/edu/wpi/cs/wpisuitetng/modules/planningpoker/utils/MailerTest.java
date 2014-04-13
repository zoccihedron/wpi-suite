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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerEntityManager;

/**
 * Class to test notifying users
 *
 * @author  Code On Bleu
 * @version 1.0
 */
public class MailerTest {
	static MockData db;
	static Game g;
	static PlanningPokerEntityManager manager;
	static User u1, u2;
	static Session s1, s2;
	static String mockSsid = "abc123";
	static Project p;
	
	
	@BeforeClass
	public static void setUp() throws WPISuiteException{
		Date start = new Date();
		Calendar endTime = new GregorianCalendar();
		endTime.set(2015, 1,1);
		Date end = endTime.getTime();
		/*
		User dummyUser = new User("Bob", "bob", "abc123", 1);
		dummyUser.setAllowEmail(true);
		dummyUser.setEmail("rdbaker@wpi.edu");

		dummyUser2 = new User("Bill", "bill", "abc123", 1);
		dummyUser2.setAllowEmail(true);
		dummyUser2.setEmail("rdbaker@wpi.edu");
		
		
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
		*/
		
		g = new Game("Game", start, end);
		u1 = new User("Bob", "bob", "abc123", 1);
		u1.setAllowEmail(true);
		u1.setEmail("planningpoker@yahoo.com");
		u2 = new User("Bill", "bill", "abc123", 2); // favorite band!
		u2.setAllowEmail(true);
		u2.setEmail("planningpoker@yahoo.com");
		p = new Project("test", "1", u1,
				new User[] {u1, u2},
				new String[] {"PlanningPoker", "PostBoard", "RequirementManager"});

		g.setProject(p);
	}
	

	@Test
	public void testEmail()
	{
		Mailer mailer = new Mailer(g);
		mailer.notifyStart();
	}
	
	

	

}
