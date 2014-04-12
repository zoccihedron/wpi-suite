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
		
	}
}
