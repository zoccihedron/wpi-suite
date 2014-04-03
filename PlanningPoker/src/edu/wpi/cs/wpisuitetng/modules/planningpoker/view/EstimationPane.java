package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class EstimationPane extends JPanel {
	private JTextField requirementName;
	private JTextField descriptionText;
	private Box blankBox;
	
	public EstimationPane() {
		
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
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0,0,0,0);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0.66;
		constraints.gridwidth = 1;
		add(requirementName, constraints);
		//requirementName.setColumns(10);
		
		// Possibly add weights later
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
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0,5,0,5);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridwidth = 3;
		add(descriptionText, constraints);
		//descriptionText.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 1.0;
		constraints.gridwidth = 3;
		add(scrollPane, constraints);

		JButton voteButton = new JButton("Vote");
		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridwidth = 3;
		constraints.insets = new Insets(0,0,20,0);
		constraints.anchor = GridBagConstraints.PAGE_END;
		add(voteButton, constraints);
	}
	
}
