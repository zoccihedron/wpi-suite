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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.GameSummaryPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.ListGamePanel;

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
	
	public void refreshListGames(){
		listGamePanel.refresh();
	}
	

}
