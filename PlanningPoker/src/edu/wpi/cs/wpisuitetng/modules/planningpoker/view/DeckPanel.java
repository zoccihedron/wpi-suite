/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

/**
 * This class creates the field for entering an
 * estimation for a given requirement. 
 *
 * @author Codon Bleu
 * @version Apr 9, 2014
 */
public class DeckPanel extends JPanel{
	private final JTextField estimateField;
	
	/**
	 * Constructs the DeckPanel
	 *
	 */
	public DeckPanel() {
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		
		//create JLabel and JTextField within scrollPane:
		final JLabel estimateLabel = new JLabel();
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
		return estimateField.getText();
	}
	
	/**
	 * Displays the old estimate made by the user in 
	 * the voting text field.
	 *
	 * @param game the game
	 * @param reqid the requirement ID
	 */
	public void displayOldEstimate(Game game, int reqid) {
		System.out.println("--------text set for old estiamte");

		final String name = ConfigManager.getInstance().getConfig().getUserName();
		final int oldEstimate = game.findEstimate(reqid).getEstimate(name);
		System.out.println("--------old estimate value: " + oldEstimate);
		if(oldEstimate > 0){
			estimateField.setText(Integer.toString(oldEstimate));
			System.out.println("--------reached ");

			
		} else {
			estimateField.setText("");
		}
		
	}
	
	

	

}
