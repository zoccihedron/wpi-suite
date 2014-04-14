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
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ScrollablePanel;

/**
 * This class creates the field for entering an estimation for a given
 * requirement.
 * 
 * @author Codon Bleu
 * @version Apr 9, 2014
 */
public class DeckPanel extends JScrollPane {
	private JTextField estimateField = new JTextField();
	private ImageIcon img = null;
	private final int CARD_WIDTH = 100;
	private final int CARD_HEIGHT = 128;
	private String currentEstimate;

	/**
	 * Constructs the DeckPanel
	 * 
	 */
	public DeckPanel() {
		this.setViewportView(textVersion());
	}

	public String getEstimateField() {
		return estimateField.getText();
	}
	
	/**
	 * changes whether jTextField for estimate is enabled
	 * @param editable
	 */
	public void setEstimateFieldEditable(boolean editable){
		estimateField.setEditable(editable);
	}

	private JPanel textVersion() {
		final ScrollablePanel textPanel = new ScrollablePanel();
		textPanel.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();

		// create JLabel and JTextField within scrollPane:
		final JLabel estimateLabel = new JLabel();
		estimateLabel.setText("Type your estimate here!");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		textPanel.add(estimateLabel, constraints);

		// create Textfield
	    estimateField = new JTextField();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		estimateField.setColumns(10);
		textPanel.add(estimateField, constraints);
		estimateField.setEditable(false);

		return textPanel;
	}

	
	/**
	 * This is a proof of concept Temp GUI there is no functionality programmed in it
	 * However it may be useful as a basis.
	 * @return The panel with clickable cards
	 */
	private JPanel deckVersion() {

		final JPanel deckPanel = new JPanel();
		deckPanel.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();

		// This creates a deck choice
		final ArrayList<Integer> cards = new ArrayList<Integer>();
		cards.add(0);
		cards.add(1);
		cards.add(1);
		cards.add(2);
		cards.add(3);
		cards.add(5);
		cards.add(8);
		try {
			img = new ImageIcon(ImageIO.read(getClass().getResource(
					"cardtemplate.png")));
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

		for (int i = 0; i < cards.size(); i++) {
			JToggleButton cardToAdd = new JToggleButton(Integer.toString(cards
					.get(i)), img);
			cardToAdd.setHorizontalTextPosition(SwingConstants.CENTER);
			cardToAdd.setVerticalTextPosition(SwingConstants.CENTER);
			constraints.fill = GridBagConstraints.NONE;
			constraints.gridx = i;
			constraints.gridy = 2;
			constraints.gridwidth = 1;
			constraints.insets = new Insets(0, 5, 0, 5);
			deckPanel.add(cardToAdd, constraints);
		}

		return deckPanel;
	}

	/**
	 * Displays the old estimate made by the user in the voting text field.
	 * 
	 * @param game
	 *            the game
	 * @param reqid
	 *            the requirement ID
	 */
	public void displayOldEstimate(Game game, int reqid) {
		System.out.println("--------text set for old estiamte");

		final String name = ConfigManager.getInstance().getConfig()
				.getUserName();
		final int oldEstimate = game.findEstimate(reqid).getEstimate(name);
		System.out.println("--------old estimate value: " + oldEstimate);
		if (oldEstimate > 0) {
			estimateField.setText(Integer.toString(oldEstimate));
			currentEstimate = Integer.toString(oldEstimate);
			System.out.println("--------reached ");

		} else {
			estimateField.setText("");
			currentEstimate = "";
		}

	}
	
	public boolean isOldEstimate(){
		return currentEstimate.equals(estimateField.getText());
	}
	
	public void setCurrentEstimate(String estimate){
		currentEstimate = estimate;
	}

}
