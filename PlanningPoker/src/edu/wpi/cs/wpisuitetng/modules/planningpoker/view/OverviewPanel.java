package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.border.BevelBorder;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.OverviewTable;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

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
	private JButton refreshBtn; // TODO: remove button 
	private final String[] colNames = {"ID", "Name", "Status", "Deadline", "Number of Requirements", "Owner"};
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
		refreshBtn = new JButton("Refresh");


		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0;

		table = new OverviewTable(data, colNames);
		scrollPane = new JScrollPane(table);

		// ID
		table.getColumnModel().getColumn(0).setMaxWidth(40);

		// Game Name
		table.getColumnModel().getColumn(1).setMinWidth(240);
		// Status
		table.getColumnModel().getColumn(2).setMinWidth(85);
		table.getColumnModel().getColumn(2).setMaxWidth(85);

		// Deadline
		table.getColumnModel().getColumn(3).setMinWidth(200);
		table.getColumnModel().getColumn(3).setMaxWidth(200);

		// Num of Requirements
		table.getColumnModel().getColumn(4).setMinWidth(40);
		table.getColumnModel().getColumn(4).setMaxWidth(120);

		// Game Creator
		table.getColumnModel().getColumn(5).setMaxWidth(85);
		table.getColumnModel().getColumn(5).setMaxWidth(200);

		scrollPane.setPreferredSize(new Dimension(1000,500));
		add(scrollPane, c);
		add(refreshBtn);

		refreshBtn.addActionListener(new GetGamesController(gamesModel, this, mainView));
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
	 * Sends an HTTP request to the server to pull games from the database
	 *
	 */
	public void getGamesFromServer(){
		final Request request = Network.getInstance().makeRequest("planningpoker/game", HttpMethod.GET); // PUT == create
		request.addObserver(new GetGamesRequestObserver(new GetGamesController(gamesModel, this, mainView))); // add an observer to process the response
		request.send();
	}
}

