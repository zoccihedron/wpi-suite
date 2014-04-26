package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;

public class CreateDeckPanel extends JPanel{
	JTextField deckName;
	JButton btnSubmit;
	
	Deck deck;
	
	public CreateDeckPanel(){
		deckName = new JTextField("Deck");
		btnSubmit = new JButton("Create Deck");
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0.6;
		constraints.anchor = constraints.WEST;
		this.add(deckName, constraints);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0.4;
		constraints.anchor = constraints.EAST;
		this.add(btnSubmit, constraints);
		
		deck = new Deck(deckName.getText(), false, new ArrayList<Integer>());
		
		btnSubmit.addActionListener(new AddDeckController(this));
		}
	
	/**
	 * @return the deck
	 */
	public Deck getDeck() {
		return deck;
	}
}