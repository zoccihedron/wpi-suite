package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;

/**
 * This is a module for WPISuiteTNG that provides a support for planning poker.
 * Planning poker is a quick and simple way to estimate the difficulty of implementing a user story.
 */
public class PlanningPoker implements IJanewayModule {
	
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
		toolbarPanel.setBorder(BorderFactory.createLineBorder(Color.green,2));
		
		// Constructs and adds the MainPanel
		JPanel mainPanel = new JPanel();
		mainPanel.add(new JLabel("PlanningPoker placeholder"));
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.green,2));
		
		// Create a tab model that contains the toolbar panel and the main content panel
		JanewayTabModel tab1 = new JanewayTabModel(getName(), new ImageIcon(), toolbarPanel, mainPanel);
		
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
