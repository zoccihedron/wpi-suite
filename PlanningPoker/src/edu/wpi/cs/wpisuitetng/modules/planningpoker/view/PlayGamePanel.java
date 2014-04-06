package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.GameInfoPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.ListRequirementsPanel;


public class PlayGamePanel extends JPanel{

	private GameInfoPanel gameInfoPanel;
	private ListRequirementsPanel listRequirementsPanel;
	// private EstimationPane estimationPane;
	private JSplitPane splitPane;

	public PlayGamePanel(Game game)
	{
		gameInfoPanel = new GameInfoPanel();
		gameInfoPanel.updatePanel(game);
		listRequirementsPanel = new ListRequirementsPanel(game);
		// estimationPane = new EstimationPane();
		splitPane = new JSplitPane();
		splitPane.setLeftComponent(listRequirementsPanel);
		// splitPane.setRightComponent(estimationPane);
		setLayout(new BorderLayout());
		add(gameInfoPanel, BorderLayout.NORTH);
		add(splitPane, BorderLayout.CENTER);
	}

	
	
	
	
}
