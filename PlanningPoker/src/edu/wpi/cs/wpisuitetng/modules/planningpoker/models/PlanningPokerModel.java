/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * This is a model for the planning poker module. This model
 * contains a list of games. It extends AbstractListModel so that
 * it can provide the model data to the JList component in the BoardPanel.
 *
 * @author Team Code On Bleu
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PlanningPokerModel extends AbstractListModel<Game> {

	// the list of all games this user could access
	private final List<Game> games;
	//the next available ID number for the game to be added
	private int nextID;

	//the static object allow the planning poker model to become a singleton
	private static PlanningPokerModel instance = null;

	private PlanningPokerModel() {
		games = new ArrayList<Game>();
		nextID = 1;
	}

	/**
	 * 
	 * @return reference to PlanningPokerModel
	 */
	public static PlanningPokerModel getInstance() {
		if (instance == null) {
			instance = new PlanningPokerModel();
		}
		return instance;
	}
	
	/**
	 * Adds a game to the list
	 *
	 * @param newGame the new game to be added to the list
	 */
	public void AddGame(Game newGame) {
		//newGame.setId(nextID++);
		games.add(newGame);
		// TODO: controller.getInstance().refreshTable()/addRequirement
	}


	/**
	 * Return the game with the given id
	 * 
	 * @param id of the game wanted
	 * 
	 * @return Game which has the given id, or null if no game matches this id
	 * 
	 */
	public Game getGame(int id){
		Game temp = null;
		for(int i = 0;i < games.size();i++){
			temp = games.get(i);
			if(temp.getId() == id){
				break;
			}
		}
		return temp;
	}


	/**
	 * Return all games stored in this model
	 * @return all games in list
	 */
	public List<Game> getAllGames(){
		return games;
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
		return getAllGames();
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

	/**
	 * Adds each game in the database to the PlanningPokerModel
	 * @param games is the array of games that the database currently holds
	 */
	public void addAllGames(Game[] games) {
		for(Game game: games)
		{
			this.AddGame(game);
		}
	}

	/**
	 * Removes all elements from the PlanningPokerModel
	 */
	public void emptyModel() {
		final int oldSize = getSize();
		final Iterator<Game> iterator = games.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
		this.setId(0);
	}

	/**
	 * Set nextID to the passed value
	 * @param i the value to set nextID to
	 */
	private void setId(int i) {
		nextID = i;
	}

	/**
	 * Updates the given game
	 * @param currentGame
	 */
	public static void UpdateGame(Game currentGame) {
		// TODO Auto-generated method stub
		
	}

}
