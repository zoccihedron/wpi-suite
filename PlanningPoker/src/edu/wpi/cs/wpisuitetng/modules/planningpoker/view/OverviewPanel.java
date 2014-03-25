package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;

import javax.swing.border.BevelBorder;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.controller.GamesToTable;

public class OverviewPanel extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public OverviewPanel() {
		
		String[] colNames = {"ID", "Name", "Deadline"};
		String[][] data = (new GamesToTable()).getTable();
		
		table = new JTable(data, colNames);
		add(new JScrollPane(table));

	}

}
