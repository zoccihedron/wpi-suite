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
import java.awt.Panel;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CloseNewGameTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;




/**
 * This class is a JPanel. It contains all the fields needed to 
 * create a new game
 * @author Code On Bleu
 */
@SuppressWarnings({"serial"})
public class NewGamePanel extends JSplitPane {
	public CreateGameInfoPanel createGameInfoPanel;
	public JTabbedPane tabPane;
	private SelectRequirementsPanel selectRequirementsPanel;
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnStart;
	private JLabel lblMessage;
	


	public NewGamePanel(PlanningPokerModel model, MainView parentWindow) {
		super(JSplitPane.VERTICAL_SPLIT);
		createGameInfoPanel = new CreateGameInfoPanel(this);

		createGameInfoPanel.setMinimumSize(new Dimension(50, 300));
		selectRequirementsPanel = new SelectRequirementsPanel();
		
		tabPane = new JTabbedPane();
		tabPane.addTab("Requirements", selectRequirementsPanel);

		this.setLeftComponent(createGameInfoPanel);
		this.setRightComponent(tabPane);
		this.setDividerLocation(300);
	}
	
	public NewGamePanel() {
		super(JSplitPane.VERTICAL_SPLIT);
		createGameInfoPanel = new CreateGameInfoPanel(this);
		createGameInfoPanel.setMinimumSize(new Dimension(50, 300));
		selectRequirementsPanel = new SelectRequirementsPanel();
		
		tabPane = new JTabbedPane();
		tabPane.addTab("Requirements", selectRequirementsPanel);
		
		JSplitPane topPanel = new JSplitPane();
		topPanel.setLeftComponent(createGameInfoPanel);
		topPanel.setRightComponent(tabPane);
		topPanel.setDividerLocation(300);
		this.setTopComponent(topPanel);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		
		
		btnSave = new JButton("Save");
		btnSave.setBounds(94, 5, 68, 25);
		bottomPanel.add(btnSave);
		
		// Maps Create Game button to AddGameController class
		btnSave.addActionListener(new AddGameController(createGameInfoPanel));
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(174, 5, 81, 25);
		btnCancel.addActionListener(new CloseNewGameTabController(createGameInfoPanel));
		bottomPanel.add(btnCancel); 
		JButton button = new JButton("Start");
		button.setBounds(12, 5, 70, 25);
		
		lblMessage = new JLabel("*Error");
		lblMessage.setBounds(260, 8, 457, 18);
		//lblMessage.setBounds(26, 274, 266, 52);
		lblMessage.setForeground(Color.RED);
		lblMessage.setVisible(false);
		lblMessage.setFont(new Font("Dialog", Font.ITALIC, 12));
		bottomPanel.add(lblMessage);		
		
		bottomPanel.add(button);
		
		
		
		this.setBottomComponent(bottomPanel); 
		this.setDividerSize(0);
		this.setEnabled(false);
		resetDividerLocation();
		this.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e){
				resetDividerLocation();
			}
			public void componentHidden(ComponentEvent e){
				resetDividerLocation();
			}
			public void componentShown(ComponentEvent e){
				resetDividerLocation();
			}
			public void componentMoved(ComponentEvent e){
				resetDividerLocation();
			}
		});
	}
	
	public NewGamePanel(Game editingGame) {
		super(JSplitPane.VERTICAL_SPLIT);
		createGameInfoPanel = new CreateGameInfoPanel(this, editingGame);
		createGameInfoPanel.setMinimumSize(new Dimension(50, 300));
		selectRequirementsPanel = new SelectRequirementsPanel();
		
		tabPane = new JTabbedPane();
		tabPane.addTab("Requirements", selectRequirementsPanel);
		
		JSplitPane topPanel = new JSplitPane();
		topPanel.setLeftComponent(createGameInfoPanel);
		topPanel.setRightComponent(tabPane);
		topPanel.setDividerLocation(300);
		topPanel.setEnabled(false);
		this.setTopComponent(topPanel);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		
		
		btnSave = new JButton("Save");
		btnSave.setBounds(94, 5, 68, 25);
		bottomPanel.add(btnSave);
		
		// Maps Create Game button to AddGameController class
		btnSave.addActionListener(new AddGameController(createGameInfoPanel));
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(174, 5, 81, 25);
		btnCancel.addActionListener(new CloseNewGameTabController(createGameInfoPanel));
		bottomPanel.add(btnCancel); 
		JButton button = new JButton("Start");
		button.setBounds(12, 5, 70, 25);
		
		lblMessage = new JLabel("*Error");
		lblMessage.setBounds(260, 8, 457, 18);
		//lblMessage.setBounds(26, 274, 266, 52);
		lblMessage.setForeground(Color.RED);
		lblMessage.setVisible(false);
		lblMessage.setFont(new Font("Dialog", Font.ITALIC, 12));
		bottomPanel.add(lblMessage);		
		
		bottomPanel.add(button);
		
		
		
		this.setBottomComponent(bottomPanel); 
		this.setDividerSize(0);
		this.setEnabled(false);
		resetDividerLocation();
		this.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e){
				resetDividerLocation();
			}
			public void componentHidden(ComponentEvent e){
				resetDividerLocation();
			}
			public void componentShown(ComponentEvent e){
				resetDividerLocation();
			}
			public void componentMoved(ComponentEvent e){
				resetDividerLocation();
			}
		});
	}

	
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
	
	public void setErrorMessageVisible(boolean bool){
		lblMessage.setVisible(bool);
	}

	public ArrayList<Integer> getGameRequirements() {
		return selectRequirementsPanel.getSelectedRequirementIds();
	}
	
	public JLabel getMessageField(){
		return lblMessage;
	}
	
}

