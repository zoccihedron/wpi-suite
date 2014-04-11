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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;

/**
 * This class is a JPanel. 
 * It contains both a summary of fields from a game and 
 * a table of requirements in a game.
 * 
 * @author Code On Bleu
 * @version 1.0
 */
@SuppressWarnings({ "serial" })
public class GameSummaryPanel extends JPanel {

	private final GameSummaryInfoPanel infoPanel;
	private final GameSummaryReqPanel reqPanel;
	private final JButton editGameButton;
	private final JButton playGameButton;
	JPanel buttonsPanel;
	Game game;
	

	/**
	 * 
	 */
	public GameSummaryPanel() {
		
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();

		buttonsPanel = new JPanel();

		editGameButton = new JButton("Edit");
		playGameButton = new JButton("Play");
		buttonsPanel.add(editGameButton);
		buttonsPanel.add(playGameButton);
		
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
				
			}

 		});
	}
	
	/**
	 * fills empty fields with values specific to game
	 * @param game to view summary of
	 */
	public void updateSummary(Game game){
		this.game = game;
		
		if(game.getStatus()==(GameStatus.DRAFT)){
			playGameButton.setEnabled(false);
		}
		else{
			playGameButton.setEnabled(true);
		}
		
		if(game.getStatus()==GameStatus.ENDED){
			playGameButton.setEnabled(false);
			editGameButton.setEnabled(false);
		}
		else{
			playGameButton.setEnabled(true);
			editGameButton.setEnabled(true);
		}
		
		infoPanel.updateInfoSummary(game);
		reqPanel.updateReqSummary(game);
	}

	
}

	
