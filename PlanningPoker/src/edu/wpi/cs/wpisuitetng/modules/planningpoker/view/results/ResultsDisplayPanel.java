
/**
 * This class is used to create a results panel  which will be displayed in the view results panel
 * 
 * @author Codon Bleu
 * @version 1.0
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.results.ResultsDisplayController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;

public class ResultsDisplayPanel extends JPanel{
	private JLabel mean;
	private JLabel median;
	private JLabel lblFinalEstimate;
	private JTextField finalEstimate;
	private JButton saveFinalEstimateBtn;
	private Game game;
	private int reqid;
	private JLabel message;
	private JTable tableUsersAndEstimates;
	private DefaultTableModel modelUsersAndEstimates;
	private EstimateTreePanel treePanel;
	private String[] columnNames = { "User","Estimate"};
	private Object[][] data = new Object[][] {};
	private JScrollPane scrollUsersAndEstimates;
	
	
	/**
	 * Initialize the labels for displaying information about the game
	 */
	public ResultsDisplayPanel(final Game game, EstimateTreePanel treePanel){
		this.treePanel = treePanel;
		this.game = game;
		
		this.mean = new JLabel();
		
		this.median = new JLabel();
		
		this.message = new JLabel();
		
		this.lblFinalEstimate = new JLabel();
		
		this.finalEstimate = new JTextField();

		this.saveFinalEstimateBtn = new JButton("Set the final estimate");
		this.saveFinalEstimateBtn.addActionListener(new ResultsDisplayController(this, game));
		this.saveFinalEstimateBtn.setEnabled(false);
		
		this.finalEstimate.setEditable(false);
		
		this.tableUsersAndEstimates = new JTable(new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int row, int column){
				return false;
			}
		});
		
		scrollUsersAndEstimates = new JScrollPane(tableUsersAndEstimates);
		
		setUpPanel();
		
	}
	
	
	/**
	 * set up constraints on panel once a requirement has been selected
	 */
	private void setUpPanel(){

		lblFinalEstimate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mean.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mean.setText("Mean: ");
		median.setText("Median: ");
		median.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFinalEstimate.setText("Final Estimate: ");
		
		mean.setBorder(new EmptyBorder(0,0,5,0));
		median.setBorder(new EmptyBorder(5,0,5,0));
		lblFinalEstimate.setBorder(new EmptyBorder(5,0,5,0));
		
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1.0;
		constraints.weightx = 0.5;
		add(scrollUsersAndEstimates,constraints);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridBagLayout());
		
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.weighty = 0.0;
		constraints.weightx = 0.0;
		
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		rightPanel.add(mean,constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		rightPanel.add(median,constraints);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		rightPanel.add(lblFinalEstimate,constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		finalEstimate.setSize(50, finalEstimate.getHeight());
		rightPanel.add(finalEstimate,constraints);


		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 3;
		rightPanel.add(saveFinalEstimateBtn,constraints);
		
		try {
			Image img = ImageIO.read(getClass().getResource("pen.png"));
			saveFinalEstimateBtn.setIcon(new ImageIcon(img));
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 2;
		rightPanel.add(message,constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		add(rightPanel,constraints);
	
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
			this.saveFinalEstimateBtn.setEnabled(true && !game.getStatus().equals(GameStatus.CLOSED));
			this.finalEstimate.setEditable(true);
		}
		
		mean.setText("Mean: " + Double.toString(estimate.getMean()));
		median.setText("Median: " + Double.toString(estimate.getMedian()));
		finalEstimate.setText("" + estimate.getFinalEstimate());
		
		// Set to new empty model to empty table
		tableUsersAndEstimates.setModel(new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int row, int column){
				return false;
			}
		});		
		modelUsersAndEstimates = (DefaultTableModel) tableUsersAndEstimates.getModel();
		HashMap<String,Integer> mapUsersAndEstimates = game.findEstimate(reqid).getUsersAndEstimates();
		Iterator it = mapUsersAndEstimates.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        if(!pairs.getValue().equals(-1)){
	        	modelUsersAndEstimates.addRow(new Object[] {pairs.getKey(),pairs.getValue()});
	        }
	       
	    }
				
			
		
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
	
	public void refresh(){
		treePanel.refresh();
	}

}
