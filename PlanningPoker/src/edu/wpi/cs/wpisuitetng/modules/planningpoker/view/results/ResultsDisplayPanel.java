package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

public class ResultsDisplayPanel extends JPanel{
	private JLabel mean;
	private JLabel median;
	public ResultsDisplayPanel(){
		this.mean = new JLabel();
		this.median = new JLabel();
		this.add(mean);
		this.add(median);
	}

	/**
	 * @param reqid is the 
	 * @param game
	 */
	public void updateData(int reqid, Game game) {
		Estimate estimate = game.findEstimate(reqid);
		mean.setText("Mean: " + Double.toString(estimate.getMean()));
		median.setText("Median: " + Double.toString(estimate.getMedian()));
	}

}
