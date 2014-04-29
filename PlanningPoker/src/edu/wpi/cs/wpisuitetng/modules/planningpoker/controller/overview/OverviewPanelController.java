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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame.UpdateGameRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.GameSummaryPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.ListGamePanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This panel controls setting and updating the game summary
 * panel on the Overview panel
 *
 * @author Team Code On Bleu
 * @version Apr 9, 2014
 */
public class OverviewPanelController {
	
	private static OverviewPanelController instance = null;
	private GameSummaryPanel gameSummaryPanel = null;
	private ListGamePanel listGamePanel = null;
	
	private OverviewPanelController() {
	}
	
	/**
	 * Finds an instance of the OverviewPanelController, or
	 * makes one if there is not one.
	 *
	 * @return an instance of the OverviewPanelController
	 */
	public static OverviewPanelController getInstance(){
		if(instance == null){
			instance = new OverviewPanelController();
		}
		return instance;
	}

	public void setGameSummary(GameSummaryPanel gsp){
		 gameSummaryPanel = gsp;
	}
	
	public void setListGames(ListGamePanel lgp){
		listGamePanel = lgp;
	}
	
	/**
	 * Updates the gameSummaryPanel in the overview with
	 * information from the given game
	 *
	 * @param game the given game
	 */
	public void updateGameSummary(Game game) {
		gameSummaryPanel.updateSummary(game);
	}
	
	/**
	 * Refresh listGamePanel
	 */
	public void refreshListGames(){
		listGamePanel.updateTree();
	}
	
	/**
	 * Start game
	 * @param game to start
	 */
	public void startGame(Game game){
		game.setStatus(GameStatus.IN_PROGRESS);
		// Send a request to the core to save this game
		final Request request = Network.getInstance().makeRequest
				("planningpoker/game", HttpMethod.POST);
		// put the updated game in the body of the request
		request.setBody(game.toJSON());
		request.send(); // send the request
		updateGameSummary(game);
	}

}
