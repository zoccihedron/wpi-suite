package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;

import javax.swing.border.BevelBorder;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GamesToTable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.PlanningPokerModel;

public class OverviewPanel extends JPanel {
	private JTable table;
	private JButton refreshBtn;
	private final String[] colNames = {"ID", "Name", "Deadline"};
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public OverviewPanel(PlanningPokerModel gamesModel) {
		
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
