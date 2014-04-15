/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Team Code On Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame.VoteActionController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.ListRequirementsPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * This class create a GUI pane that a user can use to submit their estimation to
 * a game.
 * @author Code On Bleu
 * @version 0.1
 */
public class EstimationPane extends JPanel {
	private final JTextArea requirementName;
	private final JTextArea descriptionText;
	private final Box blankBox;
	private final DeckPanel deckPanel;
	private final JLabel message;
	private final JButton voteButton;
	private int reqid;
	private ListRequirementsPanel listReqPanel;
	private Game game;
	
	private Requirement req;
	
	/**
	 * Constructor for panel
	 * @param reqid The req to vote on
	 * @param draftGame The game the vote is going towards
	 */
	public EstimationPane(ListRequirementsPanel listReqPanel){

		this.listReqPanel = listReqPanel;
		
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();

		final JLabel nameLabel = new JLabel("Name:");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 5, 0, 10);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		add(nameLabel, constraints);

		requirementName = new JTextArea();
		requirementName.setEditable(false);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0.66;
		constraints.gridwidth = 1;
		add(requirementName, constraints);

		blankBox = new Box(1);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.weightx = 0.33;
		constraints.gridwidth = 1;
		add(blankBox, constraints);

		final JLabel descriptionLabel = new JLabel("Description:");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(5, 5, 0, 5);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 3;
		add(descriptionLabel, constraints);

		constraints.insets = new Insets(0, 0, 0, 0);

		descriptionText = new JTextArea();

		descriptionText.setEditable(false);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 5, 0, 5);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridwidth = 3;
		add(descriptionText, constraints);

		final JLabel temp = new JLabel();
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


		final JPanel voteButtonPanel = new JPanel();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridwidth = 3;
		constraints.gridheight = 2;
		constraints.insets = new Insets(0, 0, 20, 0);
		constraints.anchor = GridBagConstraints.PAGE_END;
		add(voteButtonPanel, constraints);

		voteButtonPanel.setLayout(new GridBagLayout());

		//error message
		message = new JLabel();
		message.setText("    ");
		message.setFont(new Font("Dialog", Font.ITALIC, 12));
		message.setVisible(true);
		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 0, 50, 0);
		voteButtonPanel.add(message, constraints);
		
		voteButton = new JButton("Vote");
		voteButton.setPreferredSize(new Dimension(140, 40));
		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1.0;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		voteButtonPanel.add(voteButton, constraints);
		voteButton.setEnabled(false);

	}

	/**
	 * Sets the game and requirement for displaying in the deck panel and 
	 * in the requirement information fields, and displays the old
	 * estimate for the user if there is one
	 *
	 * @param reqid the requirement ID
	 * @param game the game
	 */
	public void setGameAndRequirement(int reqid, Game game){
		this.game = game;
		
		voteButton.addActionListener(new VoteActionController(this, game));
		
		voteButton.setEnabled(true);

		this.reqid = reqid;
		try{
			req = getRequirementFromId();
			requirementName.setText(req.getName());
			descriptionText.setText(req.getDescription());
			deckPanel.clearCardsSelected();
			deckPanel.displayOldEstimate(game, reqid);
			message.setText(" ");
		}
		catch(NotFoundException exception){
			this.message.setText("Exception: Requirement Not Found");
		}
		
		deckPanel.setEstimateFieldEditable(true);


	}



	private Requirement getRequirementFromId() throws NotFoundException{
		List<Requirement> reqs = RequirementManagerFacade.getInstance().getPreStoredRequirements();
		for(Requirement req: reqs){
			if(req.getId() == reqid){
				return req;
			}
		}
		throw new NotFoundException("Requirement was not found");
	}

	/**
	 * This method checks if the estimation is of the correct value (An interger) And updates the
	 * display to show an error if the value is wrong. It also checks that a deck is selected in the deck version
	 * @return True if everything is all set, return false if the value is incorrect
	 */
	public boolean checkField() {
		
		if(!deckPanel.hasDeckSelected()) {
			reportError("<html>Error: Select a card.</html>");
			return false;
		}
		
		final int estimate;
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
		
		if(game.getStatus() == GameStatus.ENDED){
			reportError("<html>Error: Game has ended</html>");
			return false;
		}
		return true;
	}



	private void reportError(String string) {
		message.setText(string);
		message.setForeground(Color.RED);
	}

	/**
	 * This method returns the estimate of the requirement. If the value 
	 * is wrong then it updates the display
	 * to show there is an error
	 * @return The value of the estimate, or zero if there is an error
	 */
	public int getEstimate(){
		final int estimate;
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

	/**
	 * This function updates the display to report a success message.
	 */
	public void reportSuccess() {
		message.setText("<html>Success: Vote Updated!</html>");
		message.setForeground(Color.BLUE);

	}

	/**
	 * Refreshes the list requirements panel
	 */
	public void refresh(){
		listReqPanel.refresh();
	}
	
	public DeckPanel getDeckPanel(){
		return deckPanel;
	}

}
