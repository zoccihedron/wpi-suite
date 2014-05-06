/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.modeltest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;

/**
 * Deck testing class for Planning Poker Deck
 *
 * @author  Code On Bleu
 * @version Mar 23, 2014
 */
public class DeckTest {
	
	@Test
	public void testDeckConstructor(){
		ArrayList<Integer> cards = new ArrayList<Integer>();
		cards.add(5);
		cards.add(2);
		Deck testDeck = new Deck("Test Deck", false, cards);
		assertNotNull(testDeck);
		assertTrue(testDeck.getName().equals("Test Deck"));
		assertFalse(testDeck.canSelectMultipleCards());
		assertEquals(5, (int) testDeck.getCards().get(1));
		
		Deck testDeck2 = new Deck();
		assertNotNull(testDeck2);
		assertEquals(testDeck2.getId(),0);
		
		Deck testDeck3 = new Deck("Test Deck3");
		assertNotNull(testDeck3);
		assertFalse(testDeck3.canSelectMultipleCards());
		assertNotNull(testDeck3.getCards());
		

	}
	
	@Test
	public void testCardRemoval(){
		ArrayList<Integer> cards = new ArrayList<Integer>();
		cards.add(5);
		cards.add(2);
		Deck testDeck = new Deck("Test Deck", false, cards);
		testDeck.removeCard(5);
		assertTrue(testDeck.getCards().size() == 1);
	}
	
	@Test
	public void testCardAddition(){
		ArrayList<Integer> cards = new ArrayList<Integer>();
		cards.add(5);
		cards.add(2);
		Deck testDeck = new Deck("Test Deck", false, cards);
		testDeck.addCard(5);
		assertTrue(testDeck.getCards().size() == 3);
	}
	
	@Test
	public void testDeckIdentification(){
		ArrayList<Integer> cards = new ArrayList<Integer>();
		cards.add(5);
		cards.add(2);
		Deck testDeck0 = new Deck();
		Deck testDeck = new Deck("Test Deck", false, cards);
		testDeck.setId(1);
		Deck testDeck2 = new Deck("Test Deck2", false, cards);
		testDeck2.setId(2);
		
		assertTrue(testDeck0.identify(0));
		assertTrue(testDeck.identify(1));
		assertTrue(testDeck2.identify(2));
		assertTrue(testDeck0.identify(testDeck0));
		assertTrue(testDeck.identify(testDeck));
		assertFalse(testDeck.identify(testDeck2));
	}
	
	
	@Test
	public void testEquals(){
		ArrayList<Integer> cards = new ArrayList<Integer>();
		Deck testDeck = new Deck("Test Deck", false, cards);
		testDeck.setId(0);
		Deck testDeck2 = new Deck("Test Deck2", false, cards);
		testDeck2.setId(1);
		
		assertTrue(testDeck.equals(testDeck));
		assertTrue(testDeck2.equals(testDeck2));
		assertFalse(testDeck2.equals(testDeck));
	}
	
	@Test
	public void testJSON(){
		ArrayList<Integer> cards = new ArrayList<Integer>();
		cards.add(5);
		cards.add(2);
		Deck testDeck = new Deck("Test Deck", false, cards);
		String jsonMessage = testDeck.toJSON();
		Deck fromMessage = Deck.fromJson(jsonMessage);
		
		assertEquals(testDeck.getName(), fromMessage.getName());
		assertEquals(testDeck.getCards(), fromMessage.getCards());
		assertEquals(testDeck.canSelectMultipleCards(), fromMessage.canSelectMultipleCards());
	}
	
	@Test
	public void testGetAndSetName(){
		ArrayList<Integer> cards = new ArrayList<Integer>();
		Deck testDeck = new Deck("Test Deck", false, cards);
		assertEquals(testDeck.getName(), testDeck.getName());
		assertEquals(testDeck.toString(), testDeck.getName());

		testDeck.setName("New Name");
		assertEquals(testDeck.getName(), "New Name");
		assertEquals(testDeck.toString(), "New Name");


	}
	
	@Test
	public void testToStringAndIsMyDeck(){
		ArrayList<Integer> cards = new ArrayList<Integer>();
		Deck testDeck = new Deck("Test Deck", false, cards);
		assertFalse(testDeck.isMyDeck());
		assertEquals(testDeck.toString(),"Test Deck");
		assertEquals(testDeck.ToString(),"Test Deck");
		
		testDeck.setMyDeck(true);
		assertTrue(testDeck.isMyDeck());
		assertEquals(testDeck.toString(),"Test Deck (Owner)");
		assertEquals(testDeck.ToString(),"Test Deck");

	}
	
	
	
	@Test
	public void testCopyFrom(){
		ArrayList<Integer> cards = new ArrayList<Integer>();
		Deck testDeck = new Deck("Test Deck", false, cards);
		testDeck.setCanSelectMultipleCards(true);
		
		Deck copiedDeck = new Deck();
		copiedDeck.copyFrom(testDeck);
		
		assertEquals(copiedDeck.getName(), "Test Deck");
		assertEquals(copiedDeck.getDeckCreator(),testDeck.getDeckCreator());
		assertTrue(copiedDeck.canSelectMultipleCards());

		
	}
	
	@Test
	public void testInUse(){
		ArrayList<Integer> cards = new ArrayList<Integer>();
		Deck testDeck = new Deck("Test Deck", false, cards);
		assertFalse(testDeck.isInUse());
		testDeck.setInUse(true);
		assertTrue(testDeck.isInUse());

	}
	
	@Test
	public void testIsUsable(){
		ArrayList<Integer> cards = new ArrayList<Integer>();
		cards.add(-1);
		cards.add(0);
		Deck testDeck = new Deck("Test Deck", false, cards);
		assertFalse(testDeck.isUsable());
		
		cards.add(3);
		cards.add(4);
		assertTrue(testDeck.isUsable());

		
	}
	
	@Test
	public void testFromJsonArray(){
		
	}
	
}
