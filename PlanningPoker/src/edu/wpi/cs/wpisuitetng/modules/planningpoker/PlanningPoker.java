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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;

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
		PlanningPokerModel games = new PlanningPokerModel();
		
		MainView mainView = new MainView(games);
		
				
		// Create a tab model that contains the toolbar panel and the main content panel
		final JanewayTabModel tab1 = new JanewayTabModel(
				getName(), 
				new ImageIcon(), 
				toolbarPanel,
				mainView);
			
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
