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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;

@SuppressWarnings("serial")
public class OverviewPanel extends JPanel {
	private JTable table;
	private JButton refreshBtn;
	private final String[] colNames = {"ID", "Name", "Number of Requirements"};
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public OverviewPanel(PlanningPokerModel gamesModel) {
		
		setBorder(BorderFactory.createLineBorder(Color.magenta, 2));

		String[][] data = {};
		refreshBtn = new JButton("Refresh");
		
		table = new JTable(data, colNames);
		scrollPane = new JScrollPane(table);
		add(refreshBtn);
		add(scrollPane);
		
		refreshBtn.addActionListener(new GetGamesController(gamesModel, this));
	}
	
	public void updateTable(String[][] data)
	{
		remove(scrollPane);
		table = new JTable(data,colNames);
		scrollPane = new JScrollPane(table);
		add(scrollPane);
		this.revalidate();
	
	}
	
	

}
