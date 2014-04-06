package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.GameInfoPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.ListRequirementsPanel;


public class PlayGamePanel extends JPanel{

	GameInfoPanel GameInfoPanel;
	ListRequirementsPanel listRequirements;
	
	public PlayGamePanel(GameInfoPanel GameInfoPanel, ListRequirementsPanel listRequirements ) {
		this.GameInfoPanel = GameInfoPanel;
		this.listRequirements = listRequirements;
	}
	
	
	
	
	
}
