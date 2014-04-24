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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.EstimationPane;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.GameInfoPanel;

/**
 * This controller sets and updates the estimation pane and game info panel.
 *
 * @author Team Code On Bleu
 * @version Apr 9, 2014
 */
public class PlayGameController {
	
	
	private EstimationPane estimationPane = null;
	private GameInfoPanel infoPanel = null;
	private boolean firstTime = true;
	
	public PlayGameController() {
	}

	public void setEstimationPane(EstimationPane ep){
		estimationPane = ep;
	}
	
	public void setGameInfoPanel(GameInfoPanel gio){
		infoPanel = gio;
	}
	
	/**
	 * Updates the GameInfo panel with the given game
	 *
	 * @param game the given game
	 */
	public void updateGameInfo(Game game){
		infoPanel.updatePanel(game);
	}
	
	/**
	 * Updates the estimation pane with the id and game
	 *
	 * @param id the requirement ID
	 * @param game the game
	 */
	public void updateEstimationPane(int id, Game game) {
		if (firstTime) {
			estimationPane.setUpEstimationPane();
		}
		firstTime = false;
		estimationPane.getDeckPanel().enableVoting();
		estimationPane.setGameAndRequirement(id, game);
	}
	
	
	

}
