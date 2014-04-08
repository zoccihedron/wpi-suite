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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

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
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CloseNewGameTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;




/**
 * This class is a JPanel. It contains all the fields needed to 
 * create a new game
 * @author Code On Bleu
 * @version 1.0
 */
@SuppressWarnings({"serial"})
public class NewGamePanel extends JSplitPane {
	private final CreateGameInfoPanel createGameInfoPanel;
	private final JTabbedPane tabPane;
	private final SelectRequirementsPanel selectRequirementsPanel;
	private final JButton btnSave;
	private final JButton btnCancel;
	private JButton btnStart;
	private final JLabel lblMessage;
	
	/**
	 * Use this constructor when starting a new game panel from scratch
	 */
	public NewGamePanel() {
		super(JSplitPane.VERTICAL_SPLIT);
		createGameInfoPanel = new CreateGameInfoPanel(this);
		createGameInfoPanel.setMinimumSize(new Dimension(50, 300));
		selectRequirementsPanel = new SelectRequirementsPanel();
		
		tabPane = new JTabbedPane();
		tabPane.addTab("Requirements", selectRequirementsPanel);
		
		final JSplitPane topPanel = new JSplitPane();
		topPanel.setLeftComponent(createGameInfoPanel);
		topPanel.setRightComponent(tabPane);
		topPanel.setDividerLocation(300);
		this.setTopComponent(topPanel);
		final JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		
		
		btnSave = new JButton("Save");
		btnSave.setBounds(141, 5, 118, 25);
		bottomPanel.add(btnSave);
		
		// Maps Create Game button to AddGameController class
		btnSave.addActionListener(new AddGameController(createGameInfoPanel));
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(269, 5, 118, 25);
		btnCancel.addActionListener(new CloseNewGameTabController(createGameInfoPanel));
		bottomPanel.add(btnCancel); 
		final JButton button = new JButton("Start");
		button.setBounds(12, 5, 118, 25);
		
		lblMessage = new JLabel("*Error");
		lblMessage.setBounds(395, 8, 457, 18);
		lblMessage.setForeground(Color.RED);
		lblMessage.setVisible(false);
		lblMessage.setFont(new Font("Dialog", Font.ITALIC, 12));
		bottomPanel.add(lblMessage);		
		
		bottomPanel.add(button);
		
		try {
		    Image img = ImageIO.read(getClass().getResource("save-icon.png"));
		    btnSave.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("undo-icon.png"));
		    btnCancel.setIcon(new ImageIcon(img));
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
	 * Use this constructor when you want to edit an existing game
	 * @param editingGame the game to be updated
	 */
	public NewGamePanel(Game editingGame) {
		super(JSplitPane.VERTICAL_SPLIT);
		createGameInfoPanel = new CreateGameInfoPanel(this, editingGame);
		createGameInfoPanel.setMinimumSize(new Dimension(50, 300));
		selectRequirementsPanel = new SelectRequirementsPanel(editingGame);
		
		tabPane = new JTabbedPane();
		tabPane.addTab("Requirements", selectRequirementsPanel);
		
		final JSplitPane topPanel = new JSplitPane();
		topPanel.setLeftComponent(createGameInfoPanel);
		topPanel.setRightComponent(tabPane);
		topPanel.setDividerLocation(300);
		topPanel.setEnabled(false);
		this.setTopComponent(topPanel);
		final JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		
		
		btnSave = new JButton("Update");
		btnSave.setBounds(141, 5, 118, 25);
		bottomPanel.add(btnSave);
		
		// Maps Create Game button to AddGameController class
		btnSave.addActionListener(new UpdateGameController(createGameInfoPanel, editingGame));
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(269, 5, 118, 25);
		btnCancel.addActionListener(new CloseNewGameTabController(createGameInfoPanel));
		bottomPanel.add(btnCancel); 
		final JButton button = new JButton("Start");
		button.setBounds(12, 5, 118, 25);
		
		lblMessage = new JLabel("*Error");
		lblMessage.setBounds(395, 8, 457, 18);
		lblMessage.setForeground(Color.RED);
		lblMessage.setVisible(false);
		lblMessage.setFont(new Font("Dialog", Font.ITALIC, 12));
		bottomPanel.add(lblMessage);		
		
		bottomPanel.add(button);
		
		try {
		    Image img = ImageIO.read(getClass().getResource("save-icon.png"));
		    btnSave.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("undo-icon.png"));
		    btnCancel.setIcon(new ImageIcon(img));
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
	
}

