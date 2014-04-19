
/**
 * This class is used to create a results panel  which will be displayed in the view results panel
 * 
 * @author Codon Bleu
 * @version 1.0
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.results.ResultsDisplayController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

public class ResultsDisplayPanel extends JPanel{
	private JLabel mean;
	private JLabel median;
	private JTextField finalEstimate;
	private JButton saveFinalEstimateBtn;
	private Game game;
	private int reqid;
	private JLabel message;
	
	/**
	 * Initialize the labels for displaying information about the game
	 */
	public ResultsDisplayPanel(final Game game){
		this.game = game;
		this.mean = new JLabel();
		this.median = new JLabel();
		this.message = new JLabel();
		this.finalEstimate = new JTextField();
		this.saveFinalEstimateBtn = new JButton("Set the final estimate");
		this.saveFinalEstimateBtn.addActionListener(new ResultsDisplayController(this, game));
		this.saveFinalEstimateBtn.setEnabled(false);
		this.finalEstimate.setEditable(false);
		this.add(mean);
		this.add(median);
		this.add(finalEstimate);
		this.add(saveFinalEstimateBtn);
		this.add(message);
		
//		saveFinalEstimateBtn.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				// Save final estimate
//				int est = Integer.parseInt(finalEstimate.getText());
//				game.findEstimate(reqid).setFinalEstimate(est);
//				
//			}
//		});
		
	}

	/**
	 * Update the panel with information from the Estimate in a game that 
	 * corresponds to the passed requirement
	 * @param reqid is the id of the requirement in req manager
	 * @param game is the game the estimate is in
	 */
	public void updateData(int reqid) {
		this.reqid = reqid;
		Estimate estimate = game.findEstimate(reqid);
		
		if(estimate.estimationHasBeenSent() || 
				!ConfigManager.getInstance().getConfig().getUserName().equals(game.getGameCreator())){
			this.saveFinalEstimateBtn.setEnabled(false);
			this.finalEstimate.setEditable(false);
		} else{
			this.saveFinalEstimateBtn.setEnabled(true);
			this.finalEstimate.setEditable(true);
		}
		
		mean.setText("Mean: " + Double.toString(estimate.getMean()));
		median.setText("Median: " + Double.toString(estimate.getMedian()));
		finalEstimate.setText("" + estimate.getFinalEstimate());
		
		
	}

	/**
	 * Checks to make sure the estimate entered is valid (i.e. a positive integer)
	 * @return whether or not the estimate is valid
	 */
	public boolean checkField() {
		final int estimate;
		try{
			reportError("<html></html>");
			estimate = Integer.parseInt(finalEstimate.getText());

		} catch (NumberFormatException e){
			reportError("<html>Error: Final estimate must be an integer.</html>");
			return false;
		}

		if(estimate < 0) {
			reportError("<html>Error: Final estimate must be an integer greater than 0.</html>");
			return false;
		}
		
		if(estimate == 0){
			reportError("<html>Error: 0 is not a valid final estimate. </html>");
			return true;
		}		
		return true;
	}
	
	/**
	 * brings up an error message if the final estimate entered is not a positive integer
	 * @param string the error message to report
	 */
	public void reportError(String string) {
		message.setText(string);
		message.setForeground(Color.RED);
	}
	
	/**
	 * getter for the integer in the text box
	 * @return the integer entered in the text box
	 */
	public int getFinalEstimate(){
		return Integer.parseInt(finalEstimate.getText());
	}

	/**
	 * getter for the requirement id
	 * @return the requirement id
	 */
	public int getReqid() {
		return reqid;
	}

}
