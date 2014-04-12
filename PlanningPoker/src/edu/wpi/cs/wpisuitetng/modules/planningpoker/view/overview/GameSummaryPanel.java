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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame.EndGameManuallyController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;

/**
 * GameSummaryPanel is a class which displays the summary of a game that can be
 * chosen to edit and/or play by a user
 * 
 * @author Team Code On Bleu
 * @version 1.0
 */
@SuppressWarnings({ "serial" })
public class GameSummaryPanel extends JPanel {

	private final GameSummaryInfoPanel infoPanel;
	private final GameSummaryReqPanel reqPanel;
	private final JButton editGameButton;
	private final JButton playGameButton;
	private final JButton endGameBtn;
	private final JLabel reportMessage;
	private final JPanel buttonsPanel;
	private final JPanel endPanel;
	Game game;
	

	/**
	 * 
	 */
	public GameSummaryPanel() {
		
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		
		buttonsPanel = new JPanel();
		endPanel = new JPanel();

		editGameButton = new JButton("Edit");
		playGameButton = new JButton("Play");
		buttonsPanel.add(editGameButton);
		buttonsPanel.add(playGameButton);
		
		endGameBtn = new JButton("End");
		reportMessage = new JLabel();
		endPanel.add(endGameBtn);
		endPanel.add(reportMessage);
		
		infoPanel = new GameSummaryInfoPanel();
		infoPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
		
		reqPanel = new GameSummaryReqPanel();
		reqPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.gridwidth = 6;
		constraints.weightx = 1.0;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(infoPanel, constraints);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.PAGE_END;
		constraints.gridwidth = 6;
		constraints.weightx = 1.0;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(reqPanel, constraints);

		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.SOUTHWEST;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(endPanel, constraints);
		
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.SOUTHEAST;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 5;
		constraints.gridy = 2;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(buttonsPanel, constraints);
		
		// TODO
		/*
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.SOUTHWEST;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(endGameBtn, constraints);
		endGameBtn.setVisible(false);
		endGameBtn.setEnabled(false);
		
		reportMessage.setText("testtesttest     ");
		reportMessage.setFont(new Font("Dialog", Font.ITALIC, 12));
		reportMessage.setVisible(true);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 10, 0, 0);
		add(reportMessage, constraints);
		//end TODO
		*/
		
		editGameButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				final MainViewTabController mvt = MainViewTabController.getInstance();
 				mvt.createGameTab(game);
				
			}
 			
 		});
		
		playGameButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				final MainViewTabController mvt = MainViewTabController.getInstance();
 				mvt.playGameTab(game);
				
			}

 		});

		endGameBtn.addActionListener(new EndGameManuallyController(this, game, true));
	}
	
	/**
	 * fills empty fields with values specific to game
	 * @param game to view summary of
	 */
	public void updateSummary(Game game){
		this.game = game;
		reportMessage.setText("     ");

		
		// Controls whether the buttons are enabled/disabled and visible/invisible.
		// The buttons start, edit, or end the game.
		// playGameBtn, editGameBtn, and endGameBtn

		// play game button is always visible
		playGameButton.setVisible(true);

		// If the user is the game creator.
		if(game.getGameCreator().equals(ConfigManager.getConfig().getUserName())) {
			//make all buttons visible
			editGameButton.setVisible(true);
			endGameBtn.setVisible(true);

			// If the game is a draft.
			if(game.getStatus().equals(GameStatus.DRAFT)) {
				playGameButton.setEnabled(false);
				editGameButton.setEnabled(true);
				endGameBtn.setEnabled(false);
			}
			// If the game is in progress.
			else if(game.getStatus().equals(GameStatus.IN_PROGRESS)) {
				playGameButton.setEnabled(true);
				editGameButton.setEnabled(false);
				endGameBtn.setEnabled(true);
			}
			// If the game is ended.
			else {
				playGameButton.setEnabled(false);
				editGameButton.setEnabled(false);
				endGameBtn.setEnabled(false);
			}
		}
		// If the user is not the game creator.
		else {			
			// disable all instances of edit and end game
			editGameButton.setVisible(false);
			editGameButton.setEnabled(false);
			endGameBtn.setVisible(false);
			endGameBtn.setEnabled(false);

			// Users cannot see the drafts of other users.
			// If the game is in progress.
			if(game.getStatus().equals(GameStatus.IN_PROGRESS)) {
				playGameButton.setEnabled(true);

			}
			// If the game is ended.
			else {
				playGameButton.setEnabled(false);
			}


			infoPanel.updateInfoSummary(game);
			reqPanel.updateReqSummary(game);
		}
	}
	
	public Game getGameObject() {
		return game;
	}
	
	public void reportError(String string) {
		reportMessage.setText(string);
		reportMessage.setForeground(Color.RED);
	}
	
	public void reportSuccess(String string) {
		reportMessage.setText(string);
		reportMessage.setForeground(Color.BLUE);
	}
}

// TODO if no problems, delete everything beneath this
	/** Function which returns a string with all requirement names
	 * for the given game appended (with new lines) into ones
	 * string
	 * 
	 * @param game holds the requirements that will be returned
	 * @return String of appended requirement names for the game
	 */
/*
	private static String getRequirementNames(Game game) {
		String temp = "";

		RequirementManagerFacade.getInstance();
		final List<Requirement> reqs =
						RequirementManagerFacade.getInstance().getPreStoredRequirements();
		for(Requirement r : reqs)
		{
			if(game.getRequirements().contains(r.getId())){
				temp += r.getName() + "\n";
			}
		}
		return temp;
	}

}*/
