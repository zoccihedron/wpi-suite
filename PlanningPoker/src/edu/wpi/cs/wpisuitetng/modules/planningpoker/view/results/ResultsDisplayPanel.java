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
import java.awt.Insets;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.results.ResultsDisplayController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;

/**
 * Panel for displaying detailed results, the right subpanel in ResultsPanel
 * 
 * @author Codon Bleu
 * @version 1.00
 */
@SuppressWarnings("serial")
public class ResultsDisplayPanel extends JPanel {
	private final JLabel mean;
	private final JLabel median;
	private final JLabel lblFinalEstimate;
	private final JTextField finalEstimate;
	private final JTextArea noteArea;
	private final JButton saveFinalEstimateBtn;
	private final Game game;
	private int reqid;
	private final JLabel message;
	private final JTable tableUsersAndEstimates;
	private DefaultTableModel modelUsersAndEstimates;
	private final EstimateTreePanel treePanel;
	private final String[] columnNames = { "User", "Estimate" };
	private final Object[][] data = new Object[][] {};
	private final JScrollPane scrollUsersAndEstimates;
	private final JScrollPane scrollNoteArea;

	/**
	 * Initialize the labels for displaying information about the game
	 *
	 * @param game The game to display the results of.
	 * @param treePanel the tree panel that has a list of estimates
	 */
	public ResultsDisplayPanel(final Game game, EstimateTreePanel treePanel) {
		this.treePanel = treePanel;
		this.game = game;

		mean = new JLabel();
		median = new JLabel();
		message = new JLabel("  ");
		lblFinalEstimate = new JLabel();
		finalEstimate = new JTextField();
		noteArea = new JTextArea();
		noteArea.setBorder(finalEstimate.getBorder());
		noteArea.setLineWrap(true);
		noteArea.setEnabled(false);

		saveFinalEstimateBtn = new JButton("Set the final estimate");
		saveFinalEstimateBtn.addActionListener(new ResultsDisplayController(
				this, game));
		ConfigManager.getInstance();
		saveFinalEstimateBtn.setVisible(ConfigManager.getConfig()
				.getUserName().equals(game.getGameCreator()));
		finalEstimate.setEditable(false);
		saveFinalEstimateBtn.setEnabled(false);
		saveFinalEstimateBtn.setToolTipText("Please select a requirement to finalize an estimate.");
		
		
		
		
		tableUsersAndEstimates = new JTable(new DefaultTableModel(data,
				columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		scrollUsersAndEstimates = new JScrollPane(tableUsersAndEstimates);
		scrollNoteArea = new JScrollPane(noteArea);

		populatePanel();
		if(game.getStatus().equals(GameStatus.CLOSED))
		{
			scrollNoteArea.setVisible(false);
			saveFinalEstimateBtn.setVisible(false);
		}

	}

	/**
	 * set up constraints on panel once a requirement has been selected
	 */
	private void populatePanel() {

		lblFinalEstimate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mean.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mean.setText("Mean: ");
		median.setText("Median: ");
		median.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFinalEstimate.setText("Final Estimate: ");

		mean.setBorder(new EmptyBorder(0, 0, 5, 0));
		median.setBorder(new EmptyBorder(5, 0, 5, 0));
		lblFinalEstimate.setBorder(new EmptyBorder(5, 0, 5, 0));

		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1.0;
		constraints.weightx = 0.5;
		add(scrollUsersAndEstimates, constraints);

		final JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridBagLayout());

		constraints.anchor = GridBagConstraints.NORTH;
		constraints.weighty = 0.0;
		constraints.weightx = 0.0;

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		rightPanel.add(mean, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		rightPanel.add(median, constraints);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		rightPanel.add(lblFinalEstimate, constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		finalEstimate.setSize(50, finalEstimate.getHeight());
		rightPanel.add(finalEstimate, constraints);

		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 5;
		rightPanel.add(saveFinalEstimateBtn, constraints);

		try {
			final Image img = ImageIO.read(getClass().getResource("pen.png"));
			saveFinalEstimateBtn.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 2;
		constraints.weightx = 0.75;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		rightPanel.add(message, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(5, 0, 5, 0);
		rightPanel.add(scrollNoteArea, constraints);
		

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(0, 20, 0, 0);
		add(rightPanel, constraints);

		finalEstimate.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(game.getStatus().equals(GameStatus.CLOSED))
				{
					saveFinalEstimateBtn.setVisible(false);
				}
				saveFinalEstimateBtn.setEnabled(canMakeEstimate()
						&& !game.getStatus().equals(GameStatus.CLOSED));
				finalEstimateBtnToolTip();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(game.getStatus().equals(GameStatus.CLOSED))
				{
					saveFinalEstimateBtn.setVisible(false);
				}
				saveFinalEstimateBtn.setEnabled(canMakeEstimate()
						&& !game.getStatus().equals(GameStatus.CLOSED));
				finalEstimateBtnToolTip();
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// Do nothing
			}
		});

		noteArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(game.getStatus().equals(GameStatus.CLOSED))
				{
					saveFinalEstimateBtn.setVisible(false);
				}
				saveFinalEstimateBtn.setEnabled(canMakeEstimate()
						&& !game.getStatus().equals(GameStatus.CLOSED));
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(game.getStatus().equals(GameStatus.CLOSED))
				{
					saveFinalEstimateBtn.setVisible(false);
				}
				if(canMakeEstimate() && !game.getStatus().equals(GameStatus.CLOSED)) {
					saveFinalEstimateBtn.setEnabled(true);
				}
				else {
					saveFinalEstimateBtn.setEnabled(false);
					saveFinalEstimateBtn.setToolTipText("The estimate has been set.");
				}
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// Do nothing
			}
		});
	}

	/**
	 * Update the panel with information from the Estimate in a game that
	 * corresponds to the passed requirement
	 * 
	 * @param reqid
	 *            is the id of the requirement in req manager
	 */
	@SuppressWarnings({ "rawtypes" })
	public void updateData(int reqid) {
		this.reqid = reqid;
		final Estimate estimate = game.findEstimate(reqid);
		ConfigManager.getInstance();
		if (!ConfigManager.getConfig().getUserName()
				.equals(game.getGameCreator())) {
			saveFinalEstimateBtn.setVisible(false);
			finalEstimate.setEditable(false);
		} else {
			saveFinalEstimateBtn.setVisible(!game.getStatus().equals(
					GameStatus.CLOSED));
			finalEstimate.setEditable(!game.getStatus().equals(
					GameStatus.CLOSED));
		}

		mean.setText("Mean: " + Double.toString(estimate.getMean()));
		median.setText("Median: " + Double.toString(estimate.getMedian()));
		noteArea.setText(estimate.getNote());
		noteArea.setVisible(true);
		noteArea.setEnabled(true);
		if (estimate.getFinalEstimate() == 0
				&& !game.getStatus().equals(GameStatus.CLOSED))
		{
			finalEstimate.setText("" + (int)estimate.getMean());
		} 
		else 
		{
			finalEstimate.setText("" + estimate.getFinalEstimate());
		}

		// Set to new empty model to empty table
		tableUsersAndEstimates
		.setModel(new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		modelUsersAndEstimates = (DefaultTableModel) tableUsersAndEstimates
				.getModel();
		final Map<String, Integer> mapUsersAndEstimates = game.findEstimate(reqid)
				.getUsersAndEstimates();
		final Iterator it = mapUsersAndEstimates.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			if (!pairs.getValue().equals(-1)) {
				modelUsersAndEstimates.addRow(new Object[] { pairs.getKey(),
						pairs.getValue() });
			}

		}

		ConfigManager.getInstance();
		if (ConfigManager.getConfig().getUserName()
						.equals(game.getGameCreator())
			&& !game.getStatus().equals(GameStatus.CLOSED))
		{
			saveFinalEstimateBtn.setVisible(true);
			finalEstimate.setEditable(true);
		}
		else 
		{
			saveFinalEstimateBtn.setVisible(false);
			finalEstimate.setEditable(false);
		}
		

	}

	/**
	 * Checks to make sure the estimate entered is valid (i.e. a positive
	 * integer)
	 * 
	 * @return whether or not the estimate is valid
	 */
	public boolean canMakeEstimate() {
		final int estimate;
		final Estimate estimateObject = game.findEstimate(reqid);
		boolean result = true;
		if(!game.getStatus().equals(GameStatus.CLOSED))
		{
			ConfigManager.getInstance();
			if(ConfigManager.getConfig().getUserName().equals(game.getGameCreator())){
				try {
					reportError("<html> </html>");
					estimate = Integer.parseInt(finalEstimate.getText());
					
					if (estimate == estimateObject.getFinalEstimate() 
							&& estimateObject.isFinalEstimateSet()){
						reportError("");
						result &= false;
					}

					if (result && estimate <= 0) {
						reportError(
								"<html>Final estimate must be an integer greater than 0.</html>");
						result &= false;
					}

					if (result && estimateObject.isSentBefore()){
						if(noteArea.getText().trim().isEmpty() 
								&& estimate != estimateObject.getFinalEstimate()){
							reportError("<html>A note must be included when modifying"
									+ " a sent final estimate.</html>");
							result &= false;
						}
					}

				} catch (NumberFormatException e) {
					reportError("<html>Final estimate must be a positive integer.</html>");
					result = false;
					System.err.println(e.getMessage());
				}
			}
		}

		return result;
	}

	/**
	 * brings up an error message if the final estimate entered is not a
	 * positive integer
	 * 
	 * @param string
	 *            the error message to report
	 */
	public void reportError(String string) {
		message.setText(string);
		message.setForeground(Color.RED);
	}

	/**
	 * getter for the integer in the text box
	 * 
	 * @return the integer entered in the text box
	 */
	public int getFinalEstimate() {
		return Integer.parseInt(finalEstimate.getText());
	}

	/**
	 * getter for the requirement id
	 * 
	 * @return the requirement id
	 */
	public int getReqid() {
		return reqid;
	}

	/**
	 * Refreshes the GUI portion of the tree panel and the data 
	 * on the results panel.
	 */
	public void refresh() {
		treePanel.refresh();
		updateData(reqid);
	}

	/**
	 * Returns the string of the note for the final estimate
	 *
	 * @return the note for the estimate
	 */
	public String getNote(){
		return noteArea.getText();
	}

	/**
	 * Sets the note for an final estimation change
	 *
	 * @param note the note for an estimation change
	 */
	public void setNote(String note){
		noteArea.setText(note);
	}
	
	/**
	 * Sets the tool tip on the final estimate button
	 * based on whether or not the button is enabled.
	 */
	public void finalEstimateBtnToolTip() {
		if(saveFinalEstimateBtn.isEnabled()) {
			saveFinalEstimateBtn.setToolTipText("Click here to change the estimate.");
		}
		else {
			saveFinalEstimateBtn.setToolTipText("Please enter an integer greater than 0.");
		}
	}
}
