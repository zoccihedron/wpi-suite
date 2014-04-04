package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.VoteActionController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class EstimationPane extends JPanel {
	private JTextField requirementName;
	private JTextField descriptionText;
	private Box blankBox;
	private DeckPanel deckPanel;
	private JScrollPane  scrollPane;
	
//	public EstimationPane(Requirement req, Estimate/Game) {
	public EstimationPane(Requirement req) {
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		JLabel nameLabel = new JLabel("Name:");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0,5,0,10);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		add(nameLabel, constraints);

		// Possibly add weights later
		requirementName = new JTextField();
		requirementName.setText(req.getName());
		requirementName.setEditable(false);
		//requirementName.setText(req.getName());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0,0,0,0);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0.66;
		constraints.gridwidth = 1;
		add(requirementName, constraints);
		//requirementName.setColumns(10);
		
		blankBox = new Box(1);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0,0,0,0);
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.weightx = 0.33;
		constraints.gridwidth = 1;
		add(blankBox, constraints);
		//requirementName.setColumns(10);
		
		JLabel descriptionLabel = new JLabel("Description:");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(5,5,0,5);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 3;
		add(descriptionLabel, constraints);
		
		constraints.insets = new Insets(0,0,0,0);

		descriptionText = new JTextField();
		descriptionText.setText(req.getDescription());
		descriptionText.setHorizontalAlignment(JTextField.NORTH_WEST);
		descriptionText.setEditable(false);
		//descriptionText.setText(req.getDescription());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0,5,0,5);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridwidth = 3;
		add(descriptionText, constraints);
		//descriptionText.setColumns(10);
		
		JLabel temp = new JLabel();
		temp.setText("temp");
	

		deckPanel = new DeckPanel();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 1.0;
		constraints.gridwidth = 3;
		constraints.weighty = 1.5;
		add(deckPanel, constraints);
		
		

		JButton voteButton = new JButton("Vote");
		voteButton.setPreferredSize(new Dimension(140, 40));
		//voteButton.setFont(new Font(null, 0, 16));
		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weightx = 1.0;
		constraints.weighty = 0.5;
		constraints.gridwidth = 3;
		constraints.insets = new Insets(0,0,20,0);
		constraints.anchor = GridBagConstraints.PAGE_END;
		add(voteButton, constraints);

		voteButton.addActionListener(new VoteActionController(this));
		
		
		
	}


	public boolean checkField() {
		return deckPanel.checkField();
	}
	


	
	//public ??? get???Object(){ 
	
	//public get
	
}
