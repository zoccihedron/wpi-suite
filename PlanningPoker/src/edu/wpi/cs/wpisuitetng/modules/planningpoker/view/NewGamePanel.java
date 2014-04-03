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

import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CloseNewGameTabController;
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
	
	public NewGamePanel(PlanningPokerModel model, MainView ParentWindow) {
		super(JSplitPane.VERTICAL_SPLIT);

		createGameInfoPanel = new CreateGameInfoPanel(model, ParentWindow, this);
		createGameInfoPanel.setMinimumSize(new Dimension(50, 300));
		selectRequirementsPanel = new SelectRequirementsPanel();
		
		tabPane = new JTabbedPane();
		tabPane.addTab("Requirements", selectRequirementsPanel);
		
		//tabPane.addTab("Create new requirement", new Panel());
		//tabPane.addTab("Create new deck", new Panel());
		
		JSplitPane topPanel = new JSplitPane();
		topPanel.setLeftComponent(createGameInfoPanel);
		topPanel.setRightComponent(tabPane);
		topPanel.setDividerLocation(300);
		this.setTopComponent(topPanel);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		
		
		btnSave = new JButton("Save");
		btnSave.setBounds(12, 5, 68, 25);
		bottomPanel.add(btnSave);
		
		// Maps Create Game button to AddGameController class
		btnSave.addActionListener(new AddGameController(model, createGameInfoPanel));
		
		btnCancel = new JButton("Cancel");
		btnCancel.setLocation(91, 5);
		btnCancel.setSize(81, 25);
		btnCancel.addActionListener(new CloseNewGameTabController(createGameInfoPanel));
		bottomPanel.add(btnCancel); 
		JButton button = new JButton("Start");
		button.setBounds(184, 5, 70, 25);
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
	
	
	public ArrayList<Integer> getGameRequirements() {
		return selectRequirementsPanel.getSelectedRequirementIds();
	}
}
