package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;

import javax.swing.border.BevelBorder;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GamesToTable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.OverviewTable;

public class OverviewPanel extends JPanel {
	private OverviewTable table;
	private JButton refreshBtn;
	private final String[] colNames = {"ID", "Name", "Status", "Deadline", "Number of Requirements", "Owner"};
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public OverviewPanel(PlanningPokerModel gamesModel) {
		
		String[][] data = {};
		refreshBtn = new JButton("Refresh");
		
		table = new OverviewTable(data, colNames);
		scrollPane = new JScrollPane(table);
		add(refreshBtn);
		add(scrollPane);
		
		refreshBtn.addActionListener(new GetGamesController(gamesModel, this));
	}
	
	public void updateTable(String[][] data)
	{
		remove(scrollPane);
		table = new OverviewTable(data,colNames);
		scrollPane = new JScrollPane(table);
		add(scrollPane);
		this.revalidate();
	
	}
	
	public OverviewTable getTable()
	{
		return table;
	}
	
	

}
