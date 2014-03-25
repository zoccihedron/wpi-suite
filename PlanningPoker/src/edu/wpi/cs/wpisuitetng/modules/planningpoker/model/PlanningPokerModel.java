/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * This is a model for the planning poker module. This model
 * contains a list of games. It extends AbstractListModel so that
 * it can provide the model data to the JList component in the BoardPanel.
 *
 * @author Robert
 * @version Mar 22, 2014
 */
public class PlanningPokerModel extends AbstractListModel<Game>{
	
	private List<Game> games;
	
	private static PlanningPokerModel instance;
	
	public PlanningPokerModel() {
		games = new ArrayList<Game>();
	}
	
	/**
	 * Adds a game to the list
	 *
	 * @param newGame the new game to be added to the list
	 */
	public void AddGame(Game newGame) {
		games.add(newGame);
	}

	/**
	 * Returns the length of the list of games
	 * 
	 * @return length of games list
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return games.size();
	}
	
	public List<Game> getGames()
	{
		return games;
	}

	/**
	 * Returns the game at the index. This
	 * implementation indexes in the reverse
	 * order.
	 * @param index the index in the list
	 * 
	 * @return the desired game
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Game getElementAt(int index) {
		return games.get(games.size() - 1 - index);
	}

	public static PlanningPokerModel getInstance() {
		if(instance == null)
		{
			instance = new PlanningPokerModel();
		}
		
		return instance;
	}

}
