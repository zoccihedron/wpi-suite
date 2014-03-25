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
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * This is a model for the planning poker module. This model
 * contains a list of games. It extends AbstractListModel so that
 * it can provide the model data to the JList component in the BoardPanel.
 *
 * @author Robert, yyan
 * @version Mar 25, 2014
 */
public class PlanningPokerModel extends AbstractListModel<Game>{
	
	// the list of all games this user could access
	private List<Game> games;
	//the next available ID number for the game to be added
	private int nextID;
	
	//the static object allow the planning poker model to become a singleton
	private static PlanningPokerModel instance;

	
	private PlanningPokerModel() {
		games = new ArrayList<Game>();
		nextID = 1;
	}
	
	
	/**
	 * 
	 * @return the instance of the planning poker model singleton.
	 */
	public static PlanningPokerModel getInstance(){
		if(instance == null){
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
		newGame.setId(nextID++);
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
			if(temp.getID()==id){
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

}
