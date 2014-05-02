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
import java.text.DecimalFormat;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame.EndGameManuallyController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.OverviewPanelController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * GameSummaryPanel is a class which displays the summary of a game that can be
 * chosen to edit and/or play by a user
 * 
 * @author Team Code On Bleu
 * @version 1.0
 */
@SuppressWarnings({ "serial" })
public class GameSummaryPanel extends JPanel {
	private GameSummaryInfoPanel infoPanel;
	private GameSummaryReqPanel reqPanel;
	private JButton editGameButton;
	private JButton playGameButton;
	private JButton endGameButton;
	private JButton startGameButton;
	private JButton viewResultsButton;
	private JButton closeGameButton;
	private JLabel helpTitle;
	private JLabel helpText;
	private JLabel reportMessage;
	private final GameSummaryPanel  summaryPanel = this;
	JPanel buttonsPanel;
	Game game = null;
	private JProgressBar overallProgressBar;
	private JProgressBar userProgressBar;
	private Timer gameUpdateTimer;
	
	/**
	 * 
	 */
	public GameSummaryPanel() {
		
		populatePanel();

	}
	 /**
	  * sets up help panel to show before a game has been selected
	  */
	private void populatePanel(){
		// Set up layout constraints
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
				
		helpTitle = new JLabel();
		helpText = new JLabel();
		
		helpTitle.setText("Games Overview");
		helpTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		helpText.setText("To begin, please select a game from the tree on the left.");
		helpText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(helpTitle, constraints);
		
		constraints.gridy = 1;
		constraints.gridx = 0;
		this.add(helpText, constraints);
		
		gameUpdateTimer = new Timer(5000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(game != null && 
						Network.getInstance().getDefaultNetworkConfiguration() 
							!= null){
					// Send a request to the core to save this game
					final Request request = Network.getInstance().makeRequest
							("planningpoker/game/" + game.getId(), HttpMethod.GET);
					// add an observer to process the response
					request.addObserver(new RequestObserver() {
						
						@Override
						public void responseSuccess(IRequest iReq) {
							final ResponseModel response = iReq.getResponse();
							final Game returnedGame = Game.fromJsonArray(response.getBody())[0];
							updateIfChanged(returnedGame);
						}
						
						@Override
						public void responseError(IRequest iReq) {
							System.err.println("The request to get a game failed.");
						}
						
						@Override
						public void fail(IRequest iReq, Exception exception) {
							System.err.println("The request to get a game failed.");
						}
					});
					request.send(); // send the request
					
				}
				
			}
		});
		gameUpdateTimer.start();
		
	}
	
	/**
	 * shows this panel once a game has been selected
	 */
	private void populateGameSummaryPanel(){
		// Set up layout constraints
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		
		
		// Panel for buttons that apply to all games
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridBagLayout());
		
		
		// Button to edit game
		editGameButton = new JButton("Edit");
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 5, 5, 0);
		add(editGameButton, constraints);
		constraints.insets = new Insets(0, 0, 0, 0);
		editGameButton.setEnabled(false);
		editGameButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				final MainViewTabController mvt = MainViewTabController
						.getInstance();

				if (game.getStatus().equals(GameStatus.IN_PROGRESS)) {

					final Request request = Network.getInstance()
							.makeRequest("Advanced/planningpoker/game/edit",
									HttpMethod.POST);
					request.setBody(game.toJSON());
					request.addObserver(new RequestObserver() {

						@Override
						public void responseSuccess(IRequest iReq) {
							final ResponseModel response = iReq.getResponse();
							final String message = response.getBody();
							if (message.trim().equals("true")) {
								mvt.createGameTab(game, true);
							} else {
								summaryPanel.reportError(message);
								editGameButton.setEnabled(false);
							}
						}

						@Override
						public void responseError(IRequest iReq) {
							// TODO Auto-generated method stub

						}

						@Override
						public void fail(IRequest iReq, Exception exception) {
							// TODO Auto-generated method stub

						}
					});
					request.send();
				}
 				else{
 					mvt.createGameTab(game, false);
 				}
			}
 			
 		});
		
		// Button to start game
		startGameButton = new JButton("Start Game");
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 20, 5, 0);
		add(startGameButton, constraints);
		constraints.insets = new Insets(0, 0, 0, 0);
		startGameButton.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent e) {
				OverviewPanelController.getInstance().startGame(game);
			}
		});
		
		// Button to end game manually
		endGameButton = new JButton("End Game");
		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 10, 5, 0);
		buttonsPanel.add(endGameButton, constraints);
		constraints.insets = new Insets(0, 0, 0, 0);
		endGameButton.setVisible(false);
		endGameButton.setEnabled(false);
		endGameButton.addActionListener(new EndGameManuallyController(this, game, true));
		
		closeGameButton = new JButton("Close Game");
		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 10, 5, 0);
		buttonsPanel.add(closeGameButton, constraints);
		constraints.insets = new Insets(0, 0, 0, 0);
		closeGameButton.addActionListener(new EndGameManuallyController(this, game, true));

		
		
		// Button to play game
		playGameButton = new JButton("Play");
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 20, 5, 0);
		add(playGameButton, constraints);
		constraints.insets = new Insets(0, 0, 0, 0);
		playGameButton.setEnabled(false);
		playGameButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				final MainViewTabController mvt = MainViewTabController.getInstance();
 				mvt.playGameTab(game);
			}
 		});
		
		// Button to view results
		viewResultsButton = new JButton("View Results");
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 20, 5, 0);
		add(viewResultsButton, constraints);
		constraints.insets = new Insets(0, 0, 0, 0);
		viewResultsButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				final MainViewTabController mvt = MainViewTabController.getInstance();
 				mvt.viewResultsTab(game);
			}
 		});
		
		overallProgressBar = new JProgressBar();
		overallProgressBar.setString("Team's Completion");
		overallProgressBar.setStringPainted(true);
		overallProgressBar.setVisible(false);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 4;
		constraints.insets = new Insets(0, 20, 0, 20);
		add(overallProgressBar, constraints);
		
		userProgressBar = new JProgressBar();
		userProgressBar.setString("Your Completion");
		userProgressBar.setStringPainted(true);
		userProgressBar.setVisible(false);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 4;
		constraints.insets = new Insets(10, 20, 0, 20);
		add(userProgressBar, constraints);
		
		final JPanel extraPanel1 = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
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
		constraints.gridy = 3;
		constraints.ipadx = 20;
		constraints.ipady = 20;
		add(reqPanel, constraints);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 3;
		constraints.gridy = 4;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.insets = new Insets(0, 0, 0, 20);
		add(buttonsPanel, constraints);
		constraints.insets = new Insets(0, 0, 0, 0);
		
		
		reportMessage = new JLabel();
		reportMessage.setText("     ");
		reportMessage.setFont(new Font("Dialog", Font.ITALIC, 12));
		reportMessage.setVisible(true);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		add(reportMessage, constraints);
		
		final JPanel extraPanel2 = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		add(extraPanel2, constraints);
		
		try {
		    Image img = ImageIO.read(getClass().getResource("editIcon.png"));
		    editGameButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("stop.png"));
		    endGameButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("playIcon.png"));
		    playGameButton.setIcon(new ImageIcon(img));
		    startGameButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("checkmark.png"));
		    viewResultsButton.setIcon(new ImageIcon(img));   
		    
		    img = ImageIO.read(getClass().getResource("close.png"));
		    closeGameButton.setIcon(new ImageIcon(img));
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
		this.removeAll();
		populateGameSummaryPanel();
		
		playGameButton.setEnabled(true);
		viewResultsButton.setEnabled(true);
		if(game.getGameCreator().equals(ConfigManager.getConfig().getUserName())) {
			editGameButton.setEnabled(true);
			endGameButton.setEnabled(true);
			closeGameButton.setEnabled(true);
			startGameButton.setEnabled(true);
			
			// If the game is a draft.
			if(game.getStatus().equals(GameStatus.DRAFT)) {
				playGameButton.setVisible(false);
				editGameButton.setVisible(true);
				endGameButton.setVisible(false);
				closeGameButton.setVisible(false);
				viewResultsButton.setVisible(false);
			}
			// If the game is in progress.
			else if(game.getStatus().equals(GameStatus.IN_PROGRESS)) {
				
				playGameButton.setVisible(!game.isEditing());
				editGameButton.setVisible(!game.isHasBeenEstimated());
				endGameButton.setVisible(true);
				closeGameButton.setVisible(false);
				viewResultsButton.setVisible(false);
				startGameButton.setVisible(false);
			}
			// If the game is ended.
			else if(game.getStatus().equals(GameStatus.ENDED)){
				playGameButton.setVisible(false);
				editGameButton.setVisible(false);
				endGameButton.setVisible(false);
				closeGameButton.setVisible(true);
				startGameButton.setVisible(false);
			}
			// If the game is closed.
			else {
				playGameButton.setVisible(false);
				editGameButton.setVisible(false);
				endGameButton.setVisible(false);
				closeGameButton.setVisible(false);
				viewResultsButton.setVisible(true);
				startGameButton.setVisible(false);
			}
		}
		// If the user is not the game creator.
		else {
			editGameButton.setVisible(false);
			endGameButton.setVisible(false);
			closeGameButton.setVisible(false);
			startGameButton.setVisible(false);
		
			// Users cannot see the drafts of other users.
			// If the game is in progress.
			if(game.getStatus().equals(GameStatus.IN_PROGRESS)) {
				playGameButton.setVisible(true);
				viewResultsButton.setVisible(false);

			}
			// If the game is ended/closed.
			else {
				playGameButton.setVisible(false);
				viewResultsButton.setVisible(true);
			}
		}
		
		updateBars();
		
		infoPanel.updateInfoSummary(game);
		reqPanel.updateReqSummary(game);

		reportMessage.setText("");
	}
	
	public Game getGameObject() {
		return game;
	}
	
	/**
	 * This function disables or enables buttons to match the boolean passed in
	 * @param check either true to enable or false to disable
	 */
	public void disableOrEnableButtons(boolean check){
		playGameButton.setEnabled(check);
		editGameButton.setEnabled(check);
	}

	/**
	 * This function sets an error text to red and outputs a string
	 * @param string Message to display
	 */
	public void reportError(String string) {
		reportMessage.setText(string);
		reportMessage.setForeground(Color.RED);
		overallProgressBar.setVisible(false);
	}
	
	/**
	 * This function sets success message to green and outputs a string
	 * @param string Success Message to display
	 */
	public void reportSuccess(String string) {
		reportMessage.setText(string);
		reportMessage.setForeground(Color.BLUE);
	}
	
	/**
	 * Update the progress bars
	 */
	public void updateBars(){
		overallProgressBar.setVisible(game.getStatus().equals(GameStatus.IN_PROGRESS));
		overallProgressBar.setMinimum(0);
		overallProgressBar.setMaximum(game.getMaxVotes());
		overallProgressBar.setValue(game.getVoteCount());
		overallProgressBar.setString("Overall Game Progress: " + 
				Double.parseDouble(new DecimalFormat("#.##").format(
						overallProgressBar.getPercentComplete() * 100)) + "%");
		
		userProgressBar.setVisible(game.getStatus().equals(GameStatus.IN_PROGRESS));
		userProgressBar.setMinimum(0);
		userProgressBar.setMaximum(game.getUserMaxVotes());
		userProgressBar.setValue(game.getUserVoteCount(
				ConfigManager.getInstance().getConfig().getUserName()));
		userProgressBar.setString("Your Progress: " + 
				Double.parseDouble(new DecimalFormat("#.##").format(
						userProgressBar.getPercentComplete() * 100)) + "%");
	}
	
	/**
	 * Updates the panel if there have been changes to the current game
	 * @param returnedGame
	 */
	public void updateIfChanged(Game returnedGame){
		final boolean updated = !game.isSameModifiedVersion(
				returnedGame.getModifiedVersion());
		final boolean statusChanged = !game.getStatus().equals(
				returnedGame.getStatus());
		if(game.identify(returnedGame) && updated){
			updateSummary(returnedGame);
			new GetGamesController().initializeTable();
			reportSuccess("<html> The game has been updated recently. </html>");
		}
		else if(game.identify(returnedGame) && statusChanged){
			updateSummary(returnedGame);
			new GetGamesController().initializeTable();
			switch(returnedGame.getStatus()){
				case CLOSED:
					reportSuccess("<html> The game has been closed.</html>");
					break;
				case DRAFT:
					reportSuccess("<html> The game has been created. </html>");
					break;
				case ENDED:
					reportSuccess("<html> The game has ended. </html>");
					break;
				case IN_PROGRESS:
					reportSuccess("<html> The game has been started. </html>");
					break;
				default:
					reportSuccess("<html> The games's status has changed. </html>");
					break;
			}
		}
		else if(game.identify(returnedGame) && game.isChanged(returnedGame, 
				ConfigManager.getInstance().getConfig().getUserName())){
			new GetGamesController().initializeTable();
			game = returnedGame;
			updateBars();
			reqPanel.updateReqSummary(game);
		}
	}
	
	/**
	 * Gets the selected requirements
	 * @return The list of selected requirement IDS
	 */
	public List<Integer> getSelectedRequirements(){
		return reqPanel.getSelectedRequirements();
	}
	
}
