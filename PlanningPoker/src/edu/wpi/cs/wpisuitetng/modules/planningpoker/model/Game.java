package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Game;

public class Game extends AbstractModel{
	private int id;
	private String name;
	private int numRequirements;
	
	public Game() {
		id = 0;
		name = "";
		numRequirements = 0;
	}
	
	public int getID() {
		return id;
	}
	
	public static Game fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Game.class);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Copies all of the values from the given game to this game.
	 * 
	 * @param updatedGame
	 *            the requirement to copy from.
	 */
	public void copyFrom(Game updatedGame) {
		this.id = updatedGame.id;
		this.name = updatedGame.name;
		this.numRequirements = updatedGame.numRequirements;
	}
}
