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


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import static org.junit.Assert.*;


public class PlanningPokerModelTest {
	PlanningPokerModel model;
	Date start, end;
	
	@Before
	public void setUp() throws WPISuiteException{
		start = new Date();
		Calendar endTime = new GregorianCalendar();
		endTime.set(2015, 1,1);
		end = endTime.getTime();	
	}
	
	
	@Test
	public void addGameTest() throws WPISuiteException{
		model = PlanningPokerModel.getInstance();
		model.emptyModel();
		assertEquals(model.getSize(), 0);
		
		Game newGame1 = new Game("newGame1", start, end, -2);
		newGame1.setId(1);
		model.AddGame(newGame1);
		assertEquals(model.getSize(), 1);
		
		Game retrievedGame = model.getElementAt(0);
		assertEquals(retrievedGame.getName(), newGame1.getName());
		assertEquals(retrievedGame.getId(), newGame1.getId());
		assertEquals(retrievedGame.getDeck(), newGame1.getDeck());
		assertEquals(retrievedGame.getDescription(), newGame1.getDescription());
		 
	}
	
	@Test
	public void getGameUsingGameIdTest() throws WPISuiteException{
		model = PlanningPokerModel.getInstance();
		model.emptyModel();
		
		Game newGame1 = new Game("newGame1", start, end, -2);
		newGame1.setId(1);
		model.AddGame(newGame1);

		Game retrievedGame = model.getGame(newGame1.getId());
		assertEquals(retrievedGame.getName(), newGame1.getName());
		assertEquals(retrievedGame.getId(), newGame1.getId());
		assertEquals(retrievedGame.getDeck(), newGame1.getDeck());
		assertEquals(retrievedGame.getDescription(), newGame1.getDescription());

	}
	
	@Test
	public void getAllGamesTest() throws WPISuiteException{
		model = PlanningPokerModel.getInstance();
		model.emptyModel();

		Game newGame1 = new Game("newGame1", start, end, -2);
		newGame1.setId(1);
		model.AddGame(newGame1);

		Game newGame2 = new Game("newGame2", start, end, -2);
		newGame2.setId(2);
		model.AddGame(newGame2);
		
		Game newGame3 = new Game("newGame3", start, end, -2);
		newGame2.setId(3);
		model.AddGame(newGame3);
		
		List<Game> retrievedAllGames = model.getAllGames();
		assertEquals(retrievedAllGames.size(), 3);
		assertTrue(retrievedAllGames.contains(newGame1));
		assertTrue(retrievedAllGames.contains(newGame2));
		assertTrue(retrievedAllGames.contains(newGame3));

	}

	@Test
	public void getGamesTest() throws WPISuiteException{
		model = PlanningPokerModel.getInstance();
		model.emptyModel();

		Game newGame1 = new Game("newGame1", start, end, -2);
		newGame1.setId(1);
		model.AddGame(newGame1);

		Game newGame2 = new Game("newGame2", start, end, -2);
		newGame2.setId(2);
		model.AddGame(newGame2);
		
		Game newGame3 = new Game("newGame3", start, end, -2);
		newGame2.setId(3);
		model.AddGame(newGame3);
		
		List<Game> retrievedAllGames = model.getGames();
		assertEquals(retrievedAllGames.size(), 3);
		assertTrue(retrievedAllGames.contains(newGame1));
		assertTrue(retrievedAllGames.contains(newGame2));
		assertTrue(retrievedAllGames.contains(newGame3));

	}	
	
	@Test
	public void addAllGamesTest() throws WPISuiteException{
		model = PlanningPokerModel.getInstance();
		model.emptyModel();
		
		Game[] gameArray = new Game[3];
		Game newGame1 = new Game("newGame1", start, end, -2);
		newGame1.setId(1);
		gameArray[0] = newGame1;
		Game newGame2 = new Game("newGame2", start, end, -2);
		newGame2.setId(2);
		gameArray[1] = newGame2;
		Game newGame3 = new Game("newGame3", start, end, -2);
		newGame3.setId(3);
		gameArray[2] = newGame3;
	
		model.addAllGames(gameArray);

		Game game1 = model.getElementAt(2);
		Game game2 = model.getElementAt(1);
		Game game3 = model.getElementAt(0);
		assertEquals(game1.getName(), "newGame1");
		assertEquals(game1.getId(), 1);
		assertEquals(game1.getDeck(), newGame1.getDeck());
		assertEquals(game2.getName(), "newGame2");
		assertEquals(game2.getId(), 2);
		assertEquals(game2.getDeck(), newGame2.getDeck());
		assertEquals(game3.getName(), "newGame3");
		assertEquals(game3.getId(), 3);
		assertEquals(game3.getDeck(), newGame3.getDeck());

	}
	


}
