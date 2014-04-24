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
public class ResultsDisplayPanel extends JPanel {
	private final JLabel mean;
	private final JLabel median;
	private final JLabel lblFinalEstimate;
	private final JTextField finalEstimate;
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
		message = new JLabel();
		lblFinalEstimate = new JLabel();
		finalEstimate = new JTextField();
		saveFinalEstimateBtn = new JButton("Set the final estimate");
		saveFinalEstimateBtn.addActionListener(new ResultsDisplayController(
				this, game));
		if(game.getStatus().equals(GameStatus.CLOSED))
		{
			saveFinalEstimateBtn.setEnabled(false);
			finalEstimate.setEditable(false);
		}
		else
		{
			saveFinalEstimateBtn.setEnabled(true);
			finalEstimate.setEditable(true);
		}

		tableUsersAndEstimates = new JTable(new DefaultTableModel(data,
				columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		scrollUsersAndEstimates = new JScrollPane(tableUsersAndEstimates);

		populatePanel();

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
		constraints.gridy = 3;
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

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0.25;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 10, 0, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		add(rightPanel, constraints);
		
		finalEstimate.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				saveFinalEstimateBtn.setEnabled(canMakeEstimate()
						&& !game.getStatus().equals(GameStatus.CLOSED));
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				saveFinalEstimateBtn.setEnabled(canMakeEstimate()
						&& !game.getStatus().equals(GameStatus.CLOSED));
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
	public void updateData(int reqid) {
		this.reqid = reqid;
		final Estimate estimate = game.findEstimate(reqid);

		mean.setText("Mean: " + Double.toString(estimate.getMean()));
		median.setText("Median: " + Double.toString(estimate.getMedian()));
		if (estimate.getFinalEstimate() == 0)
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
		
		/*
		 * Caution: large if-statement
		 * for it to pass
		 * 1) user must be game creator
		 * 2) the estimate has not yet been recorded in the requirement manager
		 * 3) the game is not closed (archived)
		 * 4) the estimate is a valid one
		 * then and only then can the final estimate field be edited and
		 * submitted
		 */
		ConfigManager.getInstance();
		if (ConfigManager.getConfig().getUserName()
						.equals(game.getGameCreator())
			&& !estimate.estimationHasBeenSent()
			&& !game.getStatus().equals(GameStatus.CLOSED)
			&& canMakeEstimate())
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
		try {
			reportError("<html></html>");
			estimate = Integer.parseInt(finalEstimate.getText());

		} catch (NumberFormatException e) {
			reportError("<html>* Final estimate must be an integer.</html>");
			return false;
		}

		if (estimate <= 0) {
			reportError("<html>* Final estimate must be an integer greater than 0.</html>");
			return false;
		}
		return true;
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
	 * Refreshs the GUI portion of the tree panel
	 */
	public void refresh() {
		treePanel.refresh();
	}

}
