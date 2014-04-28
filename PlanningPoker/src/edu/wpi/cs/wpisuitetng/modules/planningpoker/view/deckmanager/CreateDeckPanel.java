package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.JPlaceholderTextField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;


public class CreateDeckPanel extends JPanel{

	private JPlaceholderTextField deckName;
	private JButton btnSubmit;
	private Deck deck;
	private ListDecksPanel listDecksPanel;
	private final String PLACEHOLDER_TEXT = "Enter Deck Name Here";

	public CreateDeckPanel(ListDecksPanel listDecksPanel){

		this.listDecksPanel = listDecksPanel;
		deckName = new JPlaceholderTextField(PLACEHOLDER_TEXT);
		btnSubmit = new JButton("Create Deck");

		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0.75;
		constraints.anchor = constraints.WEST;
		this.add(deckName, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 5, 0, 0);
		constraints.gridwidth = 1;
		constraints.weightx = 0.25;
		constraints.anchor = constraints.EAST;
		this.add(btnSubmit, constraints);
		btnSubmit.setEnabled(false);

		deck = null;

		btnSubmit.addActionListener(new AddDeckController(this, PLACEHOLDER_TEXT, listDecksPanel));
		deckName.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(deckName.getText().trim().equals("") ||
						deckName.getText().trim().equals(PLACEHOLDER_TEXT)){
					btnSubmit.setEnabled(false);
				}
				else{
					btnSubmit.setEnabled(true);
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(deckName.getText().trim().equals("") ||
						deckName.getText().trim().equals(PLACEHOLDER_TEXT)){
					btnSubmit.setEnabled(false);
				}
				else{
					btnSubmit.setEnabled(true);
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				//Intentionally Left Blank
				
			}
		});
	}

	/**
	 * @return the deck
	 */
	public Deck getDeck() {
		return deck;
	}

	/**
	 * @return the name the user wants the text to be
	 */
	public String getDeckName() {
		return deckName.getText();
	}

	public JButton getBtnSubmit() {
		return btnSubmit;
	}
}
