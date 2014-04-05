package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.VoteActionController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class EstimationPane extends JPanel {
	private JTextArea requirementName;
	private JTextArea descriptionText;
	private Box blankBox;
	private DeckPanel deckPanel;
	private JScrollPane  scrollPane;
	private JLabel errorMessage;
	public Game game;
	public int reqid;
	public Requirement req;
//	public EstimationPane(Requirement req, Estimate/Game) {
	public EstimationPane(int reqid, Game game) {
		
		this.game = game;
		this.reqid = reqid;
		
		req = getRequirementFromId();
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		JLabel nameLabel = new JLabel("Name:");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0,5,0,10);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		add(nameLabel, constraints);

		requirementName = new JTextArea();
		requirementName.setText(req.getName());
		requirementName.setEditable(false);
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

		descriptionText = new JTextArea();
		descriptionText.setText(req.getDescription());
	
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
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 1.0;
		constraints.gridwidth = 3;
		constraints.weighty = 1.5;
		add(deckPanel, constraints);
		
		
		JPanel voteButtonPanel = new JPanel();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridwidth = 3;
		constraints.gridheight = 2;
		constraints.insets = new Insets(0,0,20,0);
		constraints.anchor = GridBagConstraints.PAGE_END;
		add(voteButtonPanel, constraints);
		
		voteButtonPanel.setLayout(new GridBagLayout());
		
		//error message
		errorMessage = new JLabel();
		errorMessage.setText("  ");
		errorMessage.setVisible(true);
		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0,0,50,0);
		voteButtonPanel.add(errorMessage, constraints);
		

		JButton voteButton = new JButton("Vote");
		voteButton.setPreferredSize(new Dimension(140, 40));
		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1.0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0,0,0,0);
		voteButtonPanel.add(voteButton, constraints);

		voteButton.addActionListener(new VoteActionController(this, game));
		
		
		
	}

	private Requirement getRequirementFromId() {
		return RequirementModel.getInstance().getRequirement(reqid);
	}

	public boolean checkField() {
		int estimate;
		try{
			estimate = Integer.parseInt(deckPanel.getEstimateField());
			
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
	}
	
	public int getEstimate(){
		int estimate;
		try{
			estimate = Integer.parseInt(deckPanel.getEstimateField());
			return estimate;
			
		} catch (NumberFormatException e){
			reportError("<html>Error: Estimate must be an integer.</html>");
			
		}
		return 0;
		
	}
	
	public int getReqID(){
		return reqid;
	}

	public void reportSuccess() {
		errorMessage.setText("Success: Vote Updated!");
		errorMessage.setForeground(Color.GREEN);
		// TODO Auto-generated method stub
		
	}




	
	//public ??? get???Object(){ 
	
	//public get
	
}
