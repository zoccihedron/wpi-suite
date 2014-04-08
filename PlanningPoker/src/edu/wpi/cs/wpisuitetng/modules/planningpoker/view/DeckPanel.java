package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

public class DeckPanel extends JPanel{
	private JTextField estimateField;
	
	public DeckPanel() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		//create JLabel and JTextField within scrollPane:
		JLabel estimateLabel = new JLabel();
		estimateLabel.setText("Type your estimate here!");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		add(estimateLabel, constraints);

		//create Textfield
		estimateField = new JTextField();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		estimateField.setColumns(10);
		add(estimateField, constraints);
		
	}

	public String getEstimateField() {
		return this.estimateField.getText();
	}
	
	public void displayOldEstimate(Game game, int reqid) {
		System.out.println("--------text set for old estiamte");

		String name = ConfigManager.getInstance().getConfig().getUserName();
		int oldEstimate = game.findEstimate(reqid).getEstimate(name);
		System.out.println("--------old estimate value: " + oldEstimate);
		if(oldEstimate > 0)	{
			estimateField.setText(Integer.toString(oldEstimate));
			System.out.println("--------reached ");

			
		} else {
			estimateField.setText("");
		}
		
	}
	
	

	

}
