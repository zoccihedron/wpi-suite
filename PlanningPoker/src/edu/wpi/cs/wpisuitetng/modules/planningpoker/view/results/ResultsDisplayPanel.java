
/**
 * This class is used to create a results panel  which will be displayed in the view results panel
 * 
 * @author Codon Bleu
 * @version 1.0
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

public class ResultsDisplayPanel extends JPanel{
	private JLabel mean;
	private JLabel median;
	
	/**
	 * Initialize the labels for displaying information about the game
	 */
	public ResultsDisplayPanel(){
		this.mean = new JLabel();
		this.median = new JLabel();
		this.add(mean);
		this.add(median);
	}

	/**
	 * Update the panel with information from the Estimate in a game that 
	 * corresponds to the passed requirement
	 * @param reqid is the id of the requirement in req manager
	 * @param game is the game the estimate is in
	 */
	public void updateData(int reqid, Game game) {
		mean.setText("");
		median.setText("");
		Estimate estimate = game.findEstimate(reqid);
		mean.setText("Mean: " + Double.toString(estimate.getMean()));
		median.setText("Median: " + Double.toString(estimate.getMedian()));
		
	}

}
