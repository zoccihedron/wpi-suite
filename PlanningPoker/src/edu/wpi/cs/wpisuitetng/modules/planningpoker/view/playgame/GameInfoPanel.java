package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

public class GameInfoPanel extends JPanel {
	JLabel titleLabel;
	JLabel numDoneLabel;
	JLabel descriptionLabel;
	
	
	public GameInfoPanel() {
		setLayout(null);
		
		JLabel titleLabel = new JLabel("Game Title");
		titleLabel.setBounds(0, 0, 225, 140);
		add(titleLabel);
		
		JLabel numDoneLabel = new JLabel("Number Completed/Total Number");
		numDoneLabel.setBounds(0, 160, 225, 140);
		add(numDoneLabel);
		
		JLabel descriptionLabel = new JLabel("Description");
		descriptionLabel.setBounds(235, 11, 205, 278);
		add(descriptionLabel);
	}
	
	/**
	 * Update the panel with the information of the game passed
	 * @param game to get infromation from
	 */
	public void updatePanel(Game game)
	{
		titleLabel.setText(game.getName());
		descriptionLabel.setText(game.getDescription());
		numDoneLabel.setText(this.numDone(game));
	}

	private String numDone(Game game) {
		String temp;
		int count = 0;
		
		for(Estimate e: game.getEstimates())
		{
			if(e.hasMadeAnEstimation(ConfigManager.getInstance().getConfig().getUserName()))
				count++;
		}
		
		temp = count + "/" + game.getEstimates().size();
		
		return temp;
	}
}
