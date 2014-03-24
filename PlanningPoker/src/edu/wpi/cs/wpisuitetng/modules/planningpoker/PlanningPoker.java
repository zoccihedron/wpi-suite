/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import java.awt.BorderLayout;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;

//import org.jdesktop.swingx.JXDatePicker;

/**
 * This is a module for WPISuiteTNG that provides a support for planning poker.
 * Planning poker is a quick and simple way to estimate the difficulty of implementing a user story.
 * @author Team Codon Bleu
 * @version 0.1
 */
public class PlanningPoker implements IJanewayModule {
	
	/**
	 * List of tabs that will be available for this module
	 */
	private List<JanewayTabModel> tabs;
	
	/**
	 * Constructs the main views for this module. Namely one tab.
	 */
	public PlanningPoker() {

		// Initialize the list of tabs (however, this module has only one tab)
		tabs = new ArrayList<JanewayTabModel>();
		
		// Create a JPanel to hold the toolbar for the tab
		JPanel toolbarPanel = new JPanel();
		toolbarPanel.add(new JLabel("PlanningPoker placeholder toolbar"));
		toolbarPanel.add(new JButton("Create Session"), BorderLayout.WEST);
		toolbarPanel.setBorder(BorderFactory.createLineBorder(Color.green, 2));
		
		// Constructs and adds the MainPanel
		JPanel mainPanel = new JPanel();
		
		//Creates the different features/fields for the mainPanel
		JTextField gameNameText = new JTextField("Name");
		JTextField usernameText = new JTextField("Username");
		JButton inviteUserButton = new JButton("Invite User");
		JRadioButton realTime = new  JRadioButton("Real-time");
		JRadioButton distributed = new  JRadioButton("Distributed");
		ButtonGroup gameTypeSelection = new ButtonGroup();
		JTextField DateText = new JTextField("MM/DD/YY");
		JButton createGameButton = new JButton("Create Game");
		
		//Customizes the mainPanel
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.green, 2));
		
		//Adds the fields and button to the main panel.
		mainPanel.add(gameNameText);
		mainPanel.add(usernameText);
		mainPanel.add(inviteUserButton);
		mainPanel.add(realTime);
		mainPanel.add(distributed);
		mainPanel.add(DateText);
		mainPanel.add(createGameButton);
		
		//Groups the JRadioButtons together so they act like actual radio buttons
		gameTypeSelection.add(realTime);
		gameTypeSelection.add(distributed);
		
		
		// Create a tab model that contains the toolbar panel and the main content panel
		final JanewayTabModel tab1 = new JanewayTabModel(
				getName(), 
				new ImageIcon(), 
				toolbarPanel, 
				mainPanel);
			
			// Add the tab to the list of tabs owned by this module
			tabs.add(tab1);
		}
		
		/*
		 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
		 */
		@Override
		public String getName() {
			return "PlanningPoker";
		}
	
		/*
		 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
		 */
		@Override
		public List<JanewayTabModel> getTabs() {
			return tabs;
	}

}
