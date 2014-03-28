package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;



public class GamesToTable {
	
	private final int NUM_OF_ELEMENTS_IN_A_GAME = 3; // number of elements in a game (e.x. id, name, num of requirements)
	Game[] games;
	
	public GamesToTable(Game[] gme)
	{ 
	 games = gme;
	}

	public String[][] getTable()
	{
		String[][] str = new String[games.length][NUM_OF_ELEMENTS_IN_A_GAME];
		
		for(int i = 0; i < games.length; i++)
		{
			for(int j = 0; j < NUM_OF_ELEMENTS_IN_A_GAME; j++)
			{
				if(j % 3 == 0) // get id
				{
					str[i][j] = "" + games[i].getId();
				}
				
				if(j % 3 == 1) // get name
				{
					str[i][j] = games[i].getName();
				}
				
				if(j % 3 == 2) // get num of requirements
				{
					str[i][j] = "" + games[i].getEstimates().size();
				}	
			}
		}
		
		return str;
	}
}
