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
		Deck testDeck = new Deck("Test Deck", false, cards);
		Deck testDeck2 = new Deck("Test Deck2", false, cards);
		
		assertTrue(testDeck.identify("Test Deck"));
		assertFalse(testDeck.identify("Test Deck2"));
		assertTrue(testDeck.identify(testDeck));
		assertFalse(testDeck.identify(testDeck2));
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
}
