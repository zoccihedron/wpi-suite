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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.utils.Mailer;

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
	private final JButton endGameButton;
	private final JLabel reportMessage;
	JPanel buttonsPanel;
	Game game;
	

	/**
	 * 
	 */
	public GameSummaryPanel() {
		
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridBagLayout());

		reportMessage = new JLabel();
		reportMessage.setText("     ");
		reportMessage.setFont(new Font("Dialog", Font.ITALIC, 12));
		reportMessage.setVisible(true);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 10, 0, 0);
		buttonsPanel.add(reportMessage, constraints);
		
		editGameButton = new JButton("Edit");
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 10, 0, 0);
		buttonsPanel.add(editGameButton, constraints);
		
		playGameButton = new JButton("Play");
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 10, 0, 0);
		buttonsPanel.add(playGameButton, constraints);
		
		endGameButton = new JButton("End Game");
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 10, 0, 0);
		buttonsPanel.add(endGameButton, constraints);
		
		endGameButton.setVisible(false);
		endGameButton.setEnabled(false);
		
		endGameButton.addActionListener(new EndGameManuallyController(this, game, true));
		
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
		constraints.anchor = GridBagConstraints.SOUTHEAST;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 5;
		constraints.gridy = 2;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(buttonsPanel, constraints);
		
		
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
				final Mailer m = new Mailer(game);
				m.notifyStart();
			}

 		});
	}
	
	/**
	 * fills empty fields with values specific to game
	 * @param game to view summary of
	 */
	public void updateSummary(Game game){
		this.game = game;
		
		if(game.getGameCreator().equals(ConfigManager.getConfig().getUserName())) {
			//make all buttons visible
			editGameButton.setVisible(true);
			endGameButton.setVisible(true);
			
			// If the game is a draft.
			if(game.getStatus().equals(GameStatus.DRAFT)) {
				playGameButton.setEnabled(false);
				editGameButton.setEnabled(true);
				endGameButton.setEnabled(false);
			}
			// If the game is in progress.
			else if(game.getStatus().equals(GameStatus.IN_PROGRESS)) {
				playGameButton.setEnabled(true);
				editGameButton.setEnabled(false);
				endGameButton.setEnabled(true);
			}
			// If the game is ended.
			else {
				playGameButton.setEnabled(false);
				editGameButton.setEnabled(false);
				endGameButton.setEnabled(false);
			}
		}
		// If the user is not the game creator.
		else {			
			// disable all instances of edit and end game
			editGameButton.setVisible(false);
			editGameButton.setEnabled(false);
			endGameButton.setVisible(false);
			endGameButton.setEnabled(false);
			
			// Users cannot see the drafts of other users.
			// If the game is in progress.
			if(game.getStatus().equals(GameStatus.IN_PROGRESS)) {
				playGameButton.setEnabled(true);

			}
			// If the game is ended.
			else {
				playGameButton.setEnabled(false);
			}
		}
		
		// play game button is always visible
		playGameButton.setVisible(true);
		
		infoPanel.updateInfoSummary(game);
		reqPanel.updateReqSummary(game);

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