package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.controller;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.PlanningPokerModel;



public class GamesToTable {
	
	private final int NUM_OF_ELEMENTS_IN_A_GAME = 3; // number of elements in a game (e.x. id, name, num of requirements)
	List<Game> games;
	
	public GamesToTable()
	{
	 games = PlanningPokerModel.getInstance().getGames();
	}

	public String[][] getTable()
	{
		String[][] str = new String[games.size()][NUM_OF_ELEMENTS_IN_A_GAME];
		
		for(int i = 0; i < games.size(); i++)
		{
			for(int j = 0; j < NUM_OF_ELEMENTS_IN_A_GAME; j++)
			{
				if(j % 3 == 0) // get id
				{
					str[i][j] = "" + games.get(i).getID();
				}
				
				if(j % 3 == 1) // get name
				{
					str[i][j] = games.get(i).getName();
				}
				
				if(j % 3 == 2) // get num of requirements
				{
					str[i][j] = "" + games.get(i).getNumRequirements();
				}	
			}
		}
		return str;
	}
}
