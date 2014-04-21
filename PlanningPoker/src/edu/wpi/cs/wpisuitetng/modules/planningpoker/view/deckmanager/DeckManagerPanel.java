package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.ManageDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame.PlayGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.GameInfoPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.ListRequirementsPanel;

public class DeckManagerPanel extends JPanel{
	private final JSplitPane splitPane;
	
	DeckManagerPanel(){
		setLayout(new BorderLayout());
		DeckDataPanel deckData = new DeckDataPanel();
		DeckManager deckManager = new DeckManager();
		//TODO Needs arguements
		ManageDeckController controller = new ManageDeckController();
		splitPane = new JSplitPane();
		splitPane.setLeftComponent(deckData);
		splitPane.setRightComponent(deckManager);
		add(splitPane, BorderLayout.CENTER);
	}
}

