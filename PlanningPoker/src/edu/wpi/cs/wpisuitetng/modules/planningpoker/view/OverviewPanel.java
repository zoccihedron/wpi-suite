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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.OverviewTable;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

@SuppressWarnings("serial")
/**
 * Creates the overview panel, which includes
 * a button for refreshing and the table that
 * displays the game data.
 *
 * @author Robert, Ian, Adam
 * @version Mar 31, 2014
 */
public class OverviewPanel extends JPanel {
	private OverviewTable table;
	private final String[] colNames = {"Name", "Status", "Deadline", "Number of Requirements", "Owner"};
	private JScrollPane scrollPane;
	private PlanningPokerModel gamesModel;
	private MainView mainView;

	/**
	 * Create the panel.
	 */
	public OverviewPanel(PlanningPokerModel gamesModel, MainView mainView) {
		
		this.mainView = mainView;
		this.gamesModel = gamesModel;
		
		String[][] data = {};
		setLayout(new BorderLayout());
	

		table = new OverviewTable(data, colNames);
		scrollPane = new JScrollPane(table);
		
		
		// Game Name
		table.getColumnModel().getColumn(0).setMinWidth(240);
		// Status
		table.getColumnModel().getColumn(1).setMinWidth(85);
		table.getColumnModel().getColumn(1).setMaxWidth(85);
		
		// Deadline
		table.getColumnModel().getColumn(2).setMinWidth(200);
		table.getColumnModel().getColumn(2).setMaxWidth(200);

		// Num of Requirements
		table.getColumnModel().getColumn(3).setMinWidth(40);
		table.getColumnModel().getColumn(3).setMaxWidth(120);
		
		// Game Creator
		table.getColumnModel().getColumn(4).setMinWidth(85);
		table.getColumnModel().getColumn(4).setMaxWidth(200);

		add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * Updates the table and revalidates to print it to the table
	 *
	 */
	public void updateTable()
	{
		table.refresh();
		table.revalidate();
	
	}
	
	/**
	 * Sends an HTTP request to the server to
	 * pull the Game data from the database
	 *
	 */
	public void getGamesFromServer(){
		final Request request = Network.getInstance().makeRequest("planningpoker/game", HttpMethod.GET); // PUT == create
		request.addObserver(new GetGamesRequestObserver(new GetGamesController(gamesModel, this, mainView))); // add an observer to process the response
		request.send();
	}
	

}
