package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	

	
	

	

}
