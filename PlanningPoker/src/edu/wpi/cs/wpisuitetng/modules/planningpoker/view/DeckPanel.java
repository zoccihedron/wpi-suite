package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class DeckPanel extends JPanel{
	public JLabel errorMessage;
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
		add(estimateField, constraints);
		
		
		//error message
		errorMessage = new JLabel();
		errorMessage.setText("Placeholder Text");
		errorMessage.setVisible(false);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		add(errorMessage, constraints);
	}
	

	
	
	public boolean checkField() {
		int estimate;
		try{
			estimate = Integer.parseInt(estimateField.getText());
			
		} catch (NumberFormatException e){
			reportError("<html>Error: Estimate must be an integer.</html>");
			return false;
		}
		
		if(estimate <= 0) {
			reportError("<html>Error: Estimate must be an integer greater than 0.</html>");
			return false;
		}
		errorMessage.setVisible(false);
		return true;
	}
	
	

	private void reportError(String string) {
		errorMessage.setText(string);
		errorMessage.setForeground(Color.RED);
		errorMessage.setVisible(true);
	}
	

}
