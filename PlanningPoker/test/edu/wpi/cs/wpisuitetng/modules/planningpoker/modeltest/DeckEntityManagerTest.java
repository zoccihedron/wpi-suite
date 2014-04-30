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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;



import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.DeckEntityManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerEntityManager;


/**
 * Test Created for DeckEntityManager class
 * @author Team Codon Bleu
 *
 */
public class DeckEntityManagerTest {
	MockData db;
	Deck deck1;
	Deck deck2;
	Deck deck3;
	DeckEntityManager manager;
	Session s1, s2;
	Project testProject;
	User dummyUser;
	String mockSsid = "abc123";

	
	
	@Before
	public void setUp() throws WPISuiteException{
		
		dummyUser = new User("Bob", "bob", "abc123", 1);
		dummyUser.setRole(Role.ADMIN);
		
		db = new MockData(new HashSet<Object>());
		db.save(dummyUser);
		manager = new DeckEntityManager(db);

		deck1 = new Deck("New Deck1", false, new ArrayList<Integer>());
		deck1.setId(1);
		deck2 = new Deck("New Deck2", true, new ArrayList<Integer>());
		deck2.setId(2);
		ArrayList<Integer> cards3 = new ArrayList<Integer>();
		cards3.add(1);
		cards3.add(2);
		cards3.add(3);
		deck3 = new Deck("New Deck3", true, cards3);
		deck3.setId(3);

		testProject = new Project("test", "1");
		s1 = new Session(dummyUser, testProject, mockSsid);
		s2 = new Session(dummyUser, testProject, mockSsid);


		manager.save(s1, deck1);
		manager.save(s1, deck2);

	}
	
	@Test
	public void makeEntityTest() throws NotFoundException, WPISuiteException{
		Deck retrievedDeck = manager.makeEntity(s1, deck3.toJSON());
		assertEquals(retrievedDeck.getId(), 3);
		Deck newDeckRetrieved = (Deck) db.retrieve(Deck.class, "id", deck3.getId()).get(0);
		assertEquals(newDeckRetrieved.getId(), retrievedDeck.getId());

	}
	
	@Test
	public void getEntityTest() throws NotFoundException, WPISuiteException{
		String notFoundE = "";
		try{
			Deck[] decks1 = manager.getEntity(s1, ""+0);
		} catch (NotFoundException e){
			notFoundE = "ID not valid";
		}
		assertEquals(notFoundE, "ID not valid");
		
		try{
			Deck[] deck2 = manager.getEntity(s1,""+100);
		} catch (NotFoundException e){
			notFoundE = "There are no decks in the list";
		}
		assertEquals(notFoundE, "There are no decks in the list");
		
		Deck retrievedDeck = manager.getEntity(s1, ""+1)[0];
		assertEquals(retrievedDeck, deck1);
		
	}
	
	@Test
	public void getAllTest() throws WPISuiteException{
		manager.save(s1,deck3);
		Deck[] decks = manager.getAll(s1);
		boolean contains1 = false, contains2 = false, contains3 = false;
		int counter = 0;
		
		for(Deck d: decks){
			if(d.getId() == 1) contains1 = true;
			if(d.getId() == 2) contains2 = true;
			if(d.getId() == 3) contains3 = true;
			counter++;

		}
		
		assertTrue(contains1);
		assertTrue(contains2);
		assertTrue(contains3);
		assertEquals(counter, 3);
		
	}
	
	
	@Test
	public void updateTest() throws WPISuiteException{
		Deck testDeck = new Deck();
		testDeck.setId(100);
		String errorMessage = "";
		try{
			Deck retrievedDeck1 = manager.update(s1, testDeck.toJSON());

		} catch (BadRequestException e){
			errorMessage = "Deck with ID does not exist";
		}
		assertEquals(errorMessage, "Deck with ID does not exist");
		
		deck1.setName("new Name for Deck1");
		Deck retrievedDeck2 = manager.update(s1, deck1.toJSON());
		assertEquals(retrievedDeck2.getName(), "new Name for Deck1");
		
		
	}
	
	
	@Test
	public void deleteEntityTest() throws WPISuiteException{
		boolean b1 = manager.deleteEntity(s1, 1+"");
		assertTrue(b1);
		
		Deck[] decks = manager.getAll(s1);
		boolean b2 = false;
		for(Deck d: decks){
			if(d.getId() == 1){
				b2 = true;
			}
		}
		assertFalse(b2);
				
	}
	
	@Test
	public void deleteAllTest() throws WPISuiteException{
		manager.deleteAll(s1);
		Deck[] decks = manager.getAll(s1);
		assertEquals(decks.length, 0);
		
	}
	
	@Test
	public void countTest() throws WPISuiteException{
		int count = manager.Count();
		assertEquals(count ,2);
	}
	
	
	@Test
	public void saveTest() throws WPISuiteException{
		boolean containsIdEquals1 = false, containsIdEquals2 = false;
		int counter = 0;
		MockData db2 = new MockData(new HashSet<Object>());
		DeckEntityManager newManager = new DeckEntityManager(db2);
		Deck newDeck = new Deck("NewDeck", false, new ArrayList<Integer>());
		newManager.save(s2, newDeck);
		
		Deck newDeck2 = new Deck("NewDeck2", false, new ArrayList<Integer>());
		newManager.save(s2,newDeck2);
		Deck[] retrievedDecks = newManager.getAll(s2);
		for(Deck d: retrievedDecks){
			if(d.getId() == 1) containsIdEquals1 = true;
			if(d.getId() == 2) containsIdEquals2 = true;
			counter++;
			
		}
		assertTrue(containsIdEquals1);
		assertTrue(containsIdEquals2);
		assertEquals(counter,2);
		

		

		
		
	}
	


	

	
	
	
	

}
