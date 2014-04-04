package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class DeckPanel extends JScrollPane{
	public JLabel errorMessage;
	

	
	public DeckPanel(){
		JPanel deckPanel = new JPanel();
		
		//create JLabel and JTextField within scrollPane:
		JLabel estimateLabel = new JLabel();
		estimateLabel.setText("Type your estimate here!");
		
		JTextField estimateField = new JTextField();
		
		//error message
		 errorMessage = new JLabel("test");
		//errorMessage.setVisible(true);
		
		add(estimateLabel);
		add(estimateField);
		add(errorMessage);
	}
	

	
	/*
	public boolean checkField() {
		int estimate;
		try{
			estimate = Integer.parseInt(estimateField.getText());
			
		} catch (NumberFormatException e){
			reportError("<html>Error: Estimate must be an integer. </html>");
			return false;
		}
		
		if(estimate <= 0) {
			reportError("<html>Error: Estimate must be an integer greater than 0. </html>");
			return false;
		}
		return true;
	}*/
	
	

	private void reportError(String string) {
		
		
		
	}
	

}
