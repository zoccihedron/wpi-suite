/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Team Codon Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame.CloseNewGameTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame.UpdateGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;




/**
 * This class is a JPanel. It contains all the fields needed to 
 * create a new game
 * @author Code On Bleu
 * @version 1.0
 */
@SuppressWarnings({"serial"})
public class NewGamePanel extends JSplitPane {
	private final CreateGameInfoPanel createGameInfoPanel;
	private final SelectRequirementsPanel selectRequirementsPanel;
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnStart;
	private final JLabel lblMessage;
	private final boolean changesSaved = false;
	private final boolean isInProgress;
	private Game game;
	/**
	 * Use this constructor when starting a new game panel from scratch
	 */
	public NewGamePanel() {
		super(JSplitPane.VERTICAL_SPLIT);
		game=null;
		lblMessage = new JLabel("*Error");
		isInProgress = false;
		
		btnSave = new JButton("Save");
		btnStart = new JButton("Start");
		btnCancel = new JButton("Cancel");
		btnCancel.setToolTipText("This will close the current tab.");
		
		selectRequirementsPanel = new SelectRequirementsPanel();
		createGameInfoPanel = new CreateGameInfoPanel(this);
		createGameInfoPanel.setMinimumSize(new Dimension(250, 300));
		
		populatePanel();
		
		// Maps Create Game button to AddGameController class
		btnSave.addActionListener(new AddGameController(createGameInfoPanel, false, false));
		btnStart.addActionListener(new AddGameController(createGameInfoPanel, true, false));
		
	}
	
	/**
	 * Use this constructor when you want to edit an existing game
	 * @param editingGame the game to be updated
	 * @param isInProgress whether the game is in progress or not
	 */
	public NewGamePanel(Game editingGame, boolean isInProgress) {
		super(JSplitPane.VERTICAL_SPLIT);
		
		lblMessage = new JLabel("*Error");
		this.isInProgress = isInProgress;
		game = editingGame;

		btnSave = new JButton("Save");
		btnStart = new JButton("Start");
		btnCancel = new JButton("Cancel");
		btnCancel.setToolTipText("This will close the current tab.");
		
		selectRequirementsPanel = new SelectRequirementsPanel(editingGame);
		createGameInfoPanel = new CreateGameInfoPanel(this, editingGame);
		createGameInfoPanel.setMinimumSize(new Dimension(50, 300));
		
		
		populatePanel();
		
		// Maps Create Game button to UpdateGameController class

		if(isInProgress){
			btnSave.addActionListener(new UpdateGameController(createGameInfoPanel, editingGame, true));
		}
		else{
			btnSave.addActionListener(new UpdateGameController(createGameInfoPanel, editingGame, false));
		}
		
		btnStart.addActionListener(
				new UpdateGameController(createGameInfoPanel, editingGame, true));
	}
	
	/**
	 * Sets up constraints on panel that are shared for each constructor
	 */
	private void populatePanel(){

		// Add some lovely padding to the requirements tables and labels 
		selectRequirementsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		final JSplitPane topPanel = new JSplitPane();
		topPanel.setLeftComponent(createGameInfoPanel);
		topPanel.setRightComponent(selectRequirementsPanel);
		topPanel.setDividerLocation(300);
		this.setTopComponent(topPanel);
		final JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		
		
		btnSave.setBounds(141, 5, 118, 25);
		btnSave.setEnabled(false);
		bottomPanel.add(btnSave);
				
		btnCancel.setBounds(269, 5, 118, 25);
		btnCancel.addActionListener(new CloseNewGameTabController(createGameInfoPanel));
		bottomPanel.add(btnCancel);
		
		btnStart.setBounds(12, 5, 118, 25);
		btnStart.setEnabled(false);
		bottomPanel.add(btnStart);
		
		lblMessage.setBounds(395, 8, 457, 18);
		lblMessage.setForeground(Color.RED);
		lblMessage.setVisible(false);
		lblMessage.setFont(new Font("Dialog", Font.ITALIC, 12));
		bottomPanel.add(lblMessage);
		
		
		try {
		    Image img = ImageIO.read(getClass().getResource("start-icon.png"));
		    btnStart.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("save-icon.png"));
		    btnSave.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("undo-icon.png"));
		    btnCancel.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("start-icon.png"));
		    btnStart.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		
		this.setBottomComponent(bottomPanel); 
		this.setDividerSize(0);
		this.setEnabled(false);
		resetDividerLocation();
		this.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e){
				resetDividerLocation();
				selectRequirementsPanel.fillTable();
			}
			public void componentHidden(ComponentEvent e){
				resetDividerLocation();
				selectRequirementsPanel.fillTable();
			}
			public void componentShown(ComponentEvent e){
				resetDividerLocation();
				selectRequirementsPanel.fillTable();
			}
			public void componentMoved(ComponentEvent e){
				resetDividerLocation();
				selectRequirementsPanel.fillTable();
			}
		});
	}
	

	/**
	 * resets the location of the divider
	 */
	public void resetDividerLocation(){
		setDividerLocation(this.getHeight() - 35);
	}
	
	/**
	 * Will disable or enable the save and start buttons
	 * @param check
	 */
	public void disableOrEnableButtons(boolean check){
		if(isInProgress){
			btnStart.setEnabled(false);
			btnStart.setToolTipText("The game is already in progress.");
			btnSave.setEnabled(check);
		}
		else{
			btnStart.setEnabled(check);
			btnSave.setEnabled(check);
		}
	}
	
	/**
	 * Changes the tooltips on buttons.
	 * @param startToolTip tool tip for the start button
	 * @param saveToolTip tool tip for the save button
	 */
	public void toolTipChanger(String startToolTip, String saveToolTip) {
		btnStart.setToolTipText(startToolTip);
		btnSave.setToolTipText(saveToolTip);
	}
	
	/**Fills the text box with a red warning based on the error Message
	 * 
	 * @param error the message to be printed, should be in <html>text</html> format
	 */
	public void reportError(String error) {
		lblMessage.setForeground(Color.RED);
		lblMessage.setText(error);
		lblMessage.setVisible(true);
	}
	
	/**Fills the text box with a green message based on the input
	 * 
	 * @param message the message to be printed, should be in <html>text</html> format
	 */
	public void reportMessage(String message) {
		lblMessage.setForeground(Color.BLUE);
		lblMessage.setText(message);
		lblMessage.setVisible(true);
	}
	
	/**
	 * Either enables or disables a red border around elements
	 * on the panel if there is problem with the requirements.
	 * @param check
	 */
	public void displayErrorBorders(boolean check) {
		selectRequirementsPanel.displayErrorBorders(check);
	}
	
	/**
	 * When tab is pressed in the game description, it switches 
	 * focus to the requirements table
	 */
	public void switchFocusToTable() {
		selectRequirementsPanel.switchFocusToTable();
	}

	
	/**
	 * Set if the error message is visible or not
	 * @param bool 
	 */
	public void setErrorMessageVisible(boolean bool){
		lblMessage.setVisible(bool);
	}

	public List<Integer> getGameRequirements() {
		return selectRequirementsPanel.getSelectedRequirementIds();
	}
	
	public JLabel getMessageField(){
		return lblMessage;
	}
	
	/**
	 * Runs a pop up window when closing a game
	 * @return boolean if it's ready to close
	 */
	public boolean isReadyToClose() {
		
		boolean result;
		if(createGameInfoPanel.isPageEdited()){
			final Object options[] = {
					"Yes", "No"
					};
			final int i = JOptionPane.showOptionDialog(this, 
					"Any unsaved changes will be lost, would you like to exit anyways?",
					"Exit?",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);
			result = (i == 0);
		} else {
			result = true;
		}
		if (isInProgress && result) {
			final Request request = Network.getInstance()
					.makeRequest("Advanced/planningpoker/game/endEdit",
							HttpMethod.POST);
			request.setBody(game.toJSON());
			request.send();
		}
		return result;
	}
	
	/**
	 *getter for game
	 * @return game with the tab
	 */
	public Game getGame(){
		return game;
	}
	
	/**
	 * Returns the SelectRequirementsPanel linked to the NewGamePanel
	 *
	 * @return the SelectRequirementsPanel
	 */
	public SelectRequirementsPanel getSelectRequirementsPanel(){
		return selectRequirementsPanel;
	}
}
