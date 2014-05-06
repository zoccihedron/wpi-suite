/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.ManageDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.OverviewPanelController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;

/**
 * This is a module for WPISuiteTNG that provides a support for planning poker.
 * Planning poker is a quick and simple way to estimate the difficulty of
 * implementing a user story.
 * 
 * @author Team Code On Bleu
 * @version 0.1
 */
public class PlanningPoker implements IJanewayModule {

	/**
	 * List of tabs that will be available for this module
	 */
	private final List<JanewayTabModel> tabs;

	/**
	 * Constructs the main views for this module. Namely one tab.
	 */
	public PlanningPoker() {

		final MainView mainView = new MainView();

		// Initialize the list of tabs (however, this module has only one tab)
		tabs = new ArrayList<JanewayTabModel>();

		// Create a toolbar for the tab
		final DefaultToolbarView toolbarView = new ToolbarView();

		// Create a tab model that contains the toolbar panel and the main
		// content panel
		final JanewayTabModel tab1 = new JanewayTabModel(getName(),
				new ImageIcon(), toolbarView, mainView);

		// Add the tab to the list of tabs owned by this module
		tabs.add(tab1);
		
		// Instantiate the controllers for getting games, requirements, and decks from the database
		OverviewPanelController.getInstance();
		RequirementManagerFacade.getInstance();
		ManageDeckController.getInstance();

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
