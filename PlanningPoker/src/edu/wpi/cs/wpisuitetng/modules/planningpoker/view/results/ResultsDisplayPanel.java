
/**
 * This class is used to create a results panel  which will be displayed in the view results panel
 * 
 * @author Codon Bleu
 * @version 1.0
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

public class ResultsDisplayPanel extends JPanel{
	private JLabel mean;
	private JLabel median;
	private JTextField finalEstimate;
	private JButton saveFinalEstimateBtn;
	private Game game;
	
	/**
	 * Initialize the labels for displaying information about the game
	 */
	public ResultsDisplayPanel(){
		this.mean = new JLabel();
		this.median = new JLabel();
		this.finalEstimate = new JTextField();
		this.saveFinalEstimateBtn = new JButton();
		this.game = null;
		//TODO !!! add action listener
		this.saveFinalEstimateBtn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.setFinalEstimate(Integer.parseInt(finalEstimate.getText()));
			}
 		});
		
		this.saveFinalEstimateBtn.setEnabled(false);
		this.add(mean);
		this.add(median);
		this.add(finalEstimate);
		this.add(saveFinalEstimateBtn);
	}

	/**
	 * Update the panel with information from the Estimate in a game that 
	 * corresponds to the passed requirement
	 * @param reqid is the id of the requirement in req manager
	 * @param game is the game the estimate is in
	 */
	public void updateData(int reqid, Game game) {
		this.game = game;
		Estimate estimate = game.findEstimate(reqid);
		
		if(estimate.isSent()){
			this.saveFinalEstimateBtn.setEnabled(false);
		} else{
			this.saveFinalEstimateBtn.setEnabled(true);
		}
		
		mean.setText("Mean: " + Double.toString(estimate.getMean()));
		median.setText("Median: " + Double.toString(estimate.getMedian()));
		finalEstimate.setText("" + estimate.getFinalEstimate());
	}

}
