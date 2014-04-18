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
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame.EndGameManuallyController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
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
	private final JButton endGameButton;
	private final JButton viewResultsButton;
	private final JButton sendReqsButton;
	private final JLabel reportMessage;
	private final GameSummaryPanel  gameSummaryPanel= this;
	JPanel buttonsPanel;
	Game game;
	

	/**
	 * 
	 */
	public GameSummaryPanel() {
		
		// Set up layout constraints
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		
		
		// Panel for buttons that apply to all games
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridBagLayout());
		
		
		// Button to edit game
		editGameButton = new JButton("Edit");
		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		buttonsPanel.add(editGameButton, constraints);
		editGameButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				final MainViewTabController mvt = MainViewTabController.getInstance();
 				mvt.createGameTab(game);
				
			}
 			
 		});
		
		// Button to play game
		playGameButton = new JButton("Play");
		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 10, 0, 20);
		buttonsPanel.add(playGameButton, constraints);
		constraints.insets = new Insets(0, 0, 0, 0);
		playGameButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				final MainViewTabController mvt = MainViewTabController.getInstance();
 				mvt.playGameTab(game);
			}
 		});
		viewResultsButton = new JButton("View Results");
		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 10, 0, 20);
		buttonsPanel.add(viewResultsButton, constraints);
		constraints.insets = new Insets(0, 0, 0, 0);
		viewResultsButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				final MainViewTabController mvt = MainViewTabController.getInstance();
 				mvt.viewResultsTab(game);
			}
 		});
		
		
		// Button to send requirements to requirement manager
		sendReqsButton = new JButton("Send Estimates");
		sendReqsButton.setToolTipText("Update estimates in the Requirement Manager");
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 20, 0, 0);
		add(sendReqsButton, constraints);
		constraints.insets = new Insets(0, 0, 0, 0);
		sendReqsButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				RequirementManagerFacade.getInstance().sendEstimates(game.getEstimates(),gameSummaryPanel);				
			}
 		});
		
		// Button to end game manually
		endGameButton = new JButton("End Game");
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 20, 0, 0);
		add(endGameButton, constraints);
		endGameButton.setVisible(false);
		endGameButton.setEnabled(false);
		endGameButton.addActionListener(new EndGameManuallyController(this, game, true));
		
		
		JPanel extraPanel1 = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		buttonsPanel.add(extraPanel1, constraints);
		
		// Panel that contains summary of game information
		infoPanel = new GameSummaryInfoPanel();
		infoPanel.setBorder(new EmptyBorder(0, 20, 10, 20));
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.gridwidth = 4;
		constraints.weightx = 1.0;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		add(infoPanel, constraints);
		
		// Panel that contains requirements in game
		reqPanel = new GameSummaryReqPanel();
		reqPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.PAGE_END;
		constraints.gridwidth = 4;
		constraints.weightx = 1.0;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.ipadx = 20;
		constraints.ipady = 20;
		add(reqPanel, constraints);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 3;
		constraints.gridy = 2;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		add(buttonsPanel, constraints);
		
		
		reportMessage = new JLabel();
		reportMessage.setText("     ");
		reportMessage.setFont(new Font("Dialog", Font.ITALIC, 12));
		reportMessage.setVisible(true);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		add(reportMessage, constraints);
		
		JPanel extraPanel2 = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		add(extraPanel2, constraints);
		
		
		
		
		
		
		
		

		try {
		    Image img = ImageIO.read(getClass().getResource("yield.png"));
		    editGameButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("stop.png"));
		    endGameButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("greenCircle.png"));
		    playGameButton.setIcon(new ImageIcon(img));   
		} 
		catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

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
				sendReqsButton.setVisible(false);
				viewResultsButton.setEnabled(false);
			}
			// If the game is in progress.
			else if(game.getStatus().equals(GameStatus.IN_PROGRESS)) {
				playGameButton.setEnabled(true);
				editGameButton.setEnabled(false);
				endGameButton.setEnabled(true);
				sendReqsButton.setVisible(false);
				viewResultsButton.setEnabled(false);
			}
			// If the game is ended.
			else {
				playGameButton.setEnabled(false);
				editGameButton.setEnabled(false);
				endGameButton.setEnabled(false);
				endGameButton.setVisible(false);
				sendReqsButton.setVisible(true);
				viewResultsButton.setEnabled(true);
				
			}
		}
		// If the user is not the game creator.
		else {			
			// disable all instances of edit and end game
			editGameButton.setVisible(false);
			editGameButton.setEnabled(false);
			endGameButton.setVisible(false);
			endGameButton.setEnabled(false);
			sendReqsButton.setVisible(false);
			viewResultsButton.setEnabled(false);
			
			// Users cannot see the drafts of other users.
			// If the game is in progress.
			if(game.getStatus().equals(GameStatus.IN_PROGRESS)) {
				playGameButton.setEnabled(true);
				viewResultsButton.setEnabled(false);

			}
			// If the game is ended.
			else {
				playGameButton.setEnabled(false);
				viewResultsButton.setEnabled(true);
			}
		}
		
		// play game button is always visible
		playGameButton.setVisible(true);
		
		infoPanel.updateInfoSummary(game);
		reqPanel.updateReqSummary(game);

		reportMessage.setText("");
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